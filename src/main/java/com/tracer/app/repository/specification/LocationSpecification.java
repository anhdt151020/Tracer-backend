package com.tracer.app.repository.specification;

import com.tracer.app.domain.*;
import com.tracer.app.dto.SearchDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class LocationSpecification {

    public static Specification<Location> filterSearch(SearchDTO searchDTO){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(Location_.DEVICE).get(DeviceInformation_.DEVICE_ID),searchDTO.getDeviceId()));

            if(searchDTO.getStartDate() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get(Location_.TIME), searchDTO.getStartDate()));
            }

            if(searchDTO.getEndDate() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get(Location_.TIME), searchDTO.getEndDate()));
            }

            return builder.and(predicates.stream().toArray(Predicate[]::new));
        });
    }
}
