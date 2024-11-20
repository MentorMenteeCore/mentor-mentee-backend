package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAvailableTime is a Querydsl query type for AvailableTime
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAvailableTime extends EntityPathBase<AvailableTime> {

    private static final long serialVersionUID = -1931333191L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAvailableTime availableTime = new QAvailableTime("availableTime");

    public final TimePath<java.time.LocalTime> availableEndTime = createTime("availableEndTime", java.time.LocalTime.class);

    public final TimePath<java.time.LocalTime> availableStartTime = createTime("availableStartTime", java.time.LocalTime.class);

    public final EnumPath<java.time.DayOfWeek> dayOfWeek = createEnum("dayOfWeek", java.time.DayOfWeek.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QAvailableTime(String variable) {
        this(AvailableTime.class, forVariable(variable), INITS);
    }

    public QAvailableTime(Path<? extends AvailableTime> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAvailableTime(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAvailableTime(PathMetadata metadata, PathInits inits) {
        this(AvailableTime.class, metadata, inits);
    }

    public QAvailableTime(Class<? extends AvailableTime> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

