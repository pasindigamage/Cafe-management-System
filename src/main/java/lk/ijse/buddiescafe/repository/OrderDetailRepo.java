package lk.ijse.buddiescafe.repository;

import lk.ijse.buddiescafe.model.OrderDetail;

import java.util.List;

public class OrderDetailRepo {
    public static boolean save(List<OrderDetail> odList) {
        for (OrderDetail od : odList) {
            if(!save((List<OrderDetail>) od)) {
                return false;
            }
        }
        return true;
    }
}
