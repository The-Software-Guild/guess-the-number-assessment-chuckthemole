/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.controller;

import com.mthree.guessnumber.model.RandomFourDigitNumber;
import com.mthree.guessnumber.service.GuessNumberServiceLayer;
import com.mthree.guessnumber.ui.GuessNumberView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Chuck
 */
public class GuessNumberController {
    private GuessNumberView view;
    private GuessNumberServiceLayer service;


    public GuessNumberController(GuessNumberServiceLayer service, GuessNumberView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() {
        
        boolean keepGoing = true;
        
        while (keepGoing) {
            int menuSelection = getMenuSelection();

            switch(menuSelection) {
                case 1:
                    createRandomFourDigitNumber();
                    break;
                case 2:
                    getAndDisplayNumbers();
                    break;
                case 3:
                    guessNumber();
                    break;
                case 4:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    break;
            }
                    
        }
        
        exitMessage();
        System.exit(0);
    }
    
    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }
    
    private Integer createRandomFourDigitNumber() {
        Integer number = service.createRandomFourDigitNumber();
        if (number == null) {
            view.displayNumberCreatedUnsuccessfulBanner();
        } else {
            view.displayNumberCreatedSuccessfulBanner();
            view.displayNumberCreated(number);
        }
        
        return number;
    }
    
    private void getAndDisplayNumbers() {
        List<Integer> list = new ArrayList();
        service.getNumbers().forEach(number -> {
            list.add(number.getNumber());
        });
        Collections.sort(list);
        view.displayNumbers(list);
    }
    
    private void guessNumber() {
        view.displayGuessNumberBanner();
        List<Integer> list = new ArrayList();
        
        service.getNumbers().forEach(number -> {
            list.add(number.getNumber());
        });
        if (list.size() > 0) {
            Collections.sort(list);
            int choice = view.getGame(list);
            playGame(choice);
        } else {
            view.displayGuessNumberNoNumbersCreatedBanner();
        }
    }
    
    private void playGame(int number) {
        int guess = view.getGuessForGame();
        System.out.println("Guess = " + guess);
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    
}
