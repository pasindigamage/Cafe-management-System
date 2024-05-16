package lk.ijse.buddiescafe.model;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OtherMaintains {
    private String id;
    private String description;
    private Date date;
    private String amount;
}
