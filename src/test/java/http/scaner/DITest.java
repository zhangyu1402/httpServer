package http.scaner;

import example.controller.HelloController;
import example.controller.TestService;
import http.annotation.Service;
import http.filter.ServiceFilter;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

public class DITest {
    @Test
    public void serviceInject() throws IllegalAccessException, InstantiationException {
//        System.out.println(TestService.class.isAnnotationPresent(Service.class));
        HelloController helloController = new HelloController();
        for (Field field :helloController.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            field.set(helloController,field.getType().newInstance());
        }
    }

}
