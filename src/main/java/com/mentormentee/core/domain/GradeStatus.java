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

    //UserCourse 엔티티에서 gradeStatus가 문자열로 저장된다.
    //따라서, enum 변환을 위해 CourseMentorDto의 MentorDto 클래스에서 문자열을 GradeaStatus enum으로 변환하고, 성적의 우선순위를 반환하는 데 사용
    public static GradeStatus fromString(String text) {
        for (GradeStatus status : GradeStatus.values()) {
            if (status.displayValue.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("잘못된 입력입니다" + text);
    }
}


