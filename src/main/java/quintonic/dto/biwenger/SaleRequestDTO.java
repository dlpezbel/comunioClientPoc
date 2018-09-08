package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.OwnerDTO;

import java.util.Date;

@ToString
public class SaleRequestDTO {
    @Getter
    @Setter
    PlayerDataRequestDTO player;

    @Getter
    @Setter
    int price;

    @Getter
    @Setter
    Date date;

    @Getter
    @Setter
    Date until;

    @Getter
    @Setter
    OwnerDTO user;
}
