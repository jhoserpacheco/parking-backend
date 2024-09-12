package com.nelumbo.user.mgsbroker.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RabbitBindingRoute {
    private String routingKey;
    private String exchange;
}
