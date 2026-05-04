package fr.uga.pddl4j.yasp;

import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.problem.Fluent;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.util.BitVector;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements a planning problem/domain encoding into DIMACS
 *
 * @author H. Fiorino
 * @version 0.1 - 30.03.2024
 */
public final class SATEncoding {
    /*
     * A SAT problem in dimacs format is a list of int list a.k.a clauses
     */

    private final int nb_fluents;
    private List<List<Integer>> initList = new ArrayList<List<Integer>>();

    /*
     * Goal
     */
    private List<Integer> goalList = new ArrayList<Integer>();

    /*
     * Actions
     */
    private final int nb_actions;
    private List<List<Integer>> actionPreconditionList = new ArrayList<List<Integer>>();
    private List<List<Integer>> actionEffectList = new ArrayList<List<Integer>>();

    /*
     * State transistions
     */
    private HashMap<Integer, List<Integer>> addList = new HashMap<Integer, List<Integer>>();
    private HashMap<Integer, List<Integer>> delList = new HashMap<Integer, List<Integer>>();
    private List<List<Integer>> stateTransitionList = new ArrayList<List<Integer>>();

    /*
     * Action disjunctions
     */
    private List<List<Integer>> actionDisjunctionList = new ArrayList<List<Integer>>();

    /*
     * Current DIMACS encoding of the planning domain and problem for #steps steps
     * Contains the initial state, actions and action disjunction
     * Goal is not there!
     */
    public List<List<Integer>> currentDimacs = new ArrayList<List<Integer>>();

    /*
     * Current goal encoding
     */
    public List<Integer> currentGoal = new ArrayList<Integer>();

    /*
     * Current number of steps of the SAT encoding
     */
    private int steps;

    public SATEncoding(Problem problem, int steps) {

        this.steps = steps;

        // Encoding of init
        // Each fact is a unit clause
        // Init state step is 1
        // We get the initial state from the planning problem
        // State is a bit vector where the ith bit at 1 corresponds to the ith fluent being true
        nb_fluents = problem.getFluents().size();
        System.out.println(" fluents = " + nb_fluents );

        // initial state
        final BitVector initState = problem.getInitialState().getPositiveFluents();
        for(int index=0; index<nb_fluents; index++){
            initList.add(List.of(initState.get(index) ? index+1 : -(index+1)));
        }

        // goal
        final BitVector goal = problem.getGoal().getPositiveFluents();
        for(int index=0; index<goal.size(); index++){
            if(goal.get(index)){
                goalList.add(index+1);
            }
        }

        // actions
        final List<Action> actions = problem.getActions();
        this.nb_actions = actions.size();
        for(int index=0; index<actions.size(); index++){
            Action action = actions.get(index);

            // preconditions
            List<Integer> precondList = new ArrayList<>();
            final BitVector preconds = action.getPrecondition().getPositiveFluents();
            for(int jndex=0; jndex<preconds.size(); jndex++){
                if(preconds.get(jndex)){
                    precondList.add(jndex+1);
                }
            }
            actionPreconditionList.add(precondList);

            // effects
            List<Integer> effectList = new ArrayList<>();
            final BitVector effects = action.getUnconditionalEffect().getPositiveFluents();
            for(int jndex=0; jndex<effects.size(); jndex++){
                if(effects.get(jndex)){
                    int effect_id = jndex+1;
                    effectList.add(effect_id);

                    // for state transitions
                    int action_id = index+1;
                    if(addList.containsKey(effect_id)){
                        addList.get(effect_id).add(action_id);
                    }else{
                        addList.put(effect_id, new ArrayList<Integer>(action_id));
                    }
                }
            }
            // negative effects
            final BitVector neffects = action.getUnconditionalEffect().getNegativeFluents();
            for(int jndex=0; jndex<neffects.size(); jndex++){
                if(neffects.get(jndex)){
                    int effect_id = jndex+1;
                    effectList.add(-effect_id);

                    // for state transitions
                    int action_id = index+1;
                    if(delList.containsKey(effect_id)){
                        delList.get(effect_id).add(action_id);
                    }else{
                        delList.put(effect_id, new ArrayList<Integer>(action_id));
                    }
                }
            }
            actionEffectList.add(effectList);

            // state transitions

            // disjonctions
            for(int jndex=0; jndex<index; jndex++){
                actionDisjunctionList.add(List.of(-(index+1), -(jndex+1)));
            }
        }

        // Makes DIMACS encoding from 1 to steps
        encode(1, steps);
    }

    /*
     * SAT encoding for next step
     */
    public void next() {
        this.steps++;
        encode(1, this.steps);
    }

    public static String toString(final List<Integer> clause, final Problem problem) {
        final int nb_fluents = problem.getFluents().size();
        List<Integer> dejavu = new ArrayList<Integer>();
        String t = "[";
        String u = "";
        int tmp = 1;
        int [] couple;
        int bitnum;
        int step;
        for (Integer x : clause) {
            if (x > 0) {
                couple = unpair(x);
                bitnum = couple[0];
                step = couple[1];
            } else {
                couple = unpair(- x);
                bitnum = - couple[0];
                step = couple[1];
            }
            t = t + "(" + bitnum + ", " + step + ")";
            t = (tmp == clause.size()) ? t + "]\n" : t + " + ";
            tmp++;
            final int b = Math.abs(bitnum);
            if (!dejavu.contains(b)) {
                dejavu.add(b);
                u = u + b + " >> ";
                if (nb_fluents >= b) {
                    Fluent fluent = problem.getFluents().get(b - 1);
                    u = u + problem.toString(fluent)  + "\n";
                } else {
                    if(b-nb_fluents-1>=problem.getActions().size()){
                        u += (b-nb_fluents-1)+" ?\n";
                    }else{
                        u = u + problem.toShortString(problem.getActions().get(b - nb_fluents - 1)) + "\n";
                    }
                }
            }
        }
        return t + u;
    }

    public static Plan extractPlan(final List<Integer> solution, final Problem problem) {
        Plan plan = new SequentialPlan();
        HashMap<Integer, Action> sequence = new HashMap<Integer, Action>();
        final int nb_fluents = problem.getFluents().size();
        int[] couple;
        int bitnum;
        int step;
        for (Integer x : solution) {
            if (x > 0) {
                couple = unpair(x);
                bitnum = couple[0];
            } else {
                couple = unpair(-x);
                bitnum = -couple[0];
            }
            step = couple[1];
            // This is a positive (asserted) action
            if (bitnum > nb_fluents) {
                final Action action = problem.getActions().get(bitnum - nb_fluents - 1);
                sequence.put(step, action);
            }
        }
        for (int s = sequence.keySet().size(); s > 0 ; s--) {
            plan.add(0, sequence.get(s));
        }
        return plan;
    }

    // Cantor pairing function generates unique numbers
    private static int pair(int num, int step) {
        if(num<0 || step<0) throw new IllegalArgumentException(Integer.toString(num)+", "+Integer.toString(step));
        return (int) (0.5 * (num + step) * (num + step + 1) + step);
    }

    private static int[] unpair(int z) {
        if(z<0) throw new IllegalArgumentException(Integer.toString(z));
        /*
        Cantor unpair function is the reverse of the pairing function. It takes a single input
        and returns the two corresponding values.
        */
        int t = (int) (Math.floor((Math.sqrt(8 * z + 1) - 1) / 2));
        int bitnum = t * (t + 3) / 2 - z;
        int step = z - t * (t + 1) / 2;
        return new int[]{bitnum, step}; //Returning an array containing the two numbers
    }

    private void encode(int from, int to) {
        this.currentDimacs.clear();
        this.currentGoal.clear();

        // init state
        for(List<Integer> init : initList){
            // init state step is 1
            int f = init.get(0);
            this.currentDimacs.add(List.of(f>0 ? pair(f, 1) : -pair(-f, 1)));
        }

        final int nb_fluents = initList.size();

        for(int step=from; step<=to; step++){
            // actions
            for(int action=0; action<this.nb_actions; action++){
                List<Integer> actionPrecond = actionPreconditionList.get(action);
                for(Integer precond : actionPrecond){
                    this.currentDimacs.add(List.of(
                        -pair(action+nb_fluents, step),
                        pair(precond, step)
                    ));
                }
                List<Integer> actionEffect = actionEffectList.get(action);
                for(Integer effect : actionEffect){
                    this.currentDimacs.add(List.of(
                        -pair(action+nb_fluents, step),
                        pair(effect, step+1)
                    ));
                }
            }
            // transitions
            for(Map.Entry<Integer, List<Integer>> entry : addList.entrySet()){
                List<Integer> transition = new ArrayList<>();
                transition.add(pair(entry.getKey(), step));
                transition.add(-pair(entry.getKey(), step+1));

                for(Integer action : entry.getValue()){
                    transition.add(pair(action+nb_fluents, step));
                }
                this.currentDimacs.add(transition);
            }
            for(Map.Entry<Integer, List<Integer>> entry : delList.entrySet()){
                List<Integer> transition = new ArrayList<>();
                transition.add(-pair(entry.getKey(), step));
                transition.add(pair(entry.getKey(), step+1));

                for(Integer action : entry.getValue()){
                    transition.add(pair(action+nb_fluents, step));
                }
                this.currentDimacs.add(transition);
            }
            // disjunctions
            for(int index=0; index<actionDisjunctionList.size(); index++){
                List<Integer> clause = actionDisjunctionList.get(index);
                this.currentDimacs.add(List.of(
                    -pair(-clause.get(0)+nb_fluents, step),
                    -pair(-clause.get(1)+nb_fluents, step)
                ));
            }
        }

        // goal
        for(int goal : goalList){
            this.currentGoal.add(pair(goal, to+1));
        }

        System.out.println("Encoding : successfully done (" + (this.currentDimacs.size()
                + this.currentGoal.size()) + " clauses, " + to + " steps)");
    }

    public String toString(Problem problem){
        StringBuilder res = new StringBuilder();

        res.append("\ninit state :\n");
        // System.err.println(initList);
        stringCNF(res, initList, problem, false);

        res.append("\ngoal :\n");
        // System.err.println(goalList);
        stringClause(res, goalList, problem, false);
        res.append("\n");

        res.append("\naction preconditions :\n");
        // System.err.println(actionPreconditionList);
        stringCNF(res, actionPreconditionList, problem, false);

        res.append("\naction effects :\n");
        // System.err.println(actionEffectList);
        stringCNF(res, actionEffectList, problem, false);

        res.append("\naction disjunctions :\n");
        // System.err.println(actionDisjunctionList);
        stringCNF(res, actionDisjunctionList, problem, true);

        res.append("\ndimacs :\n");
        for(List<Integer> clause : this.currentDimacs){
            res.append(SATEncoding.toString(clause, problem));
            res.append("\n");
        }
        res.append("\ngoal :\n");
        res.append(SATEncoding.toString(this.currentGoal, problem));

        return res.toString();
    }

    private void stringCNF(StringBuilder builder, List<List<Integer>> cnf, Problem problem, boolean isActions){
        builder.append("{");
        for(List<Integer> clause : cnf){
            stringClause(builder, clause, problem, isActions);
        }
        builder.append("}\n");
    }

    private void stringClause(StringBuilder builder, List<Integer> clause, Problem problem, boolean isActions){
        builder.append("[");
        for(Integer fluent : clause){
            builder.append(" ");
            int f = fluent;
            if(fluent<0){
                builder.append("-");
                f = -fluent;
            }
            f -= 1;
            if(!isActions){
                builder.append(problem.toString(problem.getFluents().get(f)));
            }else{
                builder.append("(");
                builder.append(problem.toShortString(problem.getActions().get(f)));
                builder.append(")");
            }
        }
        builder.append(" ]");
    }
}
