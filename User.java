package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class User {
    private String name;
    private String bio;

    private static HashMap<String, String> userPass = new HashMap<String, String>();
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private ArrayList<User> followers = new ArrayList<>();
    private ArrayList<User> followings = new ArrayList<>();

    public User(String name, String pass) {
        this.name = name;
        userPass.put(name, pass);
        this.bio = " ";
    }

    private void print(String s) {
        System.out.print(s);
    }

    public void userLoggedIn() {
        Scanner in = new Scanner(System.in);
        String command = "";
        while (!command.equals("Logout")) {
            print("\n\nEnter only one of these commands:");
            print("\n\tMy profile\n\tTimeline\n\tTweet\n\tReply\n\tDelete tweet\n\tFollow\n\tUnfollow" +
                    "\n\tReport\n\tFollowers\n\tFollowings\n\tProfile\n\tLike\n\tChange password\n\tEdit bio\n\tLogout\n\tQuit\n");
            command = in.nextLine();
            new Task(this, command);
        }
    }

    public static boolean checkPass(String n, String p) {
        if (userPass.containsKey(n)) {
            if (userPass.get(n).equals(p))
                return true;
            else
                return false;
        }
        return false;
    }

    public void addTweet(Tweet t) {
        tweets.add(t);
    }

    public void addBio(String b) {
        bio = b;
        print("Your bio was successfully changed");
    }

    public void addFollower(User f) {// the first must be add to followers of s
        followers.add(f);
    }

    public void addFollowing(User f) {// the fisrt must be add to followings of s
        followings.add(f);
    }

    public static void addUser(String n, String p) {
        userPass.put(n, p);
    }

    public void deleteTweet(Tweet t) {
        if (tweets.contains(t)) {
            tweets.remove(t);
        }
    }

    public void removeFromFollowers(User f) {// first must be remove from s followers
        followers.remove(f);
    }

    public void removeFromFollowings(User f) {
        followings.remove(f);
    }

    public String getName() {
        return name;
    }

    public ArrayList getFollowings() {
        return followings;
    }

    public ArrayList getFollowers() {
        return followers;
    }

    public ArrayList getTweets() {
        return tweets;
    }

    public String getBio() {
        return bio;
    }

    public int lastTweetId() {
        if (tweets.isEmpty()) {
            return 10000 * (Main.getNumOfUser(this.getName()) + 1);
        }
        return tweets.get(tweets.size() - 1).getID();
    }
}