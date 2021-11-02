package com.micaelps.dogs.validators;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager manager;
    private String domainAttribute;
    private Class<?> klass;

    @Override
    public void initialize(UniqueValue params){
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = manager.createQuery("select 1 from "+klass.getName() +" where "+domainAttribute+" =:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        return list.isEmpty();
    }
}
