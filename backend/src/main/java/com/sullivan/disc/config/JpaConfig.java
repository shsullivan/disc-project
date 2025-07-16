//package com.sullivan.disc.config;
//
//import com.sullivan.disc.util.CustomDataSourceManager;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.sullivan.disc.repository",
//        entityManagerFactoryRef = "dynamicEntityManagerFactory",
//        transactionManagerRef = "dynamicTransactionManager"
//)
//public class JpaConfig {
//
//    private final CustomDataSourceManager customDataSourceManager;
//
//    public JpaConfig(CustomDataSourceManager customDataSourceManager) {
//        this.customDataSourceManager = customDataSourceManager;
//    }
//
//    @Bean(name = "dynamicEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean dynamicEntityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//        factoryBean.setDataSource(customDataSourceManager.getDataSource());
//        factoryBean.setPackagesToScan("com.sullivan.disc.model");
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        factoryBean.setJpaVendorAdapter(vendorAdapter);
//
//        Map<String, Object> jpaProperties = new HashMap<String, Object>();
//        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
//        jpaProperties.put("hibernate.show_sql", true);
//
//        return factoryBean;
//    }
//
//    @Bean(name = "dynamicTransactionManager")
//    public PlatformTransactionManager dynamicTransactionManager(
//        @Qualifier("dynamicEntityManagerFactory") EntityManagerFactory factoryBean) {
//        return new JpaTransactionManager(factoryBean);
//    }
//}
