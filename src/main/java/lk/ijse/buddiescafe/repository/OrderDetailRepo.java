package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailRepo {
    public static boolean save1(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            if(!save(od)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderDetail od) throws SQLException {
        String sql = "INSERT INTO orderDetails  VALUES (?, ?, ?)"; // Specify column names explicitly
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, od.getOrderId());
        pstm.setObject(2, od.getFoodItemId());
        pstm.setObject(3, od.getQty());

        return pstm.executeUpdate() > 0;
    }

}
