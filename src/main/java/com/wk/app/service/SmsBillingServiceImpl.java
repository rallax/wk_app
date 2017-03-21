package com.wk.app.service;

import com.wk.app.facts.Customer;
import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.facts.SmsCountPerDay;
import com.wk.app.utill.FactFinder;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    HashMap<String, List<SmsBillingRecord>> smses = new HashMap<>();

    @Override
    public SmsBillingRecord calculateAndSave(Sms sms) {
        Customer customer = customerService.getCustomerByNumber(sms.getSender());
        if(customer == null)
            throw new NullPointerException("Customer is null");

        KieSession kieSession = null;
        try {
            final String KSESSION_RULES = "ksession-sms-billing-rules";
            kieSession = kieContainer.newKieSession(KSESSION_RULES);
            kieSession.insert(customer);
            kieSession.insert(sms);
            kieSession.insert(new SmsCountPerDay(customer.getNumber(), getSmsCountPerDay(customer, sms) + 1, sms.getDate()));

            kieSession.fireAllRules();

            SmsBillingRecord smsBillingRecord = FactFinder.findFact(kieSession, SmsBillingRecord.class);
            if(smsBillingRecord == null)
                throw new NullPointerException("SmsBillingRecord is null");

            logger.info("Price {} for customer {} tariff {}", smsBillingRecord.getPrice(), customer.getNumber(), customer.getTariff());

            saveSmsBillingRecord(smsBillingRecord);

            logger.info("Save smsBillingRecord");

            return smsBillingRecord;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            if(kieSession != null) {
                kieSession.dispose();
            }
        }
    }

    private static String buildSmsCountKey(Customer customer, Sms sms) {
        return customer.getNumber() + "_" + sms.getDate().toEpochDay();
    }

    private void saveSmsBillingRecord(SmsBillingRecord smsBillingRecord) {
        String key = buildSmsCountKey(smsBillingRecord.getCustomer(), smsBillingRecord.getSms());
        smses.computeIfPresent(key, (k, v) -> {v.add(smsBillingRecord); return v;});
        smses.computeIfAbsent(key, (k) -> {List<SmsBillingRecord> smsBillingRecords = new ArrayList<>();
            smsBillingRecords.add(smsBillingRecord); return smsBillingRecords;});
    }

    public int getSmsCountPerDay(Customer customer, Sms sms) {
        List<SmsBillingRecord> smsBillingRecords = smses.get(buildSmsCountKey(customer, sms));
        return smsBillingRecords == null ? 0 : smsBillingRecords.size();
    }


}
