package com.springshell.stack.cucumber.config.memory;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MemoryWebSocketConfig.class})
public @interface EnableMemWebSocketManager {
}
