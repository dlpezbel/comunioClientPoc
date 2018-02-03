package quintonic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.dto.PlayerDataDTO;
import quintonic.engine.market.EngineAveragePricePerPosition;
import quintonic.engine.market.EngineCalculateAveragePricePerPosition;
import quintonic.engine.player.EngineCalculateAverageFitnessScore;
import quintonic.engine.player.EngineCalculateAveragePriceScore;
import quintonic.engine.player.EngineCalculateMatchesPlayedScore;
import quintonic.engine.player.EngineCalculatePriceIndicatorScore;

import java.util.ArrayList;
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

    public List<PlayerDataDTO> fillPlayerScoresForBot(List<PlayerDataDTO> playerDataDTOList) {
        List<PlayerDataDTO> result = new ArrayList<>();
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

        playerDataDTOList.stream().forEach(player -> {
            if ("injured".equals(player.getFitness().get(0))) {
                System.out.println(player.getName() + ": injured player!!");
                player.setRecommendedAction("Not buy, injured");
            } else {
                PlayerDataDTO newPlayer = new PlayerDataDTO();
                double finalScore = (player.getAverageFitnessScore() +
                        player.getAveragePriceScore() +
                        player.getPriceIndicatorScore() +
                        player.getMatchesPlayedScore()) / 4;
                player.setScore(finalScore);
                newPlayer.setScore(finalScore);
                newPlayer.setName(player.getName());
                newPlayer.setPrice(player.getPrice());

                if (finalScore < 0.25){
                    player.setRecommendedAction("Not buy!!!");
                    newPlayer.setRecommendedAction("Not buy!!!");
                } else if (finalScore < 0.5){
                    player.setRecommendedAction("Not Buy");
                    newPlayer.setRecommendedAction("Not buy!!!");
                } else if (finalScore <= 0.75){
                    player.setRecommendedAction("Evaluate");
                    newPlayer.setRecommendedAction("Not buy!!!");
                } else if (finalScore > 0.75){
                    player.setRecommendedAction("Buy!!!");
                    newPlayer.setRecommendedAction("Not buy!!!");
                }
                result.add(newPlayer);
            }
        });
        return result;
    }
}
