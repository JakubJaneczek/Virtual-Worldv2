package POJava.Plants;

import POJava.*;
import POJava.Point;

import java.awt.*;

public class Belladonna extends Plant {
    private static final int BELLADONNA_POWER = 99;
    private static final int BELLADONNA_INITIATIVE = 0;
    public Belladonna(World world, Point position, int age){
        super(OrganismType.BELLADONNA, world, position, age, BELLADONNA_POWER, BELLADONNA_INITIATIVE);
        setColor(new Color(25, 0, 51));
    }

    @Override
    public void Collision(Organism other){
        other.getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
        other.setAlive(false);
        getWorld().DeleteOrganism(other);
        Log.AddComment(other.OrganismTypeToString() + " eats Belladonna and dies");
    }

    @Override
    public String OrganismTypeToString() {
        return "Belladonna";
    }
}
