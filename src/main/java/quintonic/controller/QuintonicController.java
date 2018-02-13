package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quintonic.dto.*;
import quintonic.dto.response.SimplePlayerDataResponseDTO;
import quintonic.service.BiwengerClientService;
import quintonic.service.QuintonicService;
import quintonic.transformer.PlayerTransformer;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@CrossOrigin
public class QuintonicController {

    @Autowired
    QuintonicService quintonicService;
    @Autowired
    PlayerTransformer playerTransformer;
    @Autowired
    BiwengerClientService biwengerClientService;

    @RequestMapping("/league/{idLeague}/market/players")
    @ResponseBody
    List<PlayerDataDTO> getMarketScore(@RequestHeader(value="Authorization") String bearer,
                                       @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getMarketScore(bearer, league);
        return playerTransformer.transformListToPlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping("/bot/league/{idLeague}/market/players")
    @ResponseBody
    List<SimplePlayerDataResponseDTO> getMarketScoreBot(@RequestHeader(value="Authorization") String bearer,
                                                        @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getMarketScore(bearer, league);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping( value = "/players",params = {"name"}, method = GET )
    @ResponseBody
    public List<PlayerDataDTO> getPlayersByName(
            @RequestParam( "name" ) String name, @RequestParam( "size" ) int size){
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getPlayersByName(name);
        return playerDataDTOList;
    }

    @RequestMapping("/user/players")
    @ResponseBody
    List<PlayerDataDTO> getUserPlayersScore(@RequestHeader(value="Authorization") String bearer,
                                            @RequestHeader(value="X-League") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getUserPlayerScore(bearer,league);
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



}
