package quintonic.transformer;

import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;
import quintonic.dto.response.SimplePlayerDataResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerTransformer {
    public SimplePlayerDataResponseDTO transformToSimplePlayerResponseDTO(PlayerDataDTO playerDataDTO) {
        SimplePlayerDataResponseDTO newSimplePlayerDataResponse = new SimplePlayerDataResponseDTO();
        newSimplePlayerDataResponse.setScore(playerDataDTO.getScore());
        newSimplePlayerDataResponse.setName(playerDataDTO.getName());
        newSimplePlayerDataResponse.setPrice(playerDataDTO.getPrice());
        return newSimplePlayerDataResponse;
    }

    public List<SimplePlayerDataResponseDTO> transformListToSimplePlayerListResponseDTO(List<PlayerDataDTO> playerDataDTOList) {
        List<SimplePlayerDataResponseDTO> simplePlayerDataList = new ArrayList<>();
        playerDataDTOList.stream().forEach(playerDataDTO -> {
            simplePlayerDataList.add(transformToSimplePlayerResponseDTO(playerDataDTO));
        });
        return simplePlayerDataList;
    }

    public List<PlayerDataDTO> transformListToPlayerListResponseDTO(List<PlayerDataDTO> playerDataDTOList) {
        return playerDataDTOList;
    }
}
