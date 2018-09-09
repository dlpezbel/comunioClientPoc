package quintonic.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.PlayersDataService;
import quintonic.dto.BonusDTO;
import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;
import quintonic.engine.market.EngineAveragePricePerPosition;
import quintonic.engine.market.EngineCalculateAveragePricePerPosition;
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
    EngineAveragePricePerPosition engineAveragePricePerPosition;

    @Autowired
    EngineCalculateAverageFitnessScore engineCalculateAverageFitnessScore;

    @Autowired
    EngineCalculateAveragePricePerPosition engineCalculateAveragePricePerPosition;

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
        List<PlayerDataDTO> marketEvaluatedPlayersList = playerDataDTOList.
                stream().
                map(playerDataDTO -> (PlayerDataDTO)playersDataService.getPlayers().get(playerDataDTO.getId())).
                map(playerDataDTO -> setPlayerScores(playerDataDTO)).
                map(playerDataDTO -> setPlayerFinalScore(playerDataDTO)).
                map(playerDataDTO -> setBuyRecommendedAction(playerDataDTO)).
                collect(Collectors.toList());
        return marketEvaluatedPlayersList;
    }

    @Override
    public List<PlayerDataDTO> getUserPlayersScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        //fillPlayerScores(playerDataDTOList);
        evaluatePlayersForSell(playerDataDTOList);
        return playerDataDTOList;
    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getPlayersByName(name);
        //fillPlayerScores(playerDataDTOList);
        evaluatePlayersForBuy(playerDataDTOList);
        return playerDataDTOList;
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

    private void evaluatePlayersForBuy(List<PlayerDataDTO> playerDataDTOList) {
        Map players = playersDataService.getPlayers();
        playerDataDTOList.stream().forEach(player -> {
            PlayerDataDTO fullPlayer = (PlayerDataDTO) players.get(player.getId());
            if ("injured".equals(fullPlayer.getFitness().get(0))) {
                player.setRecommendedAction("No comprar, lesionado");
            } else {
                double finalScore = (player.getAverageFitnessScore() +
                        player.getAveragePriceScore() +
                        player.getPriceIndicatorScore() +
                        player.getMatchesPlayedScore()) / 4;
                player.setScore(finalScore);
                if (finalScore < 0.25){
                    player.setRecommendedAction("¡No comprar!");
                } else if (finalScore <= 0.5){
                    player.setRecommendedAction("No comprar");
                } else if (finalScore <= 0.75){
                    player.setRecommendedAction("Evaluar");
                } else if (finalScore > 0.75){
                    player.setRecommendedAction("Comprar");
                }
            }
        });
    }

    private void evaluatePlayersForSell(List<PlayerDataDTO> playerDataDTOList) {
        playerDataDTOList.stream().forEach(player -> {
            if ("injured".equals(player.getFitness().get(0))) {
                player.setRecommendedAction("Lesionado");
            } else {
                double finalScore = (player.getAverageFitnessScore() +
                        player.getAveragePriceScore() +
                        player.getPriceIndicatorScore() +
                        player.getMatchesPlayedScore()) / 4;
                player.setScore(finalScore);

                if (finalScore < 0.25){
                    player.setRecommendedAction("No alinear. Vender jugador.");
                } else if (finalScore < 0.5){
                    player.setRecommendedAction("Vender jugador.");
                } else if (finalScore <= 0.75){
                    player.setRecommendedAction("Alinear. Evaluar posible venta.");
                } else if (finalScore > 0.75){
                    player.setRecommendedAction("Alinear y mantener.");
                }

                StringBuffer reccommendedActionDetails = new StringBuffer();

                if (player.getPriceIndicatorScore()==1) {
                    reccommendedActionDetails.append("El precio del jugador está subiendo. ");
                } else {
                    reccommendedActionDetails.append("El precion del jugador está bajando. ");
                }
                if (player.getAveragePriceScore()==1) {
                    reccommendedActionDetails.append("Jugador caro, el precion del jugador es más alto que el de la media por puntos en su posición. ");
                } else {
                    reccommendedActionDetails.append("Jugador barato, el precio del jugador es más bajo que el de la media por puntos en su posición.");
                }
                if (player.getMatchesPlayedScore()==1) {
                    reccommendedActionDetails.append("Juega todo.");
                }else if (player.getMatchesPlayedScore()>=0.75) {
                    reccommendedActionDetails.append("Juega casi todo.");
                }else if (player.getMatchesPlayedScore()>=0.5) {
                    reccommendedActionDetails.append("No juega mucho, evaluar su posible alineación. ");
                }else if (player.getMatchesPlayedScore()<0.5) {
                    reccommendedActionDetails.append("No juega suficiente, considera su venta. ");
                }
                if (player.getAverageFitnessScore()==1) {
                    reccommendedActionDetails.append("El jugador está en un buen momento de forma. Mantener en alineación. ");
                } else {
                    reccommendedActionDetails.append("No está en su mejor momento. ");
                }
                player.setRecommendedActionDetails(reccommendedActionDetails.toString());
            }
        });
    }

//    public void fillPlayerScores(List<PlayerDataDTO> playerDataDTOList) {
//        Map players = playersDataService.getPlayers();
//        playerDataDTOList.stream().forEach(player -> {
//            Double averageFitnessScore = engineCalculateAverageFitnessScore.getScore((PlayerDataDTO)players.get(player.getId()));
//            player.setAverageFitnessScore(averageFitnessScore);
//        });
//
//        playerDataDTOList.stream().forEach(player -> {
//            Double averagePriceScore = engineCalculateAveragePriceScore.getScore((PlayerDataDTO)players.get(player.getId()));
//            player.setAveragePriceScore(averagePriceScore);
//        });
//
//        playerDataDTOList.stream().forEach(player -> {
//            Double priceIndicatorScore = engineCalculatePriceIndicatorScore.getScore((PlayerDataDTO)players.get(player.getId()));
//            player.setPriceIndicatorScore(priceIndicatorScore);
//        });
//
//        playerDataDTOList.stream().forEach(player -> {
//            Double matchesPlayedScore = engineCalculateMatchesPlayedScore.getScore((PlayerDataDTO)players.get(player.getId()));
//            player.setMatchesPlayedScore(matchesPlayedScore);
//        });
//    }

}
