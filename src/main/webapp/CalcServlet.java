import com.google.code.mathparser.MathParser;
import com.google.code.mathparser.MathParserFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@WebServlet(urlPatterns = "/calc")
public class CalcServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> parameters = new HashMap<>(req.getParameterMap());
        String expression = req.getParameter("expression");
        removeExpression(parameters);
        expression = getExpressionWithValue(parameters, expression.split(""));

        if (!isExpressionWithoutVariables(parameters, expression)) {
            expression = getExpressionWithValue(parameters, expression.split(""));
        }
        MathParser mathParser = MathParserFactory.create();
        int result = mathParser.calculate(expression)
                .doubleValue()
                .intValue();
        req.setAttribute("result", result);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    private void removeExpression(Map<String, String[]> parameters) {
        parameters.remove("expression");
    }

    private String getExpressionWithValue(Map<String, String[]> parameters, String[] expression) {
        ArrayList<String> listExpression = new ArrayList<>(List.of(expression));

        IntStream.range(0, listExpression.size())
                .forEach(i -> parameters.forEach((key, value) -> {
                    if (key.equalsIgnoreCase(listExpression.get(i))) {
                        String clearValue = Arrays.toString(value)
                                .replaceAll("[\\[\\]]", "");
                        listExpression.set(i, clearValue);
                    }
                }));

        return String.join("", listExpression)
                .replaceAll("\\s+", "");
    }

    private boolean isExpressionWithoutVariables(Map<String, String[]> parameters, String expression) {
        String[] splitExpression = expression.split("");

        for (String s : splitExpression) {
            for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                String key = entry.getKey();
                return s.equalsIgnoreCase(key);
            }
        }
        return false;
    }
}