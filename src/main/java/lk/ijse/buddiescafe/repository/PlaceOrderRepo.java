package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.PlaceOrder;
import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder po) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            try {
                boolean isOrderSaved = OrdersRepo.save(po.getOrder());
                if (isOrderSaved) {
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    if (isOrderDetailSaved) {
                        boolean isItemQtyUpdate = InventorySupplierDetailRepo.updateQty(po.getOdList());
                        boolean isSupplimentQtyUpdate = AddIngrediansRepo.updateQty(po.getOdList());
                        if (isItemQtyUpdate && isSupplimentQtyUpdate) {
                            connection.commit();
                            return true;
                        }
                    }
                }
                connection.rollback();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    private static void handleSQLException(SQLException e) {
        if (e instanceof com.mysql.cj.jdbc.exceptions.MysqlDataTruncation) {
            // Handle data truncation error
            System.err.println("Data truncation error occurred: " + e.getMessage());
        } else {
            // Handle other SQL exceptions
            System.err.println("An SQL error occurred: " + e.getMessage());
        }
    }
}
