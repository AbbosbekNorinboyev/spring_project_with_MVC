package uz.pdp.authuser;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AuthUser {
    private Long id;
    private String username;
    private String password;
    private String role;

    public enum AuthRole {
        ADMIN, USER
    }
}