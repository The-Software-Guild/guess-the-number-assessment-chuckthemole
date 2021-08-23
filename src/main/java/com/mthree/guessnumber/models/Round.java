/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.models;

import java.time.LocalDate;

/**
 *
 * @author Chuck
 */
public class Round {
    private int id;
    private int guess;
    private LocalDate time;
    private String result;
    private Game game;
    
    public Round() {
        this.guess = -1;
        this.time = null;
        this.result = null;
        this.game = null;
    }
    
    public Round(int guess, Game game) {
        this.guess = guess;
        this.game = game;
        this.result = game.retrieveHiddenNumber().compareNumbers(guess);
        this.time = LocalDate.now();      
    }
    
    public int getGuess() {
        return guess;
    }
    
    public void setGuess(int guess) {
        this.guess = guess;
    }
    
    public LocalDate getTime() {
        return time;
    }
    
    public void setTime(LocalDate time) {
        this.time = time;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public Game getGame() {
        return game;
    }
    
    public void setGame(Game game) {
        this.game = game;
    }
    
    public void setID(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Guess: ")
                .append(guess)
                .append(" Time: ")
                .append(time)
                .append("Result: ")
                .append(result);
        return sb.toString();
    }
}
