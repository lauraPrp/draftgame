package com.bebbold.draftgame.controller.dto;

import com.bebbold.draftgame.model.Game;
import com.bebbold.draftgame.model.Player;
import lombok.Getter;

@Getter
public class ConnectRequest {
    private Player player;
    private String gameId;

}
