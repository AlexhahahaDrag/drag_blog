package com.alex.dragblog.base.validator.annotion;

import com.alex.dragblog.base.validator.Messages;
import com.alex.dragblog.base.validator.Payload;

public @interface IdValid {

    boolean required() default true;

    String message() default Messages.ID_LENGTH_THIRTY_TWO;

    String value() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
