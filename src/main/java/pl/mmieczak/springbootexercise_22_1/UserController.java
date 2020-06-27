package pl.mmieczak.springbootexercise_22_1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ResponseBody
    @RequestMapping(value = "/users")
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

    @ResponseBody
    @RequestMapping(value = "/submit", method = RequestMethod.POST, params = {"search"})
    public String search(@RequestParam(defaultValue = "", required = true) String name, @RequestParam(defaultValue = "", required = true) String surname, @RequestParam(defaultValue = "-1", required = false) String age) {
        Stream<User> filteredData = userRepository.filterDataUsingFormValues(name, surname, age);
        String itemsList = filteredData
                .map(User -> "<tr><td>" + User.getName() + "</td><td>" + User.getSurname() + "</td><td>" + User.getAge() + "</td></tr>")
                .collect(Collectors.joining());

        //na pewno da sie to zrobic inaczej, zakladam ze templatem ktorego jeszcze nie widzialem ;)
        String tableBegin = "<table class=\"table\"><thead><tr><th scope=\"col\">Name</th><th scope=\"col\">Surname</th><th scope=\"col\">Age</th></tr></thead><tbody>";
        String tableEnd = "</tbody></table>";
        return tableBegin + itemsList + tableEnd;
    }


    @RequestMapping(value = "/submit", method = RequestMethod.POST, params = {"remove"})
    public String remove(@RequestParam(defaultValue = "", required = true) String name, @RequestParam(defaultValue = "", required = true) String surname, @RequestParam(defaultValue = "-1", required = false) String age) {
        Stream<User> filteredData = userRepository.filterDataUsingFormValues(name, surname, age);
        userRepository.removeAll(filteredData);
        return "redirect:/users";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST, params = {"add"})
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
}
