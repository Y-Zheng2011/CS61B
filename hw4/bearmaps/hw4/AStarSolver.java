package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private long timeout, startTime, currentTime;
    private AStarGraph<Vertex> input;
    private Vertex source, target;
    private int count;

    private Map<Vertex, Double> distTo;
    private Map<Vertex, Boolean> marked;
    private Map<Vertex, Vertex> edgeTo;
    private ArrayHeapMinPQ<Vertex> fringe;

    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        startTime = System.currentTimeMillis();
        this.timeout = (long) timeout * 1000;

        this.input = input;
        source = start;
        target = end;
        count = 0;

        fringe = new ArrayHeapMinPQ<>();
        marked = new HashMap<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new LinkedList<>();

        fringe.add(source, this.input.estimatedDistanceToGoal(source, target));
        distTo.put(source, 0.0);

        solve();
        construct();
    }

    private void solve() {
        while (!fringe.getSmallest().equals(target)) {
            if (explorationTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                solution.clear();
                break;
            }
            if (fringe.size() == 0) {
                outcome = SolverOutcome.UNSOLVABLE;
                solution.clear();
                break;
            }
            count += 1;
            Vertex fresh = fringe.removeSmallest();
            marked.put(fresh, true);
            relax(fresh);
        }
        outcome = SolverOutcome.SOLVED;
    }

    private void relax(Vertex curr) {
        for (WeightedEdge<Vertex> edge : input.neighbors(curr)) {
            Vertex to = edge.to();
            double w = edge.weight();
            double tmp = distTo.get(curr) + w;
            if (!distTo.containsKey(to) || tmp < distTo.get(to)) {
                distTo.put(to, tmp);
                tmp = tmp + input.estimatedDistanceToGoal(to, target);
                if (fringe.contains(to)) {
                    fringe.changePriority(to, tmp);
                } else {
                    fringe.add(edge.to(), tmp);
                    edgeTo.put(to, curr);
                }
            }
        }
    }

    private void construct() {
        solutionWeight = distTo.get(target);
        Vertex p = target;
        while (!p.equals(source)) {
            solution.add(0, p);
            p = edgeTo.get(p);
        }
        solution.add(0, source);
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return count;
    }

    @Override
    public double explorationTime() {
        currentTime = System.currentTimeMillis();
        return (currentTime - startTime) / 1000.0;
    }


}
