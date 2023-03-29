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
public class NonTransactionalConstantService {

    private final CusCustomerDao cusCustomerDao;

    private Map<Long, CusCustomer> map = new LinkedHashMap<>();

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

    @Transactional(Transactional.TxType.NEVER)
    public void saveNon2Never() {
        CusCustomer cusCustomer = TransactionUtil.getCusCustomer("ts20-Non ");

        cusCustomerDao.save(cusCustomer);

        System.out.println("end");
    }
}
