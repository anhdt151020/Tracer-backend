package com.tracer.app.web.rest;

import com.tracer.app.domain.Plan;
import com.tracer.app.dto.PlanDTO;
import com.tracer.app.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link Plan}.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {
    private final Logger log = LoggerFactory.getLogger(PlanResource.class);

    private static final String ENTITY_NAME = "plan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanService planService;

    public PlanResource(PlanService planService) {
        this.planService = planService;
    }

    @PostMapping("/admin/plans")
    public ResponseEntity<PlanDTO> createPlan(@Valid @RequestBody PlanDTO planDTO) throws URISyntaxException {

        PlanDTO result = planService.modifyPlan(null, planDTO);

        return ResponseEntity
            .created(new URI("/api/admin/plans/" + ((result != null) ? result.getId() : "")))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "").toString()))
            .body(result);
    }

    @PutMapping("/admin/plans/{planId}")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable("planId") Long planId, @Valid @RequestBody PlanDTO planDTO) throws URISyntaxException {

        PlanDTO result = planService.modifyPlan(planId, planDTO);

        return ResponseEntity
            .created(new URI("/api/admin/plans/" + ((result != null) ? result.getId() : "")))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ((result != null) ? result.getId() : "").toString()))
            .body(result);
    }
}
