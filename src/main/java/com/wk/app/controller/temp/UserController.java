package com.wk.app.controller.temp;

import com.wk.app.couchbase.repository.SmsRepository;
import com.wk.app.facts.Sms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman Luzko
 */
//todo для тестов
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //example {"id":1,"sender":"43423423","receiver":"4545","local":false,"date":[2017,3,17]}
    public ResponseEntity<Sms> register(@RequestBody Sms sms) {
        try {
            com.wk.app.couchbase.model.Sms cbSms = new com.wk.app.couchbase.model.Sms();
            cbSms.setId(1L);
            cbSms.setLocal(sms.isLocal());
            cbSms.setReceiver(sms.getReceiver());
            cbSms.setSender(sms.getSender());
            smsRepository.save(cbSms);
            return new ResponseEntity<>(sms, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
