/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ZhiruXie.DTO;

import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 61963
 */
public class VehicleDTOTest {
    
    public VehicleDTOTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    /**
     * Test of getId method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetId() {
        System.out.println("testGetId");
        final String TESTVEHICLEID = "BUS001";
        VehicleDTO instance = new VehicleDTO(TESTVEHICLEID);
        String expResult = TESTVEHICLEID;
        System.out.println("Result: " + expResult);
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCategory method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetCategory() {
        System.out.println("getCategory");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        Enum_VehicleCategory expResult = Enum_VehicleCategory.DieselBus;
        Enum_VehicleCategory result = instance.getCategory();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRegistrationNumber method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetRegistrationNumber() {
        System.out.println("getRegistrationNumber");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        String expResult = "reg01";
        String result = instance.getRegistrationNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFuelUsed method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetFuelUsed() {
        System.out.println("getFuelUsed");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        String expResult = "10L";
        String result = instance.getFuelUsed();
        assertEquals(expResult, result);
    }

    /**
     * Test of getEfficiencyRate method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetEfficiencyRate() {
        System.out.println("getEfficiencyRate");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        double expResult = 10.5;
        double result = instance.getEfficiencyRate();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getCapacity method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetCapacity() {
        System.out.println("getCapacity");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        int expResult = 100;
        int result = instance.getCapacity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAssigned_route method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetAssigned_route() {
        System.out.println("getAssigned_route");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        String expResult = "Route A";
        String result = instance.getAssigned_route();
        assertEquals(expResult, result);
    }

    /**
     * Test of getOperationalState method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testGetOperationalState() {
        System.out.println("getOperationalState");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        Enum_VehicleOperationalState expResult = Enum_VehicleOperationalState.Active;
        Enum_VehicleOperationalState result = instance.getOperationalState();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCategory method, of class VehicleDTO.
     */
    @org.junit.jupiter.api.Test
    public void testSetCategory() {
        System.out.println("setCategory");
        VehicleDTO instance = new VehicleDTO("v01",Enum_VehicleCategory.DieselBus,"reg01","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
        Enum_VehicleOperationalState expResult = Enum_VehicleOperationalState.Active;
        Enum_VehicleOperationalState result = instance.getOperationalState();
        assertEquals(expResult, result);
    }
}
