package quintonic.engine.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quintonic.data.PlayersDataService;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculateAveragePriceScore implements EngineCalculateScore {
    @Autowired
    PlayersDataService playersDataService;

    @Override
    public Double getScore(PlayerDataDTO playerDataDTO) {
        int pricePerPoint = 0;
        if (playerDataDTO.getPoints()>0) {
            pricePerPoint = Integer.parseInt(playerDataDTO.getPrice()) / playerDataDTO.getPoints();
        }
        if (pricePerPoint!=0 &&
                pricePerPoint< playersDataService.getAveragePricePerPosition(playerDataDTO.getPosition())) {
            return new Double(1);
        } else {
            return new Double(0);
        }
    }
}
