package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.DoubleConsumer;

@Component
public class EngineCalculateAverageFitnessScore {
    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
        Optional optTotalAverage = getTotalAverage(playerDataDTO);
        Optional partialAverageOptional = getPartialAverage(playerDataDTO);

        // TODO refactor if's
        if (partialAverageOptional.isPresent() &&
                (Double)partialAverageOptional.get() > (Double)optTotalAverage.get()) {
                playerDataScored.setAverageFitnessScore(new Double(1));
        } else {
            playerDataScored.setAverageFitnessScore(new Double(0));
        }
        return playerDataScored;
    }

    private static Optional<Double> getTotalAverage(PlayerDataDTO playerDataDTO) {
        int totalPlayed =  playerDataDTO.getPlayedAway()+playerDataDTO.getPlayedHome();
        if (totalPlayed > 0) {
            return Optional.of((double) (playerDataDTO.getPoints() / totalPlayed));
        } else {
            return Optional.empty();
        }
    }

    private static Optional getPartialAverage(PlayerDataDTO playerDataDTO) {
        OptionalDouble optAverage = playerDataDTO.getFitness().
                stream().
                filter(EngineCalculateAverageFitnessScore::isInteger).
                mapToInt(fitness -> Integer.parseInt(fitness)).average();
        return optAverage.isPresent() ?
                Optional.of(optAverage.getAsDouble()) : Optional.empty();
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
