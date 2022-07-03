import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static  final int UNIT_SIZE=25;
    static  final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static  final int DELAY =150;
    static  final int x[]=new int[GAME_UNITS];
    static  final int y[]=new int[GAME_UNITS];
    int bodyParts=3;
    int appleEaten;
   int appleX;
   int appleY;
   char direction= 'R';
   boolean running=false;
   Timer timer;
   Random random;

    Gamepanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MykeyAdapater());
        startGame();

    }

    public  void startGame(){
        newApple();
        running=true;
        timer=new Timer(DELAY,this);
        timer.start();


    }
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);


    }

    public  void draw(Graphics g){
        if (running) {
            // draw the formatting line
        /*
        for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
        };

         */
            // Write the score sheet
            g.setColor(Color.green);
            g.setFont(new Font("Cambria",Font.BOLD,50));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("Score:"+appleEaten,(SCREEN_WIDTH-metrics.stringWidth(
                   "Score"))/8,SCREEN_HEIGHT/8);

            // draw apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            // draw snake head
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.orange);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                // else  draw the snake body
                else {
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }

        else{
                gameOver(g);
            }
        if (appleEaten==10){
            g.setColor(Color.gray);
            g.setFont(new Font("Ink Free",Font.BOLD,50));
            FontMetrics metrics= getFontMetrics(g.getFont());
            g.drawString("Good boy",(SCREEN_WIDTH-metrics.stringWidth(
                    "GameOver Oscar"))/10,SCREEN_HEIGHT/2);
        }






    }
    public  void gameOver(Graphics g){
        // gameover text
        g.setColor(Color.red);
        g.setFont(new Font("Cambria",Font.BOLD,20));
        FontMetrics metrics= getFontMetrics(g.getFont());
        g.drawString("GameOver Oscar your score is:"+appleEaten,(SCREEN_WIDTH-metrics.stringWidth(
                "GameOver Oscar"))/2,SCREEN_HEIGHT/2);






    }
    public  void newApple(){
        appleX=random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public  void move(){
        for (int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
            }
        // switch to change the direction of the body parts
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;

        }

    }

    public  void checkApple(){
        if ((x[0]==appleX) && (y[0]==appleY)) {
            bodyParts++;
            appleEaten++;
            newApple();
        }



    }
    public void checkCollision(){
        // Iterate the body of the snake and check if head collides with the body
        for (int i=bodyParts;i>4;i--){
            if ((x[0]==x[i]) && (y[0]==y[i])) {
                running = false;

            }

        }
        // check if snake hits the wall
        //left wall
        if (x[0]<0){
            running=false;

        }
        // Right wall
        if (x[0]>SCREEN_WIDTH){
            running=false;
        }
        //top corner
        if (y[0]<0){
            running=false;
        }
        // if it touches bottom corner
        if (x[0]>SCREEN_HEIGHT){
            running=false;
        }
        if (!running){
            timer.stop();
        }





    }

    public  class MykeyAdapater extends KeyAdapter {
        @Override
        public  void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case  KeyEvent.VK_LEFT:
                    if (direction!='R'){
                        direction='L';
                    }
                    break;
                case  KeyEvent.VK_RIGHT:
                    if (direction!='L'){
                        direction='R';
                    }
                    break;
                case  KeyEvent.VK_UP:
                    if (direction!='D'){
                        direction='U';
                    }
                    break;
                case  KeyEvent.VK_DOWN:
                    if (direction!='U'){
                        direction='D';
                    }
                    break;
            }

        }

    }


















    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }

        repaint();
    }
}
