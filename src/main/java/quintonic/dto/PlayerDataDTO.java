package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@ToString
public class PlayerDataDTO {
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
    Optional<String> owner;
    @Getter @Setter
    Optional<Integer> clause;
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
    @Getter @Setter
    String recommendedActionDetails;
}
