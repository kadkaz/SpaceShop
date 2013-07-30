package org.spacecraft.web;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * Convert any String (convert to upper case) to specific Enum
 *
 * @author Anton Pashkin
 * @version 1.0 2013/07/30
 */
public class EnumConverter<T extends Enum> extends PropertyEditorSupport {

    private Class<T> clazz;

    /**
     * Create specific enum converter
     * @param clazz is mandatory class to what is needed to convert
     */
    public EnumConverter(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("The class argument is mandatory");
        }
        this.clazz = clazz;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
        } else {
            // expecting standard java name convention for enum
            setValue(Enum.valueOf(clazz, text.toUpperCase()));
        }
    }
}
