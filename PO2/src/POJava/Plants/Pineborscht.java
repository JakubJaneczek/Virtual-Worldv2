package POJava.Plants;

import POJava.*;
import POJava.Point;

import java.awt.*;
import java.util.Random;

public class Pineborscht extends Plant {
    private static final int PINEBORSCHT_POWER = 99;
    private static final int PINEBORSCHT_INITIATIVE = 0;

    public Pineborscht(World world, Point position, int age){
        super(OrganismType.PINEBORSCHT, world, position, age, PINEBORSCHT_POWER, PINEBORSCHT_INITIATIVE);
        setColor(new Color(204, 0, 204));
    }

    @Override
    public void Action(){
        Random random = new Random();
        int tmp = random.nextInt(20);
        if(tmp < 1) this.Split();
        Burn();
    }

    private void Burn(){
        for(int i = this.getPosition().getY() - 1; i < this.getPosition().getY() + 2; i++)
        {
            for(int j = this.getPosition().getX() - 1; j < this.getPosition().getX() + 2; j++)
            {
                if(i > 0 && i < getWorld().getSizeY() && j > 0 && j < getWorld().getSizeX() && i != this.getPosition().getY() && j != this.getPosition().getX())
                {
                    Organism temp = this.getWorld().getBoard()[i][j];
                    if(temp != null)
                    {
                        if(temp.getOrganismType() == OrganismType.FOX || temp.getOrganismType() == OrganismType.SHEEP || temp.getOrganismType() == OrganismType.WOLF || temp.getOrganismType() == OrganismType.HUMAN || temp.getOrganismType() == OrganismType.TURTLE)
                        {
                            getWorld().DeleteOrganism(temp);
                            getWorld().getBoard()[i][j] = null;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void Collision(Organism other){
        other.getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
        other.setAlive(false);
        getWorld().DeleteOrganism(other);
    }
    @Override
    public String OrganismTypeToString() {
        return null;
    }
}
