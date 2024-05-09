package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.db.DbConnection;
import lk.ijse.buddiescafe.model.OrderDetail;
import lk.ijse.buddiescafe.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
                Connection connection = DbConnection.getInstance().getConnection();
                connection.setAutoCommit(false);

                try {
                    boolean isOrderSaved = OrdersRepo.save(po.getOrder());
                    if (isOrderSaved) {
                        boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                        if (isOrderDetailSaved) {
                            boolean isItemQtyUpdate = FoodItemsRepo.updateQty((List<OrderDetail>) po);
                            if (isItemQtyUpdate) {
                                connection.commit();
                                return true;
                            }
                        }
                    }
                    connection.rollback();
                    return false;
                } catch (Exception e) {
                    connection.rollback();
                    return false;
                } finally {
                    connection.setAutoCommit(true);
                }
            }

    }
