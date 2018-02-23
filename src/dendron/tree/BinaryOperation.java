package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.*;

public class BinaryOperation implements ExpressionNode {
    public static final String ADD = "+";
    public static final String SUB = "-";
    public static final String MUL = "*";
    public static final String DIV = "/";
    public static final Collection<String> OPERATORS = new ArrayList<>(Arrays.asList(ADD, SUB, MUL, DIV));

    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {
        if (OPERATORS.contains(operator)) {
            this.operator = operator;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        } else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, String.format("%s is not a valid operator", operator));
        }

    }

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

    @Override
    public void infixDisplay() {
        System.out.print("( ");
        this.leftChild.infixDisplay();
        System.out.print(" " + this.operator + " ");
        this.rightChild.infixDisplay();
        System.out.print(" )");
    }

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
