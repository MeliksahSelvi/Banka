package com.meliksah.banka.app.transactional.service;

import com.meliksah.banka.app.cus.dao.CusCustomerDao;
import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.transactional.util.TransactionUtil;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionalConstantService {

    private final CusCustomerDao cusCustomerDao;

    private Map<Long, CusCustomer> map = new LinkedHashMap<>();

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public CusCustomer findById(Long id) {

        CusCustomer cusCustomer = map.get(id);
        if (cusCustomer != null) {
            return cusCustomer;
        }

        Optional<CusCustomer> cusCustomerOptional = cusCustomerDao.findById(id);

        if (cusCustomerOptional.isPresent()) {
            cusCustomer = cusCustomerOptional.get();
        } else {
            throw new RuntimeException("error");
        }

        map.put(id, cusCustomer);

        return cusCustomer;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public CusCustomer findByIdWithNewTransaction(Long id) {

        CusCustomer cusCustomer = map.get(id);
        if (cusCustomer != null) {
            return cusCustomer;
        }

        Optional<CusCustomer> cusCustomerOptional = cusCustomerDao.findById(id);

        if (cusCustomerOptional.isPresent()) {
            cusCustomer = cusCustomerOptional.get();
        } else {
            throw new RuntimeException("error");
        }

        map.put(id, cusCustomer);

        return cusCustomer;
    }

    @Transactional(Transactional.TxType.NEVER)
    public void saveT2Never() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts21-NEVER ");

        cusCustomerDao.save(cusCustomer);

        System.out.println("end");
    }
}
