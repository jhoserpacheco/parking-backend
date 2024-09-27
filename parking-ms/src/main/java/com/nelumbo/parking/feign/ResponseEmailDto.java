package com.nelumbo.parking.feign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseEmailDto {
    private String id;
    private String status;
}
