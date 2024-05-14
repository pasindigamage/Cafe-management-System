package lk.ijse.buddiescafe.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodItems {
    private String id;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
}
