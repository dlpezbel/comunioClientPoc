package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.request.PlayerDataRequestDTO;

import java.util.List;

@ToString
public class MarketResponseDTO {
    @Getter @Setter
    List<PlayerDataRequestDTO> playersData;

}
