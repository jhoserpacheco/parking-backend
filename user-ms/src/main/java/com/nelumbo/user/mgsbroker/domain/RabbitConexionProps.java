package com.nelumbo.user.mgsbroker.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RabbitConexionProps {
    private int port;
    private String host;
    private String username;
    private String password;
    private String virtualHost;
    private boolean sslenabled;
    private String brokerTag;
}
