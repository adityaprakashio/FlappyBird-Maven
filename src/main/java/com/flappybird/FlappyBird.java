package com.flappybird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird class
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    //pipe class
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(Image img) {
            this.img = img;
        }
    }

    //game logic
    Bird bird;
    int velocityX = -4; 
    int velocityY = 0; 
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placePipeTimer;
    
    boolean gameOver = false;
    boolean gameStarted = false;
    double score = 0;

    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place pipes timer
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              placePipes();
            }
        });
        
        // --- EASY MODE SETTING ---
        // Changed from 60 to 30 to make the game slower and easier to play
        gameLoop = new Timer(1000/30, this); 
        gameLoop.start();
    }
    
    void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        
        // --- EASY MODE SETTING ---
        // Increased gap from /4 to /2 so it is much easier to fly through
        int openingSpace = boardHeight/3; 
    
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
    
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y  + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        //bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // --- BEAUTIFIED TEXT ---
        g.setFont(new Font("Dialog", Font.BOLD, 28));
        
        if (gameOver) {
            drawCenteredText(g, "Game Over: " + (int)score, boardHeight/2 - 50);
            drawCenteredText(g, "Press Space to Restart", boardHeight/2);
        }
        else if (!gameStarted) {
             drawCenteredText(g, "Press Space to Start", boardHeight/2 + 50);
             drawCenteredText(g, "0", 35); // Score at top
        }
        else {
            drawCenteredText(g, String.valueOf((int) score), 35);
        }
    }

    // Helper function to draw pretty text with shadow
    private void drawCenteredText(Graphics g, String text, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int x = (boardWidth - metrics.stringWidth(text)) / 2;
        
        // Draw Shadow (Black)
        g.setColor(Color.BLACK);
        g.drawString(text, x + 2, y + 2);
        
        // Draw Text (White)
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); 

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;

            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; 
                pipe.passed = true;
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        // Made the hitbox slightly smaller (+4 and -4) so it's more forgiving
        return a.x + 4 < b.x + b.width &&   
               a.x + a.width - 4 > b.x &&   
               a.y + 4 < b.y + b.height &&  
               a.y + a.height - 4 > b.y;    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted) { 
            move();
        }
        repaint();
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop();
        }
    }  

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameStarted = true;
                gameLoop.start();
                placePipeTimer.start();
            }
            else if (!gameStarted) {
                gameStarted = true;
                placePipeTimer.start();
                velocityY = -9;
            }
            else {
                velocityY = -9;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}