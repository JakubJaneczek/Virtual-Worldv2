package POJava.Animals;

import POJava.*;
import POJava.Point;

import java.awt.*;

public class Human extends Animal {
    private static final int HUMAN_POWER = 5;
    private static final int HUMAN_INITIATIVE = 4;
    private boolean AlzursShield = false;
    private int turnsLeft = 0;
    private int turnsCooldown = 0;
    private Direction direction;

    public Human(World world, Point position, int age) {
        super(OrganismType.HUMAN, world, position, age, HUMAN_POWER, HUMAN_INITIATIVE);
        setColor(Color.BLUE);
        direction = Direction.NO_DIRECTION;
    }

    @Override
    public String OrganismTypeToString() {
        return "Human";
    }

    public void SetCooldown(int cooldown){
        turnsCooldown = cooldown;
    }
    public void DecreaseCooldown(){
        if(turnsCooldown > 0)
            turnsCooldown--;
    }
    public void SetTurnsLeft(int turnsLeft){
        this.turnsLeft = 5;
    }
    public void DecreaseTurnsLeft(){
        if (turnsLeft > 0)
            turnsLeft--;
        else if (turnsLeft == 0)
            DisableAbility();
    }
    public int GetCooldown(){
        return turnsCooldown;
    }
    public int GetTurnsLeft(){
        return turnsLeft;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void Action(){
        if(this.getAlive())
        {
            if(AlzursShield){
                Log.AddComment(OrganismTypeToString() + " Alzur's Shield is active. Remaining time " + this.GetTurnsLeft());
            }
            MoveAction(PlanMove());
            DecreaseCooldown();
            DecreaseTurnsLeft();
        }
        direction = Direction.NO_DIRECTION;
    }

    public void MoveAction(Point position){
        if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
        else if(getWorld().getBoard()[position.getY()][position.getX()] != this)
        {
            getWorld().getBoard()[position.getY()][position.getX()].Collision(this);
            if(getWorld().getBoard()[position.getY()][position.getX()] == null) this.Move(position);
        }
    }
    public Point PlanMove(){
        int x = getPosition().getX();
        int y = getPosition().getY();
        ChooseAnySpot(getPosition()); // Blocks unavailable directions
        if(direction == Direction.NO_DIRECTION || IsDirectionLocked(direction)) {return getPosition();}
        else{
            if (direction == Direction.DOWN) return new Point(x, y + 1);
            if (direction == Direction.UP) return new Point(x, y - 1);
            if (direction == Direction.LEFT) return new Point(x - 1, y);
            if (direction == Direction.RIGHT) return new Point(x + 1, y);
            else return new Point(x, y);
        }
    }
    public void UseAbility(){
        AlzursShield = true;
        int cooldown = 10;
        SetCooldown(cooldown);
        SetTurnsLeft(turnsLeft);
    }
    public void DisableAbility(){
        AlzursShield = false;
    }

    @Override
    public void Collision(Organism other){
        if(AlzursShield){
            Log.AddComment(OrganismTypeToString() + " blocks " + other.OrganismTypeToString() + " attack with Alzur's Shield");
        }
        else{
            if(this.getPower() > other.getPower())
            {
                getWorld().getBoard()[other.getPosition().getY()][other.getPosition().getX()] = null;
                other.setAlive(false);
                getWorld().DeleteOrganism(other);
                Log.AddComment(other.OrganismTypeToString() + " attacks stronger " + getOrganismType() + " and dies");
            }
            else
            {
                Log.AddComment(other.OrganismTypeToString() + " attacks weaker " + getOrganismType() + " and kills his  opponent");
                getWorld().getBoard()[this.getPosition().getY()][this.getPosition().getX()] = null;
                this.setAlive(false);
                getWorld().DeleteOrganism(this);
            }
        }
    }
}
