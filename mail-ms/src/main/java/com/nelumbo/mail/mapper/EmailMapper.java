package com.nelumbo.mail.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailMapper {

    public <T> T emailTo(Object o, TypeReference<T> ref) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(o, ref);
    }
}
