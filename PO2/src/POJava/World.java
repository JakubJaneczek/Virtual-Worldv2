package POJava;

import POJava.Animals.Human;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class World {
    private static final int NUMBER_OF_HUMANS = 1;
    private static final int NUMBER_OF_ANTELOPES = 2;
    private static final int NUMBER_OF_BELLADONNAS = 2;
    private static final int NUMBER_OF_DANDELIONS = 2;
    private static final int NUMBER_OF_FOXES = 2;
    private static final int NUMBER_OF_GRASS = 2;
    private static final int NUMBER_OF_GUARANAS = 2;
    private static final int NUMBER_OF_PINEBORSCHT = 2;
    private static final int NUMBER_OF_SHEEPS = 2;
    private static final int NUMBER_OF_TURTLES = 2;
    private static final int NUMBER_OF_WOLFES = 2;
    private int sizeX;
    private int sizeY;
    private int roundNumber;
    private Organism[][] board;
    private boolean isHumanAlive;
    private boolean isGameOver;
    private boolean pause;
    private ArrayList<Organism> organisms;
    private Human human;

    public void setWorldGUI(WorldGUI worldGUI) {
        this.worldGUI = worldGUI;
    }

    private WorldGUI worldGUI;


    public World(int sizeX, int sizeY, WorldGUI worldGUI){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        roundNumber = 0;
        isHumanAlive = true;
        isGameOver = false;
        pause = true;
        board = new Organism[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                board[i][j] = null;
            }
        }
        organisms = new ArrayList<>();
        this.worldGUI = worldGUI;
    }

    public void AddOrganism(Organism organism){
        board[organism.getPosition().getY()][organism.getPosition().getX()] = organism;
        if(organisms.size() == 0)
            {
                organisms.add(0, organism);
            }
        else {
            for(Organism temp : organisms){
                if(organism.getInitiative() > temp.getInitiative())
                {
                    int position = organisms.indexOf(temp);
                    organisms.add(position, organism);
                    return;
                }
            }
            organisms.add(organisms.size(), organism);
        }
    }

    public void DeleteOrganism(Organism organism){
        organism.setAlive(false);
        board[organism.getPosition().getY()][organism.getPosition().getX()] = null;
        if(organism.getOrganismType() == Organism.OrganismType.HUMAN){
            isHumanAlive = false;
            human = null;
        }
    }

    public Point ChooseRandom(){
        Random rand = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] == null) {
                    while (true) {
                        int x = rand.nextInt(sizeX);
                        int y = rand.nextInt(sizeY);
                        if (board[y][x] == null) return new Point(x, y);
                    }
                }
            }
        }
        return new Point(-1, -1);
    }

    public void ExecuteRound(){
        if(isGameOver) return;
        roundNumber++;
        Log.AddComment("\n Round " + roundNumber);
        System.out.println(roundNumber);
        System.out.println(organisms.size() + "\n");
        for(int i = 0; i< organisms.size(); i++){
           if( organisms.get(i).getAge() != roundNumber && organisms.get(i).getAlive()){
               organisms.get(i).Action();
           }
        }
        for(int i = 0; i< organisms.size(); i++){
            if(!organisms.get(i).getAlive()){
                organisms.remove(i);
                i--;
            }
        }
    }

    public void GenerateWorld(){
        Point temp = ChooseRandom();
        Organism tempOrg =  OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.HUMAN, this, temp);
        AddOrganism(tempOrg);
        human = (Human) tempOrg;
        for (int i = 0; i < NUMBER_OF_ANTELOPES; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.ANTYLOPE, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_BELLADONNAS; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.BELLADONNA, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_DANDELIONS; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.DANDELION, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_FOXES; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.FOX, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_GRASS; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.GRASS, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_GUARANAS; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.GUARANA, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_PINEBORSCHT; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.PINEBORSCHT, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_SHEEPS; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.SHEEP, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_TURTLES; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.TURTLE, this, temp));
        }
        for (int i = 0; i < NUMBER_OF_WOLFES; i++)
        {
            temp = ChooseRandom();
            AddOrganism(OrganismsGenerator.CreateNewOrganism(Organism.OrganismType.WOLF, this, temp));
        }
    }

    public boolean IsPlaceTaken(Point position){
        if (board[position.getY()][position.getX()] == null) return false;
        else return true;
    }

    public void Save(String nameOfFile){
       try
       {
           nameOfFile += ".txt";
           File file = new File(nameOfFile);
           file.createNewFile();

           PrintWriter pw = new PrintWriter(file);
           pw.print(sizeX + " ");
           pw.print(sizeY + " ");
           pw.print(getRoundNumber() + " ");
           pw.print(getHumanAlive() + " ");
           pw.print(getGameOver() + "\n");

           for (int i = 0; i < organisms.size(); i++) {
               pw.print( organisms.get(i).getOrganismType() + " ");
               pw.print( organisms.get(i).getPosition().getX() + " ");
               pw.print( organisms.get(i).getPosition().getY() + " ");
               pw.print( organisms.get(i).getPower() + " ");
               pw.print( organisms.get(i).getAge() + " ");
               pw.print( organisms.get(i).getAlive());
               if ( organisms.get(i).getOrganismType() == Organism.OrganismType.HUMAN) {
                   pw.print(" " + getHuman().GetTurnsLeft() + " ");
                   pw.print(getHuman().GetCooldown() + " ");
               }
               pw.println();
           }
           pw.close();
       }
        catch (IOException e) {
        System.out.println("Error: " + e);
        }
    }

    public static World Load(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            World tmpWorld = new World(sizeX, sizeY, null);
            int roundNumber = Integer.parseInt(properties[2]);
            tmpWorld.roundNumber = roundNumber;
            boolean isHumanAlive = Boolean.parseBoolean(properties[3]);
            tmpWorld.setHumanAlive(isHumanAlive);
            boolean isGameOver = Boolean.parseBoolean(properties[4]);
            tmpWorld.setGameOver(isGameOver);
            tmpWorld.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organism.OrganismType organismType = Organism.OrganismType.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);

                Organism tmpOrganism = OrganismsGenerator.CreateNewOrganism(organismType, tmpWorld, new Point(x, y));
                int power = Integer.parseInt(properties[3]);
                tmpOrganism.setPower(power);
                int age = Integer.parseInt(properties[4]);
                tmpOrganism.setAge(age);
                boolean alive = Boolean.parseBoolean(properties[5]);
                tmpOrganism.setAlive(alive);

                if (organismType == Organism.OrganismType.HUMAN) {
                    tmpWorld.human = (Human) tmpOrganism;
                    int turnsLeft = Integer.parseInt(properties[6]);
                    tmpWorld.human.SetTurnsLeft(turnsLeft);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpWorld.human.SetCooldown(cooldown);
                }
                tmpWorld.AddOrganism(tmpOrganism);
            }
            scanner.close();
            return tmpWorld;
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Organism[][] getBoard() {
        return board;
    }

    public boolean getHumanAlive() {
        return isHumanAlive;
    }

    public boolean getGameOver() {
        return isGameOver;
    }

    public ArrayList<Organism> getOrganisms() {
        return organisms;
    }

    public Human getHuman(){
        return human;
    }

    public boolean getPause() {
        return pause;
    }

    public void setHumanAlive(boolean alive){this.isHumanAlive = alive;}

    public void setGameOver(boolean over){this.isGameOver = over;}

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
