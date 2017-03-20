package com.wk.app.controller.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Luzko
 */
@Controller
//todo для тестов
public class UserController {
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getQuestions(@RequestParam(required = true) String name, @RequestParam(required = true) int age) {
        System.out.println("create User " + "(" + age + ")");
        return new String("OK! SUCCESS");
    }

    @RequestMapping(value = "/getusers", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<String> hello() {
        final List<String> users = new ArrayList<>();
        users.add("userRal(Тест JSON)");
        users.add("userFass");
        return users;
    }

}
