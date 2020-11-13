
package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


    public class game implements ActionListener, KeyListener {

    public static game giraffe;
    private Renderer renderer;
    private final int WIDTH = 800, HEIGHT = 600;      
    private ArrayList<Obiect> obstacle;  
    private Random random;
    private int moment=0,cadere=0;      
    private boolean Over,Play;           
    private int score=0;
    private int highScore=0;
    private Image background;
    private int which=0;        
    private int actiune=0;       
    private Obiect gira;
    private int cd=0;  
    private int nrSpace=0; 
    String[] imag= {"C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\g5_1.png",
            "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\g5_3.png",
            "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\g5_4.png",
            "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\g5_5.png"
    };

    private game() {
        //alocare
        JFrame Frame = new JFrame();
        renderer = new Renderer();
        Frame.add(renderer);
        random = new Random();
        gira = new Obiect(100,HEIGHT-125,90,90,imag[which]);
        obstacle = new ArrayList<Obiect>();
        obstacle.add(new Obiect(-100, HEIGHT - 110, 70, 70,"C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\snowball3_1.png"));
            background=new ImageIcon("C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\dawn_3.png").getImage();
        Frame.setTitle("Giraffe Jump");
        Timer timer = new Timer(20, this);  
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(WIDTH, HEIGHT);
        Frame.addKeyListener(this);
        Frame.setResizable(false);
        Frame.setVisible(true);
        timer.start();

        addObstacle(true);
        addObstacle(true);
       
    }

    private void addObstacle(boolean start) {
        int obs=random.nextInt(60);
        int widthC = 30 + random.nextInt(60); 
        int difObs=450+random.nextInt(650);
        if(obs%7!=0)
        if(start)
            obstacle.add(new Obiect(WIDTH + widthC + obstacle.size() * difObs, HEIGHT - 110, widthC, 70,
                    "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\snowball3_1.png"));
            
        else {
            obstacle.add(new Obiect(obstacle.get(obstacle.size()-1).x+difObs, HEIGHT - 110, widthC,70,
                    "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\snowball3_1.png"));
        }
        else
        if(start)
            obstacle.add(new Obiect(WIDTH + widthC + obstacle.size() * difObs, HEIGHT - 175, 90, 70,
                    "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\missile3_2.png"));
            
        else {
            obstacle.add(new Obiect(obstacle.get(obstacle.size()-1).x+difObs, HEIGHT - 175 , 90, 70,
                    "C:\\Users\\Admin\\Documents\\NetBeansProjects\\project_game\\src\\project\\missile3_2.png"));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Play) {
            int spead = 17;  
            for (int i = 0; i < obstacle.size(); i++) {
                Obiect aux = obstacle.get(i);
                aux.x -= spead;  
            }
            for (int i = 0; i < obstacle.size() && !Over; i++) {
                Obiect aux = obstacle.get(i);
                if (aux.x + aux.widt < 0) {
                    obstacle.remove(aux);
                    addObstacle(false); 
                }
            }
            
            gira = new Obiect(100, HEIGHT - 125, 90, 90, imag[which]);  
            moment++;
            if(moment%3==0)
                score=score+1;         
            if (((moment % 2) == 0) && (cadere < 18)) {
                cadere = cadere + 30; 
            }
            if (cadere < 18) {
                if (actiune == 0) {
                    gira.y += cadere;
                } else {
                    gira.y -= cadere;
                }
            } else {
                gira.y = HEIGHT - 125;
            }
            if (gira.y == HEIGHT - 125) {
                actiune = 0;
                if(which==0 || which==1) {    
                    which++;
                    if (which > 1) {
                        which = 0;
                    }
                }
                else
                if(which==2 || which==3){           
                    gira.y = HEIGHT - 96;
                    which++;
                    if (which > 3) {
                        which = 2;
                    }
                }
            }
        }
            for(Obiect colum: obstacle){
                if(colum.intersects(gira) ){
                    Over=true;
                    Play=false;     
                   
                }
            }
        renderer.repaint();
        }

    public void repaint(Graphics g) {
        g.drawImage(background,0,0,null);
        g.setColor(Color.gray);
        g.fillRect(0, HEIGHT-45, WIDTH, 5);   
        if(!Over) {
            g.drawImage(gira.imj,gira.x,gira.y,null);    
        }
        for (Obiect colum : obstacle) {
                g.drawImage(colum.imj,colum.x,colum.y,colum.widt,colum.heigh,null);    
        }
        if(!Play && !Over){
            g.setColor(Color.white );
            g.setFont(new Font("Arial",1,50));
            g.drawString("GIRAFFE JUMP",210,200);
        }
        g.setColor(Color.white.darker() );
        g.setFont(new Font("Arial",1,25));
        if(!Play){
            g.drawString("Use Space to jump",280,100);
        }
        g.setColor(Color.gray.brighter());
        g.setFont(new Font("Arial",1,20));
        if(!Over && Play){
            g.drawString("HI:"+String.valueOf(highScore)+" "+String.valueOf(score),WIDTH-130,40);  

        }
        g.setColor(Color.white.darker().darker());
        g.setFont(new Font("Arial",1,100));
        if(Over) {
            g.drawString("Game Over", 120, HEIGHT / 2 - 50);  
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN && gira.y==HEIGHT-125){
            which=2;
         }

        if(e.getKeyCode()==KeyEvent.VK_SPACE && gira.y==HEIGHT-125 &&!Over &&(which==0 || which==1)){
            jump();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_DOWN ){
            which=1; 
        }

        if(e.getKeyCode()==KeyEvent.VK_SPACE && Over){
            nrSpace++;
            actiune=1;
            if(nrSpace==2) {        
                jump();
            }
        }
    }
    private void jump() {
        if(Over) {
            nrSpace=0;
            Over = false;
            gira = new Obiect(100, HEIGHT-125, 90, 90,imag[which]);
            obstacle.clear(); 
            addObstacle(true);
            addObstacle(true);
            if(score>highScore)
            highScore=score;  
            score=0;
            cadere=0;
        }
        if(!Play)
            Play=true;
        else if(!Over){
            if(cadere>0){
                cadere=0;  
            }
            cadere-=cd;
            if(cd<100) {
                cd+=20;
                jump();
            }
            else{
               cd=0;
            }
        }
    }
    public static void main(String[] args) {
         giraffe = new game();
    }
    
}

