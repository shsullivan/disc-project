package com.sullivan.disc.controller;

/**
 * @author Shawn Sullivan
 * CEN 3024C-31774
 * July 8, 2025
 * This class serves a REST endpoint for the credentials entered on the GUI login page that will then be passed to the
 * service layer to initialize the standalone database in MySQL
 */

import com.sullivan.disc.dto.DbLoginRequestDTO;
import com.sullivan.disc.util.CustomDataSourceManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:5174",
        "http://localhost:5173"
})
@RestController
@RequestMapping("/api/db")
public class DbConnectionController {

    // Attributes
    private final CustomDataSourceManager dataSourceManager; // Service layer DB initializer

    /**
     * The constructor is not utilized by other classes but designates the instantiation of the CustomDataSourceManager
     * object
     * @param dataSourceManager gives the controller the ability to access the CustomDataSourceManager initDataSource
     *                          method
     */
    public DbConnectionController(CustomDataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * POST endpoint for the application login page that accepts that login information to tbe passed to the
     * dataSourceManager
     * @param request specialized DbLoginRequestDTO containing user login information to initialize the DB connection
     * @return ResponseEntity<String> that signals to the user whether or not the connection was successful.
     */
    @PostMapping("/connect")
    public ResponseEntity<String> connect(@RequestBody DbLoginRequestDTO request) {
        try {
            dataSourceManager.initDataSource(request);
            return ResponseEntity.ok("Connected successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Connection failed: " + e.getMessage());
        }

    }
}
