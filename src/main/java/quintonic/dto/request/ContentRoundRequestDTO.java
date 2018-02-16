package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class ContentRoundRequestDTO {
    @Getter @Setter
    List<RoundBonusRequestDTO> results;
}
