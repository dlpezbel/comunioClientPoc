package quintonic.engine.player;

import org.junit.Test;
import quintonic.dto.PlayerDataDTO;

public class EngineCalculateAveragePriceScoreTest {

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineCalculateAverageFitnessScore.setScore(playerDataDTO);
    }


}