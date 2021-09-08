package servlets;

import calculator.Calculator;
import calculator.WebCalculatorFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(urlPatterns = "/calc")
public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebCalculatorFactory factory = new WebCalculatorFactory();
        Calculator calculator = factory.createCalculator();
        calculator.calculate(req, resp);
    }
}