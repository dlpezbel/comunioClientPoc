package quintonic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.dto.OfferDTO;
import quintonic.dto.PlayerDataDTO;
import quintonic.engine.market.EngineAveragePricePerPosition;
import quintonic.engine.market.EngineCalculateAveragePricePerPosition;
import quintonic.engine.player.EngineCalculateAverageFitnessScore;
import quintonic.engine.player.EngineCalculateAveragePriceScore;
import quintonic.engine.player.EngineCalculateMatchesPlayedScore;
import quintonic.engine.player.EngineCalculatePriceIndicatorScore;

import java.util.List;

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

    private void evaluatePlayersForBuy(List<PlayerDataDTO> playerDataDTOList) {
        playerDataDTOList.stream().forEach(player -> {
            if ("injured".equals(player.getFitness().get(0))) {
                System.out.println(player.getName() + ": injured player!!");
                player.setRecommendedAction("Not buy, injured");
            } else {
                double finalScore = (player.getAverageFitnessScore() +
                        player.getAveragePriceScore() +
                        player.getPriceIndicatorScore() +
                        player.getMatchesPlayedScore()) / 4;
                player.setScore(finalScore);
                if (finalScore < 0.25){
                    player.setRecommendedAction("Not buy!!!");
                } else if (finalScore < 0.5){
                    player.setRecommendedAction("Not Buy");
                } else if (finalScore <= 0.75){
                    player.setRecommendedAction("Evaluate");
                } else if (finalScore > 0.75){
                    player.setRecommendedAction("Buy!!!");
                }
            }
        });
    }

    @Override
    public List<PlayerDataDTO> getUserPlayersScore(String bearer, String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        fillPlayerScores(playerDataDTOList);
        evaluatePlayersForSell(playerDataDTOList);
        return playerDataDTOList;
    }

    @Override
    public void setPlayerOffer(String bearer, String league, OfferDTO offer) {
        biwengerClientService.setPlayerOffer(bearer, league, offer);
    }

    private void evaluatePlayersForSell(List<PlayerDataDTO> playerDataDTOList) {
        playerDataDTOList.stream().forEach(player -> {
            if ("injured".equals(player.getFitness().get(0))) {
                player.setRecommendedAction("Injured");
            } else {
                double finalScore = (player.getAverageFitnessScore() +
                        player.getAveragePriceScore() +
                        player.getPriceIndicatorScore() +
                        player.getMatchesPlayedScore()) / 4;
                player.setScore(finalScore);
                StringBuffer recommendedAction = new StringBuffer();

                if (player.getPriceIndicatorScore()==1) {
                    recommendedAction.append("The price of the player has increased. ");
                } else {
                    recommendedAction.append("The price of the player has decreased. ");
                }
                if (player.getAveragePriceScore()==1) {
                    recommendedAction.append("The price of the player is higher than the position average, so  ");
                } else {
                    recommendedAction.append("The price of the player is lower than the position average, his value should be grow up. ");
                }
                if (player.getMatchesPlayedScore()==1) {
                    recommendedAction.append("Plays everything.");
                }else if (player.getMatchesPlayedScore()>=0.75) {
                    recommendedAction.append("Plays almost everything.");
                }else if (player.getMatchesPlayedScore()>=0.5) {
                    recommendedAction.append("Don't play very much, check team lineup. ");
                }else if (player.getMatchesPlayedScore()<0.5) {
                    recommendedAction.append("Dont't play enough, consider to sell him. ");
                }
                if (player.getAverageFitnessScore()==1) {
                    recommendedAction.append("The player has a good fitness moment. Keep on lineup. ");
                } else {
                    recommendedAction.append("The player is not in his best moment. ");
                }
                player.setRecommendedAction(recommendedAction.toString());
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
