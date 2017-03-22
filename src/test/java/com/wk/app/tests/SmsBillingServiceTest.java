package com.wk.app.tests;

import com.wk.app.enums.Tariff;
import com.wk.app.facts.Customer;
import com.wk.app.facts.Sms;
import com.wk.app.facts.SmsBillingRecord;
import com.wk.app.rabbit.IMessageSender;
import com.wk.app.service.CustomerService;
import com.wk.app.service.SmsBillingService;
import com.wk.app.service.SmsBillingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author andrey.trotsenko
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SmsBillingServiceTest.SmsBillingConf.class})
public class SmsBillingServiceTest {
    private static final String CUSTOMER_SENDER = "22233";
    private static final int FREE_COUNT = 5;
    private static final BigDecimal TARIFF_SIMPLE_PRICE = new BigDecimal(2);
    private static final BigDecimal TARIFF_SMART_FREE_PRICE = new BigDecimal(0);
    private static final BigDecimal TARIFF_SMART_LOCAL_PRICE = new BigDecimal(1.5);
    private static final BigDecimal TARIFF_SMART_DEFAULT = new BigDecimal(2.5);

    @Configuration
    public static class SmsBillingConf {
        @Bean
        public SmsBillingService smsBillingService() {
            return new SmsBillingServiceImpl();
        }

        @Bean
        public CustomerService customerService() {
            return Mockito.mock(CustomerService.class);
        }

        @Bean
        public KieContainer kieContainer() {
            return KieServices.Factory.get().getKieClasspathContainer();
        }

        @Bean
        public IMessageSender messageSenderOut() {
            return Mockito.mock(IMessageSender.class);
        }
    }

    @Inject
    SmsBillingService smsBillingService;

    @Inject
    CustomerService customerService;

    @Before
    public void initialize() {
        //noinspection unchecked
        ((HashMap<String, Integer>)ReflectionTestUtils.getField(smsBillingService, "smses")).clear();
    }

    /**
     * Проверяем расчет цены смс для тарифа SIMPLE в локальной сети
     */
    @Test
    public void tariffSimpleLocal() {
        mockCustomer(Tariff.SIMPLE);
        SmsBillingRecord smsBillingRecord = smsBillingService.calculateAndSave(createDefaultLocalSms());

        assertEquals(TARIFF_SIMPLE_PRICE, smsBillingRecord.getPrice());
    }

    /**
     * Проверяем расчет цены смс для тарифа SIMPLE во внешней сети
     */
    @Test
    public void tariffSimpleExternal() {
        mockCustomer(Tariff.SIMPLE);
        SmsBillingRecord smsBillingRecord = smsBillingService.calculateAndSave(createDefaultExternalSms());

        assertEquals(TARIFF_SIMPLE_PRICE, smsBillingRecord.getPrice());
    }

    /**
     *  Проверяем расчет цены бесплатных смс для тарифа SMART
     */
    @Test
    public void tariffSmartFree() {
        mockCustomer(Tariff.SMART);

        for (int i = 0; i < FREE_COUNT; i++) {
            SmsBillingRecord smsBillingRecord = smsBillingService.calculateAndSave(createDefaultLocalSms());
            assertEquals(TARIFF_SMART_FREE_PRICE, smsBillingRecord.getPrice());
        }
    }

    /**
     *  Проверяем расчет цены смс для тарифа SMART в локальной сети
     */
    @Test
    public void tariffSmartAfterFreeWithLocal() {
        mockCustomer(Tariff.SMART);

        for (int i = 0; i < FREE_COUNT; i++) {
            smsBillingService.calculateAndSave(createDefaultLocalSms());
        }

        SmsBillingRecord smsBillingRecord = smsBillingService.calculateAndSave(createDefaultLocalSms());
        assertEquals(TARIFF_SMART_LOCAL_PRICE, smsBillingRecord.getPrice());
    }


    /**
     *  Проверяем расчет цены смс для тарифа SMART во внешней сети
     */
    @Test
    public void tariffSmartAfterFreeWithExternal() {
        mockCustomer(Tariff.SMART);

        for (int i = 0; i < FREE_COUNT; i++) {
            smsBillingService.calculateAndSave(createDefaultLocalSms());
        }

        SmsBillingRecord smsBillingRecord = smsBillingService.calculateAndSave(createDefaultExternalSms());
        assertEquals(TARIFF_SMART_DEFAULT, smsBillingRecord.getPrice());
    }

    private void mockCustomer(Tariff tariff) {
        Customer customer = new Customer(CUSTOMER_SENDER, tariff);
        Mockito.doReturn(customer).when(customerService).getCustomerByNumber(CUSTOMER_SENDER);
    }

    private Sms createDefaultExternalSms() {
        return createDefaultSms(false);
    }

    private Sms createDefaultLocalSms() {
        return createDefaultSms(true);
    }

    private Sms createDefaultSms(boolean local) {
        return new Sms(CUSTOMER_SENDER, "5555", local, new Date());
    }
}
