package com.meliksah.banka.app.transactional.service;

import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.cus.service.entityservice.CusCustomerEntityService;
import com.meliksah.banka.app.transactional.util.TransactionUtil;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionalService2 {

    private final CusCustomerEntityService cusCustomerEntityService;

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void saveRN() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts9-2  ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void saveRN(int i) {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts10-" + i);

        cusCustomerEntityService.save(cusCustomer);

        if (i == 7) {
            throw new RuntimeException("error");
        }
        System.out.println("end -> "+i);
    }

    @Transactional
    public void saveRN2(int i) {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts18-" + i);

        cusCustomerEntityService.save(cusCustomer);

        if (i == 7) {
            throw new RuntimeException("error");
        }
        System.out.println("end -> "+i);
    }

    @Transactional(Transactional.TxType.MANDATORY)
    public void saveMandatory() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts12-M ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public void saveSupport() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts13-S ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }

//    @Transactional(Transactional.TxType.NESTED)
    public void saveNested() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts16-N ");

        cusCustomerEntityService.save(cusCustomer);

        System.out.println("end");
    }
}
