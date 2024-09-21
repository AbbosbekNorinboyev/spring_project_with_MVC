package uz.pdp.vocabulary;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VocabularyDao {
    private final JdbcTemplate jdbcTemplate;

    public void saveVocabulary(@NonNull VocabularyCreateDTO dto, Long userID) {
        var sql = "insert into vocabulary (word, translations, synonyms, examples, userid) values (?, ?, ?, ?, ?);";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, dto.word());
            preparedStatement.setArray(2, con.createArrayOf("varchar", dto.translations().toArray()));
            preparedStatement.setArray(3, con.createArrayOf("varchar", dto.synonyms().toArray()));
            preparedStatement.setArray(4, con.createArrayOf("varchar", dto.examples().toArray()));
            preparedStatement.setLong(5, userID);
            return preparedStatement;
        });
    }

    public List<Vocabulary> findByUserId(long userId, int limit) {
        var sql = "SELECT * FROM vocabulary WHERE userid = ? ORDER BY gen_random_uuid() LIMIT ?";
        System.out.println("limit: " + limit);
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            List<String> translations = Arrays.asList((String[]) resultSet.getArray("translations").getArray());
            List<String> synonyms = Arrays.asList((String[]) resultSet.getArray("synonyms").getArray());
            List<String> examples = Arrays.asList((String[]) resultSet.getArray("examples").getArray());
            return Vocabulary.builder()
                    .id(resultSet.getLong("id"))
                    .word(resultSet.getString("word"))
                    .translations(translations)
                    .synonyms(synonyms)
                    .examples(examples)
                    .userID(resultSet.getLong("userid"))
                    .createDate(resultSet.getDate("create_date").toLocalDate())
                    .build();
        }, userId, limit);
    }

    public List<Vocabulary> findByUserId(long userId) {
        var sql = "SELECT * FROM vocabulary WHERE userid = ? ORDER BY create_date desc";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            List<String> translations = Arrays.asList((String[]) resultSet.getArray("translations").getArray());
            List<String> synonyms = Arrays.asList((String[]) resultSet.getArray("synonyms").getArray());
            List<String> examples = Arrays.asList((String[]) resultSet.getArray("examples").getArray());
            return Vocabulary.builder()
                    .id(resultSet.getLong("id"))
                    .word(resultSet.getString("word"))
                    .translations(translations)
                    .synonyms(synonyms)
                    .examples(examples)
                    .userID(resultSet.getLong("userid"))
                    .createDate(resultSet.getDate("create_date").toLocalDate())
                    .build();
        }, userId);
    }
}
