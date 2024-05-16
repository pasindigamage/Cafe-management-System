package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.OtherMaintains;
import lk.ijse.buddiescafe.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class otherMaintainRepo {
    public static boolean save(OtherMaintains otherMaintains) throws SQLException {
        String sql ="INSERT INTO otherMaintain VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, otherMaintains.getId());
        pstm.setObject(2, otherMaintains.getDescription());
        pstm.setObject(3, otherMaintains.getDate());
        pstm.setObject(4, otherMaintains.getAmount());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(OtherMaintains otherMaintains) throws SQLException {
        String sql ="UPDATE otherMaintain set description = ?, date = ?, amount = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, otherMaintains.getDescription());
        pstm.setObject(2, otherMaintains.getDate());
        pstm.setObject(3, otherMaintains.getAmount());
        pstm.setObject(4, otherMaintains.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM otherMaintain WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static List<OtherMaintains> getAll() throws SQLException {
        String sql = "SELECT * FROM otherMaintain";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<OtherMaintains> otherMaintainsList = new ArrayList<>();
        while (resultSet.next()) {
            String omid = resultSet.getString(1);
            String omdescription = resultSet.getString(2);
            Date date = Date.valueOf(resultSet.getString(3));
            String amount = resultSet.getString(4);

            OtherMaintains otherMaintains = new OtherMaintains(omid,omdescription,date,amount);
            otherMaintainsList.add(otherMaintains);
        }
        return otherMaintainsList;

    }

    public static String currentId() throws SQLException {
        String sql = "SELECT id FROM otherMaintain ORDER BY id desc LIMIT 1";

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
