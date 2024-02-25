package com.BarberApp.BackEnd;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamWriteConstraints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
@Configuration
public class JacksonConfiguration {
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();
        objectMapper.registerModule(new JodaModule());
        objectMapper.getFactory().setStreamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(6000).build());
        objectMapper.getFactory().setStreamWriteConstraints(StreamWriteConstraints.builder().maxNestingDepth(6000).build());
        return objectMapper;
    }

}
