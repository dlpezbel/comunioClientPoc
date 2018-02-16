package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import quintonic.dto.PlayerDataDTO;
import quintonic.dto.UserPlayersDTO;

public class TransferRequestDTO {
    @Getter @Setter
    PlayerDataDTO player;
    @Getter @Setter
    UserPlayersDTO from;
    @Getter @Setter
    UserPlayersDTO to;
    @Getter @Setter
    Integer amount;
}
