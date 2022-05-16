package com.tracer.app.web.rest;

import com.tracer.app.domain.Feature;
import com.tracer.app.domain.Plan;
import com.tracer.app.repository.FeatureRepository;
import com.tracer.app.repository.PlanRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PlanFeatureResource {
    private final FeatureRepository featureRepository;
    private final PlanRepository planRepository;

    public PlanFeatureResource(FeatureRepository featureRepository, PlanRepository planRepository) {
        this.featureRepository = featureRepository;
        this.planRepository = planRepository;
    }

    @PutMapping("/features-plans/{featureId}/plans/{planId}")
    Plan enrollFeaturesToPlan(
        @PathVariable Long featureId,
        @PathVariable Long planId
    ){
        Plan Plan = planRepository.findById(planId).get();
        Feature feature = featureRepository.findById(featureId).get();
        Plan.enrollFeature(feature);
        return planRepository.save(Plan);
    }
}
