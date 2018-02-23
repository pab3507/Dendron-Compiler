package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.*;

public class UnaryOperation implements ExpressionNode {
    public static final String NEG = "_";
    public static final String SQRT = "#";
    public static final Collection<String> OPERATORS = new ArrayList<>(Arrays.asList(NEG, SQRT));

    private String operator;
    private ExpressionNode expr;

    public UnaryOperation(String operator, ExpressionNode expr) {

        if (OPERATORS.contains(operator)) {
            this.operator = operator;
            this.expr = expr;
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, String.format("%s is not a valid operator", operator));
        }
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {
        switch (this.operator) {
            case NEG:
                return -1 * this.expr.evaluate(symTab);
            case SQRT:
                return (int) Math.sqrt(this.expr.evaluate(symTab));
        }
        return 0;
    }

    @Override
    public void infixDisplay() {
        System.out.printf("%s", this.operator);
        this.expr.infixDisplay();

    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.addAll(this.expr.emit());
        switch (this.operator) {
            case NEG:
                list.add(new Machine.Negate());
                break;
            case SQRT:
                list.add(new Machine.SquareRoot());
                break;
        }
        return list;
    }
}
