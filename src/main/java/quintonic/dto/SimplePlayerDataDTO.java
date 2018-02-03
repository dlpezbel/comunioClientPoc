package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class SimplePlayerDataDTO {
    @Getter @Setter
    String name;
    @Getter @Setter
    String price;
    @Getter @Setter
    Double score;
    @Getter @Setter
    String recommendedAction;
}
