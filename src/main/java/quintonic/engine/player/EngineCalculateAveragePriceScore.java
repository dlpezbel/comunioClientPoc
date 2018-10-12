package quintonic.engine.player;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quintonic.data.PlayersDataService;
import quintonic.dto.PlayerDataDTO;

import java.util.Optional;

@Component
public class EngineCalculateAveragePriceScore {

    static PlayersDataService playersDataService;

    @Autowired
    public EngineCalculateAveragePriceScore(PlayersDataService playersDataService) {
        this.playersDataService = playersDataService;
    }

    public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataScored);
        int pricePerPoint = 0;
        if (Optional.ofNullable(playerDataDTO.getPoints()).orElse(0)>0) {
            pricePerPoint = Integer.parseInt(playerDataDTO.getPrice()) / playerDataDTO.getPoints();
        }
        if (pricePerPoint!=0 &&
                pricePerPoint< playersDataService.getAveragePricePerPosition(playerDataDTO.getPosition())) {
            playerDataScored.setAveragePriceScore(new Double(1));
        } else {
            playerDataScored.setAveragePriceScore(new Double(0));
        }
        return playerDataScored;
    }

    // For testing purposes
    public static void setPlayersDataService(PlayersDataService playersDataService) {
        EngineCalculateAveragePriceScore.playersDataService = playersDataService;
    }


}
