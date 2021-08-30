package calculator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebCalculatorFactory {
    public Calculator createCalculator(HttpServletRequest req, HttpServletResponse resp) {
        return new CalculatorImpl(req, resp);
    }
}