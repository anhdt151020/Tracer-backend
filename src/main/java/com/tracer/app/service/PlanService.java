package com.tracer.app.service;

import com.tracer.app.domain.Feature;
import com.tracer.app.domain.Plan;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.dto.PlanDTO;
import com.tracer.app.dto.mapper.PlanValueMapper;
import com.tracer.app.repository.PlanRepository;
import com.tracer.app.service.errors.PlanErrorException;
import com.tracer.app.service.errors.UserActivePlanErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanService {
    private static final String ENTITY_NAME = "package";

    private final PlanRepository planRepository;
    private final PlanValueMapper planValueMapper;
    private final FeatureService featureService;

    public PlanService(PlanRepository planRepository, PlanValueMapper planValueMapper, FeatureService featureService) {
        this.planRepository = planRepository;
        this.planValueMapper = planValueMapper;
        this.featureService = featureService;
    }

    public Plan findByPlanNameAndPlanStatus(String packageName, StatusType status){
        Optional<Plan> Plan = planRepository.findByPlanNameAndPlanStatus(packageName, status);
        if(Plan.isPresent()){
            return Plan.get();
        }
        return null;
    }

    @Transactional
    public PlanDTO modifyPlan(Long packageId, PlanDTO planDTO){
        if(packageId != null){
            if(!isPlanByPlanId(packageId)){
                throw new PlanErrorException("Plan is not existed", ENTITY_NAME, "packageNotExisted");
            }
            if(isPlanByPlanName(packageId, planDTO.getPlanName())){
                throw new PlanErrorException("PlanName is existed", ENTITY_NAME, "packageNameExisted");
            }
            planDTO.setId(packageId);
        }else {
            if(isPlanByPlanName(planDTO.getPlanName())){
                throw new PlanErrorException("PlanName is existed", ENTITY_NAME, "packageNameExisted");
            }
            planDTO.setId(null);
        }

        Plan Plan = planValueMapper.asPlanCustom(planDTO);

        Set<Long> featureIds = Plan
            .getFeatures()
            .stream().map(Feature::getId)
            .collect(Collectors.toSet());

        if(!featureIds.isEmpty()){
            Set<Feature> features = featureService.findAllByFeatureIds(featureIds);
            if(!features.isEmpty()){
                Plan.setFeatures(features);
                if(Plan.getPlanStatus() == null){
                    Plan.setPlanStatus(StatusType.ACTIVE);
                }

                Plan = planRepository.save(Plan);

                return planValueMapper.asPlanDTOCustom(Plan);
            }
        }

        return null;
    }

    public Boolean isPlanByPlanName(String packageName){
        Optional<Plan> Plan = planRepository.findByPlanName(packageName);
        if(Plan.isPresent()){
            return true;
        }

        return false;
    }

    public Boolean isPlanByPlanName(Long id,String packageName){
        Optional<Plan> Plan = planRepository.findByIdIsNotAndPlanName(id,packageName);
        if(Plan.isPresent()){
            return true;
        }

        return false;
    }

    public Boolean isPlanByPlanId(Long id){
        Optional<Plan> Plan = planRepository.findById(id);
        if(Plan.isPresent()){
            return true;
        }

        return false;
    }

    public Plan findById(Long id){
        Optional<Plan> Plan = planRepository.findById(id);
        if(Plan.isPresent()){
            return Plan.get();
        }else{
            throw new UserActivePlanErrorException("Plan is not existed", ENTITY_NAME, "packageIsNotExisted");
        }
    }
}
