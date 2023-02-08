package com.shoppingcart.backend.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private UniqueSource uniqueSource;
    private @Autowired AutowireCapableBeanFactory beanFactory;

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {
        return uniqueSource.isUnique(field);
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        try {
            uniqueSource = constraintAnnotation.uniqueSource().getConstructor().newInstance();
            beanFactory.autowireBean(uniqueSource);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
