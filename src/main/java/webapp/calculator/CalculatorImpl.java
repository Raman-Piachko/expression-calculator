package webapp.calculator;

import com.google.code.mathparser.MathParser;
import com.google.code.mathparser.MathParserFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static webapp.calculator.CalculatorConstants.EMPTY_SYMBOL;
import static webapp.calculator.CalculatorConstants.EXPRESSION;
import static webapp.calculator.CalculatorConstants.JSP_PAGE;
import static webapp.calculator.CalculatorConstants.RESULT;
import static webapp.calculator.CalculatorConstants.SPACES;
import static webapp.calculator.CalculatorConstants.WITHOUT_SQUARE_BRACKETS;

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
        String expression = req.getParameter(EXPRESSION);

        while (isExpressionWithVariables(parameters, expression)) {
            List<String> expressionWithValue = getExpressionWithValue(parameters, expression.split(EMPTY_SYMBOL));
            expression = deleteSpaces(expressionWithValue);
        }

        MathParser mathParser = MathParserFactory.create();
        int result = mathParser.calculate(expression)
                .doubleValue()
                .intValue();

        req.setAttribute(RESULT, result);
        req.getRequestDispatcher(JSP_PAGE).forward(req, resp);
    }

    private String deleteSpaces(List<String> expressionWithValue) {
        return String.join(EMPTY_SYMBOL, expressionWithValue)
                .replaceAll(SPACES, EMPTY_SYMBOL);
    }

    private List<String> getExpressionWithValue(Map<String, String[]> parameters, String[] expression) {
        List<String> listExpression = Arrays.asList(expression);

        for (String item : listExpression) {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                if (key.equalsIgnoreCase(item)) {
                    String clearValue = Arrays.toString(value)
                            .replaceAll(WITHOUT_SQUARE_BRACKETS, EMPTY_SYMBOL);
                    int replaceableIndex = listExpression.indexOf(item);
                    listExpression.set(replaceableIndex, clearValue);
                }
            }
        }

        return listExpression;
    }

    private boolean isExpressionWithVariables(Map<String, String[]> parameters, String expression) {
        String[] splitExpression = expression.split(EMPTY_SYMBOL);

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