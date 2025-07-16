package com.sullivan.disc.util;

/*
 * Shawn Sullivan
 * CEN 3024C-31774
 * July 8, 2025
 * This utility class dynamically constructs and manages the standalone database access facilitating a user login
 * function.
 */

import com.sullivan.disc.dto.DbLoginRequestDTO;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class CustomDataSourceManager {

    // Attributes
    private DataSource dataSource;
    private EntityManagerFactory entityManagerFactory;
    private PlatformTransactionManager transactionManager;

    /*
     * Method: InitDataSource
     * Description: Method takes a DBLoginRequest DTO collected from the UI and converts use that info to initialize
     * the data source for the App (MySQL Database)
     * Args: DbLoginRequest
     * Return: Void
     */
    public void initDataSource(DbLoginRequestDTO request) throws SQLException {
        String jdbcUrl = "jdbc:mysql://" + request.host + ":" + request.port + "/disc_system";

        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(jdbcUrl);
        ds.setUsername(request.username);
        ds.setPassword(request.password);
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Test the connection
        try (Connection conn = ds.getConnection()) {
            this.dataSource = ds;
        }

        // Set up JPA EntityManagerFactory to allow for custom login
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(ds);
        factory.setPackagesToScan("com.sullivan.disc.model");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.show_sql", true);
        factory.setJpaPropertyMap(jpaProperties);

        factory.afterPropertiesSet();
        EntityManagerFactory emf = factory.getObject();
        if (emf == null) {
            throw new IllegalStateException("Could not get EntityManagerFactory");
        }

        this.entityManagerFactory = emf;
        this.transactionManager = new JpaTransactionManager(this.entityManagerFactory);
    }
}