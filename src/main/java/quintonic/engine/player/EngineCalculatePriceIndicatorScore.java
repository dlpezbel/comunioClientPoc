package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculatePriceIndicatorScore {
    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
        if (playerDataDTO.getPriceIncrement()>0) {
            playerDataScored.setPriceIndicatorScore(new Double(1));
        } else {
            playerDataScored.setPriceIndicatorScore(new Double(0));
        }
        return playerDataScored;
    }
}
