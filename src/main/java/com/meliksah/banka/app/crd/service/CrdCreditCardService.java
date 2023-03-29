package com.meliksah.banka.app.crd.service;

import com.meliksah.banka.app.crd.domain.CrdCreditCard;
import com.meliksah.banka.app.crd.domain.CrdCreditCardActivity;
import com.meliksah.banka.app.crd.dto.*;
import com.meliksah.banka.app.crd.enums.CrdCreditCardActivityType;
import com.meliksah.banka.app.crd.enums.CrdErrorMessage;
import com.meliksah.banka.app.crd.mapper.CrdCreditCardActivityMapper;
import com.meliksah.banka.app.crd.mapper.CrdCreditCardMapper;
import com.meliksah.banka.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.meliksah.banka.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.meliksah.banka.app.gen.enums.GenStatusType;
import com.meliksah.banka.app.gen.exception.exceptions.GenBusinessException;
import com.meliksah.banka.app.gen.util.DateUtil;
import com.meliksah.banka.app.gen.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    public List<CrdCreditCardReponseDto> findAll() {
        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllActiveCreditCardList();

        List<CrdCreditCardReponseDto> crdCreditCardReponseDtoList = CrdCreditCardMapper.INSTANCE.crdCreditCardListToCrdCreditCardReponseDtoList(crdCreditCardList);

        return crdCreditCardReponseDtoList;
    }

    public List<CrdCreditCardReponseDto> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllActiveCreditCardList(pageOptional,sizeOptional);

        List<CrdCreditCardReponseDto> crdCreditCardReponseDtoList = CrdCreditCardMapper.INSTANCE.crdCreditCardListToCrdCreditCardReponseDtoList(crdCreditCardList);

        return crdCreditCardReponseDtoList;
    }

    public CrdCreditCardReponseDto findById(Long id) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);
        CrdCreditCardReponseDto crdCreditCardReponseDto = CrdCreditCardMapper.INSTANCE.crdCreditCardToCrdCreditCardReponseDto(crdCreditCard);
        return crdCreditCardReponseDto;
    }

    @Transactional
    public void cancel(Long cardId) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(cardId);
        crdCreditCard.setStatusType(GenStatusType.PASSIVE);
        crdCreditCard.setCancelDate(new Date());
        crdCreditCardEntityService.save(crdCreditCard);
    }

    @Transactional
    public CrdCreditCardActivityDto payment(CrdCreditCardPaymentDto crdCreditCardPaymentDto) {
        Long crdCreditCardId = crdCreditCardPaymentDto.getCrdCreditCardId();
        BigDecimal amount = crdCreditCardPaymentDto.getAmount();

        updateCreditCardForPayment(crdCreditCardId, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForPayment(crdCreditCardId, amount);

        CrdCreditCardActivityDto crdCreditCardActivityDto = CrdCreditCardActivityMapper.INSTANCE.crdCreditCardActivityToCrdCreditCardActivityDto(crdCreditCardActivity);
        return crdCreditCardActivityDto;
    }

    private CrdCreditCard updateCreditCardForPayment(Long crdCreditCardId, BigDecimal amount) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(crdCreditCardId);

        crdCreditCard = addLimitToCard(crdCreditCard, amount);
        return crdCreditCard;
    }

    private CrdCreditCardActivity createCrdCreditCardActivityForPayment(Long crdCreditCardId, BigDecimal amount) {
        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCardId);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription("PAYMENT");
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.PAYMENT);
        crdCreditCardActivity.setTransactionDate(new Date());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    @Transactional
    public CrdCreditCardActivityDto refund(Long activityId) {
        CrdCreditCardActivity oldCrdCreditCardActivity = crdCreditCardActivityEntityService.getByIdWithControl(activityId);
        BigDecimal amount = oldCrdCreditCardActivity.getAmount();

        CrdCreditCard crdCreditCard = updateCreditCardForRefund(oldCrdCreditCardActivity, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForRefund(oldCrdCreditCardActivity, amount, crdCreditCard);

        CrdCreditCardActivityDto crdCreditCardActivityDto = CrdCreditCardActivityMapper.INSTANCE.crdCreditCardActivityToCrdCreditCardActivityDto(crdCreditCardActivity);
        return crdCreditCardActivityDto;
    }

    private CrdCreditCard updateCreditCardForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(oldCrdCreditCardActivity.getCrdCreditCardId());

        crdCreditCard = addLimitToCard(crdCreditCard, amount);
        return crdCreditCard;
    }

    private CrdCreditCard addLimitToCard(CrdCreditCard crdCreditCard, BigDecimal amount) {
        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().add(amount);

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    private CrdCreditCardActivity createCrdCreditCardActivityForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount, CrdCreditCard crdCreditCard) {
        String description = "REFUND -> " + oldCrdCreditCardActivity.getDescription();

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setTransactionDate(new Date());
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.REFUND);

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    @Transactional
    public CrdCreditCardActivityDto spend(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        BigDecimal amount = crdCreditCardSpendDto.getAmount();
        String description = crdCreditCardSpendDto.getDescription();

        CrdCreditCard crdCreditCard = getCrdCreditCard(crdCreditCardSpendDto);

        validateCreditCard(crdCreditCard);

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().subtract(amount);

        validatedCardLimit(currentAvailableLimit);

        crdCreditCard = updateCreditCardForSpend(crdCreditCard, currentDebt, currentAvailableLimit);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivity(amount, description, crdCreditCard);
        CrdCreditCardActivityDto crdCreditCardActivityDto = CrdCreditCardActivityMapper.INSTANCE.crdCreditCardActivityToCrdCreditCardActivityDto(crdCreditCardActivity);
        return crdCreditCardActivityDto;
    }

    private CrdCreditCard updateCreditCardForSpend(CrdCreditCard crdCreditCard, BigDecimal currentDebt, BigDecimal currentAvailableLimit) {
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard.setCurrentDebt(currentDebt);

        return crdCreditCardEntityService.save(crdCreditCard);
    }

    private CrdCreditCard getCrdCreditCard(CrdCreditCardSpendDto crdCreditCardSpendDto) {
        Long cardNo = crdCreditCardSpendDto.getCardNo();
        Long cvvNo = crdCreditCardSpendDto.getCvvNo();
        Date expireDate = crdCreditCardSpendDto.getExpireDate();
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.findByCardNoAndCvvNoAndExpireDate(cardNo, cvvNo, expireDate);
        return crdCreditCard;
    }

    private void validatedCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new GenBusinessException(CrdErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT);
        }
    }

    private void validateCreditCard(CrdCreditCard crdCreditCard) {
        if (crdCreditCard == null) {
            throw new GenBusinessException(CrdErrorMessage.INVALID_CARD);
        }

        if (crdCreditCard.getExpireDate().before(new Date())) {
            throw new GenBusinessException(CrdErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }

    private CrdCreditCardActivity createCrdCreditCardActivity(BigDecimal amount, String description, CrdCreditCard crdCreditCard) {
        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setTransactionDate(new Date());
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.SPEND);

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    @Transactional
    public CrdCreditCardReponseDto saveCreditCard(CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto) {
        BigDecimal earning = crdCreditCardSaveRequestDto.getEarning();
        String cutoffDayStr = crdCreditCardSaveRequestDto.getCutoffDay();

        BigDecimal limit = calculateLimit(earning);

        LocalDate cutoffDateLocal = getCutoffDateLocal(cutoffDayStr);
        Date cutoffDate = DateUtil.convertToDate(cutoffDateLocal);
        Date dueDate = getDueDate(cutoffDateLocal);

        CrdCreditCardReponseDto crdCreditCardReponseDto = createCardAndConvertToCreditCardResponseDto(limit, cutoffDate, dueDate);

        return crdCreditCardReponseDto;
    }


    private CrdCreditCardReponseDto createCardAndConvertToCreditCardResponseDto(BigDecimal limit, Date cutoffDate, Date dueDate) {
        CrdCreditCard crdCreditCard = createCreditCard(limit, cutoffDate, dueDate);

        CrdCreditCardReponseDto crdCreditCardReponseDto = CrdCreditCardMapper.INSTANCE.crdCreditCardToCrdCreditCardReponseDto(crdCreditCard);
        return crdCreditCardReponseDto;
    }

    private CrdCreditCard createCreditCard(BigDecimal limit, Date cutoffDate, Date dueDate) {
        Long cusCustomerId = crdCreditCardEntityService.getCurrentCustomerId();
        Date expireDate = getExpireDate();
        Long cardNo = getCardNo();
        Long cvvNo = getCvvNo();

        CrdCreditCard crdCreditCard = new CrdCreditCard();
        crdCreditCard.setCusCustomerId(cusCustomerId);
        crdCreditCard.setCardNo(cardNo);
        crdCreditCard.setCvvNo(cvvNo);
        crdCreditCard.setDueDate(dueDate);
        crdCreditCard.setCutoffDate(cutoffDate);
        crdCreditCard.setExpireDate(expireDate);
        crdCreditCard.setTotalLimit(limit);
        crdCreditCard.setAvailableCardLimit(limit);
        crdCreditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        crdCreditCard.setCurrentDebt(BigDecimal.ZERO);
        crdCreditCard.setStatusType(GenStatusType.ACTIVE);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    private BigDecimal calculateLimit(BigDecimal earning) {
        BigDecimal multiply = earning.multiply(BigDecimal.valueOf(3));
        return multiply;
    }

    private Date getExpireDate() {
        LocalDate expireDateLocal = getExpireDateLocal();
        Date expireDate = DateUtil.convertToDate(expireDateLocal);
        return expireDate;
    }

    private LocalDate getExpireDateLocal() {
        return LocalDate.now().plusYears(3);
    }

    private Date getDueDate(LocalDate cutoffDateLocal) {
        LocalDate dueDateLocal = getDueDateLocal(cutoffDateLocal);
        Date dueDate = DateUtil.convertToDate(dueDateLocal);
        return dueDate;
    }

    private LocalDate getDueDateLocal(LocalDate cutoffDateLocal) {
        LocalDate localDate = cutoffDateLocal.plusDays(10);
        return localDate;
    }

    private LocalDate getCutoffDateLocal(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        LocalDate cutoffDateLocal = LocalDate.of(currentYear, nextMonth, cutoffDay);
        return cutoffDateLocal;
    }

    private Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)) {
            cutoffDayStr = "1";
        }

        Integer cutoffDay = Integer.valueOf(cutoffDayStr);
        return cutoffDay;
    }

    private Long getCvvNo() {
        Long cvvNo = NumberUtil.getRandomNumber(3);
        return cvvNo;
    }

    private Long getCardNo() {
        Long cardNo = NumberUtil.getRandomNumber(16);
        return cardNo;
    }

    public List<CrdCreditCardActivityDto> findAllActivities(Long cardId, Date startDate, Date endDate) {
        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(cardId, startDate, endDate);


        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardActivityMapper.INSTANCE
                .crdCreditCardActivityListToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return crdCreditCardActivityDtoList;
    }

    public List<CrdCreditCardActivityDto> findAllActivities(Long cardId, Date startDate, Date endDate,
                                                            Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(cardId, startDate, endDate,pageOptional,sizeOptional);


        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardActivityMapper.INSTANCE
                .crdCreditCardActivityListToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return crdCreditCardActivityDtoList;
    }

    public CrdCreditCardDetails statement(Long cardId) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(cardId);

        Date termEndDate = crdCreditCard.getCutoffDate();

        LocalDate cutoffDateLocal = DateUtil.convertToLocalDate(termEndDate);

        LocalDate termStartDateLocal = cutoffDateLocal.minusMonths(1);
        Date termStartDate = DateUtil.convertToDate(termStartDateLocal);

        CrdCreditCardDetails crdCreditCardDetails = crdCreditCardEntityService.getCrdCreditCardDetails(cardId);

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(cardId, termStartDate, termEndDate);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardActivityMapper.INSTANCE.crdCreditCardActivityListToCrdCreditCardActivityDtoList(crdCreditCardActivityList);
        crdCreditCardDetails.setCrdCreditCardActivityDtoList(crdCreditCardActivityDtoList);

        return crdCreditCardDetails;
    }
}
