package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.biwenger.PlayerDataRequestDTO;

import java.util.List;

@ToString
public class UserPlayersDTO {
    @Getter @Setter
    String id;
    @Getter @Setter
    String name;
    @Getter @Setter
    String group;
    @Getter @Setter
    String points;
    @Getter @Setter
    String balance;
    @Getter @Setter
    String joinDate;
    @Getter @Setter
    String lineupDate;
    @Getter @Setter
    List<PlayerDataRequestDTO> players;
}
