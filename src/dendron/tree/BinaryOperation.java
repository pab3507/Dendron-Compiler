package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.*;

/**
 * A calculation represented by a binary operator and its two operands.
 * @author Pedro Breton
 */
public class BinaryOperation implements ExpressionNode {
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";
    public static final Collection<String> OPERATORS = new ArrayList<>(Arrays.asList(ADD, SUB, MUL, DIV));

    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    /**
     * Create a new BinaryOperation node.
     * @param operator the string rep. of the operation
     * @param leftChild the left operand
     * @param rightChild the right operand
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {
        if (OPERATORS.contains(operator)) {
            this.operator = operator;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, String.format("%s is not a valid operator", operator));
        }

    }

    /**
     * Compute the result of evaluating both operands and applying the operator to them.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return the result of the computation
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        int a = leftChild.evaluate(symTab);
        int b = rightChild.evaluate(symTab);
        switch (this.operator) {
            case ADD:
                return a + b;
            case SUB:
                return a - b;
            case MUL:
                return a * b;
            case DIV:
                if (b != 0) {
                    return a / b;
                } else {
                    Errors.report(Errors.Type.DIVIDE_BY_ZERO, "Integer divide by zero");
                }
            default:
                return 0;
        }
    }

    /**
     * Print the infixDisplay of the two child nodes separated by the operator and surrounded by parentheses.
     * Blanks are inserted throughout
     */
    @Override
    public void infixDisplay() {
        System.out.print("( ");
        this.leftChild.infixDisplay();
        System.out.print(" " + this.operator + " ");
        this.rightChild.infixDisplay();
        System.out.print(" )");
    }

    /**
     * Emit the Machine instructions necessary to perform the computation of this BinaryOperation. T
     * he operator itself is realized by an instruction that pops two values off the stack, applies the operator,
     * and pushes the answer.
     * @return a list containing instructions for the left operand, instructions for the right operand,
     * and the instruction to perform the operation
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.addAll(this.leftChild.emit());
        list.addAll(this.rightChild.emit());
        switch (this.operator) {
            case ADD:
                list.add(new Machine.Add());
                break;
            case SUB:
                list.add(new Machine.Subtract());
                break;
            case MUL:
                list.add(new Machine.Multiply());
                break;
            case DIV:
                list.add(new Machine.Divide());
                break;
        }
        return list;
    }
}
