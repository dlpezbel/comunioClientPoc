package quintonic.engine.market;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import quintonic.app.Quintonic;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Quintonic.class)
public class EngineAveragePricePerPositionTest {
    @Autowired
    EngineAveragePricePerPosition engineAveragePricePerPosition;
    @Test
    public void getAveragePricePerPosition() {
        engineAveragePricePerPosition.getAveragePricePerPosition("2");
    }

    @Test
    public void getAveragePricePerGoalkeeper() {
    }

    @Test
    public void getAveragePricePerDefender() {
    }

    @Test
    public void getAveragePricePerMidfield() {
    }

    @Test
    public void getAveragePricePerForwarder() {
    }
}