package Simran.BusinessLayer;

import Simran.dataaccesslayer.FuelEnergyLogDAO;
import Simran.transferobject.FuelEnergyLogDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test for FuelEnergyLogBusinessLogic without external libraries.
 * Uses a fake in-memory DAO implementation for testing business rules.
 */
class FuelEnergyLogBusinessLogicTest {

    private FuelEnergyLogBusinessLogic businessLogic;
    private FakeFuelEnergyLogDAO fakeDAO;

    @BeforeEach
    void setUp() {
        fakeDAO = new FakeFuelEnergyLogDAO();

        // Inject fake DAO by overriding methods in an anonymous subclass
        businessLogic = new FuelEnergyLogBusinessLogic() {
            @Override
            public List<FuelEnergyLogDTO> getAll() {
                return fakeDAO.getAll();
            }

            @Override
            public FuelEnergyLogDTO getById(int id) {
                return fakeDAO.getById(id);
            }

            @Override
            public boolean add(FuelEnergyLogDTO log, String role) {
                if (!"OPERATOR".equalsIgnoreCase(role)) return false;
                return fakeDAO.add(log);
            }

            @Override
            public boolean update(FuelEnergyLogDTO log, String role) {
                if (!"OPERATOR".equalsIgnoreCase(role)) return false;
                return fakeDAO.update(log);
            }

            @Override
            public boolean delete(int id, String role) {
                if (!"OPERATOR".equalsIgnoreCase(role)) return false;
                return fakeDAO.delete(id);
            }
        };
    }

    @Test
    void testAddWithOperatorRole() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");

        boolean result = businessLogic.add(log, "OPERATOR");

        assertTrue(result, "Operator should be able to add logs");
        assertEquals(1, fakeDAO.logs.size(), "DAO should contain the new log");
    }

    @Test
    void testAddWithManagerRoleFails() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");

        boolean result = businessLogic.add(log, "MANAGER");

        assertFalse(result, "Manager should not be able to add logs");
        assertTrue(fakeDAO.logs.isEmpty(), "DAO should remain empty");
    }

    @Test
    void testUpdateWithOperatorRole() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");
        fakeDAO.logs.add(log);

        log.setVehicleRef("BUS-456");
        boolean result = businessLogic.update(log, "OPERATOR");

        assertTrue(result, "Operator should be able to update logs");
        assertEquals("BUS-456", fakeDAO.logs.get(0).getVehicleRef());
    }

    @Test
    void testUpdateWithManagerRoleFails() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");

        boolean result = businessLogic.update(log, "MANAGER");

        assertFalse(result, "Manager should not be able to update logs");
    }

    @Test
    void testDeleteWithOperatorRole() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");
        fakeDAO.logs.add(log);

        boolean result = businessLogic.delete(1, "OPERATOR");

        assertTrue(result, "Operator should be able to delete logs");
        assertTrue(fakeDAO.logs.isEmpty(), "Log should be removed from DAO");
    }

    @Test
    void testDeleteWithManagerRoleFails() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");
        fakeDAO.logs.add(log);

        boolean result = businessLogic.delete(1, "MANAGER");

        assertFalse(result, "Manager should not be able to delete logs");
        assertEquals(1, fakeDAO.logs.size(), "Log should remain in DAO");
    }

    @Test
    void testGetById() {
        FuelEnergyLogDTO log = sampleLog(1, "BUS-123");
        fakeDAO.logs.add(log);

        FuelEnergyLogDTO found = businessLogic.getById(1);

        assertNotNull(found, "Log should be found by ID");
        assertEquals("BUS-123", found.getVehicleRef());
    }

    // Utility method to create a sample log
    private FuelEnergyLogDTO sampleLog(int id, String vehicleRef) {
        return new FuelEnergyLogDTO(
                id,
                vehicleRef,
                "Diesel",
                100.0,
                50.0,
                LocalDateTime.now()
        );
    }

    /**
     * Fake in-memory DAO implementation for testing without a real DB.
     */
    static class FakeFuelEnergyLogDAO implements FuelEnergyLogDAO {
        List<FuelEnergyLogDTO> logs = new ArrayList<>();

        @Override
        public List<FuelEnergyLogDTO> getAll() {
            return logs;
        }

        @Override
        public FuelEnergyLogDTO getById(int id) {
            return logs.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
        }

        @Override
        public boolean add(FuelEnergyLogDTO log) {
            return logs.add(log);
        }

        @Override
        public boolean update(FuelEnergyLogDTO log) {
            for (int i = 0; i < logs.size(); i++) {
                if (logs.get(i).getId() == log.getId()) {
                    logs.set(i, log);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean delete(int id) {
            return logs.removeIf(l -> l.getId() == id);
        }
    }
}