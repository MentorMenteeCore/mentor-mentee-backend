package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMentee is a Querydsl query type for Mentee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMentee extends EntityPathBase<Mentee> {

    private static final long serialVersionUID = 1416598363L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMentee mentee = new QMentee("mentee");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser user;

    public QMentee(String variable) {
        this(Mentee.class, forVariable(variable), INITS);
    }

    public QMentee(Path<? extends Mentee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMentee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMentee(PathMetadata metadata, PathInits inits) {
        this(Mentee.class, metadata, inits);
    }

    public QMentee(Class<? extends Mentee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

