package uz.pdp.authuser;

import lombok.NonNull;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuthUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long save(@NonNull AuthUser authUser) {
        var sql = "insert into authuser(username, password, role) values(:username, :password, :role)";
        var paramSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword())
                .addValue("role", authUser.getRole());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{"id"});
        Long id = (Long) keyHolder.getKeys().get("id");
        return id;
    }

    public Optional<AuthUser> findAuthUserByUsername(@NonNull String username) {
        var sql = "select * from authuser a where a.username = :username";
        var paramSource = new MapSqlParameterSource().addValue("username", username);
        var rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            var authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper);
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
