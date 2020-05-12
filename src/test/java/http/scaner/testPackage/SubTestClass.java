package http.scaner.testPackage;

import http.annotation.RESTFulController;
import http.annotation.RESTFulMapping;

@RESTFulController("world/")
public class SubTestClass {
    @RESTFulMapping("xiaowei")
    public String  test() {
        return "xiaowei";
    }

    @RESTFulMapping("xiaowei/say")
    public String  test2(String s) {
        return "xiaowei "+s;
    }

}
