package com.example.jsr303assignment;

import com.example.validator.RegularAmountConstraint;

@RegularAmountConstraint
public class RegularAmount {
    private Frequency frequency;
    private String amount;
    
    private String DEVELOPER_UPDATE;
    private String DEVELOPER_UPDATE2;
    private String DEVELOPER_UPDATE3_LOCAL;
    private String DEVELOPER_UPDATE4_LOCAL;

    private String DEVELOPER_UPDATE5;
    private String DEVELOPER_UPDATE6_LOCAL_MASTER;
    private String DEVELOPER_UPDATE7;
    private String DEVELOPER_UPDATE8_LOCAL;

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

