package com.tracer.app.web.rest;

import com.tracer.app.domain.UserActivePlan;
import com.tracer.app.dto.CreateUserActivePlanByAdminDTO;
import com.tracer.app.dto.UpdateUserActivePlanStatusDTO;
import com.tracer.app.dto.UserActivePlanDTO;
import com.tracer.app.service.UserActivePlanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link UserActivePlan}.
 */
@RestController
@RequestMapping("/api")
public class UserActivePlanResource {
    private static final String ENTITY_NAME = "userActivePlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserActivePlanService userActivePlanService;

    public UserActivePlanResource(UserActivePlanService userActivePlanService) {
        this.userActivePlanService = userActivePlanService;
    }

    @PutMapping("/admin/user-active-plan/{userActivePlanId}/status")
    public ResponseEntity<UserActivePlanDTO> updateStatusUserActivePlan(
        @PathVariable("userActivePlanId") Long id,
        @Valid @RequestBody UpdateUserActivePlanStatusDTO userActivePlanDTO) throws URISyntaxException {
        UserActivePlanDTO result = userActivePlanService.updateUserActivePlanStatus(id,userActivePlanDTO);

        return ResponseEntity
            .created(new URI("/api/admin/user-active-plan/" + id + "/status/" + ((result != null) ? result.getId() : "")))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "").toString()))
            .body(result);
    }

    @PostMapping("/admin/user-active-plan/{userId}")
    public ResponseEntity<UserActivePlanDTO> createUserActivePlanByAdmin(
        @PathVariable("userId") Long userId,
        @Valid @RequestBody CreateUserActivePlanByAdminDTO createUserActivePlanByAdminDTO) throws URISyntaxException {

        UserActivePlanDTO result = userActivePlanService.saveUserActivePlanByAdmin(userId, createUserActivePlanByAdminDTO);

        return ResponseEntity
            .created(new URI("/api/admin/user-active-plan/" + userId + "/" + ((result != null) ? result.getId() : "")))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "").toString()))
            .body(result);
    }
}
