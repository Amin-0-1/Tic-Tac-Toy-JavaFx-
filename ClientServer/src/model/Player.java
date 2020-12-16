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
     public String username;
     String email;
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
}
