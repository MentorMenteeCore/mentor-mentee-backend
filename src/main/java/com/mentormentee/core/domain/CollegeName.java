package com.mentormentee.core.domain;

public enum CollegeName {
    HUMANITIES("인문대학"), BUSINESS("경영대학"), AGRICULTURE("농업생명환경대학"), VETERINARYMEDICINE("수의대학"),
    BIOHEALTHSYSTEM("바이오 헬스 공유대학"), BIOHEALTH("바이오 헬스대학"), SOCIALSCIENCES("사회과학 대학"), ENGINEERING("공과대학"),
    EDUCATION("교육대학"), PHARMACY("약학대학"), INTERDISCIPLINARYSTUDIES("자율전공학부"), NATURESCIENCE("자연과학대학"),
    COMPUTERENGINEERING("전자정보대학"), HUMANECOLOGY("생활과학대학"), MEDICINE("의과대학"), CONVERGENCE("융합과학대학");

    private final String koreanName;

    CollegeName(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getCollegeKoreanName() {
        return this.koreanName;
    }
}


