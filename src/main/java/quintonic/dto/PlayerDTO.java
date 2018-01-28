package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PlayerDTO extends DataDTO{
    @Getter @Setter
    PlayerDataDTO data;
}
