package http.queryStringdecoder;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecodeTest {
    @Test
    public void decodeTest() {
        String url = "/hello/world/test?test=[123,zhang]&name=456";
        QueryStringDecoder decoder = new QueryStringDecoder(url, CharsetUtil.UTF_8);
        Map<String, List<String>> map = decoder.parameters();
        System.out.println("test");

    }
    @Test
    public void parsePathVar() {
        String url = "/user/zhangyu/xw18813086938";
        String path = "/user/{name}/{pass}";
        Pattern r = Pattern.compile("\\{(.*?)}");

        Matcher m = r.matcher(path);
        while(m.find()) {
            System.out.println(m.group(1));
        }
    }

}
