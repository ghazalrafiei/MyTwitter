package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;

public class Tweet {
    private String text;
    private User user;
    private String date;
    private int id; // 10000*number of user+ number of tweet
    private int kind; // 0=ordinary 1=reply
    private ArrayList<Tweet> replies=new ArrayList<>();
    public static HashMap<Integer, Integer> likes=new HashMap<>(); // id -> likes
    private static ArrayList<Tweet> allTweets =new ArrayList<>();

    public Tweet(User writer,String txt){
        user=writer;
        this.text=txt;
        id =writer.lastTweetId()+1;
        likes.put(id,0);
        kind=0;


        Date date0=new Date();
        date=date0.toString();

        allTweets.add(this);
    }
    public Tweet(User writer,String txt,boolean k){
        user=writer;
        this.text=txt;
        id=writer.lastTweetId()+1;

        likes.put(id,0);
        kind=1;

        Date date0=new Date();
        date=date0.toString();
        allTweets.add(this);

    }// only for reply tweets

    public static void like(int id){
        likes.put(id,likes.get(id)+1);
    }
    public void addReply(Tweet re){
        replies.add(re);
    }
    private String printChar(int i,Character c) {
        String k="";
        for(int j=0; j<i; j++){
            k+=c;
        }
        return k;
    }

    public String getDate(){
        return date;
    }
    public int getID(){
        return id;
    }
    public static Tweet getTweet(int id){
        for(Tweet k: allTweets)
            if(k.getID()==id)
                return k;
        return null;
    }
    public String getKind(){
        if(kind==0){
            return "ordinary";
        }
        return "not ordinary";
    }
    public User getUser(){
        return user;
    }

    public void printTweet(int i){ //with all replies
                if(i==0){
                    System.out.print("\n"+printChar(i,'\t')+user.getName()+": "+text+"\n"+printChar(i,'\t')+"ID : "+id+"\n"+printChar(i,'\t')+
                            +likes.get(id)+" like(s)\n"+printChar(i,'\t')+"Posted on "+date+"\n"+printChar(i,'\t')+printChar(date.length(),'_')+"\n");
                    for(Tweet k:replies){
                        k.printTweet(i+1);
                    }
                }
                else{
                    System.out.print("\n"+printChar(i,'\t')+user.getName()+": "+text+"\n"+printChar(i,'\t')+"ID : "+id+"\n"+printChar(i,'\t')+
                            +likes.get(id)+" like(s)\n"+printChar(i,'\t')+"Replied on "+date+"\n"+printChar(i,'\t')+printChar(date.length(),'_')+"\n");
                    for(Tweet k:replies){
                        k.printTweet(i+1);
                    }
                }
    }
}
