package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Variable implements ExpressionNode {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return symTab.get(this.name);
    }

    @Override
    public void infixDisplay() {
        System.out.printf("%s", this.name);

    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.add(new Machine.Load(this.name));
        return list;
    }
}
