/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chuck
 */
public class GuessNumberView {
    private UserIO io;
    
    public GuessNumberView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Guess Number Program>>");
        io.print("* 1. Create Number");
        io.print("* 2. Display All Numbers");
        io.print("* 3. Guess Number");
        io.print("* 4. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print(""); 

        return io.readInt("Please select from the above choices.", 1, 4);
    }
    
    public void displayNumberCreatedUnsuccessfulBanner() {
        io.print("* * * * * * Number Creation unsuccessful * * * * * *");
    }
    
    public void displayNumberCreatedSuccessfulBanner() {
        io.print("* * * * * * Number Creation Successful * * * * * *");
    }
    
    public void displayNumberCreated(Integer number) {
        io.print("Number created: " + number);
    }
    
    public void print(String s) {
        io.print(s);
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    public void displayNumbers(List<Integer> list) {
        io.print("* * * * * * Numbers Created * * * * * *");
        for (int i = 0; i < list.size(); i++) {
            io.print((i + 1) + ". " + list.get(i).toString());
        }
    }
    
    public void displayGuessNumberBanner() {
        io.print("* * * * * * Guess Number * * * * * *"); 
    }
    
    public void displayGuessNumberNoNumbersCreatedBanner() {
        io.print("* * * * * * No Numbers Created * * * * * *"); 
    }
    
    public Integer getGame(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap();
        
        io.print("    -----Choose An Active Game-----");
        for (int i = 0; i < list.size(); i++) {
            map.put(i + 1, list.get(i));
            io.print((i + 1) + ". " + list.get(i).toString());
        }
        
        int selection = io.readInt("Please select a game for the above choices.", 1, list.size());
        return map.get(selection);
    }
    
    public Integer getGuessForGame() {
        io.print("* * * * * * Enter A Four Digit Number For Your Guess * * * * * *"); 
        return io.readInt("Please select a four digit number.", 1000, 9999);
    }
}
