/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Amin
 */
public class Database {
    private static Database instanceData;
    private Connection con;
    private ResultSet rs ;
    private PreparedStatement pst;
    
    public ResultSet getResultSet(){
        return rs;
    }
    
    private Database() throws SQLException{
         DriverManager.registerDriver(new ClientDriver());
         con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTackToy","root","root");
    }

    public static Database getDataBase() throws SQLException {
        if(instanceData == null){
            instanceData = new Database();
        }
        return instanceData;
    }
    
    public void updateResultSet(){
        
        try {
            this.pst =con.prepareStatement("Select * from player",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY  );
            this.rs = pst.executeQuery(); // rs has all data
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getCountOfOfflineUserse(){
        try {
            this.pst =con.prepareStatement("select count(*) from player where isactive = false",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY  );
            ResultSet r =pst.executeQuery(); // rs has all data            System.out.println("has next"+r.next());

            return r.next() ? r.getInt(1) : -1;
        }catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch getactive");
        }
        return -1;
    }
    public void setActive(boolean state , String email){
        try {
            pst = con.prepareStatement("update player set isActive = ? where email = ?");
            pst.setString(1, state+"");
            pst.setString(2, email);
            System.out.println("db mail:"+email);
            pst.executeUpdate(); // rs has all data
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to offline catch");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public ResultSet getActivePlayers(String username){
//        
//        try {
//            this.pst =con.prepareStatement("Select * from player where isactive = true and not username = ? ",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY  );
//            pst.setString(1, username);
//            return pst.executeQuery(); // rs has all data
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("catch getactive");
//            return null;
//        }
//        
//    }
    
        public ResultSet getActivePlayers( ){
        
        try {
            this.pst =con.prepareStatement("Select * from player where isactive = true ",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY  );
            return pst.executeQuery(); // rs has all data
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("catch getactive");
            return null;
        }
        
    }
   
    
    public void disableConnection() throws SQLException{
        changeStateToOffline();
        rs.close();
        pst.close();
        con.close();
        instanceData = null;
    }
    
    public void changeStateToOffline(){
        try {
            pst = con.prepareStatement("update player set isActive = ? ",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_UPDATABLE  );
            pst.setString(1, "false");
            pst.executeUpdate(); // rs has all data
            updateResultSet();
        } catch (SQLException ex) {
            System.out.println("change state to offline");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void login(String email,String password) throws SQLException{
        pst = con.prepareStatement("update player set isActive = ?  where email = ? and password = ? ",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_UPDATABLE  );
        pst.setString(1, "true");
        pst.setString(2, email);
        pst.setString(3, password);
        pst.executeUpdate(); // rs has all data
        updateResultSet();
    }
    
    public void SignUp(String username , String email, String password) throws SQLException{

        pst = con.prepareStatement("insert into player(username,email,password) values(?,?,?)");
        pst.setString(1, username);
        pst.setString(2, email);
        pst.setString(3, password);
        pst.executeUpdate(); // rs has all data
        login(email,password);
    }

    public String checkRegister(String username , String email){
        ResultSet checkRs;
        PreparedStatement pstCheck;
        
        try {
            //        String queryString= new String("select username from player where username = ?");
            pstCheck = con.prepareStatement("select * from player where username = ? and email = ?");
            pstCheck.setString(1, username);
            pstCheck.setString(2, email);
            checkRs = pstCheck.executeQuery();
            if(checkRs.next()){
                return "already signed-up";
            }
        } catch (SQLException ex) {
            System.out.println("here ");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Registered Successfully";
    }
    public String checkSignIn(String email, String password){
        ResultSet checkRs;
        PreparedStatement pstCheck;
        String check;       
        try {
            pstCheck = con.prepareStatement("select * from player where email = ? ");
            pstCheck.setString(1, email);
            checkRs = pstCheck.executeQuery();
            if(checkRs.next()){
                if(password.equals(checkRs.getString(4))){
                    return "Logged in successfully";
                }
                return "Password is incorrect";
            }
            return "Email is incorrect";
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Connection issue, please try again later";
        }
    }
    
    public int getScore(String email){
        int score;
        ResultSet checkRs;
        PreparedStatement pstCheck;
 
        try {
            pstCheck = con.prepareStatement("select * from player where email = ?");
            pstCheck.setString(1, email);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            score = checkRs.getInt(5);
            return score;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }  
    public String getEmail(String username){
        String email;
        ResultSet checkRs;
        PreparedStatement pstCheck;
        try {
            pstCheck = con.prepareStatement("select * from player where email = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            email = checkRs.getString(3);
            return email;
        } catch (SQLException ex) {
            System.out.println("Invalod Email address");
            //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * getUserName
     * get user name to pass it to login method
     * @param email
     * @return 
     */
     public String getUserName(String email){
        String userName;
        ResultSet checkRs;
        PreparedStatement pstCheck;
        try {
            pstCheck = con.prepareStatement("select * from player where email = ?");
            pstCheck.setString(1, email);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            userName = checkRs.getString(2);
            return userName;
        } catch (SQLException ex) {
            System.out.println("Invalod Email address");
            //Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}