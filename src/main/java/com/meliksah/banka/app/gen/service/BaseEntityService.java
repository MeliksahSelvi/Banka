package com.meliksah.banka.app.gen.service;

import com.meliksah.banka.app.gen.entity.BaseAdditionalFields;
import com.meliksah.banka.app.gen.entity.BaseEntity;
import com.meliksah.banka.app.gen.enums.GenErrorMessage;
import com.meliksah.banka.app.gen.exception.exceptions.ItemNotFoundException;
import com.meliksah.banka.app.sec.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>> {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

    private final D dao;
    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public List<E> findAll() {
        return dao.findAll();
    }

    public List<E> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        PageRequest pageRequest = getPageRequest(pageOptional, sizeOptional);
        Page<E> allEntity = dao.findAll(pageRequest);
        return allEntity.toList();
    }

    public E save(E entity) {
        setAdditionalsFields(entity);
        entity = dao.save(entity);
        return entity;
    }

    public void delete(E entity) {
        dao.delete(entity);
    }

    public Optional<E> findById(Long id) {
        return dao.findById(id);
    }

    public E getByIdWithControl(Long id) {
        Optional<E> entityOptional = dao.findById(id);

        E entity;
        if (entityOptional.isPresent()) {
            entity = entityOptional.get();
        } else {
            throw new ItemNotFoundException(GenErrorMessage.ITEM_NOT_FOUND);
        }
        return entity;
    }

    public boolean existsById(Long id) {
        return dao.existsById(id);
    }

    private void setAdditionalsFields(E entity) {
        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentCustomerId = getCurrentCustomerId();

        if (baseAdditionalFields == null) {
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null) {
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentCustomerId);
        }

        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentCustomerId);
    }

    public Long getCurrentCustomerId() {
        Long currentCustomerId = authenticationService.getCurrentCustomerId();
        return currentCustomerId;
    }

    protected PageRequest getPageRequest(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        Integer page = getPageOptionalValue(pageOptional);

        Integer size = getSizeOptionalValue(sizeOptional);

        PageRequest pageRequest = PageRequest.of(page, size);
        return pageRequest;
    }

    private Integer getPageOptionalValue(Optional<Integer> pageOptional) {
        Integer page = DEFAULT_PAGE;
        if (pageOptional.isPresent()) {
            page = pageOptional.get();
        }
        return page;
    }

    private Integer getSizeOptionalValue(Optional<Integer> sizeOptional) {
        Integer size = DEFAULT_SIZE;
        if (sizeOptional.isPresent()) {
            size = sizeOptional.get();
        }
        return size;
    }

    public D getDao() {
        return dao;
    }
}
