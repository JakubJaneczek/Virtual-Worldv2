package POJava;

import java.awt.*;
import java.util.Random;

public abstract class Organism {

    private int power;
    private int initiative;
    private int age;
    private World world;
    private Point position;
    private boolean alive;
    private int breedCD;
    private boolean[] direction;
    private OrganismType organismType;
    private Color color;

    public enum OrganismType {
        HUMAN,
        WOLF,
        SHEEP,
        FOX,
        TURTLE,
        ANTYLOPE,
        GRASS,
        DANDELION,
        GUARANA,
        BELLADONNA,
        PINEBORSCHT;
    }

    public enum Direction {
        LEFT(0),
        RIGHT(1),
        UP(2),
        DOWN(3),
        NO_DIRECTION(4);

        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Organism(OrganismType organismType, World world, Point position, int age, int power, int initiative){
        this.organismType = organismType;
        this.world = world;
        this.position = position;
        this.age = age;
        this.power = power;
        this.initiative = initiative;
        alive = true;
        direction = new boolean[]{true, true, true, true};
    }

    public abstract String OrganismTypeToString();

    public abstract void Action();

    public abstract void Collision(Organism other);

    public abstract boolean IsAnimal();

    public Point ChooseAnySpot(Point position){
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();

        if (posX == 0) LockDirection(Direction.LEFT);
        if (posX == sizeX - 1) LockDirection(Direction.RIGHT);
        if (posY == 0) LockDirection(Direction.UP);
        if(posY == sizeY - 1) LockDirection(Direction.DOWN);
        while(true){
            Random random = new Random();
            int limit = 100;
            int chance = random.nextInt(limit);
            if(chance < 25 && !IsDirectionLocked(Direction.LEFT))
                return new Point(posX - 1, posY);
            else if(chance >= 25 && chance < 50 && !IsDirectionLocked(Direction.RIGHT))
                return new Point(posX + 1, posY);
            else if(chance >= 50 && chance < 75 && !IsDirectionLocked((Direction.UP)))
                return new Point(posX, posY - 1);
            else if(chance >= 75 && !IsDirectionLocked(Direction.DOWN))
                return new Point(posX, posY + 1);
        }
    }
    public Point ChooseFreeSpot(Point Position){
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int freeDirections = 0;

        if (posX == 0) LockDirection(Direction.LEFT);
        else{
            if(!world.IsPlaceTaken(new Point(posX - 1, posY))) freeDirections++;
            else {
                LockDirection(Direction.LEFT);
            }
        }

        if (posX == sizeX - 1) LockDirection(Direction.RIGHT);
        else{
            if(!world.IsPlaceTaken(new Point(posX + 1, posY))) freeDirections++;
            else{
                LockDirection(Direction.RIGHT);
            }
        }

        if (posY == 0) LockDirection(Direction.UP);
        else{
            if(!world.IsPlaceTaken(new Point(posX, posY - 1))) freeDirections++;
            else{
                LockDirection(Direction.UP);
            }
        }

        if(posY == sizeY - 1) LockDirection(Direction.DOWN);
        else{
            if(!world.IsPlaceTaken(new Point(posX, posY + 1))) freeDirections++;
            else{
                LockDirection(Direction.DOWN);
            }
        }

        if (freeDirections == 0) return new Point(posX, posY);
        while(true){
            Random random = new Random();
            int limit = 100;
            int chance = random.nextInt(limit);
            if(chance < 25 && !IsDirectionLocked(Direction.LEFT))
                return new Point(posX - 1, posY);
            else if(chance >= 25 && chance < 50 && !IsDirectionLocked(Direction.RIGHT))
                return new Point(posX + 1, posY);
            else if(chance >= 50 && chance < 75 && !IsDirectionLocked((Direction.UP)))
                return new Point(posX, posY - 1);
            else if(chance >= 75 && !IsDirectionLocked(Direction.DOWN))
                return new Point(posX, posY + 1);
        }
    }

    protected void LockDirection(Direction direction){
        this.direction[direction.getValue()] = false;
    }
    protected void UnlockDirection(Direction direction){
        this.direction[direction.getValue()] = true;
    }
    protected void UnlockAllDirections(){
        UnlockDirection(Direction.LEFT);
        UnlockDirection(Direction.RIGHT);
        UnlockDirection(Direction.UP);
        UnlockDirection(Direction.DOWN);
    }
    protected boolean IsDirectionLocked(Direction direction){
        return !(this.direction[direction.getValue()]);
    }

    public int getPower() {
        return power;
    }
    public int getInitiative() {
        return initiative;
    }
    public int getAge() {
        return age;
    }
    public int getBreedCD() {return breedCD;}
    public boolean getAlive() {
        return alive;
    }
    public Point getPosition() {
        return position;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setPower(int n){
        this.power += n;
    }
    public void setBreedCD(int n){this.breedCD += n;}
    public  OrganismType getOrganismType(){return organismType;}
    public World getWorld(){return world;}
    public Color getColor() {return color;}
    public void setColor(Color color){this.color = color;}
    public void setAge(int age){
        this.age = age;
    }
}
