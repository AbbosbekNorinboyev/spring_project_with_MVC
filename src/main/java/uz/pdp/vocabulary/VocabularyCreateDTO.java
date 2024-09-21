package uz.pdp.vocabulary;

import java.util.List;

public record VocabularyCreateDTO(String word, List<String> translations,
        List<String> synonyms, List<String> examples) {
}
