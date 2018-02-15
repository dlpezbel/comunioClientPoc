package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class OfferDTO {
    @Getter @Setter
    Integer amount;
    @Getter @Setter
    List<Integer> requestedPlayers;
    @Getter @Setter
    String to;
    @Getter @Setter
    String type;
}
