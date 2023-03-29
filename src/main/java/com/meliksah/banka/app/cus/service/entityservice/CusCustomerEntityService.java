package com.meliksah.banka.app.cus.service.entityservice;

import com.meliksah.banka.app.cus.dao.CusCustomerDao;
import com.meliksah.banka.app.cus.domain.CusCustomer;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.stereotype.Service;

@Service
public class CusCustomerEntityService extends BaseEntityService<CusCustomer, CusCustomerDao> {

    public CusCustomerEntityService(CusCustomerDao dao) {
        super(dao);
    }

    public CusCustomer findByIdentityNo(Long identityNo) {
        return getDao().findByIdentityNo(identityNo);
    }
}
