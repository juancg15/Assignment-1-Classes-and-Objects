package sait.mms.manager;

import java.io.*;
import java.util.*;
import sait.mms.problemdomain.Movie;

public class MovieManager {

    private ArrayList<Movie> movies;
    private Scanner in;

    public MovieManager() {
        movies = new ArrayList<>();
        in = new Scanner(System.in);
        loadMovies();
    }

    private void loadMovies() {
        File file = new File("res/movies.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    int duration = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    int year = Integer.parseInt(parts[2]);

                    movies.add(new Movie(duration, title, year));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading movie file.");
        }
    }

    public void displayMenu() {
        int option = 0;

        do {
            System.out.println();
            System.out.println("Movie Management system");
            System.out.println("1\tAdd New Movie and Save");
            System.out.println("2\tGenerate List of Movies Released in a Year");
            System.out.println("3\tGenerate List of Random Movies");
            System.out.println("4\tExit");
            System.out.print("\nEnter an option: ");

            option = Integer.parseInt(in.nextLine());

            switch (option) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    moviesByYear();
                    break;
                case 3:
                    randomMovies();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option!");
            }

        } while (option != 4);
    }

    private void addMovie() {
        try {
            System.out.print("\nEnter duration: ");
            int duration = Integer.parseInt(in.nextLine());

            System.out.print("Enter movie title: ");
            String title = in.nextLine();

            System.out.print("Enter year: ");
            int year = Integer.parseInt(in.nextLine());

            Movie movie = new Movie(duration, title, year);
            movies.add(movie);
            saveMovie(movie);

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    private void saveMovie(Movie movie) {
        File dir = new File("res");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, "movies.txt");

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            pw.println(movie.getDuration() + "," + movie.getTitle() + "," + movie.getYear());
            System.out.println("Saving movies...");
            System.out.println("Added movie to the data file.");
        } catch (IOException e) {
            System.out.println("Error saving movie file.");
        }
    }

    private void moviesByYear() {
        System.out.print("\nEnter year: ");
        int year = Integer.parseInt(in.nextLine());

        int totalDuration = 0;

        System.out.println("\nMovie List");
        System.out.println("Duration\tYear\tTitle");

        for (Movie m : movies) {
            if (m.getYear() == year) {
                System.out.println(m.getDuration() + "\t\t" + m.getYear() + "\t" + m.getTitle());
                totalDuration += m.getDuration();
            }
        }

        System.out.println("Total duration: " + totalDuration + " minutes");
    }

    private void randomMovies() {
        System.out.print("\nEnter number of movies: ");
        int count = Integer.parseInt(in.nextLine());

        Collections.shuffle(movies);

        int totalDuration = 0;

        System.out.println("\nMovie List");
        System.out.println("Duration\tYear\tTitle");

        for (int i = 0; i < count && i < movies.size(); i++) {
            Movie m = movies.get(i);
            System.out.println(m.getDuration() + "\t\t" + m.getYear() + "\t" + m.getTitle());
            totalDuration += m.getDuration();
        }

        System.out.println("Total duration: " + totalDuration + " minutes");
    }
}
