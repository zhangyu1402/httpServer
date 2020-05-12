package http.exception;

import org.junit.Test;

public class ParamTypeTest {
    @Test
    public void ParamTypeTest() throws Exception {
        throw new ParamTypeException("lalal");
    }
}
