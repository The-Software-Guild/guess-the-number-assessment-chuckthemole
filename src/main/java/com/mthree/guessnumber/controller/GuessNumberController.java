/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.controller;

import com.mthree.guessnumber.models.Game;
import com.mthree.guessnumber.models.Round;
import com.mthree.guessnumber.service.GuessNumberServiceLayer;
import com.mthree.guessnumber.ui.GuessNumberView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    private Integer createRandomFourDigitNumber() {
        Integer gameID = service.createRandomFourDigitNumber();
        if (gameID == null) {
            view.displayNumberCreatedUnsuccessfulBanner();
        } else {
            view.displayNumberCreatedSuccessfulBanner();
            view.print("ID=" + gameID);
            view.displayNumberCreated(service.retrieveHiddenNumber(gameID));
        }
        
        return gameID;
    }
    
    @PostMapping("/guess")
    private ResponseEntity<Round> guess(int guess, int gameID) {
        Round round = service.guessNumber(guess, gameID);
        
        if (round == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(round);
        
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
    
    @GetMapping("/getgames")
    private ResponseEntity<List<Game>> getGames() {
        List<Game> games = new ArrayList<>();
        games.addAll(service.getGames());
        
        if (games.size() < 1) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(games);
    }
    
    @GetMapping("/game/{id}")
    private ResponseEntity<Game> findByID(@PathVariable int id) {
        Game game = service.getGame(id);
        if (game == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(game);
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
    
    @GetMapping("/rounds/{id}")
    private ResponseEntity<List<Round>> findRoundsByGameID(@PathVariable int id) {
        List<Round> rounds = service.getRounds(id);

        if (rounds.size() < 1) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(rounds);
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
