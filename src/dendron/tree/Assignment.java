package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assignment implements ActionNode {
    private String name;
    private ExpressionNode rhs;

    public Assignment(String ident, ExpressionNode rhs) {
        this.name = ident;
        this.rhs = rhs;
    }

    @Override
    public void execute(Map<String, Integer> symTab) {
        symTab.put(this.name, this.rhs.evaluate(symTab));

    }

    @Override
    public void infixDisplay() {
        System.out.printf("%s := ", this.name);
        this.rhs.infixDisplay();
    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.addAll(rhs.emit());
        list.add(new Machine.Store(this.name));
        return list;
    }
}
