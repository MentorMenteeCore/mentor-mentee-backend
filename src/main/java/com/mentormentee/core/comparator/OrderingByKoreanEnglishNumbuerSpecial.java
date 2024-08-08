package com.mentormentee.core.comparator;

import com.mentormentee.core.utils.CharUtil;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderingByKoreanEnglishNumbuerSpecial {
    private static final int REVERSE = -1;
    private static final int LEFT_FIRST = -1;
    private static final int RIGHT_FIRST = 1;

    public static Comparator<String> getComparator() {
        return new Comparator<String>() {
            public int compare(String left, String right) {
                // 직접 compare 메서드를 호출하지 않고, 인스턴스 메서드를 호출합니다.
                return OrderingByKoreanEnglishNumbuerSpecial.compare(left, right);
            }
        };
    }

    public static int compare(String left, String right) {
        left = left.toUpperCase().replaceAll(" ", "");
        right = right.toUpperCase().replaceAll(" ", "");

        int leftLen = left.length();
        int rightLen = right.length();
        int minLen = Math.min(leftLen, rightLen);

        for (int i = 0; i < minLen; ++i) {
            char leftChar = left.charAt(i);
            char rightChar = right.charAt(i);

            if (leftChar != rightChar) {
                if (isKoreanAndEnglish(leftChar, rightChar)
                        || isKoreanAndNumber(leftChar, rightChar)
                        || isEnglishAndNumber(leftChar, rightChar)
                        || isKoreanAndSpecial(leftChar, rightChar)) {
                    return (leftChar - rightChar) * REVERSE;
                } else if (isEnglishAndSpecial(leftChar, rightChar)
                        || isNumberAndSpecial(leftChar, rightChar)) {
                    if (CharUtil.isEnglish(leftChar) || CharUtil.isNumber(leftChar)) {
                        return LEFT_FIRST;
                    } else {
                        return RIGHT_FIRST;
                    }
                } else {
                    return leftChar - rightChar;
                }
            }
        }

        // 숫자 처리: 문자열의 숫자가 비교 대상일 때
        return compareNumbers(left, right);
    }

    private static int compareNumbers(String left, String right) {
        Pattern pattern = Pattern.compile("(\\D*)(\\d*)");
        Matcher leftMatcher = pattern.matcher(left);
        Matcher rightMatcher = pattern.matcher(right);

        if (leftMatcher.matches() && rightMatcher.matches()) {
            String leftText = leftMatcher.group(1);
            String leftNumber = leftMatcher.group(2);
            String rightText = rightMatcher.group(1);
            String rightNumber = rightMatcher.group(2);

            // 문자열의 텍스트 부분을 우선 비교
            int textComparison = leftText.compareTo(rightText);
            if (textComparison != 0) {
                return textComparison;
            }

            // 숫자 부분을 비교 (빈 숫자는 0으로 처리)
            Integer leftNum = leftNumber.isEmpty() ? 0 : Integer.parseInt(leftNumber);
            Integer rightNum = rightNumber.isEmpty() ? 0 : Integer.parseInt(rightNumber);

            return Integer.compare(leftNum, rightNum);
        }

        return left.compareTo(right);
    }

    private static boolean isKoreanAndEnglish(char ch1, char ch2) {
        return (CharUtil.isEnglish(ch1) && CharUtil.isKorean(ch2))
                || (CharUtil.isKorean(ch1) && CharUtil.isEnglish(ch2));
    }

    private static boolean isKoreanAndNumber(char ch1, char ch2) {
        return (CharUtil.isNumber(ch1) && CharUtil.isKorean(ch2))
                || (CharUtil.isKorean(ch1) && CharUtil.isNumber(ch2));
    }

    private static boolean isEnglishAndNumber(char ch1, char ch2) {
        return (CharUtil.isNumber(ch1) && CharUtil.isEnglish(ch2))
                || (CharUtil.isEnglish(ch1) && CharUtil.isNumber(ch2));
    }

    private static boolean isKoreanAndSpecial(char ch1, char ch2) {
        return (CharUtil.isKorean(ch1) && CharUtil.isSpecial(ch2))
                || (CharUtil.isSpecial(ch1) && CharUtil.isKorean(ch2));
    }

    private static boolean isEnglishAndSpecial(char ch1, char ch2) {
        return (CharUtil.isEnglish(ch1) && CharUtil.isSpecial(ch2))
                || (CharUtil.isSpecial(ch1) && CharUtil.isEnglish(ch2));
    }

    private static boolean isNumberAndSpecial(char ch1, char ch2) {
        return (CharUtil.isNumber(ch1) && CharUtil.isSpecial(ch2))
                || (CharUtil.isSpecial(ch1) && CharUtil.isNumber(ch2));
    }
}




