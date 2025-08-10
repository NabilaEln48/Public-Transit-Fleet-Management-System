/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package ZhiruXie.Utility;

import java.time.LocalDateTime;
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
public class TimestampFormatterTest {
    
    public TimestampFormatterTest() {
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
     * Test of format method, of class TimestampFormatter.
     */
    @Test
    public void testFormat() {
        System.out.println("format");
        LocalDateTime raw = LocalDateTime.of(2025,8,6,18,0,0);
        String expResult = "2025-08-06 18:00";
        String result = TimestampFormatter.format(raw);
        assertEquals(expResult, result);
    }
    
}
