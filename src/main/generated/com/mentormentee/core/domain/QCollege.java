package com.mentormentee.core.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCollege is a Querydsl query type for College
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollege extends EntityPathBase<College> {

    private static final long serialVersionUID = 963980378L;

    public static final QCollege college = new QCollege("college");

    public final EnumPath<CollegeName> collegeName = createEnum("collegeName", CollegeName.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCollege(String variable) {
        super(College.class, forVariable(variable));
    }

    public QCollege(Path<? extends College> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCollege(PathMetadata metadata) {
        super(College.class, metadata);
    }

}

