package sait.mms.application;

import sait.mms.manager.MovieManager;

public class AppDriver {

    public static void main(String[] args) {
        MovieManager manager = new MovieManager();
        manager.displayMenu();
    }
}
