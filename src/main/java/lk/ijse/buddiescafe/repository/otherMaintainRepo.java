package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.OtherMaintains;
import lk.ijse.buddiescafe.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
