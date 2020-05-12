package example.controller;

import http.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RESTFulController(value = "/calculator")
public class HelloController {

    @Service
    TestService testService;

    @RESTFulMapping(value = "/add")
    public Map<String,String> add(@RequestParam(value = "a") int a, @RequestParam(value = "b") int b) {

        Map<String, String> result = new HashMap<>();
        result.put("result",String.valueOf(testService.add(a,b)));
        return result;
    }

    @RESTFulMapping(value = "/del")
    public Map<String,String> del(@RequestParam(value = "a") int a, @RequestParam(value = "b") int b) {

        Map<String, String> result = new HashMap<>();
        result.put("result",String.valueOf(testService.del(a,b)));
        return result;
    }

    @RESTFulMapping(value = "/say/{content}")
    public String say(@PathVariable(value = "content") String content) {
        return content;
    }


}
