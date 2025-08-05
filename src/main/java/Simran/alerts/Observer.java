/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simran.alerts;

/**
 *
 * @author simra
 */

public interface Observer {
    void update(String alertCategory, String vehicleRef, String alertMessage, String severity);
}