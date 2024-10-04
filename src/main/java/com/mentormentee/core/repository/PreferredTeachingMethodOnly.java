package com.mentormentee.core.repository;

/**
 * 이 인터페이스는
 * 엔티티중에 하나의 필드만 가지고 오고 싶을때
 * 뽑아오는 projection입니다.
 */
public interface PreferredTeachingMethodOnly {

    String getTeachingMethod();

}
