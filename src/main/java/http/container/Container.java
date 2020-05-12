package http.container;

import http.entity.MethodEntity;
import http.filter.Filter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Container {

    static public Map<String, MethodEntity> methods = new HashMap<>();
    static public Map<Class,Object> services = new HashMap<>();
    private static Set<Object> beans = null;
    static public Set<Object> getBeanSet() {
        if(beans != null) return beans;
        beans = new HashSet<>(services.values());
        for(MethodEntity methodEntity:methods.values()) {
            if(methodEntity.getClazz() != null)
                beans.add(methodEntity.getClazz());
        }
        return beans;

    }


}
