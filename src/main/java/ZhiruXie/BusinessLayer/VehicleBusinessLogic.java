
package ZhiruXie.BusinessLayer;

import ZhiruXie.DTO.VehicleDTO;
import ZhiruXie.DataAccessLayer.VehicleDAO;
import ZhiruXie.DataAccessLayer.VehicleDAOImp;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class VehicleBusinessLogic {
    private VehicleDAO dao = null;
    Connection connection;
    
    public VehicleBusinessLogic() {dao = new VehicleDAOImp(connection);}
    
    public List<VehicleDTO> getAll() throws SQLException {
        return dao.getAll();
    }

    public VehicleDTO getSingleById(String vehicleId) throws SQLException {
        return dao.getVehicleById(vehicleId);
    }

    public boolean add(VehicleDTO vehicle) throws SQLException {
        return dao.add(vehicle);
    }

    public boolean update(VehicleDTO vehicle) throws SQLException {
        return dao.update(vehicle);
    }

    public boolean delete(String vehicleId) throws SQLException {
        return dao.delete(vehicleId);
    }
}

