package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.Inventory;
import lk.ijse.buddiescafe.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepo {
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

    public static boolean save(Inventory inventory) throws SQLException {

        String sql ="INSERT INTO Inventory VALUES(?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, inventory.getId());
        pstm.setObject(2, inventory.getDescription());

        return pstm.executeUpdate() > 0;
    }

    public static Inventory searchByDescription(String description) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE description = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, description);
        ResultSet resultSet = pstm.executeQuery();

        Inventory inventory = null;

        if (resultSet.next()) {
            String fid = resultSet.getString(1);
            String iDscription = resultSet.getString(2);

            inventory = new Inventory(fid,description);
        }
        return inventory;

    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Inventory WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Inventory inventory) throws SQLException {
        String sql ="UPDATE Inventory set description = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, inventory.getDescription());

        return pstm.executeUpdate() > 0;

    }

    public static List<Inventory> getAll() throws SQLException {
        String sql = "SELECT * FROM Inventory";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Inventory> inventoryList = new ArrayList<>();
        while (resultSet.next()) {
            String fid = resultSet.getString(1);
            String description = resultSet.getString(2);

            Inventory inventory = new Inventory(fid,description);
            inventoryList.add(inventory);
        }
        return inventoryList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT description FROM Inventory";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> IdList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public static Inventory searchByCode(String sid) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, sid);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Inventory(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }
}
