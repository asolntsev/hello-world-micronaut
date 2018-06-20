package hello.domain;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;

public class JPAListener implements BeanCreatedEventListener {
    @Override
    public Object onCreated(BeanCreatedEvent event) {
        System.out.println("CREATED: " + event.getBean());
        return event.getBean();
    }
}
