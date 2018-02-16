package quintonic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import quintonic.dto.*;
import quintonic.dto.request.*;
import quintonic.dto.UserDTO;
import quintonic.engine.player.EngineCalculateAverageFitnessScore;
import quintonic.transformer.PlayerTransformer;
import quintonic.transformer.TransferTransformer;

import java.util.*;

@Service
public class BiwengerClientServiceImpl implements BiwengerClientService {
    private static final String PLAYER_NAME = "player_name";

    @Autowired
    EngineCalculateAverageFitnessScore engineCalculateAverageFitnessScore;

    @Autowired
    PlayerTransformer playerTransformer;

    @Autowired
    TransferTransformer transferTransformer;

    private RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Override
    public PlayerDataDTO getPlayerByName(String playerName)
    {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga/{"+ PLAYER_NAME +"}";

        Map<String, String> params = new HashMap<>();
        params.put(PLAYER_NAME, playerName);

        RestTemplate restTemplate = getRestTemplate();
        PlayerRequestDTO resultPlayer = restTemplate.getForObject(uri, PlayerRequestDTO.class, params);
        return playerTransformer.transformPlayerRequestToPlayerDTO(resultPlayer.getData());
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
    public List<PlayerDataDTO> getMarketPlayers(String bearer, String league) {
        final String uri = "https://biwenger.as.com/api/v1/market?&limit=100";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MarketDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, MarketDTO.class);

        MarketDTO marketDTO = result.getBody();
        List<PlayerDataDTO> playerDataDTOList = getPlayerListFromMarket(marketDTO);
        return playerDataDTOList;
    }

    public List<PlayerDataDTO> getAllPlayers() {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga?&q=a&limit=1000";

        RestTemplate restTemplate = getRestTemplate();
        PlayersDTO allPlayers = restTemplate.getForObject(uri, PlayersDTO.class);
        List<PlayerDataDTO> playerDataDTOList =  playerTransformer.transformPlayerRequestListToPlayerListDTO(allPlayers.getData());
        return playerDataDTOList;
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
        List<PlayerDataRequestDTO> playerDataRequestDTOList = userDataDTO.getData().getPlayers();
        List<PlayerDataDTO> playerDataDTOList =  playerTransformer.transformPlayerRequestListToPlayerListDTO(playerDataRequestDTOList);
        playerDataDTOList.stream().forEach(playerDataDTO -> playerDataDTO.setOwner(Optional.empty()));
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

        AccountDataDTO accountDataDTO = accountDTO.getData();
        return accountDataDTO;
    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        final String uri = "https://biwenger.as.com/api/v1/players/la-liga?&q=" + name + "&limit=1000";

        RestTemplate restTemplate = getRestTemplate();
        PlayersDTO allPlayers = restTemplate.getForObject(uri, PlayersDTO.class);
        List<PlayerDataDTO> playerDataDTOList =  playerTransformer.transformPlayerRequestListToPlayerListDTO(allPlayers.getData());
        return playerDataDTOList;
    }

    @Override
    public void setPlayerOffer(String bearer, String league, OfferDTO offer) {
        final String uri = "https://biwenger.as.com/api/v1/offers";
        RestTemplate restTemplate = getRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<OfferDTO> request = new HttpEntity<OfferDTO>(offer, headers);
        restTemplate.postForObject(uri, request, String.class);
    }

    @Override
    public List<OfferDTO> getPlayerOffers(String bearer, String league) {
        final String uri = "https://biwenger.as.com/api/v1/market";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<MarketDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, MarketDTO.class);
        MarketDTO marketDTO = result.getBody();

        //return marketDTO.getData().getOffers();
        return null;
    }

    @Override
    public Map<String, Integer> getUsersMoney(String bearer, String league, BonusDTO initialBonus) {
        final String uri ="https://biwenger.as.com/api/v1/league/news?limit=1000&offset=0&type=transfer,market,exchange,loan,loanReturn";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TransferMarketRequestDTO> result = getRestTemplate().exchange(uri, HttpMethod.GET, entity, TransferMarketRequestDTO.class);
        List<TransferMarketDataRequestDTO> transferMarketDataRequestDTOList = result.getBody().getData();
        Map<String,Integer> moneyByUser = transferTransformer.processTransfers(transferMarketDataRequestDTOList, initialBonus);

        NewsRequestDTO newsRequest = getUserBonus(bearer, league);

        transferTransformer.addBonusByUser(newsRequest,moneyByUser,initialBonus);

        return moneyByUser;
    }

    private NewsRequestDTO getUserBonus(String bearer, String league) {
        final String uri2 = "https://biwenger.as.com/api/v1/league/news?limit=40&offset=0&type=roundFinished";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", bearer);
        headers.set("X-League", league);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<NewsRequestDTO> result2 = getRestTemplate().exchange(uri2, HttpMethod.GET, entity, NewsRequestDTO.class);

        NewsRequestDTO newsRequest = result2.getBody();
        return newsRequest;
    }

    private List<PlayerDataDTO> getPlayerListFromMarket(MarketDTO marketDTO) {
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        marketDTO.getData().getSales().stream().forEach(saleDTO -> {
            PlayerDataRequestDTO playerDataRequestDTO = saleDTO.getPlayer();
            PlayerDataDTO playerDataDTO = playerTransformer.transformPlayerRequestToPlayerDTO(playerDataRequestDTO);
            if (saleDTO.getUser()!=null) {
                playerDataDTO.setOwner(Optional.ofNullable(saleDTO.getUser().getName()));
            } else {
                playerDataDTO.setOwner(Optional.empty());
            }
            playerDataDTOList.add(playerDataDTO);});
        return playerDataDTOList;
    }
}
