package quintonic.service;

import quintonic.dto.PlayerDataDTO;
import quintonic.dto.response.SimplePlayerDataResponseDTO;

import java.util.List;

public interface QuintonicService {

    List<PlayerDataDTO> getMarketScore(String bearer, String league);

    List<PlayerDataDTO> getPlayersByName(String name);

    List<PlayerDataDTO> getUserPlayerScore(String bearer, String league);
}
