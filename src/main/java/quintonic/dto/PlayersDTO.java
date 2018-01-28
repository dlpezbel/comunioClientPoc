package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class PlayersDTO extends DataDTO{
    @Getter @Setter
    List<PlayerDataDTO> data;
}
