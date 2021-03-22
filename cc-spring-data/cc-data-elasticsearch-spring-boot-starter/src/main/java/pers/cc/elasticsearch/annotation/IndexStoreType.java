package pers.cc.elasticsearch.annotation;

/**
 * @author chengce
 * @version 2018-07-09 00:46
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/index-modules-store.html"></a>
 */
public enum IndexStoreType {
    fs,
    simplefs,
    niofs,
    mmapfs,
    @Deprecated
    default_fs,
}
