package quintonic.engine.player;

import org.junit.Assert;
import org.junit.Test;
import quintonic.dto.PlayerDataDTO;

import java.util.Arrays;

public class EngineCalculateMatchesScoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
    }

    @Test
    public void whenPlayerHasNotFitness_thenScoreIsZero() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        PlayerDataDTO playerScoredDataDTO = EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getMatchesPlayedScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasOneFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("as","2",null,"0"));
        PlayerDataDTO playerScoredDataDTO = EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getMatchesPlayedScore(),new Double(0.15));
    }

    @Test
    public void whenPlayerHasThreeFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("as","2","3","0"));
        PlayerDataDTO playerScoredDataDTO = EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getMatchesPlayedScore(),new Double(0.55));
    }

    @Test
    public void whenPlayerHasFourFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("as","2","3","0","3"));
        PlayerDataDTO playerScoredDataDTO = EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getMatchesPlayedScore(),new Double(0.85));
    }

    @Test
    public void whenPlayerHasFiveFitness_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setFitness(Arrays.asList("-2","2","3","0","2"));
        PlayerDataDTO playerScoredDataDTO = EngineCalculateMatchesPlayedScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getMatchesPlayedScore(),new Double(1.0));
    }

}