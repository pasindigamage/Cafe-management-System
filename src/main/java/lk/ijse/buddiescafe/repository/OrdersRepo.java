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
        String sql = "INSERT INTO Orders  VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setObject(1, order.getId());
            pstm.setObject(2, order.getUId());
            pstm.setObject(3, order.getDate());
            pstm.setObject(4, order.getAmount());

            return pstm.executeUpdate() > 0;
        }
    }
}
