package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.InventorySupplierDetail;
import lk.ijse.buddiescafe.model.OrderDetail;
import lk.ijse.buddiescafe.model.TM.InventorySupplierDetailTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventorySupplierDetailRepo {
    public static boolean save(InventorySupplierDetail inventoryDetail) throws SQLException {
        String sql = "INSERT INTO inventorySupplier VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, inventoryDetail.getId());
            pstm.setString(2, inventoryDetail.getSupplierId());
            pstm.setString(3, inventoryDetail.getInventoryId());
            pstm.setDouble(4, inventoryDetail.getUnitPrice());  // Corrected to setDouble for unitPrice
            pstm.setInt(5, inventoryDetail.getQty());
            pstm.setDate(6, java.sql.Date.valueOf(String.valueOf(inventoryDetail.getDate())));  // Assuming getDate returns LocalDate
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean update(InventorySupplierDetail inventoryDetail) throws SQLException {
        String sql = "UPDATE inventorySupplier SET supplierId = ?, description = ?, unitPrice = ?, qty = ?, date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, inventoryDetail.getSupplierId());
            pstm.setString(2, inventoryDetail.getInventoryId());
            pstm.setDouble(3, inventoryDetail.getUnitPrice());  // Corrected to setDouble for unitPrice
            pstm.setInt(4, inventoryDetail.getQty());
            pstm.setDate(5, java.sql.Date.valueOf(String.valueOf(inventoryDetail.getDate())));  // Assuming getDate returns LocalDate
            pstm.setString(6, inventoryDetail.getId());
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM inventorySupplier WHERE id = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            return pstm.executeUpdate() > 0;
        }
    }

    public static InventorySupplierDetail searchByID(String id) throws SQLException {
        String sql = "SELECT * FROM inventorySupplier WHERE id = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return new InventorySupplierDetail(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getInt(6)
                            );
                }
            }
        }
        return null;
    }

    public static List<InventorySupplierDetailTM> getAll() throws SQLException {
        String sql = "SELECT inventorySupplier.id, FoodItems.description, Supplier.name," +
                " inventorySupplier.date, inventorySupplier.unitPrice, inventorySupplier.qty" +
                " FROM FoodItems " +
                "Join inventorySupplier " +
                "ON FoodItems.id = inventorySupplier.foodItemId " +
                "JOIN Supplier" +
                " ON inventorySupplier.supplierId = Supplier.id";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {
            List<InventorySupplierDetailTM> inventoryTMList = new ArrayList<>();
            while (resultSet.next()) {
                InventorySupplierDetailTM inventoryTM = new InventorySupplierDetailTM(
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

  /*  public static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "UPDATE inventorySupplier SET qty = qty - ? WHERE inventoryId = ? AND foodItemId = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            for (OrderDetail od : odList) {
                pstm.setInt(1, od.getQty());
                pstm.setString(2, od.getOrderId());
                pstm.setString(3, od.getFoodItemId());
                /*pstm.addBatch();
            }
            int[] affectedRows = pstm.executeBatch();
            for (int affectedRow : affectedRows) {
                if (affectedRow == 0) {
                    return false; // If no rows were affected, return false
                }
            }
            return true; // If all updates were successful, return true
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If any exception occurs, return false
        }
    }
     Query for edition
SELECT (sub1.qty - sub2.multiplied_qty) AS result
FROM (
    SELECT inventorySupplier.qty
    FROM inventorySupplier
    JOIN Inventory ON inventorySupplier.inventoryId = Inventory.id
    JOIN IngrediansDetail ON Inventory.id = IngrediansDetail.inventoryId
    WHERE IngrediansDetail.foodItemId = ?
) AS sub1,
(
    SELECT IngrediansDetail.qty * 2 AS multiplied_qty
    FROM IngrediansDetail
    WHERE IngrediansDetail.foodItemId = ?
) AS sub2;*/

    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM inventorySupplier ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }

    public static InventorySupplierDetail searchByDescription(String ingrediansIDValue) throws SQLException {
        String sql = "SELECT * FROM inventorySupplier WHERE description = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, ingrediansIDValue);
            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    return new InventorySupplierDetail(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getInt(6)
                    );
                }
            }
        }
        return null;
    }

    public static boolean updateQty(List<OrderDetail> odList) throws SQLException{
        for (OrderDetail od : odList) {
            if(!updateQty(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderDetail od) throws SQLException {
        String sql = "SELECT (sub1.qty - sub2.multiplied_qty) AS result\n" +
                "FROM (\n" +
                "    SELECT inventorySupplier.qty\n" +
                "    FROM inventorySupplier\n" +
                "    JOIN Inventory ON inventorySupplier.inventoryId = Inventory.id\n" +
                "    JOIN IngrediansDetail ON Inventory.id = IngrediansDetail.inventoryId\n" +
                "    WHERE IngrediansDetail.foodItemId = ?\n" +
                ") AS sub1,\n" +
                "(\n" +
                "    SELECT IngrediansDetail.qty * 2 AS multiplied_qty\n" +
                "    FROM IngrediansDetail\n" +
                "    WHERE IngrediansDetail.foodItemId = ?\n" +
                ") AS sub2;";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setInt(1, od.getQty());
        pstm.setString(2, od.getFoodItemId());

        return pstm.executeUpdate() > 0;
    }
}