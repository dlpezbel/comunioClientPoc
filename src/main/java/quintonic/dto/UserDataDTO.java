package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserDataDTO extends DataDTO{
    @Getter @Setter
    UserPlayersDTO data;
}
