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
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@Getter
public class CustomDataSourceManager {

    // Attributes
    private DataSource dataSource;

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

        // Test the connection
        try (Connection conn = ds.getConnection()) {
            this.dataSource = ds;
        }
    }
}