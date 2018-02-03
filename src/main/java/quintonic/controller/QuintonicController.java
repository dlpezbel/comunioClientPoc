package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import quintonic.dto.*;
import quintonic.service.BiwengerClientService;
import quintonic.service.QuintonicService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuintonicController {
    @Autowired
    BiwengerClientService biwengerClientService;
    @Autowired
    QuintonicService quintonicService;

    @RequestMapping("/market/players")
    @ResponseBody
    List<PlayerDataDTO> getMarketScore(@RequestHeader(value="Authorization") String bearer,
                                       @RequestHeader(value="X-League") String league) {
        MarketDTO marketDTO = biwengerClientService.getMarket(bearer, league);
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        marketDTO.getData().getSales().stream().forEach(saleDTO -> playerDataDTOList.add(saleDTO.getPlayer()));
        quintonicService.fillPlayerScores(playerDataDTOList);
        return playerDataDTOList;
    }

    @RequestMapping("/user/players")
    @ResponseBody
    List<PlayerDataDTO> getUserPlayersScore(@RequestHeader(value="Authorization") String bearer,
                                            @RequestHeader(value="X-League") String league) {
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getUserPlayers(bearer, league);
        quintonicService.fillPlayerScores(playerDataDTOList);
        return playerDataDTOList;
    }

    @RequestMapping("/user/login")
    @ResponseBody
    TokenDTO login(@RequestBody UserDTO userDTO) {
        return biwengerClientService.login(userDTO);
    }

    @RequestMapping("/user/account")
    @ResponseBody
    AccountDataDTO getUserAccount(@RequestHeader(value="Authorization") String bearer) {
        return biwengerClientService.getUserAccount(bearer);
    }


    @RequestMapping("/marketbot")
    @ResponseBody
    List<PlayerDataDTO> getMarketScoreBot(@RequestHeader(value="Authorization") String bearer,
                                       @RequestHeader(value="X-League") String league) {
        MarketDTO marketDTO = biwengerClientService.getMarket(bearer, league);
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        marketDTO.getData().getSales().stream().forEach(saleDTO -> playerDataDTOList.add(saleDTO.getPlayer()));
        return quintonicService.fillPlayerScoresForBot(playerDataDTOList);
    }

}
