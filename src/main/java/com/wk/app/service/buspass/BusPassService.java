package com.wk.app.service.buspass;

import com.wk.app.facts.buspass.BusPass;
import com.wk.app.facts.buspass.Person;

/**
 * @author andrey.trotsenko
 */
public interface BusPassService {
    BusPass getBusPass(Person person);
}
