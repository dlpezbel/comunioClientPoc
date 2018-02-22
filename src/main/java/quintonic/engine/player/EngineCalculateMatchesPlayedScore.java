package quintonic.engine.player;

import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculateMatchesPlayedScore implements EngineCalculateScore {
    @Override
    public Double getScore(PlayerDataDTO playerDataDTO) {
        long lScore = playerDataDTO.getFitness().stream().filter(
                s -> {
                    try{
                        Integer.parseInt(s);
                        return true;
                    }catch(NumberFormatException e){
                        //not int
                        return false;
                    }}
        ).count();
        if (lScore == 5) {
            return new Double(1);
        } else if (lScore == 4) {
            return new Double(0.75);
        } else {
            return new Double(0);
        }
    }
}
