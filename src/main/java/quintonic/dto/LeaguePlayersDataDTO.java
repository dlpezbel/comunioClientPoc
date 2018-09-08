package quintonic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaguePlayersDataDTO {
    @Getter
    @Setter
    @JsonProperty("players")
    private Map<String, PlayerDataDTO> players;


    @Getter @Setter
    String id;

}
