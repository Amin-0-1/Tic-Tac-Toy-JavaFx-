///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package model;

/**
 *
 * @author Amin
 */
public class Player {
//    private int id;
    private String username;
    private String email;
    private int score;
    private boolean isactive;
    private boolean isplaying;

    
    public Player(){
        
    }   
    
    public Player(String username,String email,int score,boolean isActive,boolean isPlaying){
        this.email = email;
        this.isactive = isActive;
        this.isplaying = isPlaying;
        this.score = score;
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the isactive
     */
    public boolean isIsactive() {
        return isactive;
    }

    /**
     * @param isactive the isactive to set
     */
    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    /**
     * @return the isplaying
     */
    public boolean isIsplaying() {
        return isplaying;
    }

    /**
     * @param isplaying the isplaying to set
     */
    public void setIsplaying(boolean isplaying) {
        this.isplaying = isplaying;
    }
}
