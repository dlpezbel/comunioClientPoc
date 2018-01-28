package quintonic.service;

import quintonic.dto.*;

import java.util.List;

public interface BiwengerClientService {
    PlayerDTO getPlayerByName(String playerName);

    TokenDTO login(UserDTO userDTO);

    MarketDTO getMarket(String bearer, String league);

    List<PlayerDataDTO> getAllPlayers();

    List<PlayerDataDTO> getUserPlayers(String bearer, String league);
}
