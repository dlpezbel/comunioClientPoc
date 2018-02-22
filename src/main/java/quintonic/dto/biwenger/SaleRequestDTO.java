package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.OwnerDTO;

@ToString
public class SaleRequestDTO {
    @Getter
    @Setter
    PlayerDataRequestDTO player;

    @Getter
    @Setter
    OwnerDTO user;
}
