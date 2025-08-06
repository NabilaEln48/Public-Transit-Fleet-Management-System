/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ZhiruXie.BusinessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.util.List;


public class VehicleBusinessLogic implements BusinessLogic<VehicleDTO> {
    private VehicleDAO dao = null;
    
    public VehicleBusinessLogic() {dao = new VehicleDAOImp();}
    
    @Override
    public List<VehicleDTO> getAll(int... params) {
        return dao.getAll();
    }

    @Override
    public VehicleDTO getSingleById(Object... params) {
        return dao.getSingleById(params[0].toString());
    }
    
    @Override
    public boolean add(Object... params) {
        return dao.add((VehicleDTO)params[0]);
    }
    
    @Override
    public boolean update(Object... params) {
        return dao.update((VehicleDTO)params[0]);
    }
    
    @Override
    public boolean delete(Object... params) {
        return dao.delete(params[0].toString());
    }
}
