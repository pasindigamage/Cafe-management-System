package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.model.PlaceOrder;

import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder po) {
        try {
            boolean isOrderSaved = OrdersRepo.save(po.getOrder());

            if (isOrderSaved && OrderDetailRepo.save1(po.getOdList())) {
                boolean isQtyUpdated = FoodItemsRepo.update1(po.getOdList());

                if (isQtyUpdated) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
