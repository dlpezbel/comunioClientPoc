package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.UserPlayersDTO;

import java.util.List;

@ToString
public class OfferRequestDTO {
    @Getter @Setter
    Integer id;
    @Getter @Setter
    Integer amount;
    @Getter @Setter
    UserPlayersDTO to;
    @Getter @Setter
    UserPlayersDTO from;
    @Getter @Setter
    List<Integer> requestedPlayers;
}
