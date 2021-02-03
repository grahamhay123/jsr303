package com.example.jsr303assignment;

import com.example.validator.RegularAmountConstraint;

@RegularAmountConstraint
public class RegularAmount {
    private Frequency frequency;
    private String amount;
    
    private String DEVELOPER_UPDATE1;
    private String DEVELOPER_UPDATE2;
    private String DEVELOPER_UPDATE3;

    public Frequency getFrequency() {
        return frequency;
    }

    private String REMOTE_COMMIT;
    private String REMOTE_COMMIT2;
    
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

