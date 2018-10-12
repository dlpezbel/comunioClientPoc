package quintonic.engine.player;

import org.junit.Assert;
import org.junit.Test;
import quintonic.dto.PlayerDataDTO;

import java.util.Arrays;

public class EngineCalculateAverageFitnessScoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
    }

    @Test
    public void whenPlayerHasNotFitness_thenScoreIsZero() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAverageFitnessScore());
        Assert.assertEquals(playerScoredDataDTO.getAverageFitnessScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasOneFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("as","2",null,"0"));
        playerDataDTO.setPlayedAway(1);
        playerDataDTO.setPlayedHome(null);
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAverageFitnessScore());
        Assert.assertEquals(playerScoredDataDTO.getAverageFitnessScore(),new Double(1.0));
    }

    @Test
    public void whenPlayerHasFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("2","2","2","2"));
        playerDataDTO.setPlayedAway(2);
        playerDataDTO.setPlayedHome(2);
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAverageFitnessScore());
    }

    @Test
    public void whenPlayerHasPartialAvgGreaterThanTotalAvg_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("4","6","6","4"));
        playerDataDTO.setPlayedAway(5);
        playerDataDTO.setPlayedHome(5);
        playerDataDTO.setPoints(30);
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAverageFitnessScore());
        Assert.assertEquals(playerScoredDataDTO.getAverageFitnessScore(),new Double(1.0));
    }

    @Test
    public void whenPlayerHasPartialAvgLessThanTotalAvg_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("4","6","6","4"));
        playerDataDTO.setPlayedAway(5);
        playerDataDTO.setPlayedHome(5);
        playerDataDTO.setPoints(51);
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAverageFitnessScore());
        Assert.assertEquals(playerScoredDataDTO.getAverageFitnessScore(),new Double(0.0));
    }

}