package com.nelumbo.parking.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-user", url = "${user.client.url}")
public interface AuthClient {

    @GetMapping("/auth/validate")
    ResponseEntity<UserDto> validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("/user/getByEmail")
    ResponseEntity<UserDto> getUserByEmail(@RequestHeader("Authorization") String token, @RequestParam String email);

}