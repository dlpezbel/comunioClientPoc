package quintonic.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quintonic.dto.PlayerDataDTO;
import quintonic.service.BiwengerClientService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
public class PlayersDataService {
    public static final String GOALKEEPER = "1";
    public static final String DEFENDER = "2";
    public static final String MIDFIELD = "3";
    public static final String FORWARDER = "4";
    private Integer averagePricePerGoalkeeper;
    private Integer averagePricePerDefender;
    private Integer averagePricePerMidfield;
    private Integer averagePricePerForwarder;
    private Map players;
    private Date date;

    @Autowired
    private BiwengerClientService biwengerClientService;

    public Map getPlayers() {
        if (date == null || yesterday().after(date)) {
            date = new Date(System.currentTimeMillis());
            players = (Map<?,?>) biwengerClientService.getAllPlayers();
        }
        return players;
    }

    public Integer getAveragePricePerPosition(String position) {
        List<PlayerDataDTO> playerDataDTOList = (List<PlayerDataDTO>) players.values().stream().collect(Collectors.toList());
        this.setAveragePricePerGoalkeeper(
                this.calculate(playerDataDTOList, GOALKEEPER));
        this.setAveragePricePerDefender(
                this.calculate(playerDataDTOList, DEFENDER));
        this.setAveragePricePerMidfield(
                this.calculate(playerDataDTOList, MIDFIELD));
        this.setAveragePricePerForwarder(
                this.calculate(playerDataDTOList, FORWARDER));

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

    public Integer calculate(List<PlayerDataDTO> playerList, String position) {
        int pricePerPoint = 0;
        int priceSum = playerList.stream().
                filter(playerDTO -> position.equals(playerDTO.getPosition())).
                filter(playerDTO -> Integer.parseInt(playerDTO.getPrice()) > 200000).
                mapToInt(playerDTO -> Integer.parseInt(playerDTO.getPrice())).sum();

        int pointsSum = playerList.stream().
                filter(playerDTO -> position.equals(playerDTO.getPosition())).
                filter(playerDTO -> Integer.parseInt(playerDTO.getPrice()) > 200000).
                mapToInt(playerDTO -> playerDTO.getPoints()).sum();
        if (pointsSum > 0) {
            pricePerPoint = priceSum/pointsSum;
        }
        return new Integer(pricePerPoint);
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
