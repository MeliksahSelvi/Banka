package com.meliksah.banka.app.transactional.service;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.transactional.util.TransactionUtil;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionalService {

    private final CusCustomerEntityService cusCustomerEntityService;
    private final NonTransactionalService nonTransactionalService;
    private final TransactionalService2 transactionalService2;
    private final TransactionalConstantService transactionalConstantService;

    public void save() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts2  ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2N() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts3  ");

        cusCustomerEntityService.save(cusCustomer);

        nonTransactionalService.save();

        System.out.println("end");
    }

    public void saveT2T() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts5  ");

        cusCustomerEntityService.save(cusCustomer);

        save();

        System.out.println("end");
    }

    public void saveButError() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts6  ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
        throw new RuntimeException("error");
    }

    public void saveT2RN() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts8-1  ");

        cusCustomerEntityService.save(cusCustomer);

        saveRN();
        System.out.println("end");
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void saveRN() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts8-2  ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2RNWithDifferentBean() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts9-1  ");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveRN();
        System.out.println("end");
    }

    public void saveT2RNButError() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts10  ");

        cusCustomerEntityService.save(cusCustomer);

        try {
            for (int i = 0; i < 10; i++) {
                transactionalService2.saveRN(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end");
    }

    public void saveT2RNButError2() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts18  ");

        cusCustomerEntityService.save(cusCustomer);

        for (int i = 0; i < 10; i++) {
            try {
                transactionalService2.saveRN2(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void saveMandatory() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts11-T ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    public void saveT2M() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts12-T ");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveMandatory();

        System.out.println("end");
    }

    public void saveT2S() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts13-T ");

        cusCustomerEntityService.save(cusCustomer);

        transactionalService2.saveSupport();

        System.out.println("end");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void saveSupport() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts14-S ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public void doSomething() {

        for (int i = 0; i < 9999; i++) {
            CusCustomer cusCustomer = transactionalConstantService.findById(1L);
        }
    }

    public void doSomethingWithNewTransaction() {

        for (int i = 0; i < 9999; i++) {
            CusCustomer cusCustomer = transactionalConstantService.findByIdWithNewTransaction(1L);
        }
    }

    public void saveNested() {
        transactionalService2.saveNested();
    }

    public void saveNeverTransactional() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts21");

        cusCustomerEntityService.save(cusCustomer);

        transactionalConstantService.saveT2Never();

        System.out.println("end");
    }
}
