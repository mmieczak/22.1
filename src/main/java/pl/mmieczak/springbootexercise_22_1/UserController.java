package pl.mmieczak.springbootexercise_22_1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private pl.mmieczak.springbootexercise_22_1.UserRepository userRepository;

    public UserController(pl.mmieczak.springbootexercise_22_1.UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@PostMapping("/add")
    @RequestMapping("/add")
    public String add(@RequestParam String name, @RequestParam(defaultValue = "", required = false) String surname, @RequestParam(defaultValue = "-1", required = true) String age) {
        int ageToInt = Integer.parseInt(age);
        boolean result = true;
        if (!name.equals("") && ageToInt >= 0) {
            userRepository.add(name, surname, Integer.parseInt(age));
        } else {
            System.out.println("Age < 0");
            result = false;
        }
        return result ? "redirect:success.html" : "redirect:err.html";
    }

    @ResponseBody
    @RequestMapping("/users")
    public String showList() {
        List<User> userList = userRepository.getAll();
        String itemsList = userList.stream()
                .map(User -> "<tr><td>" + User.getName() + "</td><td>" + User.getSurname() + "</td><td>" + User.getAge() + "</td></tr>")
                .collect(Collectors.joining());

        //na pewno da sie to zrobic inaczej, zakladam ze templatem ktorego jeszcze nie widzialem ;)
        String tableBegin = "<table class=\"table\"><thead><tr><th scope=\"col\">Name</th><th scope=\"col\">Surname</th><th scope=\"col\">Age</th></tr></thead><tbody>";
        String tableEnd = "</tbody></table>";
        return tableBegin + itemsList + tableEnd;
    }
}

