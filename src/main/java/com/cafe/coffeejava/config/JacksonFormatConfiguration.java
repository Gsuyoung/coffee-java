package com.cafe.coffeejava.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonFormatConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(@Value("${constant.jackson.date-format}") final String dateFormat
                                                                                      , @Value("${constant.jackson.datetime-format}") final String dateTimeFormat) {
        return builder -> {
            builder.simpleDateFormat(dateFormat)
                    .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                    .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }
}
