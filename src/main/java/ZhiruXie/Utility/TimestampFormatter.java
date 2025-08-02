/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
