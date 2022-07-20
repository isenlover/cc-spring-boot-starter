package pers.cc.spring.data.jpa.model.po;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseSnowFlakeStringIdPO is a Querydsl query type for BaseSnowFlakeStringIdPO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseSnowFlakeStringIdPO extends EntityPathBase<BaseSnowFlakeStringIdPO> {

    private static final long serialVersionUID = -72519986L;

    public static final QBaseSnowFlakeStringIdPO baseSnowFlakeStringIdPO = new QBaseSnowFlakeStringIdPO("baseSnowFlakeStringIdPO");

    public final DateTimePath<java.util.Date> createTime = createDateTime("createTime", java.util.Date.class);

    public final StringPath id = createString("id");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QBaseSnowFlakeStringIdPO(String variable) {
        super(BaseSnowFlakeStringIdPO.class, forVariable(variable));
    }

    public QBaseSnowFlakeStringIdPO(Path<? extends BaseSnowFlakeStringIdPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseSnowFlakeStringIdPO(PathMetadata metadata) {
        super(BaseSnowFlakeStringIdPO.class, metadata);
    }

}

