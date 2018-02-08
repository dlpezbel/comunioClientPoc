package quintonic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import quintonic.dto.*;
import quintonic.engine.player.EngineCalculateAverageFitnessScore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BiwengerClientServiceImpl implements BiwengerClientService {
    private static final String PLAYER_NAME = "player_name";

    @Autowired
    EngineCalculateAverageFitnessScore engineCalculateAverageFitnessScore;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Override
    public PlayerDTO getPlayerByName(String playerName)
    {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga/{"+ PLAYER_NAME +"}";

        Map<String, String> params = new HashMap<String, String>();
        params.put(PLAYER_NAME, playerName);

        RestTemplate restTemplate = getRestTemplate();
        PlayerDTO resultPlayer = restTemplate.getForObject(uri, PlayerDTO.class, params);

        System.out.println(resultPlayer);

        return resultPlayer;
    }

    @Override
    public TokenDTO login(UserDTO userDTO) {
        final String uri = "https://biwenger.as.com/api/v1/auth/login";
        RestTemplate restTemplate = getRestTemplate();
        HttpEntity<UserDTO> request = new HttpEntity<>(new UserDTO(userDTO.getEmail(),userDTO.getPassword()));

        TokenDTO tokenDTO = restTemplate.postForObject(uri,request,TokenDTO.class);
        System.out.println(tokenDTO);

        return tokenDTO;
    }

    @Override
    public MarketDTO getMarket(String bearer, String league) {
        final String uri = "https://biwenger.as.com/api/v1/market?&limit=100";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MarketDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, MarketDTO.class);

        MarketDTO marketDTO = result.getBody();

        return marketDTO;
    }

    public List<PlayerDataDTO> getAllPlayers() {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga?&q=a&limit=1000";

        RestTemplate restTemplate = getRestTemplate();
        PlayersDTO allPlayers = restTemplate.getForObject(uri, PlayersDTO.class);

        return allPlayers.getData();
    }

    @Override
    public List<PlayerDataDTO> getUserPlayers(String bearer, String league) {
        final String uri = "https://biwenger.as.com/api/v1/user?fields=*,players(*,fitness)";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDataDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, UserDataDTO.class);

        UserDataDTO userDataDTO = result.getBody();

        System.out.println("team user: " + userDataDTO);

        List<PlayerDataDTO> playerDataDTOList = userDataDTO.getData().getPlayers();
        return playerDataDTOList;
    }

    public AccountDataDTO getUserAccount(String bearer) {
        final String uri = "https://biwenger.as.com/api/v1/account";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", bearer);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<AccountDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, AccountDTO.class);

        AccountDTO accountDTO = result.getBody();

        System.out.println("team user: " + accountDTO);

        AccountDataDTO accountDataDTO = accountDTO.getData();
        return accountDataDTO;

    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga?&q=" + name + "&limit=1000";

        RestTemplate restTemplate = getRestTemplate();
        PlayersDTO allPlayers = restTemplate.getForObject(uri, PlayersDTO.class);

        return allPlayers.getData();
    }
}
