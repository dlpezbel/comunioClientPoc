package quintonic.service;

import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;

import java.util.List;

public interface QuintonicService {

    List<PlayerDataDTO> getMarketScore(String bearer, String league);

    List<PlayerDataDTO> getPlayersByName(String name);

    List<PlayerDataDTO> getUserPlayersScore(String bearer, String league);

    void setPlayerOffer(String bearer, String league, OfferDTO offer);
}
