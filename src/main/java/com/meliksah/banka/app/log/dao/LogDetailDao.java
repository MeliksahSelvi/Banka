package com.meliksah.banka.app.log.dao;

import com.meliksah.banka.app.log.entity.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDetailDao extends JpaRepository<LogDetail,Long>{
}
