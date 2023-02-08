package POJava.Animals;

import POJava.*;
import POJava.Point;

import java.awt.*;
import java.util.Random;

public class Turtle extends Animal {
    private static final int TURTLE_POWER = 2;
    private static final int TURTLE_INITIATIVE = 1;

    public Turtle(World world, Point position, int age) {
        super(OrganismType.TURTLE, world, position, age, TURTLE_POWER, TURTLE_INITIATIVE);
        setColor(new Color(0, 102, 0));
    }

    @Override
    public void Action(){
        Random random = new Random();
        int tmp = random.nextInt(4);
        if(tmp < 1){
            Point position = ChooseAnySpot(this.getPosition());
            if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
            else{
                getWorld().getBoard()[position.getY()][position.getX()].Collision(this);
                if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
            }
        }
    }
    @Override
    public void Collision(Organism other){
        if(this.getOrganismType() == other.getOrganismType()) Breed();
        else if(other.getPower() < 5){
            Log.AddComment(OrganismTypeToString() + " blocks " + other.OrganismTypeToString());
        }
        else if(this.getPower() > other.getPower())
        {
            getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
            other.setAlive(false);
            getWorld().DeleteOrganism(other);
            Log.AddComment(other.OrganismTypeToString() + " attacks stronger " + this.OrganismTypeToString() + " and dies");
        }
        else
        {
            getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
            this.setAlive(false);
            getWorld().DeleteOrganism(this);
            Log.AddComment(other.OrganismTypeToString() + " attacks weaker " + getOrganismType() + " and kills his  opponent");
        }
    }
    @Override
    public String OrganismTypeToString() {
        return "Turtle";
    }
}
