/**
 * Copyright (c) 2010-2012, Karat and/or its affiliates. All rights reserved.
 * Karat PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * You can't redistribute it and/or modify without permissions.
 */
package org.spacecraft.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Component;

/**
 * @author Anton Pashkin
 * @version 2012/07/31
 */
@Component("objectMapper")
public class Mapper extends ObjectMapper {

    public Mapper() {
        // no need to send null variables to clients
        getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

        // explicit annotation methods convert to json
        configure(SerializationConfig.Feature.AUTO_DETECT_GETTERS, false);
        configure(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, false);
    }
}
