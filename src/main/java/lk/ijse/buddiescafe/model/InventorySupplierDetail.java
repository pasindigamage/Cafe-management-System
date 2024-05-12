package lk.ijse.buddiescafe.model;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventorySupplierDetail {
    private String id;
    private String supplierId;
    private String description;
    private Double unitPrice;
    private int qty;
    private String date;

    }

