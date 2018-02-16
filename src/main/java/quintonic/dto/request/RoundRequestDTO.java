package quintonic.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class RoundRequestDTO {
    @Getter @Setter
    String type;
    @Getter @Setter
    String title;
    @Getter @Setter
    ContentRoundRequestDTO content;
}
