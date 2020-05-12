package http.util;

import http.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

public class IOCUtil {
    private static final Logger log = LoggerFactory.getLogger(IOCUtil.class);
    public static void inject(Set<Object> beans, Map<Class,Object> beansMap) throws IllegalAccessException {
        log.info("Begin to inject");
        for(Object obj : beans) {
            Class clz = obj.getClass();
            log.info("Begin to inject class {}",clz);
            for(Field field : clz.getDeclaredFields()){
                if(field.isAnnotationPresent(Service.class)) {
                    log.info("Begin to inject field {}",field);
                    field.setAccessible(true);
                    field.set(obj,beansMap.get(field.getType()));
                }
            }
        }
        log.info("Finish to inject");
    }
}
