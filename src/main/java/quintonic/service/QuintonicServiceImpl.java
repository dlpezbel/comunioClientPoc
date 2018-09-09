package quintonic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.data.PlayersDataService;
import quintonic.dto.BonusDTO;
import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;
import quintonic.engine.player.EngineCalculateAverageFitnessScore;
import quintonic.engine.player.EngineCalculateAveragePriceScore;
import quintonic.engine.player.EngineCalculateMatchesPlayedScore;
import quintonic.engine.player.EngineCalculatePriceIndicatorScore;

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
    BiwengerClientService biwengerClientService;

    @Autowired
    PlayersDataService playersDataService;

    @Override
    public List<PlayerDataDTO> getMarketScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getMarketPlayers(bearer, league);
        return playerDataDTOList.
                stream().
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(playerDataDTO -> setPlayerScores(playerDataDTO)).
                map(playerDataDTO -> setPlayerFinalScore(playerDataDTO)).
                map(playerDataDTO -> setBuyRecommendedAction(playerDataDTO)).
                collect(Collectors.toList());
    }

    @Override
    public List<PlayerDataDTO> getUserPlayersScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        return playerDataDTOList.
                stream().
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(playerDataDTO -> setPlayerScores(playerDataDTO)).
                map(playerDataDTO -> setPlayerFinalScore(playerDataDTO)).
                map(playerDataDTO -> setSellRecommendedAction(playerDataDTO)).
                map(playerDataDTO -> setSellRecommendedActionDetails(playerDataDTO)).
                collect(Collectors.toList());
    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        List<PlayerDataDTO> players = (List<PlayerDataDTO>)playersDataService.getPlayers().values().stream().collect(Collectors.toList());
        return  players.stream().filter(playerDataDTO -> playerDataDTO.getName().toUpperCase().contains(name.toUpperCase())).
                map(playerDataDTO -> setPlayerScores(playerDataDTO)).
                map(playerDataDTO -> setPlayerFinalScore(playerDataDTO)).
                map(playerDataDTO -> setBuyRecommendedAction(playerDataDTO)).
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

    private PlayerDataDTO setPlayerFinalScore(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataScored = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO,playerDataScored);
        double finalScore = (playerDataScored.getAverageFitnessScore() +
                playerDataScored.getAveragePriceScore() +
                playerDataScored.getPriceIndicatorScore() +
                playerDataScored.getMatchesPlayedScore()) / 4;
        playerDataScored.setScore(finalScore);
        return playerDataScored;
    }

    public PlayerDataDTO setPlayerScores(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerScoreDataDTO = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO,playerScoreDataDTO);
        Double averageFitnessScore = engineCalculateAverageFitnessScore.getScore(playerDataDTO);
        playerScoreDataDTO.setAverageFitnessScore(averageFitnessScore);
        Double averagePriceScore = engineCalculateAveragePriceScore.getScore(playerDataDTO);
        playerScoreDataDTO.setAveragePriceScore(averagePriceScore);
        Double priceIndicatorScore = engineCalculatePriceIndicatorScore.getScore(playerDataDTO);
        playerScoreDataDTO.setPriceIndicatorScore(priceIndicatorScore);
        Double matchesPlayedScore = engineCalculateMatchesPlayedScore.getScore(playerDataDTO);
        playerScoreDataDTO.setMatchesPlayedScore(matchesPlayedScore);
        return playerScoreDataDTO;
    }

    private PlayerDataDTO setBuyRecommendedAction(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO,playerDataEvaluated);
        if ("injured".equals(playerDataEvaluated.getFitness().get(0))) {
            playerDataEvaluated.setRecommendedAction("No comprar, lesionado");
        } else if (playerDataDTO.getScore() < 0.25){
            playerDataEvaluated.setRecommendedAction("¡No comprar!");
        } else if (playerDataDTO.getScore() <= 0.5){
            playerDataEvaluated.setRecommendedAction("No comprar");
        } else if (playerDataDTO.getScore() <= 0.75){
            playerDataEvaluated.setRecommendedAction("Evaluar");
        } else if (playerDataDTO.getScore() > 0.75){
            playerDataEvaluated.setRecommendedAction("Comprar");
        }
        return playerDataEvaluated;
    }

    private PlayerDataDTO setSellRecommendedAction(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataEvaluated);
        if ("injured".equals(playerDataDTO.getFitness().get(0))) {
            playerDataEvaluated.setRecommendedAction("Lesionado");
        } else if (playerDataDTO.getScore() < 0.25){
            playerDataEvaluated.setRecommendedAction("No alinear. Vender jugador.");
        } else if (playerDataDTO.getScore() < 0.5){
            playerDataEvaluated.setRecommendedAction("Vender jugador.");
        } else if (playerDataDTO.getScore() <= 0.75){
            playerDataEvaluated.setRecommendedAction("Alinear. Evaluar posible venta.");
        } else if (playerDataDTO.getScore() > 0.75){
            playerDataEvaluated.setRecommendedAction("Alinear y mantener.");
        }
        return playerDataEvaluated;
    }

    private PlayerDataDTO setSellRecommendedActionDetails(PlayerDataDTO playerDataDTO) {
        PlayerDataDTO playerDataEvaluated = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataDTO, playerDataEvaluated);
        StringBuffer reccommendedActionDetails = new StringBuffer();

        if (playerDataDTO.getPriceIndicatorScore()==1) {
            reccommendedActionDetails.append("El precio del jugador está subiendo. ");
        } else {
            reccommendedActionDetails.append("El precion del jugador está bajando. ");
        }
        if (playerDataDTO.getAveragePriceScore()==1) {
            reccommendedActionDetails.append("Jugador caro, el precion del jugador es más alto que el de la media por puntos en su posición. ");
        } else {
            reccommendedActionDetails.append("Jugador barato, el precio del jugador es más bajo que el de la media por puntos en su posición.");
        }
        if (playerDataDTO.getMatchesPlayedScore()==1) {
            reccommendedActionDetails.append("Juega todo.");
        }else if (playerDataDTO.getMatchesPlayedScore()>=0.75) {
            reccommendedActionDetails.append("Juega casi todo.");
        }else if (playerDataDTO.getMatchesPlayedScore()>=0.5) {
            reccommendedActionDetails.append("No juega mucho, evaluar su posible alineación. ");
        }else if (playerDataDTO.getMatchesPlayedScore()<0.5) {
            reccommendedActionDetails.append("No juega suficiente, considera su venta. ");
        }
        if (playerDataDTO.getAverageFitnessScore()==1) {
            reccommendedActionDetails.append("El jugador está en un buen momento de forma. Mantener en alineación. ");
        } else {
            reccommendedActionDetails.append("No está en su mejor momento. ");
        }
        playerDataEvaluated.setRecommendedActionDetails(reccommendedActionDetails.toString());
        return playerDataEvaluated;
    }
}
