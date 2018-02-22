package quintonic.engine.market;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;
import quintonic.service.BiwengerClientService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class EngineAveragePricePerPosition {
    public static final String GOALKEEPER = "1";
    public static final String DEFENDER = "2";
    public static final String MIDFIELD = "3";
    public static final String FORWARDER = "4";
    @Getter @Setter
    Integer averagePricePerGoalkeeper;
    @Getter @Setter
    Integer averagePricePerDefender;
    @Getter @Setter
    Integer averagePricePerMidfield;
    @Getter @Setter
    Integer averagePricePerForwarder;

    @Autowired
    BiwengerClientService biwengerClientService;

    @Autowired
    EngineCalculateAveragePricePerPosition engineCalculateAveragePricePerPosition;

    Date date;

    public Integer getAveragePricePerPosition(String position) {
        if (date == null || yesterday().after(date)) {
            date = new Date(System.currentTimeMillis());
            List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getAllPlayers();
            this.setAveragePricePerGoalkeeper(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList, GOALKEEPER));
            this.setAveragePricePerDefender(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList, DEFENDER));
            this.setAveragePricePerMidfield(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList, MIDFIELD));
            this.setAveragePricePerForwarder(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList, FORWARDER));
        }
        if (GOALKEEPER.equals(position)) {
            return averagePricePerGoalkeeper;
        } else if (DEFENDER.equals(position)) {
            return averagePricePerDefender;
        } else if (MIDFIELD.equals(position)) {
            return averagePricePerMidfield;
        } else if (FORWARDER.equals(position)) {
            return averagePricePerForwarder;
        }
        return new Integer(0);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
