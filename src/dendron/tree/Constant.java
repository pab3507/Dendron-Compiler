package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An expression node representing a constant, i.e., literal value
 * @author Pedro Breton
 */
public class Constant implements ExpressionNode {
    private final int value;

    /**
     * Store the integer value in this new Constant.
     * @param value the integer it will hold
     */
    public Constant(int value) {
        this.value = value;
    }

    /**
     * Evaluate the constant
     * @param symTab symbol table, if needed, to fetch variable values
     * @return this Constant's value
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return this.value;
    }

    /**
     * Print this Constant's value on standard output.
     */
    @Override
    public void infixDisplay() {
        System.out.printf("%d", this.value);

    }

    /**
     * Emit an instruction to push the value onto the stack.
     * @return a list containing that one instruction
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.add(new Machine.PushConst(this.value));
        return list;
    }
}
