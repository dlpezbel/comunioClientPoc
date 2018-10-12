package quintonic.engine.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import quintonic.data.PlayersDataService;
import quintonic.dto.PlayerDataDTO;

public class EngineCalculateAveragePriceScoreTest {

    @Mock
    PlayersDataService playersDataServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        EngineCalculateAveragePriceScore.setPlayersDataService(playersDataServiceMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPlayerIsNull_thenIllegalArgumentExceptionIsThrown() {
        PlayerDataDTO playerDataDTO = null;
        EngineCalculateAveragePriceScore.setScore(playerDataDTO);
    }

    @Test
    public void whenPlayerHasPriceButNoPoints_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPrice("160000");
        playerDataDTO.setPosition(PlayersDataService.DEFENDER);
        PlayerDataDTO playerScoredDataDTO = EngineCalculateAveragePriceScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAveragePriceScore());
        Assert.assertEquals(playerScoredDataDTO.getAveragePriceScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasPriceAvgGreaterThanPositionAvg_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPrice("160000");
        playerDataDTO.setPosition(PlayersDataService.DEFENDER);
        playerDataDTO.setPoints(100);
        Mockito.when(playersDataServiceMock.getAveragePricePerPosition(PlayersDataService.DEFENDER)).thenReturn(1500);

        PlayerDataDTO playerScoredDataDTO = EngineCalculateAveragePriceScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAveragePriceScore());
        Assert.assertEquals(playerScoredDataDTO.getAveragePriceScore(),new Double(0.0));
    }

    @Test
    public void whenPlayerHasPriceAvgLessThanPositionAvg_thenScoreIsCalculated() {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        playerDataDTO.setPrice("160000");
        playerDataDTO.setPosition(PlayersDataService.DEFENDER);
        playerDataDTO.setPoints(100);
        Mockito.when(playersDataServiceMock.getAveragePricePerPosition(PlayersDataService.DEFENDER)).thenReturn(1601);

        PlayerDataDTO playerScoredDataDTO = EngineCalculateAveragePriceScore.setScore(playerDataDTO);
        Assert.assertNotNull(playerScoredDataDTO.getAveragePriceScore());
        Assert.assertEquals(playerScoredDataDTO.getAveragePriceScore(),new Double(1.0));
    }

}