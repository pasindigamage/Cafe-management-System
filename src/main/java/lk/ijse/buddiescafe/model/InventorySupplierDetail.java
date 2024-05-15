package lk.ijse.buddiescafe.model;
import lombok.*;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDetail {
    private String supplierId;
    private String inventoryId;
    private Date date;
}