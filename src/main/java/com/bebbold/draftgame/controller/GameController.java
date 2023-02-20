package com.bebbold.draftgame.controller;

import com.bebbold.draftgame.controller.dto.ConnectRequest;
import com.bebbold.draftgame.exception.InvalidGameException;
import com.bebbold.draftgame.exception.InvalidParamException;
import com.bebbold.draftgame.exception.NotFoundException;
import com.bebbold.draftgame.model.Game;
import com.bebbold.draftgame.model.GamePlay;
import com.bebbold.draftgame.model.Player;
import com.bebbold.draftgame.service.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.jsse.JSSEImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player){
        log.info("start game request{}",player);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest req) throws InvalidParamException, InvalidGameException {
        log.info("connect request: {}",req);
        return ResponseEntity.ok(gameService.connectToGame(req.getPlayer(),req.getGameId()));
    }

    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandomGame(@RequestBody Player player) throws InvalidParamException, InvalidGameException, NotFoundException {
        log.info("connect random for player: {}",player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay req) throws InvalidParamException, InvalidGameException, NotFoundException {
        log.info("gameplay: {}",req);
        Game game = gameService.gamePlay(req);
        //use websockets to notify players/communicate between server &  client
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameID(), game);

        return ResponseEntity.ok(game);
    }
}
