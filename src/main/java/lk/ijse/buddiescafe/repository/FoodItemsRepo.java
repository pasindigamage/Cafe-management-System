package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Employee;
import lk.ijse.buddiescafe.model.FoodItems;
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.OrderDetail;

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

    public static FoodItems searchByCode(String foodItemDValue) throws SQLException {
        String sql = "SELECT * FROM FoodItems WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, foodItemDValue);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new FoodItems(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT description FROM FoodItems";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> IdList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public static boolean updateQty(List<OrderDetail> odList) {
        for (OrderDetail od : odList) {
            if(!updateQty((List<OrderDetail>) od)) {
                return false;
            }
        }
        return true;

    }
}
