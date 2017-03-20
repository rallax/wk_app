package com.wk.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Luzko
 */
@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        logger.info("index");
        return "index";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public List<String> hello() {
        System.out.println("hello");
        final List<String> users = new ArrayList<>();
        users.add("userRal(Тест JSON)");
        users.add("userFass");
        return users;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        System.out.println("login url request");
        return "login";
    }
}
