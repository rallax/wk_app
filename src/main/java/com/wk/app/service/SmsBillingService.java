package com.wk.app.service;

import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;

/**
 * @author andrey.trotsenko
 */
public interface SmsBillingService {
    SmsBillingRecord calculateAndSave(Sms sms);
}
