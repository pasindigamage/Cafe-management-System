package lk.ijse.buddiescafe.repository;
import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.TM.InventoryTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Inventory WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static Inventory searchByID(String id) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        Inventory inventory = null;

        if (resultSet.next()) {
            String inventoryId = resultSet.getString(1);
            String SupplierId = resultSet.getString(2);
            String description = resultSet.getString(3);
            String unitPrice = resultSet.getString(4);
            String qty = resultSet.getString(5);
            String date = resultSet.getString(6);

            inventory = new Inventory(inventoryId,null,description,unitPrice,qty,date);
        }
        return inventory;
    }


    public static List<InventoryTM> getAll() throws SQLException {
        String sql = " SELECT Inventory.id, Inventory.description, Supplier.name, " +
                "Inventory.date, Inventory.unitPrice, Inventory.qty " +
                "FROM Inventory join Supplier on Inventory.supplierId = Supplier.id;\n";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<InventoryTM> inventoryTMList = new ArrayList<>();
        while (resultSet.next()) {
            String Id = resultSet.getString(1);
            String description = resultSet.getString(2);
            String sName = resultSet.getString(3);
            String date = resultSet.getString(4);
            String unitPrice = resultSet.getString(5);
            String qty = resultSet.getString(6);

            InventoryTM inventoryTM = new InventoryTM(Id,description,sName,unitPrice,qty,date);
            inventoryTMList.add(inventoryTM);
        }
        return inventoryTMList;
    }

    public static Inventory searchByCode(String ingrediansIDValue) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, ingrediansIDValue);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
                    );
        }
        return null;

    }
}