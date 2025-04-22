package org.example.springdebugger.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class MyEnvPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication app) {
        Map<String, Object> map = new HashMap<>();
        map.put("developer.name", "<<UNDEFINED>>");
        env.getPropertySources().addLast(new MapPropertySource("myProcessorProps", map));
    }
}
