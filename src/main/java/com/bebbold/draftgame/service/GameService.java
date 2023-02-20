package com.bebbold.draftgame.service;

import com.bebbold.draftgame.exception.NotFoundException;
import com.bebbold.draftgame.exception.InvalidParamException;
import com.bebbold.draftgame.exception.InvalidGameException;

import com.bebbold.draftgame.model.*;
import com.bebbold.draftgame.storage.GameStorage;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GameService {

    public Game createGame(Player player) {
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setGameID(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setGameStatus(GameStatusEnum.NEW);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new InvalidParamException("Game with provided ID does not exists");
        }
        Game game = GameStorage.getInstance().getGames().get(gameId);
        if (game.getPlayer2() != null) {
            throw new InvalidGameException("Game has already 2 players");
        }
        game.setPlayer2(player2);
        game.setGameStatus(GameStatusEnum.IN_PROGRESS);
        return game;

    }

    public Game connectToRandomGame(Player player2) throws NotFoundException {
        Game game = GameStorage.getInstance().getGames()
                .values()
                .stream()
                .filter(it -> it.getGameStatus().equals(GameStatusEnum.NEW))
                .findFirst().orElseThrow(() -> new NotFoundException("Game not found"));

        game.setPlayer2(player2);
        game.setGameStatus(GameStatusEnum.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
        if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
            throw new NotFoundException("Game not found");
        }
        Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
        if (game.getGameStatus().equals(GameStatusEnum.FINISHED)) {
            throw new InvalidGameException("GAME OVER");
        }

        int[][] board = game.getBoard();
        board[gamePlay.getCoordX()][gamePlay.getCoordY()] = gamePlay.getType().getValue();

        checkWinner(game.getBoard(), TicTac.X);
        checkWinner(game.getBoard(), TicTac.O);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    private boolean checkWinner(int[][] board, TicTac ticTacToe) {
        int[] boardArray = new int[9];
        int counterIndex = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                boardArray[counterIndex] = board[i][j];
                counterIndex++;
            }
        }
        //check win combinations // 0 4 8 // 0 1 2 // 3 4 5 //6 7 8 //2 4 6 //0 3  6 // 1 4 7 //2 5 8

        int[][] winCombos = {{0, 4, 8}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}};
        for (int i = 0; i < winCombos.length; i++) {
            int counter = 0;
            for (int j = 0; j < winCombos[i].length; j++) {
                if (boardArray[winCombos[i][j]] == ticTacToe.getValue()) {
                    counter++;
                    if (counter == 3)
                        return true;
                }
            }
        }
        return false;
    }
}
