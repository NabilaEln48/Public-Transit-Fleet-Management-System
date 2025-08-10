/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ZhiruXie.Utility;

import ZhiruXie.BusinessLayer.BusinessLogic;
import ZhiruXie.BusinessLayer.VehicleBusinessLogic;
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
public class SimpleBusinessLogicFactoryTest {
    
    public SimpleBusinessLogicFactoryTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getBusinessLogic method, of class SimpleBusinessLogicFactory.
     */
    @Test
    public void testGetBusinessLogic() {
        System.out.println("getBusinessLogic");
        String type = "vehicle";
        BusinessLogic expResult = SimpleBusinessLogicFactory.getBusinessLogic(type);
        assertEquals(expResult, expResult);
    }
    
}
