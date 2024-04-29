package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Employee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
        String sql ="INSERT INTO Employee VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getPosition());
        pstm.setObject(4, employee.getAddress());
        pstm.setObject(5, employee.getEmail());
        pstm.setObject(6, employee.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql ="UPDATE employee set name = ?, position = ?, email = ?, contact = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, employee.getName());
        pstm.setObject(2, employee.getPosition());
        pstm.setObject(3, employee.getEmail());
        pstm.setObject(4, employee.getContact());
        pstm.setObject(5, employee.getId());

        return pstm.executeUpdate() > 0;
    }

}
