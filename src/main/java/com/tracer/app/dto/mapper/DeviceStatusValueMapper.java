package com.tracer.app.dto.mapper;

import com.tracer.app.domain.DeviceStatus;
import com.tracer.app.dto.DeviceStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceStatusValueMapper {
    DeviceStatusDTO asDeviceStatusDTO(DeviceStatus deviceStatus);

    DeviceStatus asDeviceStatus(DeviceStatusDTO deviceStatusDTO);
}
