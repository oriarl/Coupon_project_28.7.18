package com.example.demo.entry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ConnectionPool {
    private List<Connection> connections;
    private static final int MAX_CONNECTIONS = 15;

    // private ctor - creates 5 connections
    private ConnectionPool() {
        connections = new ArrayList<>();
        for (int i = 0; i < MAX_CONNECTIONS; i++)
        	{
            Connection aConnection = new Connection(8080, i + " !");
            connections.add( aConnection );
            

            }
        
            
        }
    public synchronized Connection getConnection() throws InterruptedException {
        while( connections.isEmpty() )
        {
            wait();
        }
        Connection connectionToUser = connections.get(0);
        connections.remove(0);
        return connectionToUser;
    	}   
    public synchronized void returnConnection (Connection connection)  {
        notify();
        connections.add( connection );
    }
    public void closeAllConnections() {
    	
    }
    }
	


