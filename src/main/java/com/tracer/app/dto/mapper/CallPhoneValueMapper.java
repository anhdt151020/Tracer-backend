package com.tracer.app.dto.mapper;

import com.tracer.app.domain.CallPhone;
import com.tracer.app.dto.CallPhoneDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CallPhoneValueMapper {
    CallPhone asCallPhone(CallPhoneDTO callPhoneDTO);

    CallPhoneDTO asCallPhoneDTO(CallPhone callPhone);

    List<CallPhone> asCallPhones(List<CallPhoneDTO> callPhoneDTOS);

    List<CallPhoneDTO> asCallPhoneDTOs(List<CallPhone> callPhones);
}
