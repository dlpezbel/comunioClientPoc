package quintonic.dto.biwenger;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RoundRequestDTO {
    @Getter @Setter
    String type;
    @Getter @Setter
    String title;
    @Getter @Setter
    ContentRoundRequestDTO content;
}
