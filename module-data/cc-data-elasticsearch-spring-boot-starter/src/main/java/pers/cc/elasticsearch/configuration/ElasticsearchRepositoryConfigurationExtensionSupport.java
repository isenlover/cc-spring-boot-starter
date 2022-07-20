package pers.cc.elasticsearch.configuration;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.repository.config.*;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.util.ClassUtils;
import pers.cc.elasticsearch.annotation.Document;
import pers.cc.elasticsearch.support.ElasticsearchRepositoryFactoryBean;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author chengce
 * @version 2018-07-11 22:31
 */
public class ElasticsearchRepositoryConfigurationExtensionSupport extends RepositoryConfigurationExtensionSupport {
    private static final Class<?> PAB_POST_PROCESSOR = PersistenceAnnotationBeanPostProcessor.class;
    private static final String DEFAULT_TRANSACTION_MANAGER_BEAN_NAME = "transactionManager";
    private static final String ENABLE_DEFAULT_TRANSACTIONS_ATTRIBUTE = "enableDefaultTransactions";

    @Override
    protected String getModulePrefix() {
        return getModuleName().toLowerCase(Locale.US);
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return ElasticsearchRepositoryFactoryBean.class.getName();
    }

//    @Override
//    protected String getModulePrefix() {
//        return "Elasticsearch";
//    }
//
    @Override
    public String getModuleName() {
        return "Elasticsearch";
    }

    @Override
    protected Collection<Class<? extends Annotation>> getIdentifyingAnnotations() {
        return Arrays.asList(Document.class);
    }
//
//    @Override
//    public String getRepositoryFactoryBeanClassName() {
//        return ElasticsearchRepositoryFactoryBean.class.getName();
//    }
//
//    @Override
//    public void registerBeansForRoot(BeanDefinitionRegistry registry, RepositoryConfigurationSource config) {
//
//        super.registerBeansForRoot(registry, config);
//
//        Object source = config.getSource();
//
////        registerIfNotAlreadyRegistered(new RootBeanDefinition(EntityManagerBeanDefinitionRegistrarPostProcessor.class),
////                registry, EM_BEAN_DEFINITION_REGISTRAR_POST_PROCESSOR_BEAN_NAME, source);
//
////        registerIfNotAlreadyRegistered(new RootBeanDefinition(JpaMetamodelMappingContextFactoryBean.class), registry,
////                JPA_MAPPING_CONTEXT_BEAN_NAME, source);
//
//        registerIfNotAlreadyRegistered(new RootBeanDefinition(PAB_POST_PROCESSOR), registry,
//                AnnotationConfigUtils.PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME, source);
//
//        // Register bean definition for DefaultJpaContext
//
//        RootBeanDefinition contextDefinition = new RootBeanDefinition();
//        contextDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
//
//        registerIfNotAlreadyRegistered(contextDefinition, registry, "elasticsearch_bean_name", source);
//    }
//
//    /*
//     * (non-Javadoc)
//     * @see org.springframework.totalYData.repository.config.RepositoryConfigurationExtensionSupport#getConfigurationInspectionClassLoader(org.springframework.core.io.ResourceLoader)
//     */
//    protected ClassLoader getConfigurationInspectionClassLoader(ResourceLoader loader) {
//
//        ClassLoader classLoader = loader.getClassLoader();
//
//        return loader.getClassLoader();
//    }
//
//
//    public ElasticsearchRepositoryConfigurationExtensionSupport() {
//        super();
//    }
//
//    @Override
//    public <T extends RepositoryConfigurationSource> Collection<RepositoryConfiguration<T>> getRepositoryConfigurations(
//            T configSource,
//            ResourceLoader loader) {
//        return super.getRepositoryConfigurations(configSource, loader);
//    }
//
//    @Override
//    public <T extends RepositoryConfigurationSource> Collection<RepositoryConfiguration<T>> getRepositoryConfigurations(
//            T configSource,
//            ResourceLoader loader,
//            boolean strictMatchesOnly) {
//        return super.getRepositoryConfigurations(configSource, loader, strictMatchesOnly);
//    }
//
//    @Override
//    public String getDefaultNamedQueryLocation() {
//        return super.getDefaultNamedQueryLocation();
//    }
//
//    @Override
//    public void postProcess(BeanDefinitionBuilder builder, RepositoryConfigurationSource source) {
//        super.postProcess(builder, source);
//    }
//
//    @Override
//    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
//        super.postProcess(builder, config);
//    }
//
//    @Override
//    public void postProcess(BeanDefinitionBuilder builder, XmlRepositoryConfigurationSource config) {
//        super.postProcess(builder, config);
//    }
//
//    /**
//     * Utility to determine if a lazy Java agent is being used that might transform classes at a later time.
//     *
//     * @author Mark Paluch
//     * @since 2.1
//     */
//    @UtilityClass
//    static class LazyJvmAgent {
//
//        private static final Set<String> AGENT_CLASSES;
//
//        static {
//
//            Set<String> agentClasses = new LinkedHashSet<>();
//
//            agentClasses.add("org.springframework.instrument.InstrumentationSavingAgent");
//            agentClasses.add("org.eclipse.persistence.internal.jpa.deployment.JavaSECMPInitializerAgent");
//
//            AGENT_CLASSES = Collections.unmodifiableSet(agentClasses);
//        }
//
//        /**
//         * Determine if any agent is active.
//         *
//         * @return {@literal true} if an agent is active.
//         */
//        static boolean isActive(@Nullable ClassLoader classLoader) {
//
//            return AGENT_CLASSES.stream() //
//                    .anyMatch(agentClass -> ClassUtils.isPresent(agentClass, classLoader));
//        }
//    }
}
