package com.meliksah.banka.app.log.service.entityservice;

import com.meliksah.banka.app.gen.service.BaseEntityService;
import com.meliksah.banka.app.log.dao.LogDetailDao;
import com.meliksah.banka.app.log.entity.LogDetail;
import org.springframework.stereotype.Service;

@Service
public class LogDetailEntityService extends BaseEntityService<LogDetail, LogDetailDao> {
    public LogDetailEntityService(LogDetailDao dao) {
        super(dao);
    }
}
