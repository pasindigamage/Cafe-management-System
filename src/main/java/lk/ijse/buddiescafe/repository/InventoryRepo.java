package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.OrderDetail;
import lk.ijse.buddiescafe.model.TM.InventoryTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {
    public static boolean save(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO Inventory VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, inventory.getId());
            pstm.setString(2, inventory.getSupplierId());
            pstm.setString(3, inventory.getDescription());
            pstm.setDouble(4, inventory.getUnitPrice());  // Corrected to setDouble for unitPrice
            pstm.setInt(5, inventory.getQty());
            pstm.setDate(6, java.sql.Date.valueOf(String.valueOf(inventory.getDate())));  // Assuming getDate returns LocalDate
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean update(Inventory inventory) throws SQLException {
        String sql = "UPDATE Inventory SET supplierId = ?, description = ?, unitPrice = ?, qty = ?, date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, inventory.getSupplierId());
            pstm.setString(2, inventory.getDescription());
            pstm.setDouble(3, inventory.getUnitPrice());  // Corrected to setDouble for unitPrice
            pstm.setInt(4, inventory.getQty());
            pstm.setDate(5, java.sql.Date.valueOf(String.valueOf(inventory.getDate())));  // Assuming getDate returns LocalDate
            pstm.setString(6, inventory.getId());
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Inventory WHERE id = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            return pstm.executeUpdate() > 0;
        }
    }

    public static Inventory searchByID(String id) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE description = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return new Inventory(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getInt(5),
                            resultSet.getString(6) // Convert java.sql.Date to LocalDate
                    );
                }
            }
        }
        return null;
    }

    public static List<InventoryTM> getAll() throws SQLException {
        String sql = "SELECT Inventory.id, Inventory.description, Inventory.date, Inventory.unitPrice, Inventory.qty, Supplier.name " +
                "FROM Inventory JOIN Supplier ON Inventory.supplierId = Supplier.id";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            List<InventoryTM> inventoryTMList = new ArrayList<>();
            while (resultSet.next()) {
                InventoryTM inventoryTM = new InventoryTM(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),  // Assuming getDate returns LocalDate
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                inventoryTMList.add(inventoryTM);
            }
            return inventoryTMList;
        }
    }

    public static boolean updateQty(List<OrderDetail> odList) {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            String sql = "UPDATE Inventory SET qty = qty - ? WHERE id = ?";
            try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                for (OrderDetail od : odList) {
                    pstm.setInt(1, od.getQty());
                    pstm.setString(2, od.getItemCode());
                    pstm.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM Inventory ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
}
