package POJava.Plants;

import POJava.*;
import POJava.Point;

import java.awt.*;

public class Guarana extends Plant {
    private static final int GUARANA_POWER = 0;
    private static final int GUARANA_INITIATIVE = 0;

    public Guarana(World world, Point position, int age){
        super(OrganismType.GUARANA, world, position, age, GUARANA_POWER, GUARANA_INITIATIVE);
        setColor(Color.RED);
    }
    @Override
    public void Collision(Organism other){
        other.setPower(3);
        this.getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
        this.setAlive(false);
        getWorld().DeleteOrganism(this);
        Log.AddComment(other.OrganismTypeToString() + " eats Guarana and gains 3 power. Now it's power is " + other.getPower());
    }
    @Override
    public String OrganismTypeToString() {
        return "Guarana";
    }
}
