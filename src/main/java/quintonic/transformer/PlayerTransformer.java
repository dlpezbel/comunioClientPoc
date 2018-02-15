package quintonic.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quintonic.dto.PlayerDataDTO;
import quintonic.dto.request.PlayerDataRequestDTO;
import quintonic.dto.response.FullPlayerDataResponseDTO;
import quintonic.dto.response.SimplePlayerDataResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PlayerTransformer {
    public SimplePlayerDataResponseDTO transformToSimplePlayerResponseDTO(PlayerDataDTO playerDataDTO) {
        SimplePlayerDataResponseDTO newSimplePlayerDataResponse = new SimplePlayerDataResponseDTO();

        newSimplePlayerDataResponse.setScore(playerDataDTO.getScore());
        newSimplePlayerDataResponse.setName(playerDataDTO.getName());
        newSimplePlayerDataResponse.setPrice(playerDataDTO.getPrice());
        newSimplePlayerDataResponse.setRecommendedAction(playerDataDTO.getRecommendedAction());
        return newSimplePlayerDataResponse;
    }

    public List<SimplePlayerDataResponseDTO> transformListToSimplePlayerListResponseDTO(List<PlayerDataDTO> playerDataDTOList) {
        List<SimplePlayerDataResponseDTO> simplePlayerDataList = new ArrayList<>();
        playerDataDTOList.stream().forEach(playerDataDTO -> {
            simplePlayerDataList.add(transformToSimplePlayerResponseDTO(playerDataDTO));
        });
        return simplePlayerDataList;
    }

    public List<FullPlayerDataResponseDTO> transformListToPlayerListResponseDTO(List<PlayerDataDTO> playerDataDTOList) {
        List<FullPlayerDataResponseDTO> playerDataResponseDTOList = new ArrayList<>();
        playerDataDTOList.stream().forEach(playerDataDTO -> {
            FullPlayerDataResponseDTO fullPlayerDataResponseDTO = new FullPlayerDataResponseDTO();
            BeanUtils.copyProperties(playerDataDTO, fullPlayerDataResponseDTO);
            if (playerDataDTO.getOwner()!=null) {
                fullPlayerDataResponseDTO.setOwner(playerDataDTO.getOwner().orElseGet(() -> "Computer"));
            }
            if (playerDataDTO.getClause()!=null) {
                fullPlayerDataResponseDTO.setClause(playerDataDTO.getClause().orElseGet(() -> 0));
            }
            playerDataResponseDTOList.add(fullPlayerDataResponseDTO);
        });
        return playerDataResponseDTOList;
    }

    public List<PlayerDataDTO> transformPlayerRequestListToPlayerListDTO(List<PlayerDataRequestDTO> playerDataRequestDTOList) {
        List<PlayerDataDTO> playerDataDTOList = new ArrayList<>();
        playerDataRequestDTOList.stream().forEach(playerDataRequestDTO -> {
            playerDataDTOList.add(transformPlayerRequestToPlayerDTO(playerDataRequestDTO));
        });
        return playerDataDTOList;
    }

    public PlayerDataDTO transformPlayerRequestToPlayerDTO(PlayerDataRequestDTO playerDataRequestDTO) {
        PlayerDataDTO playerDataDTO = new PlayerDataDTO();
        BeanUtils.copyProperties(playerDataRequestDTO,playerDataDTO);
        if (playerDataRequestDTO.getOwner()!=null) {
            playerDataDTO.setClause(Optional.ofNullable(playerDataRequestDTO.getOwner().getClause()));
        } else {
            playerDataDTO.setClause(Optional.empty());
        }
        return playerDataDTO;
    }
}
