package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quintonic.dto.*;
import quintonic.dto.response.FullPlayerDataResponseDTO;
import quintonic.dto.response.SimplePlayerDataResponseDTO;
import quintonic.service.BiwengerClientService;
import quintonic.service.QuintonicService;
import quintonic.transformer.PlayerTransformer;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@CrossOrigin
public class QuintonicBotController {

    @Autowired
    QuintonicService quintonicService;
    @Autowired
    PlayerTransformer playerTransformer;

    @RequestMapping("/bot/league/{idLeague}/market/players")
    @ResponseBody
    List<SimplePlayerDataResponseDTO> getMarketPlayersScoreBot(@RequestHeader(value="Authorization") String bearer,
                                                        @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getMarketScore(bearer, league);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping("bot/league/{idLeague}/user/players")
    @ResponseBody
    List<SimplePlayerDataResponseDTO> getUserPlayersScoreBot(@RequestHeader(value="Authorization") String bearer,
                                                        @RequestHeader(value="X-League") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getUserPlayersScore(bearer,league);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }

}
