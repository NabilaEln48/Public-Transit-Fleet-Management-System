/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ZhiruXie.DataAccessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.enums.Enum_VehicleCategory;
import ZhiruXie.enums.Enum_VehicleOperationalState;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 61963
 */
public class VehicleDAOImpTest {
    
    public VehicleDAOImpTest() {
    }
    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }
//    
//    /**
//     * Test of delete method, of class VehicleDAOImp.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        String vehicleId = "v04";
//        VehicleDAOImp instance = new VehicleDAOImp();
//        boolean expResult = true;
//        boolean result = instance.delete(vehicleId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of add method, of class VehicleDAOImp.
//     */
//    @Test
//    public void testAdd() {
//        System.out.println("add");
//        VehicleDTO vehicle = new VehicleDTO("v04",Enum_VehicleCategory.DieselBus,"reg04","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
//        VehicleDAOImp instance = new VehicleDAOImp();
//        boolean expResult = true;
//        boolean result = instance.add(vehicle);
//        assertEquals(expResult, result);
//    }
//    
//    /**
//     * Test of getSingleById method, of class VehicleDAOImp.
//     */
//    @Test
//    public void testGetSingleById() {
//        System.out.println("getSingleById");
//        String vehicleId = "v02";
//        VehicleDAOImp instance = new VehicleDAOImp();
//        VehicleDTO expResult = new VehicleDTO("v02",Enum_VehicleCategory.DieselBus,"reg022","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
//        VehicleDTO result = instance.getSingleById(vehicleId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of update method, of class VehicleDAOImp.
//     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        VehicleDTO updatedVehicle = new VehicleDTO("v02",Enum_VehicleCategory.DieselBus,"reg222","10L",10.5,100,"Route A",Enum_VehicleOperationalState.Active);
//        VehicleDAOImp instance = new VehicleDAOImp();
//        boolean expResult = true;
//        boolean result = instance.update(updatedVehicle);
//        assertEquals(expResult, result);
//    }
    
}
