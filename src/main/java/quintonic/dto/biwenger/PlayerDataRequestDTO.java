package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.ClauseDTO;
import quintonic.dto.TeamDTO;

import java.util.List;

@ToString
public class PlayerDataRequestDTO {
    @Getter @Setter
    String id;
    @Getter @Setter
    String name;
    @Getter @Setter
    String slug;
    @Getter @Setter
    String position;
    @Getter @Setter
    String price;
    @Getter @Setter
    String status;
    @Getter @Setter
    Integer priceIncrement;
    @Getter @Setter
    TeamDTO team;
    @Getter @Setter
    List<String> fitness;
    @Getter @Setter
    Integer points;
    @Getter @Setter
    Integer playedHome;
    @Getter @Setter
    Integer playedAway;
    @Getter @Setter
    Integer pointsHome;
    @Getter @Setter
    Integer pointsAway;
    @Getter @Setter
    Integer pointsLastSeason;
    @Getter @Setter
    ClauseDTO owner;
}
