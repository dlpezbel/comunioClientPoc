package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quintonic.dto.AccountDataDTO;
import quintonic.dto.PlayerDataDTO;
import quintonic.dto.TokenDTO;
import quintonic.dto.UserDTO;
import quintonic.dto.response.FullPlayerDataResponseDTO;
import quintonic.dto.response.SimplePlayerDataResponseDTO;
import quintonic.service.BiwengerClientService;
import quintonic.service.QuintonicService;
import quintonic.transformer.PlayerTransformer;

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
    List<FullPlayerDataResponseDTO> getMarketPlayersScore(@RequestHeader(value="Authorization") String bearer,
                                                   @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getMarketScore(bearer, league);
        return playerTransformer.transformListToPlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping("/bot/league/{idLeague}/market/players")
    @ResponseBody
    List<SimplePlayerDataResponseDTO> getMarketPlayersScoreBot(@RequestHeader(value="Authorization") String bearer,
                                                        @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getMarketScore(bearer, league);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping("/league/{idLeague}/user/players")
    @ResponseBody
    List<FullPlayerDataResponseDTO> getUserPlayersScore(@RequestHeader(value="Authorization") String bearer,
                                                        @PathVariable(value="idLeague") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getUserPlayersScore(bearer,league);
        return playerTransformer.transformListToPlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping("bot/league/{idLeague}/user/players")
    @ResponseBody
    List<SimplePlayerDataResponseDTO> getUserPlayersScoreBot(@RequestHeader(value="Authorization") String bearer,
                                                        @RequestHeader(value="X-League") String league) {
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getUserPlayersScore(bearer,league);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }

    @RequestMapping( value = "/players",params = {"name"}, method = GET )
    @ResponseBody
    public List<FullPlayerDataResponseDTO> getPlayersByName(
            @RequestParam( "name" ) String name, @RequestParam( "size" ) int size){
        List<PlayerDataDTO> playerDataDTOList = quintonicService.getPlayersByName(name);
        return playerTransformer.transformListToPlayerListResponseDTO(playerDataDTOList);
    }

/*    @RequestMapping("/league/{idLeague}/user/offer")
    @ResponseBody
    public void setPlayerOffer(@RequestHeader(value="Authorization") String bearer,
                            @PathVariable(value="idLeague") String league) {
                            https://biwenger.as.com/api/v1/offers
        List<PlayerDataDTO> playerDataDTOList = quintonicService.setPlayerOffer(bearer,league,offer);
        return playerTransformer.transformListToSimplePlayerListResponseDTO(playerDataDTOList);
    }*/


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
