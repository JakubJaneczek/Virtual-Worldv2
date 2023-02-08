package POJava.Animals;

import POJava.Animal;
import POJava.Point;
import POJava.World;

import java.awt.*;

public class Sheep extends Animal {
    private static final int SHEEP_POWER = 4;
    private static final int SHEEP_INITIATIVE = 4;
    public Sheep(World world, Point position, int age) {
        super(OrganismType.SHEEP, world, position, age, SHEEP_POWER, SHEEP_INITIATIVE);
        setColor(new Color(255, 153, 204));
    }

    @Override
    public String OrganismTypeToString() {
        return "Sheep";
    }
}
