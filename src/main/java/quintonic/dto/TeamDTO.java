package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TeamDTO {
    @Getter @Setter
    String id;
    @Getter @Setter
    String name;
}
