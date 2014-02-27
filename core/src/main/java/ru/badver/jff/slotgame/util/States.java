package ru.badver.jff.slotgame.util;

/**
 * Created by wish on 23.02.14.
 */
public enum States {
    START, // just started
    LOADING, // load assets
    LOADED, // finish loading
    PAUSED,
    GAME,  // main game
    RISK,  // risk game
    BONUS  // bonus game
}
