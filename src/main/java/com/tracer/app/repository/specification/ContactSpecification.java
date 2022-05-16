package com.tracer.app.repository.specification;

import com.tracer.app.domain.*;
import com.tracer.app.dto.SearchDTO;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ContactSpecification {

    public static Specification<Contact> filterSearch(SearchDTO searchDTO){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get(Contact_.DEVICE).get(DeviceInformation_.DEVICE_ID),searchDTO.getDeviceId()));

            if(searchDTO.getSearch() != null && searchDTO.getSearch().length() > 0){
                predicates.add(builder.or(
                    builder.like(root.get(Contact_.DISPLAY_NAME), searchDTO.likeSearch()),
                    builder.like(root.get(Contact_.PHONE_NUMBER), searchDTO.likeSearch())));
            }

            return builder.and(predicates.stream().toArray(Predicate[]::new));
        });
    }
}
