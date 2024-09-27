package com.nelumbo.parking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "ms-email", url = "${user.client.url-email}")
public interface EmailClient {

    @PostMapping(path = "/email/send")
    ResponseEmailDto sendEmail(@RequestHeader("Authorization") String token, @RequestBody EmailParkingDto emailParkingDto);

    @GetMapping(path = "/email/{id}")
    ResponseEmailDto getEmailById(@RequestHeader("Authorization") String token, @PathVariable String id);

}
