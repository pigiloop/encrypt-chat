package ru.vinhome.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/apiv2/")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("ru.vinhome.controller");
        register(JacksonFeature.class);
    }

}
