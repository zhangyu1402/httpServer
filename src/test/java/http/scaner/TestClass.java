package http.scaner;


import http.annotation.RESTFulController;
import http.annotation.RESTFulMapping;

@RESTFulController("hello/")
public class TestClass {
    @RESTFulMapping("zhangyu")
    public String test1() {
        return "zhangyu";
    }
}
