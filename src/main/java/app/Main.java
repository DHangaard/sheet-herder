package app;

import app.config.ApplicationConfig;

import app.utils.ExecutionTimer;

public class Main
{
    public static void main(String[] args)
    {
        ExecutionTimer.start();
        ApplicationConfig.startServer(7070);
        System.out.println(ExecutionTimer.finish());
    }
}