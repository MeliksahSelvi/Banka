package com.meliksah.banka.app.acc.dao;

import com.meliksah.banka.app.acc.domain.AccAccountActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccAccountActivityDao extends JpaRepository<AccAccountActivity, Long> {
}
