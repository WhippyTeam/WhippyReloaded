package com.whippyteam.shared.command.annotation;

import com.whippyteam.shared.command.CommandParamType;

public @interface RequestParam {

    int param();

    CommandParamType type() default CommandParamType.STRING;
}
