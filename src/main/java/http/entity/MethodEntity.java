package http.entity;

import java.lang.reflect.Method;

public class MethodEntity {
    private Method method;
    private Object clazz;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getClazz() {
        return clazz;
    }

    public void setClazz(Object clazz) {
        this.clazz = clazz;
    }

    public MethodEntity(Method method, Object clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public MethodEntity() {
    }
}
