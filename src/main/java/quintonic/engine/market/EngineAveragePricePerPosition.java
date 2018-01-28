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
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList,"1"));
            this.setAveragePricePerDefender(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList,"2"));
            this.setAveragePricePerMidfield(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList,"3"));
            this.setAveragePricePerForwarder(
                    engineCalculateAveragePricePerPosition.calculate(playerDataDTOList,"4"));
        }
        if ("1".equals(position)) {
            return averagePricePerGoalkeeper;
        } else if ("2".equals(position)) {
            return averagePricePerDefender;
        } else if ("3".equals(position)) {
            return averagePricePerMidfield;
        } else if ("4".equals(position)) {
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
