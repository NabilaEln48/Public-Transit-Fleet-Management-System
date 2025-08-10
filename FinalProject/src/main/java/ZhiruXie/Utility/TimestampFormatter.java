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

/** This class provides methods for formatting LocalDateTime content.
 * @author Zhiru Xie
 * @since JDK21
 * @version 1.0
 * @see ZhiruXie.Utility
 */
public class TimestampFormatter {
    /** Formats a raw LocalDateTime instance to remove special characters and extra spaces.
     * @param raw A LocalDateTime instance
     * @return A processed time String without special characters
    */
    public static String format(LocalDateTime raw){
        return raw.toString().replace("T", " ");
    }
}
