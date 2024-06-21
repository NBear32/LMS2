package com.dw.lms.controller;

import com.dw.lms.model.Withdrawal;
import com.dw.lms.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("saveWithdrawal") // "/api/withdrawal/saveWithdrawal"
    public ResponseEntity<String> saveWithdrawal(@RequestBody Withdrawal withdrawal) {

        System.out.println("Controller: " + withdrawal.getWithdrawalId());
        System.out.println("Controller: " + withdrawal.getSysDate());
        System.out.println("Controller: " + withdrawal.getUpdDate());
        System.out.println("Controller: " + withdrawal.getWithdrawalDate());
        System.out.println("Controller: " + withdrawal.getWithdrawalReason());
        System.out.println("Controller: " + withdrawal.getUser().getUserId());

        return new ResponseEntity<>(withdrawalService.saveWithdrawal(withdrawal),
                HttpStatus.CREATED);
    }

}