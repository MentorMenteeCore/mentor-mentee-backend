package com.mentormentee.core.domain;

public enum GradeStatus {
    APLUS("A+"),
    A("A"),
    BPLUS("B+"),
    B("B"),
    CPLUS("C+"),
    C("C"),
    DPLUS("D+"),
    D("D"),
    F("F");

    private final String displayValue;

    GradeStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }


}
