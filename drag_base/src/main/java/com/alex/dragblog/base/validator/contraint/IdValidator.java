package com.alex.dragblog.base.validator.contraint;

import com.alex.dragblog.base.global.Constants;
import com.alex.dragblog.base.validator.annotion.IdValid;
import com.alex.dragblog.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *description:
 *author:       alex
 *createDate:   2020/7/4 15:52
 *version:      1.0.0
 */
public class IdValidator implements ConstraintValidator<IdValid, String> {

    @Override
    public void initialize(IdValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StringUtils.isBlank(value) || StringUtils.isEmpty(value.trim()) || value.length() != Constants.THIRTY_TWO)
            return false;
        return true;
    }
}
