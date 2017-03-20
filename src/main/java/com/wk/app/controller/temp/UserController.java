package com.wk.app.controller.temp;

import com.wk.app.couchbase.model.Sms;
import com.wk.app.couchbase.repository.SmsRepository;
import com.wk.app.facts.SmsBillingRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Luzko
 */
@Controller
//todo для тестов
public class UserController {

    @Autowired
    SmsRepository smsRepository;

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

    @RequestMapping(value = "/sms", method = RequestMethod.POST)
    //example {"id":1,"sender":"43423423","receiver":"4545","local":false,"date":[2017,3,17]}
    public void add(@RequestBody Sms sms) {
        com.wk.app.couchbase.model.Sms cbSms = new com.wk.app.couchbase.model.Sms();
        cbSms.setId(sms.getId());
        cbSms.setLocal(sms.isLocal());
        cbSms.setNppPerDay(sms.getNppPerDay());
        cbSms.setReceiver(sms.getReceiver());
        cbSms.setSender(sms.getSender());
        smsRepository.save(cbSms);
    }

}
