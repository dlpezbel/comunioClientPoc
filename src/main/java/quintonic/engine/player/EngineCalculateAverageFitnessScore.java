package quintonic.engine.player;

import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.OptionalDouble;

@Component
public class EngineCalculateAverageFitnessScore implements EngineCalculateScore {
    @Override
    public Double getScore(PlayerDataDTO playerDataDTO) {
        double totalAverage = getTotalAverage(playerDataDTO);
        OptionalDouble partialAverageOptional = getPartialAverage(playerDataDTO);

        if (partialAverageOptional.isPresent()) {
            if (partialAverageOptional.getAsDouble() > totalAverage) {
                return new Double(1);
            } else {
                return new Double(0);
            }
        }
        return new Double(0);
    }

    private double getTotalAverage(PlayerDataDTO playerDataDTO) {
        int totalPlayed =  playerDataDTO.getPlayedAway()+playerDataDTO.getPlayedHome();
        if (totalPlayed > 0) {
            return (double) (playerDataDTO.getPoints() / totalPlayed);
        } else {
            return 0;
        }
    }

    private OptionalDouble getPartialAverage(PlayerDataDTO playerDataDTO) {
        return playerDataDTO.getFitness().stream().filter(s -> {
            return isInteger(s);
        }).mapToInt(fitness -> Integer.parseInt(fitness)).average();
    }

    private boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            //not int
            return false;
        }
    }
}
