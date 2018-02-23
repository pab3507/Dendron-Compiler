package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Print implements ActionNode {
    private ExpressionNode printee;

    public Print(ExpressionNode printee){
        this.printee = printee;
    }

    @Override
    public void execute(Map<String, Integer> symTab) {
        System.out.printf("=== %s\n",printee.evaluate(symTab));
    }

    @Override
    public void infixDisplay() {
        System.out.printf("Print ");
        printee.infixDisplay();
    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        list.addAll(this.printee.emit());
        list.add(new Machine.Print());
        return list;
    }
}
