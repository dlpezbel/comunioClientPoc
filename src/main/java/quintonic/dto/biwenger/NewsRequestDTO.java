package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import quintonic.dto.DataDTO;

import java.util.List;

@ToString
public class NewsRequestDTO  extends DataDTO {
    @Getter
    @Setter
    List<RoundRequestDTO> data;
}
