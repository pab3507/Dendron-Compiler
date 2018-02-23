package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Constant implements ExpressionNode {
    private final int value;

    public Constant(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(Map<String, Integer> symTab) {
        return this.value;
    }

    @Override
    public void infixDisplay() {
        System.out.printf("%d", this.value);

    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.add(new Machine.PushConst(this.value));
        return list;
    }
}
