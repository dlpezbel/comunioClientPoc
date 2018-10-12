package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;

@Component
public class EngineCalculateAverageFitnessScore {
    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
        Optional<Double> optScore = getPartialAverage(playerDataDTO).
                flatMap(partial -> getTotalAverage(playerDataDTO).
                        flatMap(getScoreFunction(partial)));
        playerDataScored.setAverageFitnessScore(optScore.orElse(new Double(0)));
        return playerDataScored;
    }

    private static Function<Double, Optional<Double>> getScoreFunction(Double partial) {
        return total -> {
            if (partial>total) return Optional.of(new Double(1));
            else return Optional.of(new Double(0));
        };
    }

    private static Optional<Double> getTotalAverage(PlayerDataDTO playerDataDTO) {
        Optional<Integer> optPlayedAway = Optional.ofNullable(playerDataDTO.getPlayedAway());
        Optional<Integer> optPlayedHome = Optional.ofNullable(playerDataDTO.getPlayedHome());
        if (optPlayedAway.isPresent() || optPlayedHome.isPresent()) {
            Integer totalPlayed =  optPlayedAway.orElse(0)+optPlayedHome.orElse(0);
            return Optional.of((double) (Optional.ofNullable(playerDataDTO.getPoints()).orElse(0) / totalPlayed));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Double> getPartialAverage(PlayerDataDTO playerDataDTO) {
        Optional<List<String>> optionalFitnessList = Optional.ofNullable(playerDataDTO.getFitness());
        OptionalDouble optAverage = optionalFitnessList.orElse(new ArrayList<>()).
                stream().
                filter(EngineGlobalScore::isInteger).
                mapToInt(fitness -> Integer.parseInt(fitness)).average();
        return optAverage.isPresent() ?
                Optional.of(optAverage.getAsDouble()) : Optional.empty();
    }

}
