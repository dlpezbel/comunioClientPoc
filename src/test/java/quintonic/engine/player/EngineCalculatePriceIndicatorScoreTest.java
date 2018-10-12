package quintonic.engine.player;

import org.junit.Assert;
import org.junit.Test;
import quintonic.dto.PlayerDataDTO;

public class EngineCalculatePriceIndicatorScoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineCalculatePriceIndicatorScore.setScore(playerDataDTO);
    }

    @Test
    public void whenPlayerHasNotFitness_thenScoreIsZero() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        PlayerDataDTO playerScoredDataDTO = EngineCalculatePriceIndicatorScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getPriceIndicatorScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasPositivePriceIncrement_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPriceIncrement(1000);
        PlayerDataDTO playerScoredDataDTO = EngineCalculatePriceIndicatorScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getPriceIndicatorScore(),new Double(1.0));
    }

    @Test
    public void whenPlayerHasNoPriceIncrement_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPriceIncrement(0);
        PlayerDataDTO playerScoredDataDTO = EngineCalculatePriceIndicatorScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getPriceIndicatorScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasNegativePriceIncrement_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPriceIncrement(-1000);
        PlayerDataDTO playerScoredDataDTO = EngineCalculatePriceIndicatorScore.setScore(playerDataDTO);
        Assert.assertEquals(playerScoredDataDTO.getPriceIndicatorScore(),new Double(0.0));
    }
}