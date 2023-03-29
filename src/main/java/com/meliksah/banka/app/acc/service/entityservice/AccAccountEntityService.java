package com.meliksah.banka.app.acc.service.entityservice;

import com.meliksah.banka.app.acc.dao.AccAccountDao;
import com.meliksah.banka.app.acc.domain.AccAccount;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import com.meliksah.banka.app.gen.service.BaseEntityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccAccountEntityService extends BaseEntityService<AccAccount, AccAccountDao> {

    public AccAccountEntityService(AccAccountDao dao) {
        super(dao);
    }

    public List<AccAccount> findAllActiveAccAccountList() {
        return getDao().findAllByStatusType(GenStatusType.ACTIVE);
    }

    public List<AccAccount> findAllActiveAccAccountList(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);

        Page<AccAccount> allByStatusType = getDao().findAllByStatusType(GenStatusType.ACTIVE, pageRequest);
        return allByStatusType.toList();
    }

    public List<AccAccount> findAllPassiveAccAccountList() {
        return getDao().findAllByStatusType(GenStatusType.PASSIVE);
    }

}
