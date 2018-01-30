package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
public class AccountDataDTO {
    @Getter
    @Setter
    List<LeagueDTO> leagues;

}
