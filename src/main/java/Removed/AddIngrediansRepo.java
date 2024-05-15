package Removed;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddIngrediansRepo {

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT description FROM FoodItems";
        List<String> IdList = new ArrayList<>();

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                IdList.add(resultSet.getString(1));
            }
        }
        return IdList;
    }

    public static List<String> getInvenIds() throws SQLException {
        String sql = "SELECT description FROM Inventory";
        List<String> IdList = new ArrayList<>();

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            while (resultSet.next()) {
                IdList.add(resultSet.getString(1));
            }
        }

        return IdList;
    }

    public static boolean save(AddIngredians addIngredians) throws SQLException {
        String sql = "INSERT INTO IngrediansDetail (id, inventoryId, foodItemId, qty) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, addIngredians.getId());
        pstm.setObject(2, addIngredians.getInventoryId());
        pstm.setObject(3, addIngredians.getFoodItemId());
        pstm.setObject(4, addIngredians.getQty());
        return pstm.executeUpdate() > 0;
    }

    public static boolean update(AddIngredians addIngredians) throws SQLException {
        String sql = "UPDATE IngrediansDetail SET foodItemId = ?, inventoryId = ?, qty = ? WHERE id = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setObject(1, addIngredians.getFoodItemId());
            pstm.setObject(2, addIngredians.getInventoryId());
            pstm.setObject(3, addIngredians.getQty());
            pstm.setObject(4, addIngredians.getId());
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM IngrediansDetail WHERE id = ?";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setObject(1, id);
            return pstm.executeUpdate() > 0;
        }
    }

    public static List<AddIngredians> getAll() throws SQLException {
        String sql = " select IngrediansDetail.id, FoodItems.description, " +
                "Inventory.description, IngrediansDetail.qty " +
                "from FoodItems " +
                "join IngrediansDetail " +
                "on FoodItems.id = IngrediansDetail.foodItemId " +
                "join Inventory " +
                "on IngrediansDetail.inventoryId = Inventory.id";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<AddIngredians> addIngrediansList = new ArrayList<>();
        while (resultSet.next()) {
            String Id = resultSet.getString(1);
            String fid = resultSet.getString(2);
            String iid = resultSet.getString(3);
            String qty = resultSet.getString(4);

            AddIngredians addIngredians = new AddIngredians(Id,fid,iid,qty);
            addIngrediansList.add(addIngredians);
        }
        return addIngrediansList;

    }

    public static boolean updateQty(List<OrderDetail> odList) {
        try (Connection connection = DbConnection.getInstance().getConnection()) {
            for (OrderDetail od : odList) {
                AddIngredians addIngredians = getIngredientByFoodItemId(od.getFoodItemId());
                if (addIngredians != null) {
                    String sql = "UPDATE IngrediansDetail SET qty = qty - ? WHERE inventoryId = ?";
                    try (PreparedStatement pstm = connection.prepareStatement(sql)) {
                        pstm.setInt(1, od.getQty());
                        pstm.setString(2, addIngredians.getId());
                        pstm.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static AddIngredians getIngredientByFoodItemId(String foodItemId) throws SQLException {
        String sql = "SELECT * FROM IngrediansDetail WHERE foodItemId = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, foodItemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new AddIngredians(
                            resultSet.getString("id"),
                            resultSet.getString("inventoryId"),
                            resultSet.getString("foodItemId"),
                            resultSet.getString("qty")
                    );
                }
            }
        }
        return null;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM IngrediansDetail ORDER BY id desc LIMIT 1";

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
