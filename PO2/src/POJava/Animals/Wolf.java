package POJava.Animals;

import POJava.Animal;
import POJava.Point;
import POJava.World;

import java.awt.*;

public class Wolf extends Animal {
    private static final int WOLF_POWER = 9;
    private static final int WOLF_INITIATIVE = 5;

    public Wolf(World world, Point position, int age) {
        super(OrganismType.WOLF, world, position, age, WOLF_POWER, WOLF_INITIATIVE);
        setColor(new Color(64, 64, 64));
    }

    @Override
    public String OrganismTypeToString() {
        return "Wolf";
    }
}
