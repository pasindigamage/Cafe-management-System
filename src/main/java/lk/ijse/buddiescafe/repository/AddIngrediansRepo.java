package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.AddIngredians;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddIngrediansRepo {

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

    public static List<String> getInvenIds() throws SQLException {
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

    public static boolean save(AddIngredians addIngredians) throws SQLException {
        String sql ="INSERT INTO IngrediansDetail VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, addIngredians.getInventoryId());
        pstm.setObject(2, addIngredians.getFoodItemId());
        pstm.setObject(3, addIngredians.getQty());
        return pstm.executeUpdate() > 0;
    }

    public static boolean update(AddIngredians addIngredians) throws SQLException {
        String sql = "UPDATE IngrediansDetail set inventoryId = ?, qty = ? where foodItemId =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, addIngredians.getInventoryId());
        pstm.setObject(2, addIngredians.getQty());
        pstm.setObject(3, addIngredians.getFoodItemId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM  IngrediansDetail WHERE foodItemId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
}
