package com.nelumbo.parking.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ms-email", url = "${user.client.url}")
public interface EmailClient {
}
