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
    private final RandomFourDigitNumber HIDDEN_NUMBER = 
            RandomFourDigitNumber.createHiddenNumber();
    
    static {
        gameCount = 0;
    }
    
    public Game() {
        this.number = new RandomFourDigitNumber(-1);
    }
    
    public Game(RandomFourDigitNumber number) {
        this.isActive = true;
        Game.gameCount++;
        this.gameID = gameCount;
        this.number = number;
    }
    
    public boolean isActive() {
        return this.isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    public int getGameID() {
        return this.gameID;
    }
    
    public void setID(int id) {
        this.gameID = id;
    }
    
    public int getNumberOfGames() {
        return Game.gameCount;
    }
    
    public RandomFourDigitNumber getNumber() {
        if (this.isActive) {
            return HIDDEN_NUMBER;
        }
        return this.number;
    }
    
    public RandomFourDigitNumber retrieveHiddenNumber() {
        return this.number;
    }
    
    public void setNumber(Integer number) {
        this.number.setNumber(number);
    }
    
    public void gameOver() {
        setIsActive(false);
    }
}
