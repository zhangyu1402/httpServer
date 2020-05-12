package http.filter;

import http.annotation.RESTFulController;
import http.annotation.RESTFulMapping;
import http.container.Container;
import http.entity.MethodEntity;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerFilter implements Filter{

    public ControllerFilter() {

    }
    @Override
    public void filter(Class clazz) throws IllegalAccessException, InstantiationException {
        Object curObj  = clazz.newInstance();
        RESTFulController controller = (RESTFulController) clazz.getAnnotation(RESTFulController.class);
        if(clazz.isAnnotationPresent(RESTFulController.class)){
            for(Method method:clazz.getMethods()) {
                if(method.isAnnotationPresent(RESTFulMapping.class)) {
                    RESTFulMapping mapping = method.getAnnotation(RESTFulMapping.class);
                    Container.methods.put(controller.value().concat(mapping.value()),new MethodEntity(method,curObj));
                }
            }
        }
    }
}
