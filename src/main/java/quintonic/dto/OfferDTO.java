package quintonic.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferDTO {
    @Getter @Setter
    Integer id;
    @Getter @Setter
    Integer amount;
    @Getter @Setter
    List<Integer> requestedPlayers;
    @Getter @Setter
    UserPlayersDTO from;
    @Getter @Setter
    UserPlayersDTO to;
    @Getter @Setter
    String type;
}
