package model;

/// Represents state of client/server connections over game lifetime
public enum NetworkState {
    MULTIPLAYER_INIT,       // Shifted to multiplayer mode
    SERVER_INIT,            // Game is now server
    CLIENT_INIT,            // Game is now a client
    CONNECTED,              // Both players connected
    DISCONNECTED,           // Connection disconnected
    FAILED                  // A step has failed during the process
}