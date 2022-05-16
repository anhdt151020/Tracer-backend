package com.tracer.app.repository.specification;

import com.tracer.app.domain.*;
import com.tracer.app.dto.SearchDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CallPhoneSpecification {

    public static Specification<CallPhone> filterSearch(SearchDTO searchDTO){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(CallPhone_.DEVICE).get(DeviceInformation_.DEVICE_ID),searchDTO.getDeviceId()));

            if(searchDTO.getSearch() != null && searchDTO.getSearch().length() > 0){
                predicates.add(builder.or(
                    builder.like(root.get(CallPhone_.NAME), searchDTO.likeSearch()),
                    builder.like(root.get(CallPhone_.NUMBER), searchDTO.likeSearch())));
            }

            if(searchDTO.getStartDate() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get(CallPhone_.DATE), searchDTO.getStartDate()));
            }

            if(searchDTO.getEndDate() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get(CallPhone_.DATE), searchDTO.getEndDate()));
            }

            return builder.and(predicates.stream().toArray(Predicate[]::new));
        });
    }
}
