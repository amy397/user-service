package mzc.shopping.demo.dto;


import lombok.Builder;
import lombok.Getter;
import mzc.shopping.demo.entity.User;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private String role;
    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .phone(user.getPhone())
        .role(user.getRole().name())
         .createdAt(user.getCreatedAt())
          .build();
    }








}
