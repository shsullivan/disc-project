package com.sullivan.disc.controller;

/*
 * Shawn Sullivan
 * CEN 3024C-31774
 * July 8, 2025
 * This class serves a REST endpoint for the credentials entered on the GUI login page that will then be passed to the
 * service layer to initialize the standalone database in MySQL
 */

import com.sullivan.disc.dto.DbLoginRequest;
import com.sullivan.disc.util.CustomDataSourceManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/db")
public class DbConnectionController {

    // Attributes
    private final CustomDataSourceManager dataSourceManager; // Service layer DB initializer

    //Constructor
    public DbConnectionController(CustomDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    // End point for POST method from GUI login page.
    @PostMapping("/connect")
    public ResponseEntity<String> connect(@RequestBody DbLoginRequest request) {
        try {
            dataSourceManager.initDataSource(request);
            return ResponseEntity.ok("Connected successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Connection failed: " + e.getMessage());
        }
    }
}
