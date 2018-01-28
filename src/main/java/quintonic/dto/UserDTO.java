package quintonic.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class UserDTO {
    @Getter @Setter
    String email;
    @Setter @Getter
    String password;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
