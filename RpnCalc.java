import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class RpnCalc {
    public static void main(String[] args){
            String expression = "8 6 + 5  "; //Note that there must be a space (' ') at the end of the expression to work properly.
        String s=infixToPostfix(expression);
        BigDecimal bigDecimal= new BigDecimal(1356465);
      System.out.println(calcPosFixExpression(s));
        
        }





    // A utility function to return precedence of a given operator
    // Higher returned value means higher precedence
    static int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }



    // The main method that converts given infix expression
    // to postfix expression.
    static String infixToPostfix(String exp)
    {
        // initializing empty String for result
        StringBuilder result = new StringBuilder("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result.append(c);

                // If the scanned character is an '(', push it to the stack.
            else if (c == '(')
                stack.push(c);

                //  If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')')
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result.append(stack.pop());

                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // invalid expression
                else
                    stack.pop();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())){
                    if(stack.peek() == '(')
                        return "Invalid Expression";
                    result.append(stack.pop());
                }
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result.append(stack.pop());
        }
        return result.toString();
    }


    public static double calcPosFixExpression(String expression){
        Stack<Double> values = new Stack<>();
        double secondValue;
        String item;
        for(int i = 0; i < expression.length(); ++i){
            if(expression.charAt(i) == ' '){
                item = expression.substring(i-1, i);
                item = item.replaceAll("\\s", ""); //for those who dont know \\s is white space
                switch (item) {
                    case "+":
                        values.push(values.pop() + values.pop());
                        break;
                    case "-":
                        secondValue = values.pop();
                        values.push(values.pop() - secondValue);
                        break;
                    case "*":
                        values.push(values.pop() * values.pop());
                        break;
                    case "/":
                        secondValue = values.pop();
                        values.push(values.pop() / secondValue);
                        break;
                    case "sqrt":
                        values.push(Math.sqrt(values.pop()));
                        break;
                    default:
                        values.push(Double.parseDouble(item));
                        break;
                }
            }
        }
        return values.peek();
    }
}



