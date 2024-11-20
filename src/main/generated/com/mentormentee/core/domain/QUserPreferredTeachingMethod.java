package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPreferredTeachingMethod is a Querydsl query type for UserPreferredTeachingMethod
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserPreferredTeachingMethod extends EntityPathBase<UserPreferredTeachingMethod> {

    private static final long serialVersionUID = -475392921L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPreferredTeachingMethod userPreferredTeachingMethod = new QUserPreferredTeachingMethod("userPreferredTeachingMethod");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath preferredTeachingMethod = createString("preferredTeachingMethod");

    public final QUser user;

    public QUserPreferredTeachingMethod(String variable) {
        this(UserPreferredTeachingMethod.class, forVariable(variable), INITS);
    }

    public QUserPreferredTeachingMethod(Path<? extends UserPreferredTeachingMethod> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPreferredTeachingMethod(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPreferredTeachingMethod(PathMetadata metadata, PathInits inits) {
        this(UserPreferredTeachingMethod.class, metadata, inits);
    }

    public QUserPreferredTeachingMethod(Class<? extends UserPreferredTeachingMethod> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

