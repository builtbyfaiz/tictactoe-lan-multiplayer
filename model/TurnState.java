package model;

/// Stores game's turn state, useful for gameplay over network 
public enum TurnState {
    MY_TURN,                // Player has to move
    MOVE_SENT,              // Move is broadcasted to the other player over network
    WAITING_FOR_OPPONENT,   // Waiting to receive a valid move
    MOVE_RECEIVED           // To Process received move
}
