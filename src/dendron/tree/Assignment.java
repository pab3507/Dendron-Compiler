package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An ActionNode that represents the assignment of the value of an expression to a variable.
 * @author Pedro Breton
 */
public class Assignment implements ActionNode {
    private String name;
    private ExpressionNode rhs;

    /**
     * Set up an Assignment node.

     * @param ident the name of the variable that is getting a new value
     * @param rhs  the expression on the "right-hand side" (RHS) of the assignment statement
     */
    public Assignment(String ident, ExpressionNode rhs) {
        this.name = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluate the RHS expression and assign the result value to the variable.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        symTab.put(this.name, this.rhs.evaluate(symTab));

    }

    /**
     * Show this assignment on standard output as a variable followed by an assignment arrow (":=")
     * followed by the infix form of the RHS expression.
     */
    @Override
    public void infixDisplay() {
        System.out.printf("%s := ", this.name);
        this.rhs.infixDisplay();
    }

    /**
     * This method returns a STORE instruction for the variable in question preceded by the code emitted by the RHS node
     * that eventually pushes the value of the expression onto the stack.
     * @return This method returns a STORE instruction for the variable in question preceded by the code emitted by the
     * RHS node that eventually pushes the value of the expression onto the stack.
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.addAll(rhs.emit());
        list.add(new Machine.Store(this.name));
        return list;
    }
}
