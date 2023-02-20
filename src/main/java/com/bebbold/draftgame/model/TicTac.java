package com.bebbold.draftgame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicTac {
    X(1),O(2);
    private Integer value;
}
