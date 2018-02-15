package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.DataDTO;

@ToString
public class PlayerRequestDTO extends DataDTO {
    @Getter @Setter
    PlayerDataRequestDTO data;
}
