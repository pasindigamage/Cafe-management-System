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
}
