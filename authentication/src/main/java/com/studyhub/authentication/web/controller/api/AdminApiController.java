package com.studyhub.authentication.web.controller.api;

import com.studyhub.authentication.config.JWTService;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/auth/v1")
public class AdminApiController {

    private final AdminService adminService;
    private final JWTService jwtService;

    public AdminApiController(AdminService adminService, JWTService jwtService) {
        this.adminService = adminService;
        this.jwtService = jwtService;
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<AppUser>> getAllUsers(HttpServletRequest request) {
        String token = jwtService.extractTokenFromHeader(request.getHeader("Authorization"));
        boolean isAdmin = jwtService.extractAuthorities(token).contains("ROLE_ADMIN");

        if(isAdmin) {
            return ResponseEntity.ok(adminService.findAllAppUser());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
