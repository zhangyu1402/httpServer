package http.scaner;

import http.annotation.PathVariable;
import http.annotation.RESTFulMapping;
import http.exception.ParamTypeException;
import http.route.DefaultDispatcher;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.junit.Test;

import java.util.*;

public class ParaTest {
    @RESTFulMapping(value = "/name/{name}")
    public String testMethod(@PathVariable(value = "name") String name) {
        return name;
    }

    @Test
    public void paramTest() throws NoSuchMethodException, ParamTypeException {
        String url  = "/name/{name}";
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        Map<String, List<String>> pathVars = new HashMap<>();
        pathVars.put("name",new ArrayList<>(Collections.singletonList("zhangyu")));
        Object[] paramArr = new DefaultDispatcher().parseParam(decoder, ParaTest.class.getMethod("testMethod", String.class), null, null, pathVars);
        for(Object obj:paramArr) {
            System.out.println(obj);
        }
    }
}
