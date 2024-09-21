package uz.pdp.vocabulary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.config.security.SessionUser;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class VocabularyController {
    private final SessionUser sessionUser;
    private final VocabularyDao vocabularyDao;

    @GetMapping("/home")
    public String homePage(Model model, @RequestParam(name = "name", required = false, defaultValue = "4") int limit) {
        List<Vocabulary> vocabularies = vocabularyDao.findByUserId(sessionUser.getUserId(), limit);
        model.addAttribute("vocabularies", vocabularies);
        model.addAttribute("lim", limit);
        return "home";
    }

    @GetMapping("/vocabulary/add")
    public String createVocabularyPage() {
        return "vocabulary_create";
    }

    @PostMapping("/vocabulary/add")
    public String createVocabulary(@ModelAttribute VocabularyCreateDTO dto) {
        // TODO: change userID from static to SecurityContextHolder's user id
        System.out.println(dto);
        vocabularyDao.saveVocabulary(dto, sessionUser.getUserId());
        return "redirect:/home";
    }

    @GetMapping("/vocabulary/all")
    public String allVocabularyPage(Model model) {
        List<Vocabulary> vocabularyList = vocabularyDao.findByUserId(sessionUser.getUserId());
        Map<LocalDate, List<Vocabulary>> vocabularies =
                vocabularyList.stream().collect(Collectors.groupingBy(Vocabulary::getCreateDate));
        model.addAttribute("vocabularies", vocabularies);
        return "vocabulary_list";
    }
}
