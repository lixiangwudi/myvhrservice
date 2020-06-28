package org.javaboy.vhr.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @description 返回json空值去掉null和""
 */
@Configuration
public class JacksonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//        Include.Include.ALWAYS	默认
//        Include.NON_DEFAULT	属性为默认值不序列化
//        Include.NON_EMPTY	属性为 空（""） 或者为 NULL 都不序列化
//        Include.NON_NULL	属性为NULL 不序列化
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        /**
         * 该特性决定parser是否允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）。 如果该属性关闭，则如果遇到这些字符，则会抛出异常。
         * JSON标准说明书要求所有控制符必须使用引号，因此这是一个非标准的特性。
         *
         * 注意：默认时候，该属性关闭的。需要设置：JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS为true。
         *
         */
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        return objectMapper;
    }
}
