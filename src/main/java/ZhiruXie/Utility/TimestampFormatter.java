/*
 * Assessment: Project Group
 * Course Id: CST8288 OOP with Design Patterns
 * Section: 012
 * Student Name: Zhiru Xie
 * Student Id: 041143904
 * Professor Name: Teddy Yap
 */
package ZhiruXie.Utility;

import java.time.LocalDateTime;

/**
 *
 * @author 61963
 */
public class TimestampFormatter {
    public static String format(LocalDateTime raw){
        return raw.toString().replace("T", " ");
    }
}
