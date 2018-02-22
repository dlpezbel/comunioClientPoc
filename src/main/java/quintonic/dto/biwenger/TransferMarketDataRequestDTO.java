package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class TransferMarketDataRequestDTO {
    @Getter @Setter
    String type;
    @Getter @Setter
    List<TransferRequestDTO> content;
    @Getter @Setter
    long date;
}
