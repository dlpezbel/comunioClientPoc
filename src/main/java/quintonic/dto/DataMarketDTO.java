package quintonic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.biwenger.OfferRequestDTO;
import quintonic.dto.biwenger.SaleRequestDTO;

import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataMarketDTO {
    @Getter @Setter
    List<SaleRequestDTO> sales;

    @Getter @Setter
    List<OfferRequestDTO> offers;
}
