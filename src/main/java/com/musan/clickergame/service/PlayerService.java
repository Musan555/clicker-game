package com.musan.clickergame.service;

import com.musan.clickergame.model.Player;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final Player player = new Player();

    public Player getPlayer() {
        return player;
    }
}