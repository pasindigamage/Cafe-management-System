package lk.ijse.buddiescafe.repository;
import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.KitchenWare;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KitchenWareRepo {
    public static boolean save(KitchenWare kitchenWare) throws SQLException {
        String sql ="INSERT INTO KitchenWare VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, kitchenWare.getId());
        pstm.setObject(2, kitchenWare.getSupplierId());
        pstm.setObject(3, kitchenWare.getDescription());
        pstm.setObject(4, kitchenWare.getQty());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM KitchenWare WHERE id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }


    public static boolean update(KitchenWare kitchenWare) throws SQLException {
        String sql = "UPDATE KitchenWare set supplierId = ?, description = ?,qty = ? where id =? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, kitchenWare.getSupplierId());
        pstm.setObject(2, kitchenWare.getDescription());
        pstm.setObject(3, kitchenWare.getQty());
        pstm.setObject(4, kitchenWare.getId());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT description FROM KitchenWare";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> IdList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while(resultSet.next()) {
            IdList.add(resultSet.getString(1));
        }
        return IdList;
    }

    public static KitchenWare searchByCode(String kid) throws SQLException {
        String sql = "SELECT * FROM KitchenWare WHERE description = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, kid);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new KitchenWare(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

}
