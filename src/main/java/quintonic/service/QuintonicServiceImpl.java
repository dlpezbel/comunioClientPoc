package quintonic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.data.PlayersDataService;
import quintonic.dto.BonusDTO;
import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;
import quintonic.engine.player.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuintonicServiceImpl implements QuintonicService{

    @Autowired
    EngineCalculateAverageFitnessScore engineCalculateAverageFitnessScore;

    @Autowired
    EngineCalculateAveragePriceScore engineCalculateAveragePriceScore;

    @Autowired
    EngineCalculatePriceIndicatorScore engineCalculatePriceIndicatorScore;

    @Autowired
    EngineCalculateMatchesPlayedScore engineCalculateMatchesPlayedScore;

    @Autowired
    EngineGlobalScore engineGlobalScore;

    @Autowired
    BiwengerClientService biwengerClientService;

    @Autowired
    PlayersDataService playersDataService;

    @Override
    public List<PlayerDataDTO> getMarketScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getMarketPlayers(bearer, league);
        return playerDataDTOList.
                stream().
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(EngineCalculateAverageFitnessScore::setScore).
                map(EngineCalculateAveragePriceScore::setScore).
                map(EngineCalculatePriceIndicatorScore::setScore).
                map(EngineCalculateMatchesPlayedScore::setScore).
                map(EngineGlobalScore::setPlayerFinalScore).
                map(EngineGlobalScore::setBuyRecommendedAction).
                collect(Collectors.toList());
    }

    @Override
    public List<PlayerDataDTO> getUserPlayersScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        return playerDataDTOList.stream().
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(EngineCalculateAverageFitnessScore::setScore).
                map(EngineCalculateAveragePriceScore::setScore).
                map(EngineCalculatePriceIndicatorScore::setScore).
                map(EngineCalculateMatchesPlayedScore::setScore).
                map(EngineGlobalScore::setPlayerFinalScore).
                map(EngineGlobalScore::setSellRecommendedAction).
                map(EngineGlobalScore::setSellRecommendedActionDetails).
                collect(Collectors.toList());
    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        List<PlayerDataDTO> players = (List<PlayerDataDTO>)playersDataService.getPlayers().values().stream().collect(Collectors.toList());
        return  players.stream().filter(playerDataDTO -> playerDataDTO.getName().toUpperCase().contains(name.toUpperCase())).
                map(EngineCalculateAverageFitnessScore::setScore).
                map(EngineCalculateAveragePriceScore::setScore).
                map(EngineCalculatePriceIndicatorScore::setScore).
                map(EngineCalculateMatchesPlayedScore::setScore).
                map(EngineGlobalScore::setPlayerFinalScore).
                map(EngineGlobalScore::setBuyRecommendedAction).
                collect(Collectors.toList());
    }

    @Override
    public OfferDTO setPlayerOffer(String bearer, String league, OfferDTO offer) {
        return biwengerClientService.setPlayerOffer(bearer, league, offer);
    }

    @Override
    public List<OfferDTO> getPlayerOffers(String bearer, String league) {
        return biwengerClientService.getPlayerOffers(bearer, league);
    }

    @Override
    public Map<String, Integer> getUsersMoney(String bearer, String league, BonusDTO bonus) {
        return biwengerClientService.getUsersMoney(bearer, league, bonus);
    }

    @Override
    public void removeOffer(String bearer, String league, String idOffer) {
        biwengerClientService.removeOffer(bearer, league, idOffer);
    }

}
