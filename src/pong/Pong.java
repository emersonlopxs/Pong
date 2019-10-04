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
    
    int velocidade = 10;
    
    private int Hello()
    {
        return 2;
    }
    
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
        g.setColor(Color.orange);
        g.fillRect(0,0, Dimensao.width, Dimensao.height);
        
        g.setColor(Color.white);
        g.drawString("pressione i para comecar ", 550, 350);
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
        pause(15);
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
                return 3;
            }
            // Se Bater no Cursor Direito
            if (PosicaoVertical > CursorDireito - 20
                    && PosicaoVertical < CursorDireito + 201
                    && PosicaoHorizontal > 1149 && PosicaoHorizontal < 1171) {
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
                return 1;
            }
            // Se Bater no Cursor Esquerdo
            if (PosicaoVertical > CursorEsquerdo - 22
                    && PosicaoVertical < CursorEsquerdo + 201
                    && PosicaoHorizontal > 49 && PosicaoHorizontal < 71) {
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
                return 4;
            }
            // Se Bater no Cursor Direito
            if (PosicaoVertical > CursorDireito - 22
                    && PosicaoVertical < CursorDireito + 201
                    && PosicaoHorizontal > 1149 && PosicaoHorizontal < 1171) {
                Score2 = -3 + (int)(Math.random() * 3);;
                return 4;
            }
            repeat();
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
                return 2;
            }
            // Se Bater no Cursor Esquerdo
            if (PosicaoVertical > CursorEsquerdo - 22
                    && PosicaoVertical < CursorEsquerdo + 201
                    && PosicaoHorizontal > 49 && PosicaoHorizontal < 71) {
                Score1 += -3 + (int)(Math.random() * 3);;
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
                || (CursorEsquerdo >= getSize().height - 70 && MoveEsquerda > 0)) {
            MoveEsquerda = 0;
        }
        if ((CursorDireito <= 0 && MoveDireita < 0)
                || (CursorDireito >= getSize().height - 70 && MoveDireita > 0)) {
            MoveDireita = 0;
        }
        CursorEsquerdo += MoveEsquerda;
        CursorDireito += MoveDireita;
        
        // goff.setColor(Color.pink);
        
        GetImage();
        goff.drawImage(deserto, 0, 0, this);
        
        // goff.fillRect(0, 0, Dimensao.width, Dimensao.height);
        
        goff.setColor(Color.blue);
        goff.fillRect(50, CursorEsquerdo, 7, 70);
        goff.setColor(Color.green);
        goff.fillRect(1150, CursorDireito, 7, 70);
        goff.setColor(Color.yellow);
        goff.fillOval(PosicaoHorizontal, PosicaoVertical, 15, 15);
        
        goff.setColor(Color.white);
        goff.drawString("Player A: " + Score1, 500, 40);
        goff.drawString("Player B: " + Score2, 700, 40);
        g.drawImage(Imagem, 0, 0, this);
    }

    @Override
    public boolean keyDown(Event e, int key) {
        if (key == Event.UP) {
            MoveEsquerda = 20;
        }
        if (key == Event.DOWN) {
            MoveEsquerda = -20;
        }
        if (key == 'w') {
            MoveDireita = 20;
        }
        if (key == 's') {
            MoveDireita = -20;
        }
        if (key == 'i') {
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
            MoveEsquerda = 0;
        }
        if (key == 'w' || key == 's') {
            MoveDireita = 0;
        }
        return true;
    }
}
