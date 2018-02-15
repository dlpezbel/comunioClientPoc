package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.OwnerDTO;
import quintonic.dto.request.PlayerDataRequestDTO;

@ToString
public class SaleRequestDTO {
    @Getter
    @Setter
    PlayerDataRequestDTO player;

    @Getter
    @Setter
    OwnerDTO user;
}
