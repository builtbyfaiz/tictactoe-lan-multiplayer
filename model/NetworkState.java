package model;

/// Represents state of client/server connections over game lifetime
public enum NetworkState {
    SERVER_INIT,            // Game is now server
    CLIENT_INIT,            // Game is now a client
    CONNECTED,              // Both players connected
}