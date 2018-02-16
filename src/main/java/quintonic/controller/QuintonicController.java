package quintonic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/league/{idLeague}/user/offer")
    @ResponseBody
    public HttpStatus setPlayerOffer(@RequestHeader(value="Authorization") String bearer,
                                            @PathVariable(value="idLeague") String league, @RequestBody OfferDTO offer) {
        quintonicService.setPlayerOffer(bearer,league,offer);
        return HttpStatus.OK;
    }

    @RequestMapping("/league/{idLeague}/market/offers")
    @ResponseBody
    public List<OfferDTO> getPlayerOffers(@RequestHeader(value="Authorization") String bearer,
                                     @PathVariable(value="idLeague") String league) {
        return quintonicService.getPlayerOffers(bearer,league);
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

    @RequestMapping("/league/{idLeague}/users/money")
    @ResponseBody
    public Map<String, Integer> getUsersMoney(@RequestHeader(value="Authorization") String bearer,
                                              @PathVariable(value="idLeague") String league,
                                              @RequestParam( "bonus" ) int bonus) {
        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setBonus(bonus);
        return quintonicService.getUsersMoney(bearer, league, bonusDTO);
    }
}
