package com.bebbold.draftgame.controller;

import com.bebbold.draftgame.model.Game;
import com.bebbold.draftgame.model.Player;
import com.bebbold.draftgame.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player){
        log.info("start game request{}",player);
        return ResponseEntity.OK(gameService.createGame(player));
    }
}
