package POJava.Plants;

import POJava.Plant;
import POJava.Point;
import POJava.World;

import java.awt.*;

public class Grass extends Plant {
    private static final int GRASS_POWER = 0;
    private static final int GRASS_INITIATIVE = 0;

    public Grass(World world, Point position, int age){
        super(OrganismType.GRASS, world, position, age, GRASS_POWER, GRASS_INITIATIVE);
        setColor(Color.GREEN);
    }

    @Override
    public String OrganismTypeToString() {
        return "Grass";
    }
}
