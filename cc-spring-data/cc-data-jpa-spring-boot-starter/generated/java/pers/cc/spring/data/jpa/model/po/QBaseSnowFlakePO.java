package pers.cc.spring.data.jpa.model.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseSnowFlakePO is a Querydsl query type for BaseSnowFlakePO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseSnowFlakePO extends EntityPathBase<BaseSnowFlakePO> {

    private static final long serialVersionUID = -1893314654L;

    public static final QBaseSnowFlakePO baseSnowFlakePO = new QBaseSnowFlakePO("baseSnowFlakePO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QBaseSnowFlakePO(String variable) {
        super(BaseSnowFlakePO.class, forVariable(variable));
    }

    public QBaseSnowFlakePO(Path<? extends BaseSnowFlakePO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseSnowFlakePO(PathMetadata metadata) {
        super(BaseSnowFlakePO.class, metadata);
    }

}

