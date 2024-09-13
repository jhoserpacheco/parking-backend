package com.nelumbo.parking.mgsbroker.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class RespuestaBroker {
    protected boolean solicitudExito;
    protected String mensajeRespuesta;

    @NoArgsConstructor
    public static class Builder{
        private boolean solicitudExito;
        private String mensajeRespuesta;

        public Builder solicitudExito(Boolean solicitudExito){
            this.solicitudExito = solicitudExito;
            return this;
        }

        public Builder mensajeRespuesta(String mensajeRespuesta){
            this.mensajeRespuesta = mensajeRespuesta;
            return this;
        }

        public RespuestaBroker build(){ return new RespuestaBroker(solicitudExito,mensajeRespuesta);}
    }
    public static Builder builder() {
        return new Builder();
    }
}
