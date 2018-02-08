package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quintonic.dto.*;
import quintonic.service.BiwengerClientService;
import quintonic.service.QuintonicService;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@CrossOrigin
public class QuintonicController {
    @Autowired
    BiwengerClientService biwengerClientService;
    @Autowired
    QuintonicService quintonicService;

    @RequestMapping("/league/{idLeague}/market/players")
    @ResponseBody
    List<PlayerDataDTO> getMarketScore(@RequestHeader(value="Authorization") String bearer,
                                       @PathVariable(value="idLeague") String league) {
        MarketDTO marketDTO = biwengerClientService.getMarket(bearer, league);
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        marketDTO.getData().getSales().stream().forEach(saleDTO -> playerDataDTOList.add(saleDTO.getPlayer()));
        quintonicService.fillPlayerScores(playerDataDTOList);
        return playerDataDTOList;
    }

    @RequestMapping( value = "/players",params = {"name"}, method = GET )
    @ResponseBody
    public List<PlayerDataDTO> getPlayersByName(
            @RequestParam( "name" ) String name, @RequestParam( "size" ) int size){
        List<PlayerDataDTO> playerDataDTOList = biwengerClientService.getPlayersByName(name);
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
    List<SimplePlayerDataDTO> getMarketScoreBot(@RequestHeader(value="Authorization") String bearer,
                                       @RequestHeader(value="X-League") String league) {
        MarketDTO marketDTO = biwengerClientService.getMarket(bearer, league);
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        marketDTO.getData().getSales().stream().forEach(saleDTO -> playerDataDTOList.add(saleDTO.getPlayer()));
        return quintonicService.fillPlayerScoresForBot(playerDataDTOList);
    }

}
