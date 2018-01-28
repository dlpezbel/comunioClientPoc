package quintonic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class TokenDTO {
    @Getter @Setter
    String token;
    public String getToken() {
        return this.token;
    }
}
