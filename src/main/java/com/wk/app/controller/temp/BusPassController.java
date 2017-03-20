package com.wk.app.controller.temp;

import com.wk.app.facts.buspass.BusPass;
import com.wk.app.facts.buspass.Person;
import com.wk.app.service.buspass.BusPassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * @author Roman Luzko
 */
@RestController
public class BusPassController {
    private static Logger log = LoggerFactory.getLogger(BusPassController.class);

    @Inject
    private BusPassService busPassService;

    @RequestMapping(value = "/buspass",
            method = RequestMethod.GET, produces = "application/json")
    public BusPass getQuestions(@RequestParam(required = true) String name, @RequestParam(required = true) int age) {
        Person person = new Person(name, age);
        System.out.println("Bus pass request received for: " + person);
        BusPass busPass = busPassService.getBusPass(person);
        return busPass;
    }

}
