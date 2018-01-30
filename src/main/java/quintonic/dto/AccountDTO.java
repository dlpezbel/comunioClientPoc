package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class AccountDTO extends DataDTO{
    @Getter
    @Setter
    AccountDataDTO data;
}
