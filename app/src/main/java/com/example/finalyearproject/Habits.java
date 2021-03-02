package com.example.finalyearproject;

public class Habits {

    private long ID;
    private String  Name;
    private String  Count;
    private String  CountName;
    private String  SessionTracking;
    private String  Time;

    Habits(){}
    Habits(String Name, String Count, String CountName, String SessionTracking, String Time){
        this.Name = Name;
        this.Count =Count;
        this.CountName= CountName;
        this.SessionTracking = SessionTracking;
        this.Time = Time;
    }
    Habits(long ID, String Name, String Count, String CountName, String SessionTracking, String Time ){
        this.ID =ID;
        this.Name = Name;
        this.Count =Count;
        this.CountName= CountName;
        this.SessionTracking = SessionTracking;
        this.Time = Time;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getCountName() {
        return CountName;
    }

    public void setCountName(String countName) {
        CountName = countName;
    }

    public String getSessionTracking() {
        return SessionTracking;
    }

    public void setSessionTracking(String sessionTracking) {
        SessionTracking = sessionTracking;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
