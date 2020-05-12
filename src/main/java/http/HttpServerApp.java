package http;

import http.container.Container;
import http.filter.ControllerFilter;
import http.filter.Filter;
import http.filter.ServiceFilter;
import http.route.Dispatcher;
import http.scaner.HttpServerScaner;
import http.util.IOCUtil;

import java.util.ArrayList;
import java.util.List;

public class HttpServerApp {
    public void run(Class clazz) throws Exception {
        ControllerFilter controllerFilter = new ControllerFilter();
        ServiceFilter serviceFilter = new ServiceFilter();
        List<Filter> list = new ArrayList<>();
        list.add(controllerFilter);
        list.add(serviceFilter);
        HttpServerScaner serverScaner = new HttpServerScaner(list);
        serverScaner.scan(clazz,clazz.getPackage().getName());

        IOCUtil.inject(Container.getBeanSet(),Container.services);
        Dispatcher router = new Dispatcher();


        HttpServer server  = new HttpServer(8080);
        server.addRouter(router);
        server.start();


    }

}
