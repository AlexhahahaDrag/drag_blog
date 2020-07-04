package com.alex.dragblog.base.validator.contraint;

import com.alex.dragblog.base.validator.annotion.NotBlank;
import com.alex.dragblog.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *description:  不为空的字符串
 *author:       alex
 *createDate:   2020/7/4 16:00
 *version:      1.0.0
 */
public class StringValidator implements ConstraintValidator<NotBlank, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || StringUtils.isBlank(value) || StringUtils.isEmpty(value.trim()))
            return false;
        return true;
    }

    @Override
    public void initialize(NotBlank constraintAnnotation) {

    }
}
