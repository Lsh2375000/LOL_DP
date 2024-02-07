package dp.lol.lol_dp.lol;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping({"/", "lol"})
public class MainController {

    @GetMapping("")
    public String mainGET() {
        log.info("MainController mainGET().....");

        return "lol/main";
    }
}
