package mzc.shopping.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import mzc.shopping.demo.entity.User;


@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {

private String accessToken;
private String tokenType;
private Long expiresIn;
private User user;

public static TokenResponse of(String token, Long expiresIn, User user) {
    return TokenResponse.builder()
            .accessToken(token)
            .tokenType("Bearer")
            .expiresIn(expiresIn)
            .user(user)
            .build();
}



}
