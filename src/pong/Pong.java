package pong;

import java.applet.Applet;
import java.awt.*;
import java.util.Scanner;


public class Pong extends Applet implements Runnable {
   
    int CursorDireito = 210, CursorEsquerdo = 210;
    int MoveDireita = 0, MoveEsquerda = 0;
    int PosicaoHorizontal = 0, PosicaoVertical = 0;
    int Score1 = 0, Score2 = 0;
    Thread runner;
    Graphics goff, g;
    Image Imagem;
    Dimension Dimensao;
    Image deserto;
   
    MediaTracker thetracker = null;
    
    float velocidade = 5;
    
    @Override
    public void init() {
        setBackground(Color.black);
        setFont(new Font("sans-serif", Font.BOLD, 25));
        resize(1280, 720);
    }
    
    private void GetImage() {
        thetracker = new MediaTracker(this);
        deserto = Toolkit.getDefaultToolkit().getImage("IMG/3.jpg");
        thetracker.addImage(deserto, 0);
    }

    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0,0, Dimensao.width, Dimensao.height);
        g.setColor(Color.white);
        
        g.drawString("1 - LENTO ", 600, 300);
        g.drawString("2 - NORMAL ", 600, 400);
        g.drawString("3 - RAPIDO ", 600, 500);
    }

    @Override
    public void start() {
        Dimensao = size();
        Imagem = createImage(Dimensao.width, Dimensao.height);
        goff = Imagem.getGraphics();
        
        g = getGraphics();        
    }

    @Override
    public void stop() {
        if (runner != null) {
            runner.interrupt();
            runner = null;
        }
    }

    @Override
    public void run() {
        int Movimento;
        Movimento = MoveBolaEDI();
        while (true) {
            if (Movimento == 1) {
                Movimento = MoveBolaEDI();
            }
            if (Movimento == 2) {
                Movimento = MoveBolaEDS();
            }
            if (Movimento == 3) {
                Movimento = MoveBolaDEI();
            }
            if (Movimento == 4) {
                Movimento = MoveBolaDES();
            }
        }
    }

    public void repeat() {
        update(g);
        pause(1);
    }

    // Move Bola da Esquerda para Diagonal Direita Inferior
    public int MoveBolaEDI() {
        for (;; PosicaoVertical += velocidade, PosicaoHorizontal += velocidade) {
            // Se Bater na Borda Inferior
            if (PosicaoVertical > getSize().height - 10) {
                return 2;
            }
            // Se Bater na Borda Direita
            if (PosicaoHorizontal > getSize().width - 10) {
                Score2 += -10;
                return 3;
            }
            // Se Bater no Cursor Direito
            if (PosicaoVertical > CursorDireito - 20
                    && PosicaoVertical < CursorDireito + 201
                    && PosicaoHorizontal > 1164 && PosicaoHorizontal < 1171) {
                Score2++;
                return 3;
            }
            repeat();
        }
    }

    // Move Bola da Direita para Diagonal Esquerda Inferior
    public int MoveBolaDEI() {
        for (;; PosicaoHorizontal -= velocidade, PosicaoVertical += velocidade) {
            // Se Bater na Borda Inferior
            if (PosicaoVertical > getSize().height - 10) {
                return 4;
            }
            // Se Bater na Borda Esquerda
            if (PosicaoHorizontal < 1) {
                Score1 += -10;
                return 1;
            }
            // Se Bater no Cursor Esquerdo
            if (PosicaoVertical > CursorEsquerdo - 22
                    && PosicaoVertical < CursorEsquerdo + 201
                    && PosicaoHorizontal > 64 && PosicaoHorizontal < 71) {
                Score1++;
                return 1;
            }
            repeat();
        }
    }

    // Move Bola da Esquerda para Diagonal Direita Superior
    public int MoveBolaEDS() {
        for (;; PosicaoHorizontal += velocidade, PosicaoVertical -= velocidade) {
            // Se Bater na Borda Superior
            if (PosicaoVertical < 1) {
                return 1;
            }
            // Se Bater na Borda Direita
            if (PosicaoHorizontal > getSize().width - 10) {
                Score2 += -10;
                return 4;
            }
            // Se Bater no Cursor Direito
            if (PosicaoVertical > CursorDireito - 9
                    && PosicaoVertical < CursorDireito + 151
                    && PosicaoHorizontal > 1164 && PosicaoHorizontal < 1171) {
                Score2 = -2 + (int)(Math.random() * 8);;
                return 4;
            }
            repeat();
            velocidade += 0.001;
        }
    }

    // Move Bola da Direita para Diagonal Esquerda Superior
    public int MoveBolaDES() {
        for (;; PosicaoVertical -= velocidade, PosicaoHorizontal -= velocidade) {
            // Se Bater na Borda Superior
            if (PosicaoVertical < 1) {
                return 3;
            }
            // Se Bater na Borda Esquerda
            if (PosicaoHorizontal < 1) {
//                velocidade = 1;
                Score1 += -10;
                return 2;
                
            }
            // Se Bater no Cursor Esquerdo
            if (PosicaoVertical > CursorEsquerdo - 9
                    && PosicaoVertical < CursorEsquerdo + 151
                    && PosicaoHorizontal > 64 && PosicaoHorizontal < 71) {
                Score1 += -2 + (int)(Math.random() * 8);;
                return 2;
            }
            repeat();
        }
    }

    void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void update(Graphics g) {
        if ((CursorEsquerdo <= 0 && MoveEsquerda < 0)
                || (CursorEsquerdo >= getSize().height - 150 && MoveEsquerda > 0)) {
            MoveEsquerda = 0;
        }
        if ((CursorDireito <= 0 && MoveDireita < 0)
                || (CursorDireito >= getSize().height - 150 && MoveDireita > 0)) {
            MoveDireita = 0;
        }
        CursorEsquerdo += MoveEsquerda;
        CursorDireito += MoveDireita;
        
        // goff.setColor(Color.pink);
        
        GetImage();
        goff.drawImage(deserto, 0, 0, this);
        
        // goff.fillRect(0, 0, Dimensao.width, Dimensao.height);
        
        goff.setColor(Color.green);
        goff.fillRect(66, CursorEsquerdo, 7, 150);
        goff.setColor(Color.red);
        goff.fillRect(1170, CursorDireito, 7, 150);
        goff.setColor(Color.yellow);
        goff.fillOval(PosicaoHorizontal, PosicaoVertical, 10, 10);
        
//        goff.setColor(Color.red);
//        goff.fillOval(PosicaoHorizontal, PosicaoVertical, 10, 10);
        
        goff.setColor(Color.green);
        goff.drawString("JOGADOR A" , 100, 40);
        goff.drawString(" " + Score1, 400, 40);
        
        goff.drawString(" " + velocidade, 620, 40);
               
        goff.setColor(Color.red);
        goff.drawString("JOGADOR B", 1100, 40);
        goff.drawString(" " + Score2, 900, 40);
        g.drawImage(Imagem, 0, 0, this);
    }

    @Override
    public boolean keyDown(Event e, int key) {
        if (key == Event.UP) {
            MoveDireita = 15;
        }
        if (key == Event.DOWN) {
            MoveDireita = -15;
        }
        if (key == 'w') {
            MoveEsquerda = 15;
        }
        if (key == 's') {
            MoveEsquerda = -15;
        }
        
        if (key == 'i') {
            if (runner == null) {
            runner = new Thread(this);
            runner.start();
            }
        }
            
        if (key == '1') {
            velocidade = 1;
             if (runner == null) {
            runner = new Thread(this);
            runner.start();
             }
        }
        
        if (key == '2') {
            velocidade = 3;
             if (runner == null) {
            runner = new Thread(this);
            runner.start();
             }
        }
        
        if (key == '3')  {
            velocidade = 5;
             if (runner == null) {
            runner = new Thread(this);
            runner.start();
             }
        }
        return true;
        }
        

    @Override
    public boolean keyUp(Event e, int key) {
        if (key == Event.DOWN || key == Event.UP) {
            MoveDireita = 0;
        }
        if (key == 'w' || key == 's') {
            MoveEsquerda = 0;
        }
        if (key == 'q') {
            
            runner.stop();
            g.setColor(Color.orange);
            g.fillRect(0,0, Dimensao.width, Dimensao.height);
            g.setColor(Color.black);
            
            g.drawString("GAVE OVER", 610, 350);
            
            if (Score1 > Score2) {
                g.drawString("JOGADOR A VENCEU", 580, 400);
            } else if (Score2 > Score1) {
                g.drawString("JOGADOR B VENCEU", 580, 400);
            } else {
                g.drawString("EMPATE", 630, 400);
            }
            
            g.drawString("JOGADOR A: " + Score1, 600, 450);
            g.setColor(Color.black);
            g.drawString("JOGADOR B: " + Score2, 600, 500);
        }
//        
        if(velocidade > 6) {
            runner.stop();
            g.setColor(Color.orange);
            g.fillRect(0,0, Dimensao.width, Dimensao.height);
            g.setColor(Color.black);
            
            g.drawString("GAME OVER", 610, 350);
            
            if (Score1 > Score2) {
                g.drawString("JOGADOR A VENCEU", 600, 400);
            } else if (Score2 > Score1) {
                g.drawString("JOGADOR B VENCEU", 600, 400);
            } else {
                g.drawString("EMPATE", 630, 400);
            }
            
            g.drawString("JOGADOR A: " + Score1, 600, 450);
            g.setColor(Color.black);
            g.drawString("JOGADOR B: " + Score2, 600, 500);
        }
        
        return true;
    }
}
