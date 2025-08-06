/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ZhiruXie.BusinessLayer;

import java.util.List;

public interface BusinessLogic<T> {
    List<T> getAll(int... params);
    T getSingleById(Object... params);
    boolean add(Object... params);
    boolean update(Object... params);
    boolean delete(Object... params);
}
