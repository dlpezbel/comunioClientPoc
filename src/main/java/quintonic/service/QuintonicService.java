package quintonic.service;

import quintonic.dto.BonusDTO;
import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;

import java.util.List;
import java.util.Map;

public interface QuintonicService {

    List<PlayerDataDTO> getMarketScore(String bearer, String league);

    List<PlayerDataDTO> getPlayersByName(String name);

    List<PlayerDataDTO> getUserPlayersScore(String bearer, String league);

    OfferDTO setPlayerOffer(String bearer, String league, OfferDTO offer);

    List<OfferDTO> getPlayerOffers(String bearer, String league);

    Map<String, Integer> getUsersMoney(String bearer, String league, BonusDTO bonus);

    void removeOffer(String bearer, String league, String idOffer);
}
