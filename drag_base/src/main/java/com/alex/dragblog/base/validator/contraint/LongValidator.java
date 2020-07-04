package com.alex.dragblog.base.validator.contraint;

import com.alex.dragblog.base.validator.annotion.LongNotNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongValidator implements ConstraintValidator<LongNotNull, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null;
    }

    @Override
    public void initialize(LongNotNull constraintAnnotation) {

    }
}
