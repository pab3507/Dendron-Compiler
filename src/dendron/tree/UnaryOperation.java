package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.*;

/**
 * A calculation represented by a unary operator and its operand.
 * @author Pedro Breton
 */
public class UnaryOperation implements ExpressionNode {
    public static final String NEG = "_";
    public static final String SQRT = "#";
    public static final Collection<String> OPERATORS = new ArrayList<>(Arrays.asList(NEG, SQRT));

    private String operator;
    private ExpressionNode expr;

    /**
     * Create a new UnaryOperation node.
     * @param operator the string rep. of the operation
     * @param expr the operand
     */
    public UnaryOperation(String operator, ExpressionNode expr) {

        if (OPERATORS.contains(operator)) {
            this.operator = operator;
            this.expr = expr;
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, String.format("%s is not a valid operator", operator));
        }
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
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

    /**
     * Print, the infixDisplay of the child nodes preceded by the operator and without an intervening blank.
     */
    @Override
    public void infixDisplay() {
        System.out.printf("%s", this.operator);
        this.expr.infixDisplay();

    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this UnaryOperation.
     * The operator itself is realized by an instruction that pops a value off the stack, applies the operator,
     * and pushes the answer.
     * @return a list containing instructions for the expression and the instruction to perform the operation
     */
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
