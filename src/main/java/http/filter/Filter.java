package http.filter;

public interface Filter {
    public void filter(Class clazz) throws IllegalAccessException, InstantiationException;
}
