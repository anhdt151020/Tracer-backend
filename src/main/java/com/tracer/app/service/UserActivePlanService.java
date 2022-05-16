package com.tracer.app.service;

import com.tracer.app.domain.Plan;
import com.tracer.app.domain.User;
import com.tracer.app.domain.UserActivePlan;
import com.tracer.app.domain.enumeration.StatusType;
import com.tracer.app.dto.CreateUserActivePlanByAdminDTO;
import com.tracer.app.dto.UpdateUserActivePlanStatusDTO;
import com.tracer.app.dto.UserActivePlanDTO;
import com.tracer.app.dto.mapper.UserActivePlanValueMapper;
import com.tracer.app.repository.UserActivePlanRepository;
import com.tracer.app.repository.UserRepository;
import com.tracer.app.service.errors.AccountErrorException;
import com.tracer.app.service.errors.UserActivePlanErrorException;
import com.tracer.app.utils.Datetime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class UserActivePlanService {
    private static final String ENTITY_NAME = "userActivePlanService";

    private final UserActivePlanRepository userActivePlanRepository;
    private final UserActivePlanValueMapper userActivePlanValueMapper;
    private final UserRepository userRepository;
    private final PlanService planService;

    public UserActivePlanService(UserActivePlanRepository userActivePlanRepository, UserActivePlanValueMapper userActivePlanValueMapper, UserRepository userRepository, PlanService planService) {
        this.userActivePlanRepository = userActivePlanRepository;
        this.userActivePlanValueMapper = userActivePlanValueMapper;
        this.userRepository = userRepository;
        this.planService = planService;
    }

    @Transactional
    public UserActivePlan saveUserActivePlan(User user, Plan plan, Long time){
        UserActivePlan userActivePlan = new UserActivePlan();

        userActivePlan.setUser(user);
        userActivePlan.setPlan(plan);
        Long startTime = Instant.now().getEpochSecond();
        userActivePlan.setStartDate(startTime);
        userActivePlan.setEndDate(Datetime.plus(startTime,time));
        userActivePlan.setStatus(StatusType.ACTIVE);

        return userActivePlanRepository.save(userActivePlan);
    }

    @Transactional
    public UserActivePlanDTO updateUserActivePlanStatus(Long id, UpdateUserActivePlanStatusDTO updateUserActivePlanStatusDTO){
        UserActivePlan userActivePlan = findByIdAndUserIdAndPlanId(id, updateUserActivePlanStatusDTO.getUserId(), updateUserActivePlanStatusDTO.getPlanId());
        if(userActivePlan == null){
            throw new UserActivePlanErrorException("UserActivePlan is not existed", ENTITY_NAME, "userActivePlanNotExisted");
        }

        userActivePlan.setStatus(updateUserActivePlanStatusDTO.getStatusType());

        return userActivePlanValueMapper.asUserActivePlanDTOCustom(userActivePlanRepository.save(userActivePlan));
    }

    @Transactional
    public UserActivePlanDTO saveUserActivePlanByAdmin(Long userId, CreateUserActivePlanByAdminDTO createUserActivePlanByAdminDTO){
        if(createUserActivePlanByAdminDTO.getStartDate() > createUserActivePlanByAdminDTO.getEndDate()){
            throw new UserActivePlanErrorException("Time is error", ENTITY_NAME, "timeError");
        }
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new AccountErrorException("User is not exist", ENTITY_NAME, "userNotExisted"));

        Plan Plan = planService.findById(createUserActivePlanByAdminDTO.getPlanId());

        UserActivePlan userActivePlan = new UserActivePlan();

        userActivePlan.setUser(user);
        userActivePlan.setPlan(Plan);
        userActivePlan.setStartDate(createUserActivePlanByAdminDTO.getStartDate());
        userActivePlan.setEndDate(createUserActivePlanByAdminDTO.getEndDate());
        userActivePlan.setStatus(StatusType.ACTIVE);

        return userActivePlanValueMapper.asUserActivePlanDTOCustom(userActivePlanRepository.save(userActivePlan));
    }

    public Boolean isUserActivePlanByIdAndUserIdAndPlanId(Long id, Long userId, Long planId){
        Optional<UserActivePlan> userActivePlan = userActivePlanRepository.findByIdAndUserIdAndPlanId(id,userId,planId);
        if(userActivePlan.isPresent()){
            return true;
        }
        return false;
    }

    public UserActivePlan findByIdAndUserIdAndPlanId(Long id, Long userId, Long planId){
        Optional<UserActivePlan> userActivePlan = userActivePlanRepository.findByIdAndUserIdAndPlanId(id,userId,planId);
        if(userActivePlan.isPresent()){
            return userActivePlan.get();
        }

        return null;
    }
}
