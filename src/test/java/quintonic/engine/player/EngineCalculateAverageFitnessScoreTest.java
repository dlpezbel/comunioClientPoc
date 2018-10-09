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
    public void whenPlayerHasNotFitness_thenScoreIsNotCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNull(playerScoredDataDTO.getScore());
    }

    //@Test
    public void whenPlayerHasOneFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("as","1",null,"0"));
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getScore());
    }

}