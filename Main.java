package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<User> allUsers = new ArrayList<>();

    private static void userRegister() {
        Scanner in = new Scanner(System.in);
        print("User name : ");
        String n = in.nextLine();
        if (n.equals("Menu"))
            return;
        while (allUsers.contains(getUser(n))) {
            print("This username has been used. " +
                    "\nPlease Try another username");
            n = in.nextLine();
            if (n.equals("Menu"))
                return;
        }
        print("Choose your password (at least 8 characters) : ");
        String p = in.next();
        if (p.equals("Menu"))
            return;
        while (p.length() > 2) {
            print("Characters are less than 8. Please try another password : ");
            p = in.next();
            if (p.equals("Menu"))
                return;
        }
        User user = new User(n, p);
        allUsers.add(user);
        print("You successfully signed up ! ");
        in.close();
    }

    private static void userLogin() {
        Scanner in = new Scanner(System.in);
        print("User name : ");
        String u = in.next();
        if (u.equals("Menu"))
            return;
        User u0 = getUser(u);
        while (!allUsers.contains(u0)) {
            print("There's no user " + u + "\nPlease Try again : ");
            u = in.next();
            if (u.equals("Menu"))
                return;
            u0 = getUser(u);
        }
        print("Password : ");
        boolean check = false;
        String p = in.next();
        if (p.equals("Menu"))
            return;
        while (!check) {
            if (User.checkPass(u, p))
                check = true;
            else {
                print("Wrong password! \nTry again : ");
                p = in.next();
                if (p.equals("Menu"))
                    return;
            }
        }
        print("You are logged in!");
        getUser(u).userLoggedIn();
        in.close();
    }

    public static User getUser(String k) {
        for (User t : allUsers) {
            if (t.getName().equals(k))
                return t;
        }
        return null;
    }

    public static int getNumOfUser(String k) {
        for (User t : allUsers) {
            if (t.getName().equals(k))
                return allUsers.indexOf(t);
        }
        return 0;
    }

    private static void print(String s) {
        System.out.print(s);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            print("\n\tInfo\n\tLogin \n\tSign up\n\tMenu\n\tQuit\n");
            String command = in.nextLine();
            switch (command) {
                case "Quit":
                    System.exit(0);
                    break;

                case "Sign up":
                    userRegister();
                    break;

                case "Login":
                    userLogin();
                    break;

                case "Info":
                    print("Please enter all commands as they shown to you(First character is always capital).\n");
                    print("If you want to go back in menu, and it was mentioned that you must enter integer, enter 0. If it was not, please enter "
                            +
                            "Menu.\n");
                    print("If you want to like someone's post, first you must follow that user.\n");
                    print("New update!\nYou can reply to ANY tweet. Even the replies tweets!");
                    break;

                default:
                    print("\nEnter only one of these commands: ");
            }
            in.close();
        }

    }
}