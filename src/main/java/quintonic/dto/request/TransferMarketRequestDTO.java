package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import quintonic.dto.DataDTO;

import java.util.List;

public class TransferMarketRequestDTO  extends DataDTO {
    @Getter @Setter
    List<TransferMarketDataRequestDTO> data;
}