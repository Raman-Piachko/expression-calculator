package webapp;

import webapp.calculator.Calculator;
import webapp.calculator.WebCalculatorFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/calc")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebCalculatorFactory factory = new WebCalculatorFactory();
        Calculator calculator = factory.createCalculator(req, resp);
        calculator.calculate();
    }
}