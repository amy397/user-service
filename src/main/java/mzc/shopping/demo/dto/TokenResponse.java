package mzc.shopping.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {

private String accessToken;
private String tokenType;
private Long expiresIn;

public static TokenResponse of(String token, Long expiresIn) {
    return TokenResponse.builder()
            .accessToken(token)
            .tokenType("Bearer")
            .expiresIn(expiresIn)
            .build();
}



}
