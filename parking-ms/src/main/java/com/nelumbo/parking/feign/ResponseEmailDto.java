package com.nelumbo.parking.feign;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseEmailDto {
    private UUID id;
    private String status;
}
