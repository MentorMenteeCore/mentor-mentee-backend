package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserTransaction is a Querydsl query type for UserTransaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserTransaction extends EntityPathBase<UserTransaction> {

    private static final long serialVersionUID = -1252709930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserTransaction userTransaction = new QUserTransaction("userTransaction");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTransaction transaction;

    public final QUser user;

    public QUserTransaction(String variable) {
        this(UserTransaction.class, forVariable(variable), INITS);
    }

    public QUserTransaction(Path<? extends UserTransaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserTransaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserTransaction(PathMetadata metadata, PathInits inits) {
        this(UserTransaction.class, metadata, inits);
    }

    public QUserTransaction(Class<? extends UserTransaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.transaction = inits.isInitialized("transaction") ? new QTransaction(forProperty("transaction")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

