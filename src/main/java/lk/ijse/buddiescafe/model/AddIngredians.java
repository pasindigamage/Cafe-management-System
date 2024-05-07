package lk.ijse.buddiescafe.model;
import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AddIngredians {
    String inventoryId;
    String foodItemId;
    int qty;
    private JFXButton btnRemove;
}


