package com.tracer.app.dto.mapper;

import com.tracer.app.domain.DeviceInformation;
import com.tracer.app.dto.DeviceInformationDTO;
import org.mapstruct.Mapper;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeviceInformationValueMapper {
    DeviceInformation asDeviceInformation(DeviceInformationDTO deviceInformationDTO);

    DeviceInformationDTO asDeviceInformationDTO(DeviceInformation deviceInformation);

    default DeviceInformation asDeviceInformationCustom(DeviceInformationDTO deviceInformationDTO){
        if(deviceInformationDTO == null) return null;
        DeviceInformation deviceInformation = new DeviceInformation();
        if(deviceInformationDTO.getId() != null) deviceInformation.setId(deviceInformationDTO.getId());
        if(StringUtils.hasText(deviceInformationDTO.getName()))
            deviceInformation.setName(deviceInformationDTO.getName());
        if(StringUtils.hasText(deviceInformationDTO.getModel()))
            deviceInformation.setModel(deviceInformationDTO.getModel());
        if(StringUtils.hasText(deviceInformationDTO.getVersion()))
            deviceInformation.setVersion(deviceInformationDTO.getVersion());
        if(StringUtils.hasText(deviceInformationDTO.getDeviceId()))
            deviceInformation.setDeviceId(deviceInformationDTO.getDeviceId());
        if(StringUtils.hasText(deviceInformationDTO.getCellNumber()))
            deviceInformation.setCellNumber(deviceInformationDTO.getCellNumber());
        if(StringUtils.hasText(deviceInformationDTO.getOperator()))
            deviceInformation.setOperator(deviceInformationDTO.getOperator());
        if(deviceInformationDTO.getInternalStorage() != null)
            deviceInformation.setInternalStorage(deviceInformationDTO.getInternalStorage());
        if(deviceInformationDTO.getExternalStorage() != null)
            deviceInformation.setExternalStorage(deviceInformationDTO.getExternalStorage());
        if(deviceInformationDTO.getMemory() != null)
            deviceInformation.setMemory(deviceInformationDTO.getMemory());

        return deviceInformation;
    }
}
