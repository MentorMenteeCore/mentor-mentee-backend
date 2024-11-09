package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmailSession is a Querydsl query type for EmailSession
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmailSession extends EntityPathBase<EmailSession> {

    private static final long serialVersionUID = 2077572727L;

    public static final QEmailSession emailSession = new QEmailSession("emailSession");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath verifyCode = createString("verifyCode");

    public QEmailSession(String variable) {
        super(EmailSession.class, forVariable(variable));
    }

    public QEmailSession(Path<? extends EmailSession> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailSession(PathMetadata metadata) {
        super(EmailSession.class, metadata);
    }

}

