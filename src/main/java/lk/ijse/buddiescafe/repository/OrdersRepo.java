package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.Order;
import lk.ijse.buddiescafe.model.OrderDetail;

import java.sql.*;
import java.util.List;

public class
OrdersRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM Orders ORDER BY id desc LIMIT 1";

        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet resultSet = pstm.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (id, userId, date) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1, order.getId());
            pstm.setString(2, order.getUId());
            pstm.setDate(3, Date.valueOf(order.getDate()));
            return pstm.executeUpdate() > 0;
        }
    }
}
