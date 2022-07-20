package pers.cc.spring.data.jpa.model.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseIncrementPO is a Querydsl query type for BaseIncrementPO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseIncrementPO extends EntityPathBase<BaseIncrementPO> {

    private static final long serialVersionUID = 762067103L;

    public static final QBaseIncrementPO baseIncrementPO = new QBaseIncrementPO("baseIncrementPO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QBaseIncrementPO(String variable) {
        super(BaseIncrementPO.class, forVariable(variable));
    }

    public QBaseIncrementPO(Path<? extends BaseIncrementPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseIncrementPO(PathMetadata metadata) {
        super(BaseIncrementPO.class, metadata);
    }

}

