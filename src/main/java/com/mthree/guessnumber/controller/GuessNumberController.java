/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.controller;

import com.mthree.guessnumber.models.RandomFourDigitNumber;
import com.mthree.guessnumber.service.GuessNumberServiceLayer;
import com.mthree.guessnumber.ui.GuessNumberView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Chuck
 */

@RestController
@RequestMapping("/api/guessnumber")
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
    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
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
    
    @GetMapping("/getallnumbers")
    private List<Integer> getAndDisplayNumbers() {
        List<Integer> list = new ArrayList();
        service.getNumbers().forEach(number -> {
            list.add(number.getNumber());
        });
        Collections.sort(list);
        view.displayNumbers(list);
        
        return list;
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
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    
}
