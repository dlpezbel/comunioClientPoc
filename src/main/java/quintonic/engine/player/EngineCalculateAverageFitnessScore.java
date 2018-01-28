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
            System.out.println("Average last 5 matches: " + partialAverageOptional.getAsDouble() + " media total: " + totalAverage);
            if (partialAverageOptional.getAsDouble() > totalAverage) {
                System.out.println(playerDataDTO.getName() + ": Score last 5 matches is better than global average.");
                return new Double(1);
            } else {
                System.out.println(playerDataDTO.getName() +": Score last 5 matches is worse than global average.");
                return new Double(0);
            }
        }
        return new Double(0);
    }

    private double getTotalAverage(PlayerDataDTO playerDataDTO) {
        int totalPlayed =  playerDataDTO.getPlayedAway()+playerDataDTO.getPlayedAway();
        if (totalPlayed > 0) {
            return (double) (playerDataDTO.getPoints() / totalPlayed);
        } else {
            return 0;
        }
    }

    private OptionalDouble getPartialAverage(PlayerDataDTO playerDataDTO) {
        return playerDataDTO.getFitness().stream().filter(s -> {
                try{
                    Integer.parseInt(s);
                    return true;
                }catch(NumberFormatException e){
                    //not int
                    return false;
                }}).mapToInt(fitness -> Integer.parseInt(fitness)).average();
    }
}
