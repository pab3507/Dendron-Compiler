package dendron.tree;

import dendron.machine.Machine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Program implements ActionNode{
    private LinkedList<ActionNode> actionList;

    public Program(){
        actionList = new LinkedList<>();
    }

    public void addAction(ActionNode newNode){
        actionList.add(newNode);
    }


    @Override
    public void execute(Map<String, Integer> symTab) {
        for (ActionNode node : actionList){
            node.execute(symTab);
        }
    }

    @Override
    public void infixDisplay() {
        for (ActionNode node : actionList){
            node.infixDisplay();
        }
    }

    @Override
    public List<Machine.Instruction> emit() {
        ArrayList<Machine.Instruction> list = new ArrayList<>();
        for (ActionNode node : actionList){
            list.addAll(node.emit());
        }
        return list;
    }
}
