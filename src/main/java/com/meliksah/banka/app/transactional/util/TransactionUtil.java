package com.meliksah.banka.app.transactional.util;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import org.springframework.util.StringUtils;

public class TransactionUtil {

    public static CusCustomer getCusCustomer(String suffix) {

        String testName = "test";

        if (StringUtils.hasText(suffix)) {
            testName += "-" + suffix;
        }
        CusCustomer cusCustomer = new CusCustomer();
        cusCustomer.setName(testName);
        cusCustomer.setSurName(testName);
        cusCustomer.setIdentityNo(123L);
        cusCustomer.setPassword("123");
        return cusCustomer;
    }
}
