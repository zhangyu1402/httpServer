package http.route;

import com.alibaba.fastjson.JSONObject;
import http.annotation.Param;
import http.annotation.PathVariable;
import http.annotation.RequestParam;
import http.container.Container;
import http.entity.MethodEntity;
import http.exception.ParamTypeException;
import http.filter.ControllerFilter;
import http.filter.ServiceFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher {

//    private ControllerFilter controllerFilter;
//    private ServiceFilter serviceFilter;

    public Dispatcher() {}

//    public Dispatcher(ControllerFilter controllerFilter, ServiceFilter serviceFilter) {
//        this.controllerFilter = controllerFilter;
//        this.serviceFilter = serviceFilter;
//    }

    public Map<String,List<String>> parsePathVar(String resource,String path) {
        Map<String,List<String>> pathVars = new HashMap<>();
        int i = 0, j = 0;
        while(i<resource.length() && j<path.length()) {
            char c1 = resource.charAt(i);
            char c2 = path.charAt(j);
            if(c1 == c2) {
                i++;
                j++;
            }else if(c1 == '{') {
                int ii = i;
                while(ii<resource.length() && resource.charAt(ii) != '}') {
                    ii++;
                }
                String key = resource.substring(i+1,ii);
                int jj = j;
                while(jj<path.length() && path.charAt(jj) != '/') {
                    jj++;
                }
                List<String> valueList = new ArrayList<>();
                String value = path.substring(j,jj);
                valueList.add(value);
                pathVars.put(key,valueList);
                i = ii+1;
                j = jj;
            }else {
                return null;
            }
        }
        if(i>=resource.length() && j>=path.length()) return pathVars;
        return null;
    }

    public DefaultFullHttpResponse routWithPathVar(FullHttpRequest request, DefaultFullHttpResponse response,QueryStringDecoder decoder) throws ParamTypeException, InvocationTargetException, IllegalAccessException {
        String requestPath = decoder.path();


        for(String url: Container.methods.keySet()) {
            Map<String,List<String>> pathVars = parsePathVar(url,requestPath);
            if(pathVars != null) {
                MethodEntity methodEntity = Container.methods.get(url);
                Method method = methodEntity.getMethod();
                Object[] args = parseParam(decoder,method,request,response,pathVars);
                Object result = methodEntity.getMethod().invoke(methodEntity.getClazz(),args);
                ByteBuf byteBuf = Unpooled.copiedBuffer(JSONObject.toJSONString(JSONObject.toJSON(result)),CharsetUtil.UTF_8);
                return (DefaultFullHttpResponse) response.replace(byteBuf);
            }
        }
        return null;
    }

    public DefaultFullHttpResponse Route(FullHttpRequest request, DefaultFullHttpResponse response) throws InvocationTargetException, IllegalAccessException, ParamTypeException {
        String uri = request.uri();
        QueryStringDecoder decoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
        if(!Container.methods.containsKey(decoder.path())) {
            DefaultFullHttpResponse result = routWithPathVar(request,response,decoder);
            if(result != null) return result;
            response.setStatus(HttpResponseStatus.NOT_FOUND);
            return response;
        }

        MethodEntity methodEntity = Container.methods.get(decoder.path());
        Method method = methodEntity.getMethod();
        Object result = method.invoke(methodEntity.getClazz(),parseParam(decoder,method,request,response,null));
        ByteBuf byteBuf = Unpooled.copiedBuffer(JSONObject.toJSONString(JSONObject.toJSON(result)),CharsetUtil.UTF_8);

        return (DefaultFullHttpResponse) response.replace(byteBuf);
    }

    public Object parseParamHelper(Parameter param,Map<String,List<String>> params, String varName) throws ParamTypeException {
        for(String key : params.keySet()) {
            if( key.equals(varName)) {
                if(param.getParameterizedType() == int.class) {
                    return Integer.parseInt(params.get(key).get(0));
                } else if(param.getParameterizedType() == float.class) {
                    return Float.parseFloat(params.get(key).get(0));
                }else if(param.getParameterizedType() == String.class) {
                    return params.get(key).get(0);
                }
            }
        }
        throw new ParamTypeException("can not match your parameter"+param.getParameterizedType()+" with request params");
    }


    public Object[] parseParam(QueryStringDecoder decoder,Method method,FullHttpRequest request, DefaultFullHttpResponse response,Map<String,List<String>> pathVars) throws ParamTypeException {
        Map<String, List<String>> params = decoder.parameters();
        Object[] result = new Object[method.getParameterCount()];
        int i = 0;
        for(Parameter param : method.getParameters()) {
            if(param.getParameterizedType() == FullHttpRequest.class) {
                result[i++] = request;
            } else if(param.getParameterizedType() == FullHttpRequest.class) {
                result[i++] = response;
            }else if(param.isAnnotationPresent(RequestParam.class)) {
                result[i++] = parseParamHelper(param,params,param.getAnnotation(RequestParam.class).value());
            }else if(param.isAnnotationPresent(PathVariable.class)) {
                result[i++] = parseParamHelper(param,pathVars,param.getAnnotation(PathVariable.class).value());
            }
        }
        return result;
    }
}
