package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.OptionalDouble;

@Component
public class EngineCalculateAverageFitnessScore {
    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
        double totalAverage = getTotalAverage(playerDataDTO);
        OptionalDouble partialAverageOptional = getPartialAverage(playerDataDTO);
        // TODO refactor if's
        if (partialAverageOptional.isPresent()) {
            if (partialAverageOptional.getAsDouble() > totalAverage) {
                playerDataScored.setAverageFitnessScore(new Double(1));
            } else {
                playerDataScored.setAverageFitnessScore(new Double(0));
            }
        }
        return playerDataScored;
    }

    private static double getTotalAverage(PlayerDataDTO playerDataDTO) {
        int totalPlayed =  playerDataDTO.getPlayedAway()+playerDataDTO.getPlayedHome();
        if (totalPlayed > 0) {
            return (double) (playerDataDTO.getPoints() / totalPlayed);
        } else {
            return 0;
        }
    }

    private static OptionalDouble getPartialAverage(PlayerDataDTO playerDataDTO) {
        return playerDataDTO.getFitness().
                stream().
                filter(EngineCalculateAverageFitnessScore::isInteger).
                mapToInt(fitness -> Integer.parseInt(fitness)).average();
    }

    private static boolean isInteger(String s) {
        try{
            Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            //not int
            return false;
        }
    }
}
