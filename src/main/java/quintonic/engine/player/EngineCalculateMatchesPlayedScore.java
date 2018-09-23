package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculateMatchesPlayedScore {
    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
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
            playerDataScored.setMatchesPlayedScore(new Double(1));
        } else if (lScore == 4) {
            playerDataScored.setMatchesPlayedScore(new Double(0.75));
        } else {
            playerDataScored.setMatchesPlayedScore(new Double(0));
        }
        return playerDataScored;
    }
}
