package app;

import app.config.ApplicationConfig;

import app.utils.ExecutionTimer;

public class Main
{
    public static void main(String[] args)
    {
        ApplicationConfig.startServer(7070);
    }
}