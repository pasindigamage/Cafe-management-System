package lk.ijse.buddiescafe.repository;
import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.KitchenWare;
import lk.ijse.buddiescafe.model.KitchenWareMaintains;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<KitchenWareMaintains> getAll() throws SQLException {
        String sql = "select * from kitchenWareMaintain";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<KitchenWareMaintains> kitchenWareMaintainsList = new ArrayList<>();
        while (resultSet.next()) {
            String Id = resultSet.getString(1);
            String kwId = resultSet.getString(2);
            String description = resultSet.getString(3);
            String date = resultSet.getString(4);
            String amount = resultSet.getString(5);

            KitchenWareMaintains kitchenWareMaintains = new KitchenWareMaintains(Id,kwId,description,date,amount);
            kitchenWareMaintainsList.add(kitchenWareMaintains);
        }
        return kitchenWareMaintainsList;
    }
}
