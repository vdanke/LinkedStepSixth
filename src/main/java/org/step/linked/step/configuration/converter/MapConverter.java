package org.step.linked.step.configuration.converter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationPropertiesBinding
public class MapConverter implements Converter<String, Map<String, String>> {

    @Override
    public Map<String, String> convert(String property) {
        Map<String, String> accessMap = new HashMap<>();
        String[] keyValuePairs = property.split(",");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");
            accessMap.put(keyValue[0], keyValue[1]);
        }
        return accessMap;
    }
}
