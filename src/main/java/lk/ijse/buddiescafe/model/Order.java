package lk.ijse.buddiescafe.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String id;
    private String uId;
    private String date;
    private Double amount;
}
