package dendron.tree;

import dendron.Errors;
import dendron.machine.Machine;
import dendron.tree.ActionNode;
import dendron.tree.ExpressionNode;

import java.util.*;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * THIS CLASS IS UNIMPLEMENTED. All methods are stubbed out.
 *
 * @author Pedro Creton
 */
public class ParseTree {
    private Map<String,Integer> symTab;
    private ArrayList<List<String>> tokensClusters = new ArrayList<>();
    private ArrayList<ActionNode> actions = new ArrayList<>();
    private Program program;
    private Stack stack = new Stack();

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     * @param program the token list (Strings)
     */
    public ParseTree( List< String > program ) {
        this.symTab = new HashMap<>();
        this.program = new Program();
        for (String token : program){
            switch (token){
                case ":=":
                case "@":
                    tokensClusters.add(new ArrayList<>());
                    tokensClusters.get(tokensClusters.size()-1).add(token);
                    break;
                default:
                    tokensClusters.get(tokensClusters.size()-1).add(token);
            }
        }

        for (List<String> tokens : tokensClusters){
            for (int i = tokens.size()-1; i>=0; i--){
                String token = tokens.get(i);
                switch (token){
                    case ":=":
                        Variable var = (Variable) stack.pop();
                        actions.add(new Assignment(var.getName(),(ExpressionNode) stack.pop()));
                        break;
                    case "@":
                        actions.add(new Print((ExpressionNode) stack.pop()));
                        break;

                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        stack.push(new BinaryOperation(token, (ExpressionNode) stack.pop(), (ExpressionNode) stack.pop()));
                        break;

                    case "_":
                    case "#":
                        stack.push(new UnaryOperation(token, (ExpressionNode) stack.pop()));
                        break;

                    default:
                        if (token.matches( "^[a-zA-Z].*" )){
                            stack.push(new Variable(token));

                        } else if (token.matches("[-+]?\\d+")){
                            stack.push(new Constant(Integer.parseInt(token)));
                        } else {
                            Errors.report(Errors.Type.ILLEGAL_VALUE, "Not a valid Expression");
                        }
                }
            }
        }

        for (ActionNode action : actions){
            this.program.addAction(action);
        }

    }

//    /**
//     * Parse the next action (statement) in the list.
//     * (This method is not required, just suggested.)
//     * @param program the list of tokens
//     * @return a parse tree for the action
//     */
//    private ActionNode parseAction( List< String > program ) {
//        switch (program.get(0)){
//            case "@":
//                return new Print(parseExpr(program.subList(1,program.size())));
//            case ":=":
//                if (program.get(1).matches( "^[a-zA-Z].*" )) {
//                    return new Assignment(program.get(1),parseExpr(program.subList(2,program.size())));
//                } else {
//                    Errors.report(Errors.Type.ILLEGAL_VALUE, "Not a valid variable name");
//                    break;
//                }
//            default:
//                Errors.report(Errors.Type.ILLEGAL_VALUE, "Not a valid statement");
//                break;
//        }
//        return null;
//    }
//
//    /**
//     * Parse the next expression in the list.
//     * (This method is not required, just suggested.)
//     * @param program the list of tokens
//     * @return a parse tree for this expression
//     */
//    private ExpressionNode parseExpr( List< String > program ) {
//        String firstToken = program.get(0);
//        int idxNextExpr = 1;
//        switch (firstToken){
//            case "+":
//            case "-":
//            case "*":
//            case "/":
//                while (program.get(idxNextExpr).matches("[+-/*_#]")){
//                    idxNextExpr++;
//                }
//                int idxNextExpr2 = idxNextExpr;
//                while (idxNextExpr2<program.size() && !program.get(idxNextExpr2).matches("[+-/*_#]")){
//                    idxNextExpr2++;
//                }
//
//                return new BinaryOperation(firstToken, parseExpr(program.subList(1, idxNextExpr+1)), parseExpr(program.subList(idxNextExpr+1, program.size())));
//
//            case "_":
//            case "#":
//                while (program.get(idxNextExpr).matches("[+-/*_#]")){
//                    idxNextExpr++;
//                }
//                while (idxNextExpr<program.size() && !program.get(idxNextExpr).matches("[+-/*_#]")){
//                    idxNextExpr++;
//                }
//                return new UnaryOperation(firstToken,parseExpr(program.subList(1,idxNextExpr)));
//
//            default:
//                if (firstToken.matches( "^[a-zA-Z].*" )){
//                    return new Variable(firstToken);
//                } else if (firstToken.matches("[-+]?\\d+")){
//                    return new Constant(Integer.parseInt(firstToken));
//                } else {
//                    Errors.report(Errors.Type.ILLEGAL_VALUE, "Not a valid Expression");
//                }
//
//        }
//        return null;
//    }

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see dendron.tree.ActionNode#infixDisplay()
     */
    public void displayProgram() {
        System.out.println("The Program, with expressions in infix notation:\n");
        program.infixDisplay();
        System.out.println();
    }

    /**
     * Run the program represented by the tree directly
     * @see dendron.tree.ActionNode#execute(Map)
     */
    public void interpret() {
        System.out.println("Interpreting the parse tree...");
        program.execute(this.symTab);
        System.out.println("Interpretation complete.\n");

        Errors.dump(symTab);
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     * @return the Machine.Instruction list
     * @see Machine.Instruction#execute()
     */
    public List< Machine.Instruction > compile() {
        return program.emit();
    }

}
