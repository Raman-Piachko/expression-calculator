package webapp.calculator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebCalculatorFactory {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public WebCalculatorFactory(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    public Calculator createCalculator() {
        return new CalculatorImpl(req, resp);
    }
}