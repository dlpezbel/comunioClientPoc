package quintonic.engine.player;

import static quintonic.engine.player.EngineGlobalScore.isInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;

@Component
public class EngineCalculateMatchesPlayedScore {

  public static PlayerDataDTO setScore(PlayerDataDTO playerDataDTO) {
    PlayerDataDTO playerDataScored = new PlayerDataDTO();
    BeanUtils.copyProperties(playerDataDTO, playerDataScored);

    Optional<List<String>> optionalFitnessList = Optional.ofNullable(playerDataDTO.getFitness());

    long lScore = optionalFitnessList.orElse(new ArrayList<>()).stream().filter(
        s -> isInteger(s)).count();
    if (lScore == 5) {
      playerDataScored.setMatchesPlayedScore(new Double(1));
    } else if (lScore == 4) {
      playerDataScored.setMatchesPlayedScore(new Double(0.85));
    } else if (lScore == 3) {
      playerDataScored.setMatchesPlayedScore(new Double(0.55));
    } else if (lScore == 2) {
      playerDataScored.setMatchesPlayedScore(new Double(0.15));
    } else {
      playerDataScored.setMatchesPlayedScore(new Double(0));
    }
    return playerDataScored;
  }
}
