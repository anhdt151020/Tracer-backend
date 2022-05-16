package com.tracer.app.repository.specification;

import com.tracer.app.domain.*;
import com.tracer.app.dto.SearchDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SmsPhoneSpecification {
    public static Specification<SmsPhone> filterSearch(SearchDTO searchDTO){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(SmsPhone_.DEVICE).get(DeviceInformation_.DEVICE_ID),searchDTO.getDeviceId()));
            predicates.add(builder.equal(root.get(SmsPhone_.NUMBER),searchDTO.getNumber()));
            if(searchDTO.getSearch() != null && searchDTO.getSearch().length() > 0){
                predicates.add(builder.like(root.get(SmsPhone_.DATA),searchDTO.likeSearch()));
            }

            return builder.and(predicates.stream().toArray(Predicate[]::new));
        });
    }
}
