package http;

import http.config.Config;
import http.container.Container;
import http.filter.ControllerFilter;
import http.filter.Filter;
import http.filter.ServiceFilter;
import http.route.DefaultDispatcher;
import http.route.Dispatcher;
import http.scaner.HttpServerScaner;
import http.util.IOCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class HttpServerApp {

    private static final Logger log = LoggerFactory.getLogger(HttpServerApp.class);

    private HttpServer server;

    private Config config;


    private void init(Class clazz) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        config = new Config("/Users/zhangyu/nettyLearn/httpServer/src/main/resources/application.properties");
        server = new HttpServer(config.getPort());
        Dispatcher router = new DefaultDispatcher();
        server.addRouter(router);

        ControllerFilter controllerFilter = new ControllerFilter();
        ServiceFilter serviceFilter = new ServiceFilter();
        List<Filter> list = new ArrayList<>();
        list.add(controllerFilter);
        list.add(serviceFilter);
        HttpServerScaner serverScaner = new HttpServerScaner(list);

        serverScaner.scan(clazz, clazz.getPackage().getName());
        IOCUtil.inject(Container.getBeanSet(), Container.services);

    }


    public void run(Class clazz) throws Exception {
        init(clazz);
        server.start();
    }

}
