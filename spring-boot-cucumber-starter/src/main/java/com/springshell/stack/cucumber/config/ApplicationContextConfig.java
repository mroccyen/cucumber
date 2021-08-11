package com.springshell.stack.cucumber.config;

import com.springshell.stack.cucumber.utils.ApplicationContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author qingsp
 * @version 0.0.1
 * @date 2020-08-09
 */
public class ApplicationContextConfig {

	/**
	 * applicationContext全局保存器
	 */
	@Bean
	@ConditionalOnMissingBean
	public ApplicationContextHolder applicationContextHolder() {
		return new ApplicationContextHolder();
	}

}
