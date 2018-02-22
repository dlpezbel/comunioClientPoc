package quintonic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public List<PlayerDataDTO> getMarketScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getMarketPlayers(bearer, league);
        fillPlayerScores(playerDataDTOList);
        evaluatePlayersForBuy(playerDataDTOList);
        return playerDataDTOList;
    }

    @Override
    public List<PlayerDataDTO> getPlayersByName(String name) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getPlayersByName(name);
        fillPlayerScores(playerDataDTOList);
        evaluatePlayersForBuy(playerDataDTOList);
        return playerDataDTOList;
    }

    @Override
    public List<PlayerDataDTO> getUserPlayersScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        fillPlayerScores(playerDataDTOList);
        evaluatePlayersForSell(playerDataDTOList);
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

    private void evaluatePlayersForBuy(List<PlayerDataDTO> playerDataDTOList) {
        playerDataDTOList.stream().forEach(player -> {
            if ("injured".equals(player.getFitness().get(0))) {
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

    public void fillPlayerScores(List<PlayerDataDTO> playerDataDTOList) {
        playerDataDTOList.stream().forEach(player -> {
            Double averageFitnessScore = engineCalculateAverageFitnessScore.getScore(player);
            player.setAverageFitnessScore(averageFitnessScore);
        });

        playerDataDTOList.stream().forEach(player -> {
            Double averagePriceScore = engineCalculateAveragePriceScore.getScore(player);
            player.setAveragePriceScore(averagePriceScore);
        });

        playerDataDTOList.stream().forEach(player -> {
            Double priceIndicatorScore = engineCalculatePriceIndicatorScore.getScore(player);
            player.setPriceIndicatorScore(priceIndicatorScore);
        });

        playerDataDTOList.stream().forEach(player -> {
            Double matchesPlayedScore = engineCalculateMatchesPlayedScore.getScore(player);
            player.setMatchesPlayedScore(matchesPlayedScore);
        });
    }

}
