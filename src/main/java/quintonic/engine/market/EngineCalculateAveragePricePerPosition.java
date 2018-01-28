package quintonic.engine.market;

import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

import java.util.List;

@Component
public class EngineCalculateAveragePricePerPosition {
    public Integer calculate(List<PlayerDataDTO> playerList, String position) {
        int pricePerPoint = 0;
        int priceSum = playerList.stream().
                filter(playerDTO -> position.equals(playerDTO.getPosition())).
                filter(playerDTO -> Integer.parseInt(playerDTO.getPrice()) > 200000).
                mapToInt(playerDTO -> Integer.parseInt(playerDTO.getPrice())).sum();

        int pointsSum = playerList.stream().
                filter(playerDTO -> position.equals(playerDTO.getPosition())).
                filter(playerDTO -> Integer.parseInt(playerDTO.getPrice()) > 200000).
                mapToInt(playerDTO -> playerDTO.getPoints()).sum();
        if (pointsSum > 0) {
            pricePerPoint = priceSum/pointsSum;
        }
        return new Integer(pricePerPoint);
    }
}
