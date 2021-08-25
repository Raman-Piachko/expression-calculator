package webapp.calculator;

import com.google.code.mathparser.MathParser;
import com.google.code.mathparser.MathParserFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorImpl implements Calculator {
    private HttpServletRequest req;
    private HttpServletResponse resp;

    public CalculatorImpl(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void calculate() throws ServletException, IOException {
        Map<String, String[]> parameters = new HashMap<>(req.getParameterMap());
        String expression = req.getParameter("expression");

        while (isExpressionWithVariables(parameters, expression)) {
            expression = getExpressionWithValue(parameters, expression.split(""));
        }

        MathParser mathParser = MathParserFactory.create();
        int result = mathParser.calculate(expression)
                .doubleValue()
                .intValue();

        req.setAttribute("result", result);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    private String getExpressionWithValue(Map<String, String[]> parameters, String[] expression) {
        List<String> listExpression = new ArrayList<>(Arrays.asList(expression));

        for (String item : listExpression) {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                if (key.equalsIgnoreCase(item)) {
                    String clearValue = Arrays.toString(value)
                            .replaceAll("[\\[\\]]", "");
                    int replaceableIndex = listExpression.indexOf(item);
                    listExpression.set(replaceableIndex, clearValue);
                }
            }
        }

        return String.join("", listExpression)
                .replaceAll("\\s+", "");
    }

    private boolean isExpressionWithVariables(Map<String, String[]> parameters, String expression) {
        String[] splitExpression = expression.split("");

        for (String s : splitExpression) {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String key = entry.getKey();
                if (s.equalsIgnoreCase(key)) {
                    return true;
                }
            }
        }

        return false;
    }
}