package com.meliksah.banka.app.transactional.service;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.transactional.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NonTransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private TransactionalService transactionalService;
    private final NonTransactionalConstantService nonTransactionalConstantService;

    @Autowired
    public void setTransactionalService(@Lazy TransactionalService transactionalService) {
        this.transactionalService = transactionalService;
    }

    public void save() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts1");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2N() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts4  ");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService.save();

        System.out.println("end");
    }

    public void saveButError() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts7  ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
        throw new RuntimeException("error");
    }

    public void saveN2M() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts11-N ");

        transactionalService.saveMandatory();

        System.out.println("end");
    }

    public void saveN2S() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts14-N ");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService.saveSupport();

        System.out.println("end");
    }

    public void doSomething() {

        for (int i = 0; i < 9999; i++) {
            CusCustomer cusCustomer = nonTransactionalConstantService.findById(1L);
        }
    }

    public void saveNever() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts20");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalConstantService.saveNon2Never();

        System.out.println("end");
    }
}
