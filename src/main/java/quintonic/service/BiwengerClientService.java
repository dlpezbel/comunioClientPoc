package quintonic.service;

import quintonic.dto.*;
import quintonic.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface BiwengerClientService {
    PlayerDataDTO getPlayerByName(String playerName);

    TokenDTO login(UserDTO userDTO);

    List<PlayerDataDTO> getMarketPlayers(String bearer, String league);

    Map getAllPlayers();

    List<PlayerDataDTO> getUserPlayers(String bearer, String league);

    AccountDataDTO getUserAccount(String bearer);

    List<PlayerDataDTO> getPlayersByName(String name);

    OfferDTO setPlayerOffer(String bearer, String league, OfferDTO offer);

    List<OfferDTO> getPlayerOffers(String bearer, String league);

    Map<String, Integer> getUsersMoney(String bearer, String league, BonusDTO bonus);

    void removeOffer(String bearer, String league, String idOffer);
}
