package quintonic.engine.player;

import org.junit.Assert;
import org.junit.Test;
import quintonic.dto.PlayerDataDTO;

public class EngineGlobalScoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineGlobalScore.setPlayerFinalScore(playerDataDTO);
    }

    @Test
    public void whenPlayerHasNoScores_thenGlobalScoreIsZero() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        PlayerDataDTO playerScoredDataDTO = EngineGlobalScore.setPlayerFinalScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getScore(),new Double(0.0));
        Assert.assertTrue(playerScoredDataDTO.getScore()<=1.0 && playerScoredDataDTO.getScore()>=0);
    }

    @Test
    public void whenPlayerHasOnePartialScore_thenGlobalScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPriceIndicatorScore(1.0);
        PlayerDataDTO playerScoredDataDTO = EngineGlobalScore.setPlayerFinalScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getScore());
        Assert.assertNotEquals(playerScoredDataDTO.getScore(),new Double(0.0));
        Assert.assertTrue(playerScoredDataDTO.getScore()<=1.0 && playerScoredDataDTO.getScore()>=0);
    }

    @Test
    public void whenPlayerHasPartialScore_thenGlobalScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPriceIndicatorScore(1.0);
        playerDataDTO.setAveragePriceScore(1.0);
        playerDataDTO.setMatchesPlayedScore(1.0);
        playerDataDTO.setAverageFitnessScore(1.0);
        PlayerDataDTO playerScoredDataDTO = EngineGlobalScore.setPlayerFinalScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getScore());
        Assert.assertNotEquals(playerScoredDataDTO.getScore(),new Double(0.0));
        Assert.assertTrue(playerScoredDataDTO.getScore()<=1.0 && playerScoredDataDTO.getScore()>=0);
    }
}