package com.mentormentee.core.domain;

/**
 * 나중에 유저코스에서 사용할때 APLUS이면
 * .getDisplayValue()이렇게 해서 A+ 이런식으로 출력하고 싶어서
 * 했는데 이렇게 해도 되요?
 */

public enum GradeStatus {
    APLUS("A+", 1),
    A("A", 2),
    BPLUS("B+", 3),
    B("B", 4),
    C("C", 5);

    private final String displayValue;
    private final int priority;

    GradeStatus(String displayValue, int priority) {
        this.displayValue = displayValue;
        this.priority = priority;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public int getPriority() {
        return priority;
    }

    public static GradeStatus fromString(String text) {
        for (GradeStatus status : GradeStatus.values()) {
            if (status.displayValue.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant " + text);
    }
}


