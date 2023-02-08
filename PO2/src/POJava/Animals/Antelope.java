package POJava.Animals;

import POJava.*;
import POJava.Point;

import java.awt.*;
import java.util.Random;

public class Antelope extends Animal {
    private static final int ANTELOPE_POWER = 4;
    private static final int ANTELOPE_INITIATIVE = 4;

    public Antelope(World world, Point position, int age) {
        super(OrganismType.FOX, world, position, age, ANTELOPE_POWER, ANTELOPE_INITIATIVE);
        setColor(new Color(153, 76, 0));
    }

    @Override
    public Point ChooseAnySpot(Point position){
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = getWorld().getSizeX();
        int sizeY = getWorld().getSizeY();

        if (posX == 0 || posX == 1) LockDirection(Direction.LEFT);
        if (posX == sizeX - 1 || posX == sizeX - 2) LockDirection(Direction.RIGHT);
        if (posY == 0 || posY == 1) LockDirection(Direction.UP);
        if(posY == sizeY - 1 || posY == sizeY - 2) LockDirection(Direction.DOWN);
        while(true){
            Random random = new Random();
            int limit = 100;
            int chance = random.nextInt(limit);
            if(chance < 25 && !IsDirectionLocked(Direction.LEFT))
                return new Point(posX - 2, posY);
            else if(chance >= 25 && chance < 50 && !IsDirectionLocked(Direction.RIGHT))
                return new Point(posX + 2, posY);
            else if(chance >= 50 && chance < 75 && !IsDirectionLocked((Direction.UP)))
                return new Point(posX, posY - 2);
            else if(chance >= 75 && !IsDirectionLocked(Direction.DOWN))
                return new Point(posX, posY + 2);
        }
    }

    @Override
    public void Collision(Organism other) {
        Random random = new Random();
        int tmp = random.nextInt(4);
        if(tmp < 2)
        {
            Point pos = ChooseFreeSpot(getPosition());
            if(pos == getPosition())return;
            else Move(pos);
            Log.AddComment(OrganismTypeToString() + " runs away from danger");
        }
        else{
            if(this.getOrganismType() == other.getOrganismType()) Breed();
            else if(this.getPower() > other.getPower())
            {
                getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
                other.setAlive(false);
                getWorld().DeleteOrganism(other);
            }
            else
            {
                getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
                this.setAlive(false);
                getWorld().DeleteOrganism(this);
            }
        }
    }

    @Override
    public String OrganismTypeToString() {
        return "Antelope";
    }
}
