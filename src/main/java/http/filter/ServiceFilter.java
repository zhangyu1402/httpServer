package http.filter;

import http.annotation.RESTFulController;
import http.annotation.Service;
import http.container.Container;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ServiceFilter implements Filter {

    public ServiceFilter() {

    }
    @Override
    public void filter(Class clazz) throws IllegalAccessException, InstantiationException {
        Object curObj  = clazz.newInstance();
        if(clazz.isAnnotationPresent(Service.class) && !Container.services.containsKey(clazz)) {
            Container.services.put(clazz,curObj);
        }
    }
}
