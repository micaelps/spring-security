package com.micaelps.dogs.user;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

@RestController
public class NewUserSystemController {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @PostMapping(path = "/users")
    public void save(@RequestBody @Valid NewUserSystemRequest newUserRequest){
        UserSystem model = newUserRequest.toModel();
        entityManager.persist(model);
    }
}
