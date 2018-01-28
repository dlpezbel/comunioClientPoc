package quintonic.service;

import quintonic.dto.PlayerDataDTO;

import java.util.List;

public interface QuintonicService {
    void fillPlayerScores(List<PlayerDataDTO> playerDataDTOList);
}
