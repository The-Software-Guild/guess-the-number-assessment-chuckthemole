/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.service;

import com.mthree.guessnumber.data.GuessNumberDao;
import com.mthree.guessnumber.models.Game;
import com.mthree.guessnumber.models.RandomFourDigitNumber;
import com.mthree.guessnumber.models.Round;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Chuck
 */

@Configuration
public class GuessNumberServiceLayerImpl implements GuessNumberServiceLayer {
    GuessNumberDao dao;

    public GuessNumberServiceLayerImpl(GuessNumberDao dao) {
        this.dao = dao;
    }

    @Override
    public Integer createRandomFourDigitNumber() {
        return dao.createRandomFourDigitNumber();
    }

    @Override
    public List<RandomFourDigitNumber> getNumbers() {
        return dao.getNumbers();
    }

    @Override
    public Round guessNumber(int guess, int gameID) {
        return dao.guessNumber(guess, gameID);
    }

    @Override
    public Map<Integer, Game> getGames() {
        return dao.getGames();
    }

    @Override
    public Integer getNumber(int gameID) {
        return dao.getNumber(gameID);
    }
    
    @Override
    public Integer retrieveHiddenNumber(int gameID) {
        return dao.retrieveHiddenNumber(gameID);
    }

    @Override
    public Game getGame(int id) {
        return dao.getGame(id);
    }

    @Override
    public List<Round> getRounds(int gameID) {
        return dao.getRounds(gameID);
    }
}
