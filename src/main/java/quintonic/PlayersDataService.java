package quintonic;

import lombok.Data;
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
    Map players;
    Date date;

    @Autowired
    private BiwengerClientService biwengerClientService;

    public Map getPlayers() {
        if (date == null || yesterday().after(date)) {
            date = new Date(System.currentTimeMillis());
            players = (Map<?,?>) biwengerClientService.getAllPlayers();
        }
        return players;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
