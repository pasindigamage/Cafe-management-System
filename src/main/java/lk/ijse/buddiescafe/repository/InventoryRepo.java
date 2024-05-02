package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM Inventory ORDER BY id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Inventory inventory) throws SQLException {
        String sql ="INSERT INTO Inventory VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, inventory.getId());
        pstm.setObject(2, inventory.getSupplierId());
        pstm.setObject(3, inventory.getDescription());
        pstm.setObject(4, inventory.getUnitPrice());
        pstm.setObject(5, inventory.getQty());
        pstm.setObject(6, inventory.getDate());

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(Inventory inventory) throws SQLException {
        String sql = "UPDATE Inventory set supplierId = ?, description = ?, unitPrice = ?, qty = ?, date = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, inventory.getSupplierId());
        pstm.setObject(2, inventory.getDescription());
        pstm.setObject(3, inventory.getUnitPrice());
        pstm.setObject(4, inventory.getQty());
        pstm.setObject(5, inventory.getDate());
        pstm.setObject(6, inventory.getId());

        return pstm.executeUpdate() > 0;
    }
}
