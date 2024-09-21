package uz.pdp.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.authuser.AuthUser;
import uz.pdp.authuser.AuthUserDao;

@Service
public record CustomUserDetailsService(AuthUserDao authUserDao) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findAuthUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username '%s'".formatted(username)));
        return new CustomUserDetails(authUser);
    }
}
