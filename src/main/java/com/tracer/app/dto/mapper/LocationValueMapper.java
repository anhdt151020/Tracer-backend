package com.tracer.app.dto.mapper;

import com.tracer.app.domain.CallPhone;
import com.tracer.app.domain.Location;
import com.tracer.app.dto.CallPhoneDTO;
import com.tracer.app.dto.LocationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationValueMapper {
    Location asLocation(LocationDTO locationDTO);

    LocationDTO asLocationDto(Location location);

    List<Location> asLocations(List<LocationDTO> locationDTOS);

    List<LocationDTO> asLocationDTOS(List<Location> locations);
}
