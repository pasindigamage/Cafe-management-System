package lk.ijse.buddiescafe.model;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlaceOrder {
        private Order order;
        private List<OrderDetail> odList;
    }