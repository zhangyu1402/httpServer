package http.scaner;


import http.filter.Filter;

import java.io.File;
import java.net.URL;
import java.util.List;

public class HttpServerScaner {
    public List<Filter> filters;

    public HttpServerScaner(){

    }

    public HttpServerScaner(List<Filter> filterList) {
        filters = filterList;
    }

    public  void scan(Class<?> clazz,String packageName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL url = clazz.getClassLoader().getResource(packageName.replace(".","/"));
        String path = url.getPath();
        File file = new File(path);
        for(String subFileName : file.list()) {
            File subFile = new File(path+"/"+subFileName);
            if(subFile.isFile() ) {
                if(subFileName.contains("$")) continue;
                Class curClazz = Class.forName(packageName+"."+subFileName.replace(".class",""));
                if(curClazz.isAnnotation() || curClazz.isInterface() ) continue;

                for(Filter f: filters) {
                    f.filter(curClazz);
                }
            }else if(subFile.isDirectory()) {
                scan(clazz,packageName+"."+subFileName);
            }
        }
    }
}
