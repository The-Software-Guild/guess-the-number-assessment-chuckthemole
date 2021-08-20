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
    private final int guess;
    private final LocalDate time;
    private final String result;
    private final Game game;
    
    public Round(int guess, Game game) {
        this.guess = guess;
        this.game = game;
        this.result = game.retrieveHiddenNumber().compareNumbers(guess);
        this.time = LocalDate.now();      
    }
    
    public int getGuess() {
        return guess;
    }
    
    public LocalDate getTime() {
        return time;
    }
    
    public String getResult() {
        return result;
    }
    
    public Game getGame() {
        return game;
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
