package app.kncscrobbler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MappingController {

    @GetMapping("/")
    public String index() {
        return "index2";
    }
    
    @GetMapping("/index2")
    public String index2() {
        return "index2";
    }
    

    


}
