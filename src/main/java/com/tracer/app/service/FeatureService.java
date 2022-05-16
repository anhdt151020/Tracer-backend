package com.tracer.app.service;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.domain.Feature;
import com.tracer.app.repository.FeatureRepository;
import com.tracer.app.service.errors.DeviceInformationErrorException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final UserService userService;
    private final DeviceInformationService deviceInformationService;

    public FeatureService(FeatureRepository featureRepository, UserService userService, DeviceInformationService deviceInformationService) {
        this.featureRepository = featureRepository;
        this.userService = userService;
        this.deviceInformationService = deviceInformationService;
    }

    public Set<Feature> findAllByFeatureIds(Set<Long> ids){
        return featureRepository.findAllByIdIn(ids);
    }

    public Optional<DeviceInformation> findDeviceInformationWithPermission(String deviceId, String url, String featureName){
        userService.checkUserAccessPermission(url,featureName);
        Optional<DeviceInformation> deviceInformation = deviceInformationService.findOneByUserLogin(deviceId);
        if (deviceInformation.isEmpty()) {
            throw new DeviceInformationErrorException("Device Information not found !!", featureName, "DeviceInformationNotFoun!!");
        }
        return deviceInformation;
    }
}
