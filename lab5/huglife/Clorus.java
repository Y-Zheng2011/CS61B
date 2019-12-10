package huglife;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    private int r, g, b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r, g, b);
    }


    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors){
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> anyPlip = new ArrayDeque<>();

        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                anyPlip .add(d);
            }
        }
        if (emptyNeighbors.size() < 1) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip.size() >= 1) {
            return new Action(Action.ActionType.ATTACK, randomPick(anyPlip));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomPick(emptyNeighbors));
        }
        return new Action(Action.ActionType.MOVE, randomPick(emptyNeighbors));
    }

    private Direction randomPick(Deque<Direction> d) {
        Direction ret = null;
        while (!d.isEmpty()) {
            ret = d.pollFirst();
            if (Math.random()*(d.size() + 1.0) < 1.0) {
                break;
            }
        }
        return ret;
    }
}
