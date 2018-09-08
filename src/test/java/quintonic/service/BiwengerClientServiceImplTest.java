package quintonic.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import quintonic.app.Quintonic;
import quintonic.engine.market.EngineAveragePricePerPosition;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Quintonic.class)
public class BiwengerClientServiceImplTest {
    @Autowired
    BiwengerClientServiceImpl biwengerClientService;

    @Test
    public void getAllPlayers() {
        Map players = biwengerClientService.getAllPlayers();
        assertNotNull(players);
    }
}