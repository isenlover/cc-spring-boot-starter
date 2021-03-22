package pers.cc.spring.core.annotation.spring;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * 它在所有被@Configuration注解修饰的类处理完成后才运行 {@link DeferredImportSelector}
 *
 * @author chengce
 * @version 2018-06-01 17:02
 */
public class EnableAutoConfigurationImport implements DeferredImportSelector, BeanClassLoaderAware {
    private ClassLoader classLoader;

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> beanNames = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);
        return beanNames.toArray(new String[beanNames.size()]);
    }
}
