/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personalfinancemanager;

/**
 *
 * @author tasif
 */

import java.io.*;
import java.util.*;

public class UserDataUtil {

    private static final String FILE_NAME = "users.csv";

    // Save user data
    public static void saveUser(User user) throws IOException {
        File file = new File(FILE_NAME);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            file.createNewFile();
        }

        // Append the user data to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(user.getFullName() + "," + user.getEmail() + "," + user.getUsername() + "," + user.getPassword());
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Error writing to file: " + e.getMessage());
        }
    }

    // Load all users
    public static List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_NAME);

        // Check if file exists, return empty list if not
        if (!file.exists()) {
            return users; // No users to load
        }

        // Read from the CSV file
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 4) {
                    users.add(new User(details[0], details[1], details[2], details[3]));
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading from file: " + e.getMessage());
        }

        return users;
    }

    // Check if the username already exists
    public static boolean isUsernameTaken(String username) throws IOException {
        for (User user : loadUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Authenticate the user (login)
    public static User authenticate(String username, String password) throws IOException {
        for (User user : loadUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
