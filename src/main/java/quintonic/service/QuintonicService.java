package quintonic.service;

import quintonic.dto.PlayerDataDTO;
import quintonic.dto.SimplePlayerDataDTO;

import java.util.List;

public interface QuintonicService {
    void fillPlayerScores(List<PlayerDataDTO> playerDataDTOList);

    List<SimplePlayerDataDTO> fillPlayerScoresForBot(List<PlayerDataDTO> playerDataDTOList);
}
