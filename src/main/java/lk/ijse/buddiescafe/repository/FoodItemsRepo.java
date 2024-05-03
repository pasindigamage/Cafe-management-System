package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.FoodItems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodItemsRepo {
    public static boolean save(FoodItems foodItems) throws SQLException {

        String sql ="INSERT INTO FoodItems VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, foodItems.getId());
        pstm.setObject(2, foodItems.getDescription());
        pstm.setObject(3, foodItems.getAmount());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(FoodItems foodItems) throws SQLException {
        String sql ="UPDATE FoodItems set description = ?, amount = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, foodItems.getDescription());
        pstm.setObject(2, foodItems.getAmount());
        pstm.setObject(3, foodItems.getId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM FoodItems WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static FoodItems searchByDescription(String id) throws SQLException {
        String sql = "SELECT * FROM FoodItems WHERE description = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();

        FoodItems foodItems = null;

        if (resultSet.next()) {
            String fid = resultSet.getString(1);
            String description = resultSet.getString(2);
            String amount = resultSet.getString(3);


            foodItems = new FoodItems(fid,description,amount);
        }
        return foodItems;
    }

    public static List<FoodItems> getAll() throws SQLException {
        String sql = "SELECT * FROM FoodItems";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<FoodItems> foodItemsList = new ArrayList<>();
        while (resultSet.next()) {
            String fid = resultSet.getString(1);
            String description = resultSet.getString(2);
            String amount = resultSet.getString(3);

            FoodItems foodItems = new FoodItems(fid,description,amount);
            foodItemsList.add(foodItems);
        }
        return foodItemsList;
    }

}
