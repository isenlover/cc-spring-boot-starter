package pers.cc.spring.data.jpa.model.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseCustomPO is a Querydsl query type for BaseCustomPO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseCustomPO extends EntityPathBase<BaseCustomPO> {

    private static final long serialVersionUID = -730724385L;

    public static final QBaseCustomPO baseCustomPO = new QBaseCustomPO("baseCustomPO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QBaseCustomPO(String variable) {
        super(BaseCustomPO.class, forVariable(variable));
    }

    public QBaseCustomPO(Path<? extends BaseCustomPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseCustomPO(PathMetadata metadata) {
        super(BaseCustomPO.class, metadata);
    }

}

