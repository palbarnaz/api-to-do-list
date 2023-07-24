package com.apitodolistjava.apitodolist.repositories.specifications;

import com.apitodolistjava.apitodolist.models.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.UUID;

public class TaskSpecification {
    public static Specification<Task> filterByDescriptionAndStatus(String description, Boolean archived, UUID userId){
        return (root, query, criteriaBuilder) -> {
            var conditions = new ArrayList<Predicate>();
            if(userId != null){
                conditions.add(criteriaBuilder.equal(root.get("userId"), userId));
            }
            if(description != null && !description.isEmpty()){
                conditions.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%" ));
            }

            if(archived != null){


                conditions.add(

                        criteriaBuilder.equal(root.get("archived"), archived)
                );
            }

            var conditionsInArray = conditions.toArray(new Predicate[0]);

            return criteriaBuilder.and(conditionsInArray);

        };
    }
}
