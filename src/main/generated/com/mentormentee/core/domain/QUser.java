package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -695480344L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<AvailableTime, QAvailableTime> availabilities = this.<AvailableTime, QAvailableTime>createList("availabilities", AvailableTime.class, QAvailableTime.class, PathInits.DIRECT2);

    public final QDepartment department;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final StringPath selfIntroduction = createString("selfIntroduction");

    public final ListPath<UserCourse, QUserCourse> userCourse = this.<UserCourse, QUserCourse>createList("userCourse", UserCourse.class, QUserCourse.class, PathInits.DIRECT2);

    public final StringPath userCurrentAccessedChatRoom = createString("userCurrentAccessedChatRoom");

    public final StringPath userName = createString("userName");

    public final ListPath<UserPreferredTeachingMethod, QUserPreferredTeachingMethod> userPreferredTeachingMethodList = this.<UserPreferredTeachingMethod, QUserPreferredTeachingMethod>createList("userPreferredTeachingMethodList", UserPreferredTeachingMethod.class, QUserPreferredTeachingMethod.class, PathInits.DIRECT2);

    public final StringPath userProfilePicture = createString("userProfilePicture");

    public final EnumPath<Role> userRole = createEnum("userRole", Role.class);

    public final EnumPath<WaysOfCommunication> waysOfCommunication = createEnum("waysOfCommunication", WaysOfCommunication.class);

    public final NumberPath<Integer> yearInUni = createNumber("yearInUni", Integer.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department"), inits.get("department")) : null;
    }

}

