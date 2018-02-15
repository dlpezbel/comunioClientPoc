package quintonic.engine.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;
import quintonic.dto.request.PlayerDataRequestDTO;
import quintonic.engine.market.EngineAveragePricePerPosition;

@Component
public class EngineCalculateAveragePriceScore implements EngineCalculateScore {
    @Autowired
    EngineAveragePricePerPosition engineAveragePricePerPosition;

    @Override
    public Double getScore(PlayerDataDTO playerDataDTO) {
        int pricePerPoint = 0;
        if (playerDataDTO.getPoints()>0) {
            pricePerPoint = Integer.parseInt(playerDataDTO.getPrice()) / playerDataDTO.getPoints();
        }
        if (pricePerPoint!=0 &&
                pricePerPoint< engineAveragePricePerPosition.getAveragePricePerPosition(playerDataDTO.getPosition())) {
            return new Double(1);
        } else {
            return new Double(0);
        }
    }
}
