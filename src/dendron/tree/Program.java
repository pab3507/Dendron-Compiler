package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * An ActionNode used to represent a sequence of other ActionNodes. The main use of this node type is to be the root of
 * the entire program tree.
 * @author Pedro Breton
 */
public class Program implements ActionNode {
    private LinkedList<ActionNode> actionList;

    /**
     * Initialize this instance as an empty sequence of ActionNode children.
     */
    public Program() {
        actionList = new LinkedList<>();
    }

    /**
     * Add a child of this Program node.
     * @param newNode node to add
     */
    public void addAction(ActionNode newNode) {
        actionList.add(newNode);
    }

    /**
     * Execute each ActionNode in this object, from first-added to last-added.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {

        for (ActionNode node : actionList) {
            node.execute(symTab);
        }

    }

    /**
     * Show the infix displays of all children on standard output. The order is first-added to last-added.
     */
    @Override
    public void infixDisplay() {
        for (ActionNode node : actionList) {
            node.infixDisplay();
            System.out.println();
        }
    }

    /**
     * Create a list of instructions emitted by each child, from the first-added child to the last-added.
     * @return the concatenated instructions lists from all children
     */
    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        for (ActionNode node : actionList) {
            list.addAll(node.emit());
        }
        return list;
    }
}
