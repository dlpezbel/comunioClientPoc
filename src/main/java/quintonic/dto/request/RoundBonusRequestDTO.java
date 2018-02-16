package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import quintonic.dto.UserPlayersDTO;

public class RoundBonusRequestDTO {
    @Getter @Setter
    UserPlayersDTO user;
    @Getter @Setter
    Integer bonus;
}
