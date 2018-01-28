package quintonic.engine.player;

import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculatePriceIndicatorScore implements EngineCalculateScore {
    @Override
    public Double getScore(PlayerDataDTO playerDataDTO) {
        if (playerDataDTO.getPriceIncrement()>0) {
            return new Double(1);
        }
        return new Double(0);
    }
}
