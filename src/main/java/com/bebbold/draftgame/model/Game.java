package com.bebbold.draftgame.model;


import lombok.Data;

@Data
public class Game {
    private Player player1;
    private Player player2;
    private String gameID;
    private GameStatusEnum gameStatus;
    private int[][] board;
    private TicTac winner;
}
