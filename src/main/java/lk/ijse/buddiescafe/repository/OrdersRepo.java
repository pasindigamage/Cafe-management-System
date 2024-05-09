package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrdersRepo {
    public static String currentId() throws SQLException {
            String sql = "SELECT id FROM Orders ORDER BY id desc LIMIT 1";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO orderDetails VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, order.getId());
        pstm.setString(2, order.getUserId());
        pstm.setString(3, order.getDate());

        return pstm.executeUpdate() > 0;
    }
}

