/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mthree.guessnumber.data;

import com.mthree.guessnumber.models.Game;
import com.mthree.guessnumber.models.RandomFourDigitNumber;
import com.mthree.guessnumber.models.Round;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Chuck
 */

@Repository
public class GuessNumberDaoFileImpl implements GuessNumberDao {
    private Map<Integer, RandomFourDigitNumber> randomFourDigitNumberList;
    private Map<Integer, Game> games;
    private Map<Game, List<Round>> rounds;
    private final static String GAME_OVER = "e:4:p:0";
        
    public GuessNumberDaoFileImpl() {
        randomFourDigitNumberList = new HashMap();
        rounds = new HashMap();
        games = new HashMap();
    }
    
    @Override
    public Integer createRandomFourDigitNumber() {
        RandomFourDigitNumber number;
        Game game;
        
        while (true) {
            number = new RandomFourDigitNumber();
            if (!randomFourDigitNumberList.containsKey(number.getNumber())) {
                randomFourDigitNumberList.put(number.getNumber(), number);
                game = new Game(number);
                games.put(game.getGameID(), game);
                break;
            }
        }
        
        return game.getGameID();
    }   

    @Override
    public List<RandomFourDigitNumber> getNumbers() {
        List<RandomFourDigitNumber> list = new ArrayList();
        list.addAll(randomFourDigitNumberList.values());
        return list;
    } 
    
    @Override
    public Map<Integer, Game> getGames() {
        return games;
    } 

    @Override
    public Round guessNumber(Integer number, int gameID) {
        Round round = new Round(number, games.get(gameID));
        
        if (rounds.containsKey(games.get(gameID))) {
            rounds.get(games.get(gameID)).add(round);
        } else {
            List<Round> list = new ArrayList();
            list.add(round);
            rounds.put(games.get(gameID), list);
        }
        
        if (round.getResult().equals(GAME_OVER)) {
            games.get(gameID).gameOver();
        }
        
        return round;
    }

    @Override
    public Integer getNumber(int gameID) {
        return games.get(gameID).getNumber().getNumber();
    }
    
    @Override
    public Integer retrieveHiddenNumber(int gameID) {
        return games.get(gameID).retrieveHiddenNumber().getNumber();
    }

    @Override
    public Game getGame(int id) {
        return games.get(id);
    }

    @Override
    public List<Round> getRounds(int gameID) {
        return rounds.get(games.get(gameID));
    }
}
