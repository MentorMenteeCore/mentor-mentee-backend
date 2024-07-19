package com.mentormentee.core.domain;

/**
 * 나중에 유저코스에서 사용할때 APLUS이면
 * .getDisplayValue()이렇게 해서 A+ 이런식으로 출력하고 싶어서
 * 했는데 이렇게 해도 되요?
 */
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
