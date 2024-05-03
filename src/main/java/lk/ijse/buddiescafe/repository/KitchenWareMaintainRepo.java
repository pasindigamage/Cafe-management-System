package lk.ijse.buddiescafe.repository;
import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.KitchenWareMaintains;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KitchenWareMaintainRepo {

    public static boolean save(KitchenWareMaintains kitchenWareMaintains) throws SQLException {
        String sql ="INSERT INTO kitchenWareMaintain VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, kitchenWareMaintains.getId());
        pstm.setObject(2, kitchenWareMaintains.getKitchenWareId());
        pstm.setObject(3, kitchenWareMaintains.getDescription());
        pstm.setObject(4, kitchenWareMaintains.getDate());
        pstm.setObject(5, kitchenWareMaintains.getAmount());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM kitchenWareMaintain WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(KitchenWareMaintains kitchenWareMaintains) throws SQLException {
        String sql = "UPDATE kitchenWareMaintain set kitchenWareId = ?, description = ?, amount = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, kitchenWareMaintains.getKitchenWareId());
        pstm.setObject(2, kitchenWareMaintains.getDescription());
        pstm.setObject(3, kitchenWareMaintains.getAmount());
        pstm.setObject(4, kitchenWareMaintains.getId());
        return pstm.executeUpdate() > 0;

    }
}
