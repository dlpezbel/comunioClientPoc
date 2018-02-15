package quintonic.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.ClauseDTO;
import quintonic.dto.DataDTO;
import quintonic.dto.TeamDTO;

import java.util.List;

@ToString
public class FullPlayerDataResponseDTO extends DataDTO{
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
    String owner;
    @Getter @Setter
    Integer clause;
    @Getter @Setter
    Double averageFitnessScore;
    @Getter @Setter
    Double averagePriceScore;
    @Getter @Setter
    Double priceIndicatorScore;
    @Getter @Setter
    Double matchesPlayedScore;
    @Getter @Setter
    Double score;
    @Getter @Setter
    String recommendedAction;
}
