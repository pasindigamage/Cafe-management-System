package lk.ijse.buddiescafe.model;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private String id;
    private String supplierId;
    private String description;
    private String unitPrice;
    private String qty;
    private String date;
}
