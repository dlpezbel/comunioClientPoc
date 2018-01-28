package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MarketDTO {
    @Getter @Setter
    String status;

    @Getter @Setter
    DataMarketDTO data;
}
