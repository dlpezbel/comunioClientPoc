package quintonic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.biwenger.PlayerDataRequestDTO;

import java.util.List;

@ToString
public class LeagueDataDTO extends DataDTO{
    @Getter @Setter
    LeaguePlayersDataDTO data;
}
