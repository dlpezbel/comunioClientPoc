package quintonic.service;

import quintonic.dto.*;
import quintonic.dto.UserDTO;

import java.util.List;

public interface BiwengerClientService {
    PlayerDataDTO getPlayerByName(String playerName);

    TokenDTO login(UserDTO userDTO);

    List<PlayerDataDTO> getMarketPlayers(String bearer, String league);

    List<PlayerDataDTO> getAllPlayers();

    List<PlayerDataDTO> getUserPlayers(String bearer, String league);

    AccountDataDTO getUserAccount(String bearer);

    List<PlayerDataDTO> getPlayersByName(String name);
}
