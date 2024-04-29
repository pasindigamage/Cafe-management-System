package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierRepo {
    public static boolean save(Supplier supplier) throws SQLException {
        String sql ="INSERT INTO Supplier VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, supplier.getId());
        pstm.setObject(2, supplier.getNic());
        pstm.setObject(3, supplier.getName());
        pstm.setObject(4, supplier.getCompanyAddress());
        pstm.setObject(5, supplier.getEmail());
        pstm.setObject(6, supplier.getContact());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql ="UPDATE Supplier set nic = ?, name = ?, companyAddress = ?, email = ?, contact = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, supplier.getNic());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getCompanyAddress());
        pstm.setObject(4, supplier.getEmail());
        pstm.setObject(5, supplier.getContact());
        pstm.setObject(6, supplier.getId());

        return pstm.executeUpdate() > 0;
    }
}
