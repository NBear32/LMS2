package com.dw.lms.service;

import com.dw.lms.model.Withdrawal;
import com.dw.lms.repository.WithdrawalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Service
@Transactional
public class WithdrawalService {
    @Autowired
    private WithdrawalRepository withdrawalRepository;

    public List<Withdrawal> getAllWithdrawal() {
        return withdrawalRepository.findAll();
    }

    @PersistenceContext
    private EntityManager entityManager;

    public String saveWithdrawal(Withdrawal withdrawal) {

        //withdrawal.setWithdrawalDate(LocalDateTime.now().toLocalDate()); // 현재일자
        //withdrawal.setSysDate(LocalDateTime.now()); // 현재일시
        //withdrawal.setUpdDate(LocalDateTime.now()); // 현재일시

//        withdrawal 테이블에 trigger 생성
//        user 테이블의 act_yn = 'N' 와
//        course_registration 테이블의 lecture_status = 'R' [수강반려] 반영

        return withdrawalRepository.save(withdrawal).getUser().getUserId(); // 없으면 Insert, 있으면 Update
    }

}