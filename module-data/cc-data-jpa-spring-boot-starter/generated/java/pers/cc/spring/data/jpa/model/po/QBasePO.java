package pers.cc.spring.data.jpa.model.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBasePO is a Querydsl query type for BasePO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBasePO extends EntityPathBase<BasePO> {

    private static final long serialVersionUID = -1350694962L;

    public static final QBasePO basePO = new QBasePO("basePO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QBasePO(String variable) {
        super(BasePO.class, forVariable(variable));
    }

    public QBasePO(Path<? extends BasePO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBasePO(PathMetadata metadata) {
        super(BasePO.class, metadata);
    }

}

