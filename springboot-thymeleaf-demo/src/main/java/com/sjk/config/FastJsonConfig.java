package com.sjk.config;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class FastJsonConfig extends WebMvcConfigurerAdapter {
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		/**
		 * 1 预先定义一个converts 转换消息对象
		 * 2 添加fastjson配置
		 * 3想convert中追加配置信息 
		 * 4在转换列表中添加fastjson转化消息对象 
		 */
		super.configureMessageConverters(converters);
		FastJsonHttpMessageConverter converter=new FastJsonHttpMessageConverter();
		com.alibaba.fastjson.support.config.FastJsonConfig fastjsonConfig=new com.alibaba.fastjson.support.config.FastJsonConfig();
		fastjsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		converter.setFastJsonConfig(fastjsonConfig);
		converters.add(converter);
	}

	/***
	@Bean
	public  HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter converter=new FastJsonHttpMessageConverter();
		com.alibaba.fastjson.support.config.FastJsonConfig fastjsonConfig=new com.alibaba.fastjson.support.config.FastJsonConfig();
		fastjsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		converter.setFastJsonConfig(fastjsonConfig);
		return new HttpMessageConverters(converter);		
	}
	**/
	
	
}
