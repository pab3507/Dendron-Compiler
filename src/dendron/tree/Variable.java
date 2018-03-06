package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The ExpressionNode for a simple variable
 * @author Pedro Breton
 */
public class Variable implements ExpressionNode {
    private String name;

    /**
     * Set the name of this new Variable. Note that it is not wrong for more than one Variable node to refer to the same
     * variable. Its actual value is stored in a symbol table.
     * @param name the name of this variable
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Get the name
     * return name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param symTab symbol table, if needed, to fetch variable values
     * @return
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        if (symTab.containsKey(this.name)){
            return symTab.get(this.name);
        } else {
            Errors.report(Errors.Type.PREMATURE_END, "Reach premature end");
            return -1;
        }

    }

    /**
     * Print on standard output the Variable's name.
     */
    @Override
    public void infixDisplay() {
        System.out.printf("%s", this.name);

    }

    /**
     * Emit a LOAD instruction that pushes the Variable's value onto the stack.
     * @return a list containing a single LOAD instruction and the expression's emit instructions
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.add(new Machine.Load(this.name));
        return list;
    }
}
