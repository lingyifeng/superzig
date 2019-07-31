package com.android.hz;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SuperzigAndroidApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SuperzigAndroidApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SuperzigAndroidApplication.class);
    }
//	@Bean
//	public HttpMessageConverters fastJsonHttpMessageConverters() {
//		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//		FastJsonConfig fastJsonConfig = new FastJsonConfig();
//		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//		fastConverter.setFastJsonConfig(fastJsonConfig);
//		HttpMessageConverter<?> converter = fastConverter;
//		return new HttpMessageConverters(converter);
//	}

//	@Bean
//	public DataSource dataSource() {
//		return new EmbeddedDatabaseBuilder()
//				.setType(EmbeddedDatabaseType.HSQL)
//				.addScript("my-schema.sql")
//				.addScript("my-test-data.sql")
//				.build();
//	}


}

