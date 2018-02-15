package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.request.SaleRequestDTO;

import java.util.List;

@ToString
public class DataMarketDTO {
    @Getter @Setter
    List<SaleRequestDTO> sales;
}
