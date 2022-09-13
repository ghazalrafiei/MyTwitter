package com.company;

import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

public class Task {
    private Scanner in = new Scanner(System.in);

    public Task(User user, String job) {
        String u = user.getName();
        switch (job) {
            case "My profile":
                print("Bio: " + user.getBio());
                ArrayList<Tweet> tweets = user.getTweets();
                for (Tweet t : tweets) {
                    print("\n");
                    if (t.getKind().equals("ordinary")) {
                        t.printTweet(0);
                        print("\n");
                    }

                }
                print("\n");
                break;

            case "Timeline":
                timeLine(user);
                break;

            case "Tweet":
                tweets(user);
                break;

            case "Reply":
                reply(user);
                break;

            case "Delete tweet":
                delTweet(user);
                break;

            case "Follow":
                follow(user);
                break;

            case "Unfollow":
                unfollow(user);
                break;

            case "Report":
                report(user);
                break;

            case "Followers":
                followers(user);
                break;

            case "Followings":
                followings(user);
                break;

            case "Profile":
                profile();
                break;

            case "Like":
                like(user);
                break;

            case "Change password":
                changePass(u);
                break;

            case "Edit bio":
                addBio(user);
                break;

            case "Quit":
                System.exit(0);
                break;
        }
    }

    public static void print(String s) {
        System.out.print(s);
    }

    private void timeLine(User user0) {
        print("\n");
        ArrayList<Tweet> timeLine = new ArrayList<>();
        timeLine.addAll(user0.getTweets());
        ArrayList<User> flwgs = user0.getFollowings();
        for (User u : flwgs) {
            timeLine.addAll(u.getTweets());
        }

        // sort by posted time :
        if (!timeLine.isEmpty()) {
            timeLine.sort(Comparator.comparing(Tweet::getDate));
            for (Tweet t : timeLine) {
                if (t.getKind().equals("ordinary")) {
                    t.printTweet(0);
                }
            }
        } else
            print("There is no tweet to show!");
    }

    private void follow(User user0) {
        User flwU;
        boolean check = false;
        while (!check) {
            print("Enter an username you want to follow : ");
            String flw = in.next(); // m wants to follow
            if (flw.equals("Menu"))
                return;
            flwU = Main.getUser(flw);
            if (flwU == null) {
                print("This user does not exist at all! Try again\n");
                continue;
            }

            if (!user0.getFollowings().contains(flwU)) {
                user0.addFollowing(flwU);
                flwU.addFollower(user0);
                print("You successfully followed " + flwU.getName());
                check = false;
            } else
                print("You already are following this user! Try again");
        }
    }

    private void unfollow(User user0) {
        User unflwU;
        boolean check = false;
        while (!check) {
            print("Enter an username you want to unfollow : ");
            String unflw = in.next();
            if (unflw.equals("Menu"))
                return;
            unflwU = Main.getUser(unflw);
            if (unflwU != null)
                check = true;

            if (user0.getFollowings().contains(unflwU)) {
                user0.removeFromFollowings(unflwU);
                unflwU.removeFromFollowers(user0);
                print("You unfollowed that user successfully!");
            } else
                print("You already aren't following this user!\n");
        }
    }

    private void report(User user0) {
        boolean check = false;
        while (!check) {
            print("Which user do you want to report from your followers? ");
            String r = in.next();
            if (r.equals("Menu"))
                return;
            User rU = Main.getUser(r);
            if (rU != null && user0.getFollowers().contains(rU)) {
                user0.removeFromFollowers(rU);
                rU.removeFromFollowings(user0);
                print("\nReported successfully\n");
                check = true;
            } else
                print("\nThis user is not following you. Try again \n");
        }
    }

    private void changePass(String u) {
        boolean check = false;
        while (!check) {
            print("\nEnter your old password : ");
            String old0 = in.next();
            if (old0.equals("Menu"))
                return;
            print("Enter again : ");
            String old1 = in.next();
            if (old1.equals("Menu"))
                return;
            if (!old0.equals(old1)) {
                print("Wrong! Try again");
            } else
                check = true;
        }
        print("Choose your new password : ");
        String new0 = "";
        while (new0.length() < 8) {
            print("It must be at least 8 characters");
            new0 = in.next();
            if (new0.equals("Menu"))
                return;
        }
        print("Verify : ");
        String new1 = in.next();
        if (new1.equals("Menu"))
            return;
        else if (new0.equals(new1)) {
            print("Your password successfully has been changed");
            User.addUser(u, new0);
        }
    }

    private void like(User u0) {
        boolean check = false;
        while (!check) {
            print("Enter Id of tweet you want to like(integer)...");
            int mustBeLikeD = in.nextInt();
            if (mustBeLikeD == 0)
                return;
            else if (Tweet.likes.containsKey(mustBeLikeD)) {
                Tweet liked = Tweet.getTweet(mustBeLikeD);
                User u = liked.getUser();
                if (u.getFollowers().contains(u0) || u == u0) {
                    Tweet.like(mustBeLikeD);
                    print("Liked !");
                    check = true;
                } else
                    print("You can't access to this tweet!\n");
            } else
                print("You can't access to this tweet!\n");
        }
    }

    private void profile() {
        boolean check = false;
        while (!check) {
            print("Whose profile do you want to see?");
            String s = in.next();
            if (s.equals("Menu"))
                return;
            User k = Main.getUser(s);
            if (k != null) {
                print("\nBio: " + k.getBio());
                ArrayList<Tweet> tweets = k.getTweets();
                if (tweets.isEmpty())
                    print("\nThere is no tweet to show!");
                for (Tweet t : tweets) {
                    if (t.getKind().equals("ordinary")) {
                        t.printTweet(0);
                    }

                }
                check = true;
            } else
                print("This user does not exist at all.\nCheck your spelling\n");
        }

    }

    private void followings(User user0) {
        if (user0.getFollowings().isEmpty())
            print("You have no following");
        else {
            ArrayList<User> followings = user0.getFollowings();
            for (User k : followings)
                print(k.getName() + " ");
        }
    }

    private void followers(User user0) {
        if (user0.getFollowers().isEmpty())
            print("You have no follower");
        else {
            ArrayList<User> followers = user0.getFollowers();
            for (User k : followers) {
                print(k.getName() + " ");
            }
        }
    }

    private void tweets(User user0) {
        print("Write...(less than 140 characters)\n");
        String text = in.nextLine();
        if (text.length() > 140) {
            print("It has more than 140 characters");
        } else {
            Tweet t = new Tweet(user0, text); // in fght texte o id o like
            user0.addTweet(t);
            print("Posted successfully!");
        }
    }

    private void reply(User user0) {
        boolean check = false;
        while (!check) {
            print("Enter Id of tweet you want to reply(integer) :");
            int re = in.nextInt();
            if (re == 0)
                return;
            Tweet base = Tweet.getTweet(re);
            if (base != null) {
                print("Write...");
                in.nextLine();
                String text = in.nextLine();
                if (text.equals("Menu"))
                    return;
                Tweet reply = new Tweet(user0, text, true);
                Tweet replied = Tweet.getTweet(re);
                replied.addReply(reply);
                user0.addTweet(reply);
                print("Replied successfully");
                check = true;
            } else
                print("This tweet does not exist at all!");
        }

    }

    private void delTweet(User user0) {
        boolean check = false;
        while (!check) {
            print("Which Tweet do you want to tweet?(integer) ...");
            int del = in.nextInt();
            if (del == 0)
                return;
            ArrayList<Tweet> tweets = user0.getTweets();
            boolean ch = false;
            for (Tweet t : tweets) {
                if (t.getID() == del) {
                    user0.deleteTweet(t);
                    print("Deleted successfully!");
                    ch = true;
                    check = true;
                    break;
                }
            }
            if (ch == false) {
                print("You do not have posted this tweet!\n");
            }
        }
    }

    private void addBio(User user0) {
        print("Write about yourself...\n");
        String b = in.nextLine();
        if (b.equals("Menu"))
            return;
        user0.addBio(b);

    }
}