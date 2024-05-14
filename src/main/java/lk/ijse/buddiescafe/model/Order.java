package lk.ijse.buddiescafe.model;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String id;
    private String uId;
    private Date date;
    private double amount;
}
