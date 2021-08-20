/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.models;

/**
 *
 * @author Chuck
 */
public class Game {
    private boolean isActive;
    private int gameID;
    private static int gameCount;
    private RandomFourDigitNumber number;
    
    static {
        gameCount = 0;
    }
    
    public Game(RandomFourDigitNumber number) {
        this.isActive = true;
        Game.gameCount++;
        this.gameID = gameCount;
        this.number = number;
    }
    
    public void gameOver() {
        setIsActive(false);
    }
    
    private void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
