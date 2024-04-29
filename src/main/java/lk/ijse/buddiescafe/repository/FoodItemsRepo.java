package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.FoodItems;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodItemsRepo {
    public static boolean save(FoodItems foodItems) throws SQLException {

        String sql ="INSERT INTO FoodItems VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, foodItems.getId());
        pstm.setObject(2, foodItems.getDescription());
        pstm.setObject(3, foodItems.getAmount());

        return pstm.executeUpdate() > 0;
    }
}
