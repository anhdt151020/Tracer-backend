package com.tracer.app.dto.mapper;

import com.tracer.app.domain.SmsPhone;
import com.tracer.app.dto.SmsPhoneDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SmsPhoneValueMapper {
    SmsPhone asSmsPhone(SmsPhoneDTO smsPhoneDTO);

    SmsPhoneDTO asSmsPhoneDTO(SmsPhone smsPhone);

    List<SmsPhone> asSmsPhones(List<SmsPhoneDTO> smsPhoneDTOS);

    List<SmsPhoneDTO> asSmsPhoneDTOs(List<SmsPhone> smsPhones);
}
