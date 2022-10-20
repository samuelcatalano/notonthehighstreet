package notonthehighstreet.model;

import lombok.*;
import notonthehighstreet.model.base.BaseModel;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends BaseModel {

    private String code;
    private String name;
    private BigDecimal price;

}
