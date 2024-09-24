package codeFiles;
/**
 * Asvin and Alex
 * Due date: Jan 22
 * This is the battle class
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

public class Battle extends JPanel implements Runnable, KeyListener {

    Thread thread; // Thread to run the battle
    HashMap<String, String> questions; // Questions for the battle
    int level=1; // current level
    int screenWidth = 840, screenHeight = 840; // Screen dimensions
    int playerHealth = 4, monsterHealth = 4; // Health of player and monster
    Image battleScreen, playerImage, monsterImage; // Images for battle screen, player and monster
    Image binaryMonster, exceptionMonster, fileMonster, stringMonster; // monsters for level 1
    Image health1Monster, health2Monster, health3Monster, health4Monster; // Images for monster health
    Image health1Player, health2Player, health3Player, health4Player; // Images for player health
    String outputMessage = "Battle commences....."; // Initial output message
    String curQuestion="", curAns = ""; // Current question and answer

    public static HashMap<String, String> Level1Questions = new HashMap<>();
    public static HashMap<String, String> Level2Questions = new HashMap<>();
    public static HashMap<String, String> Level3Questions = new HashMap<>();
    List<String> questionList = new ArrayList<>();

    /**
     * Constructor for Battle class.
     * @param level is the Level of the battle
     */
    public Battle(int level) {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setVisible(true);

        Level1Questions.put("An advantage of BufferedReader over Scanner is that it’s faster.", "T");
        Level1Questions.put("When using PrintWriter and a file which is being called does not exist, an error is returned. ", "F");
        Level1Questions.put("When using PrintWriter, if a file being called already exists, by default it will be overwritten unless you add a flag of true", "T");
        Level1Questions.put("The following exceptions should be caught in this order: FileNotFoundException, IOException, Exception", "T");
        Level1Questions.put("A signed bit of 1 indicates a positive number in binary", "F");
        Level1Questions.put("101011 base 2 is equivalent to 43 in base 10", "T");
        Level1Questions.put("123 base 10 is equivalent to 1011011 base 2", "F");
        Level1Questions.put("-43 base 10 is equivalent to 11010111 in base 2", "F");
        Level1Questions.put("Smallest value that can be stored in a 10 bit variable type is -2^9", "T");
        Level1Questions.put("5.43 * 10^9 fits in an integer for java", "F");
        Level1Questions.put("The following lines of code will output 34: StringBuilder s = new StringBuilder(); s.ensureCapacity(20); System.out.println(s.capacity());", "T");
        Level1Questions.put("StringBuilders can be directly compared to each other using equals() or compareTo()", "F");

        Level2Questions.put("By default, a variable is an instance variable", "T");
        Level2Questions.put("Instance variables can be accessed by both instance and static variables", "F");
        Level2Questions.put("There exist multiple copies of static variables", "F");
        Level2Questions.put("Static variables can be called by instance objects and class names", "T");
        Level2Questions.put("Data encapsulation is the process of hiding internal data by making them private", "T");
        Level2Questions.put("By default, indexOf() and contains() searches by value", "F");
        Level2Questions.put("An interface does not contain any instance variables, is allowed to have static fields, and can only contain empty methods", "T");
        Level2Questions.put("Linear search is O(n) complexity while binary search is O(logn) complexity in worst case scenario", "T");
        Level2Questions.put("If key is not found, the value that is returned is: -insertion point - 1", "T");
        Level2Questions.put("Inheritance is the idea that superclasses inherit variables and methods from subclasses", "F");
        Level2Questions.put("Blank modifier means that no other class can access it", "F");
        Level2Questions.put("Polymorphism stands for one name, many forms", "T");
        Level2Questions.put("Method overriding and method overloading are examples of polymorphism", "T");
        Level2Questions.put("By default, a variable is an instance variable", "T");
        Level2Questions.put("By default, a variable is an instance variable", "T");
        Level2Questions.put("By default, a variable is an instance variable", "T");
        Level2Questions.put("By default, a variable is an instance variable", "T");
        Level2Questions.put("By default, a variable is an instance variable", "T");

        Level3Questions.put("ArrayList data are stored in consecutive memory slots", "T");
        Level3Questions.put("One advantage of arraylists is that they have fast random/direct access ", "T");
        Level3Questions.put("After making changes to a list, the sublist is no longer valid", "T");
        Level3Questions.put("Queue is a LIFO structure", "F");
        Level3Questions.put("A queue is best implemented with a Linkedlist", "T");
        Level3Questions.put("A stack is best implemented with a LinkedList", "F");
        Level3Questions.put("Stack is not a subclass of vector", "F");
        Level3Questions.put("Queue is an interface, not a class", "T");
        Level3Questions.put("TreeSet keeps elements sorted at ALL times", "T");
        Level3Questions.put("HashSet keeps elements sorted", "F");
        Level3Questions.put("The following process occurs during re-hash: buckets are doubled and then existing elements are redistributed", "T");

        loadQuestions();

        this.level = level;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method is the threading of the battle.
     * It initializes the battle and repaints the screen while the thread is alive.
     */
    @Override
    public void run() {
        initializeBattle();
        while (thread.isAlive()) {
            repaint();
            System.out.println("monsterType = " + Main.monsterType);
        }
    }

    /**
     * This method sets the program for a new battle.
     * It loads the images and questions, and updates the question on the screen.
     */
    public void initializeBattle() {
        loadImages();
        updateQuestionOnScreen();
    }

    /**
     * This method draws wrapped text on the screen.
     * @param g2 Graphics object
     * @param text Text to be drawn
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param width Width of the text
     */
    private void drawWrappedText(Graphics g2, String text, int x, int y, int width) {

        // process words in line
        g2.setFont(new Font("Verdana", Font.BOLD, 20));
        FontMetrics fontMetrics = g2.getFontMetrics();
        String[] words = text.split(" ");
        String curLine = "";

        // finds height of current line of words
        int curY = y + fontMetrics.getHeight();

        // prints wrapped text
        for (String word : words) {
            String testLine = curLine + word + " ";
            int lineWidth = fontMetrics.stringWidth(testLine);

            // word exceeds line limit
            if (lineWidth > width) {
                g2.drawString(curLine, x, curY);
                curLine = word + " ";
                curY += fontMetrics.getHeight();
            }

            // word is within line limit
            else {
                curLine = testLine;
            }
        }

        // Draw the last line
        g2.drawString(curLine, x, curY);
    }

    /**
     * This method draws stuff onto screen.
     * @param g Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //g2.drawImage(playerImage, 100, 500, null);
        //g2.drawImage(monsterImage, 500, 100, null);

        // draw image
        g2.drawImage(battleScreen, 0, 0, null);

        // write text
        drawWrappedText(g2, outputMessage, 50, 660, 420);

        // Draw monster health
        if(monsterHealth == 1) {
            g2.drawImage(health1Monster, screenWidth/2 + 80, 380, null);
        } else if (monsterHealth == 2) {
            g2.drawImage(health2Monster, screenWidth/2 + 80, 380, null);
        } else if (monsterHealth == 3) {
            g2.drawImage(health3Monster, screenWidth/2 + 80, 380, null);
        } else if (monsterHealth == 4) {
            g2.drawImage(health4Monster, screenWidth/2 + 80, 380, null);
        }

        // Draw player health
        if(playerHealth == 1) {
            g2.drawImage(health1Player, 130, 60, null);
        } else if (playerHealth == 2) {
            g2.drawImage(health2Player, 130, 60, null);
        } else if (playerHealth == 3) {
            g2.drawImage(health3Player, 130, 60, null);
        } else if (playerHealth == 4) {
            g2.drawImage(health4Player, 130, 60, null);
        }


    }

    // loads questions for battle
    public void loadQuestions() {

        // Load questions based on level
        if(level == 1) {
            questions = new HashMap<>(Level1Questions);
        } else if (level == 2) {
            questions = new HashMap<>(Level2Questions);
        } else if (level == 3) {
            questions = new HashMap<>(Level3Questions);
        }

        questionList = new ArrayList<>(questions.keySet());
        Collections.sort(questionList, new randomizeQuestionsComparator());

        randomQuestions();
    }

    public void randomQuestions() {
        ArrayList<String> test = new ArrayList<>();
        for(int i = 0; i < questions.size(); i++) {
            int idx = (int)(Math.random()*((questionList.size()-1)-0+1)) + 0;
            test.add(questionList.get(idx));
            questionList.remove(idx);
        }
        questionList = new ArrayList<>(test);
    }

    // render images
    public void loadImages() {
        MediaTracker tracker = new MediaTracker(this);

        try {
            System.out.println("monsterType = " + Main.monsterType);
            // Load battle screen image based on monster type
            if(Main.monsterType == 2) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/ArrayListMonsterBattle.png");
            } else if(Main.monsterType == 3) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/BinarySearchMonsterBattle.png");
            } else if(Main.monsterType == 4) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/ComparatorMonsterBattle.png");
            } else if(Main.monsterType == 5) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/MonsterBattle.png");
            } else if (Main.monsterType == 6) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/BinaryMonsterBattle.png");
            } else if (Main.monsterType == 7) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/ExceptionMonsterBattle.png");
            } else if (Main.monsterType == 8) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/FileMonsterBattle.png");
            } else if (Main.monsterType == 9) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/StringMonsterBattle.png");
            } else if (Main.monsterType == 10) {
                battleScreen = Toolkit.getDefaultToolkit().getImage("src/BattlePics/GreenBattle.png");
                tracker.addImage(battleScreen, 11);
            }

            // Load player health images
            health1Player = Toolkit.getDefaultToolkit().getImage("src/BattlePics/1Health.png");
            tracker.addImage(health1Player, 1);

            health2Player = Toolkit.getDefaultToolkit().getImage("src/BattlePics/2Health.png");
            tracker.addImage(health2Player, 2);

            health3Player = Toolkit.getDefaultToolkit().getImage("src/BattlePics/3Health.png");
            tracker.addImage(health3Player, 3);

            health4Player = Toolkit.getDefaultToolkit().getImage("src/BattlePics/4Health.png");
            tracker.addImage(health3Player, 4);

            // Monster health images are same as player health images
            health1Monster = health1Player;
            health2Monster = health2Player;
            health3Monster = health3Player;
            health4Monster = health4Player;

        } catch (Exception e) { System.out.println("Error loading images"); }

        // make sure the images are loaded before continuing
        try { tracker.waitForAll(); }
        catch (InterruptedException e) { }

    }

    // updates questions on screen
    public void updateQuestionOnScreen() {
        curQuestion  = questionList.get(0);
        outputMessage = curQuestion;
        curAns = questions.get(curQuestion);
        questionList.remove(0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * This method is called when a key is pressed.
     * It checks the key pressed and updates the health of player and monster accordingly.
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // true
        if (key == KeyEvent.VK_T) {
            if(curAns.equals("T")) {
                monsterHealth--;
            } else {
                playerHealth--;
            }
            updateQuestionOnScreen();
        }

        // false
        else if (key == KeyEvent.VK_F) {
            if(curAns.equals("F")) {
                monsterHealth--;
            } else {
                playerHealth--;
            }
            updateQuestionOnScreen();
        }

        // cheat code
        else if (key == KeyEvent.VK_W) {
            monsterHealth--;
            updateQuestionOnScreen();
        }

        // Close dialog box if player health is 0
        if(playerHealth == 0 || monsterHealth == 0) {

            if(monsterHealth == 0) {
                if(level == 1) Level1.lvl1Points++;
                else if(level == 2) Level2.lvl2Points++;
                if(level == 3) Level3.lvl3Points++;
            }

            if(level == 1) {
                Level1.closeDialogBox("battle");
            } else if (level == 2) {
                Level2.closeDialogBox("battle");
            } else if (level == 3) {
                System.out.println("test");
                Level3.closeDialogBox("battle");
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}