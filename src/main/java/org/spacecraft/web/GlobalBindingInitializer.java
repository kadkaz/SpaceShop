package org.spacecraft.web;

import org.spacecraft.domain.Ship;
import org.spacecraft.persistence.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * Register application initialization data
 *
 * @author Anton Pashkin
 * @version 1.0 2013/07/30
 */
@Component
public class GlobalBindingInitializer implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Ship.SortField.class, new EnumConverter<>(Ship.SortField.class));
        binder.registerCustomEditor(Order.class, new EnumConverter<>(Order.class));
    }
}
