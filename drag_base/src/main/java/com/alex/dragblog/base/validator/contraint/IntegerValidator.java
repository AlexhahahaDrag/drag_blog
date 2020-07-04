package com.alex.dragblog.base.validator.contraint;

import com.alex.dragblog.base.validator.annotion.IntegerNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<IntegerNotNull, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer != null;
    }

    @Override
    public void initialize(IntegerNotNull constraintAnnotation) {

    }
}
