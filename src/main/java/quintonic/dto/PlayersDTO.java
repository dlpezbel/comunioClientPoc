package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.biwenger.PlayerDataRequestDTO;

import java.util.List;

@ToString
public class PlayersDTO extends DataDTO{
    @Getter @Setter
    List<PlayerDataRequestDTO> data;
}
