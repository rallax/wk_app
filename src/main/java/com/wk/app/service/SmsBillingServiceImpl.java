package com.wk.app.service;

import com.wk.app.facts.Customer;
import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.utill.FactFinder;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;

/**
 * @author andrey.trotsenko
 */

@Service
public class SmsBillingServiceImpl implements SmsBillingService {
    Logger logger = LoggerFactory.getLogger(SmsBillingServiceImpl.class);

    @Inject
    CustomerService customerService;

    @Inject
    KieContainer kieContainer;

    HashMap<String, Integer> smses = new HashMap<>();

    @Override
    public SmsBillingRecord calculate(Sms sms) {
        Customer customer = customerService.getCustomerByNumber(sms.getSender());
        if(customer == null)
            throw new NullPointerException("Customer is null");

        saveSmsCount(customer, sms);
        setNppPerDay(customer, sms);

        KieSession kieSession = null;
        try {
            final String KSESSION_RULES = "ksession-sms-billing-rules";
            kieSession = kieContainer.newKieSession(KSESSION_RULES);
            kieSession.insert(customer);
            kieSession.insert(sms);

            kieSession.fireAllRules();

            return FactFinder.findFact(kieSession, SmsBillingRecord.class);

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            if(kieSession != null) {
                kieSession.dispose();
            }
        }
    }

    private void saveSmsCount(Customer customer, Sms sms) {
        String key = buildSmsCountKey(customer, sms);
        smses.computeIfPresent(key, (k, v) -> v + 1);
        smses.computeIfAbsent(key, (k) -> 1);
    }

    private void setNppPerDay(Customer customer, Sms sms) {
        sms.setNppPerDay(smses.get(buildSmsCountKey(customer, sms)));
    }

    private static String buildSmsCountKey(Customer customer, Sms sms) {
        return customer.getNumber() + "_" + sms.getDate().toEpochDay();
    }

    public int getSmsCountPerDay(Customer customer, Sms sms) {
        return smses.get(buildSmsCountKey(customer, sms));
    }


}
