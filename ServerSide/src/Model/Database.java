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
    public ResultSet rs ;
    private PreparedStatement pst;
    
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
    
    public void updateResultSet()  {
        
        try {
            this.pst =con.prepareStatement("Select * from player",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_READ_ONLY  );
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.rs = pst.executeQuery(); // rs has all data
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disableConnection() throws SQLException{
        rs.close();
        pst.close();
        con.close();
        instanceData = null;
    }
    
    public void login(String username,String password) throws SQLException{
        pst = con.prepareStatement("update player set isActive = ?  where username = ? and password = ? ",ResultSet.TYPE_SCROLL_SENSITIVE ,ResultSet.CONCUR_UPDATABLE  );
        pst.setString(1, "true");
        pst.setString(2, username);
        pst.setString(3, password);
        pst.executeUpdate(); // rs has all data
        updateResultSet();
        System.out.println("Model.Database.login()");
    }
    
    public void SignUp(String username , String email, String password) throws SQLException{

        pst = con.prepareStatement("insert into player(username,email,password) values(?,?,?)");
        pst.setString(1, username);
        pst.setString(2, email);
        pst.setString(3, password);
        pst.executeUpdate(); // rs has all data
        updateResultSet();
        login(username,password);
    }
<<<<<<< HEAD

    public String checkRegister(String username , String email){
        ResultSet checkRs;
        PreparedStatement pstCheck;
        String check;
        try {
            //        String queryString= new String("select username from player where username = ?");
            pstCheck = con.prepareStatement("select * from player where username = ?");
            pstCheck.setString(1, username);
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
    public String checkSignIn(String username, String password){
        ResultSet checkRs;
        PreparedStatement pstCheck;
        String check;       
        try {
            pstCheck = con.prepareStatement("select * from player where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            if(checkRs.next()){
                if(password.equals(checkRs.getString(4))){
                    return "Logged in successfully";
                }
                return "Password is incorrect";
            }
            return "Username is incorrect";
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Connection issue, please try again later";
        }
    }
    
    public int getScore(String username){
        int score;
        ResultSet checkRs;
        PreparedStatement pstCheck;
 
        try {
            pstCheck = con.prepareStatement("select * from player where username = ?");
            pstCheck.setString(1, username);
            checkRs = pstCheck.executeQuery();
            checkRs.next();
            score = checkRs.getInt(5);
            return score;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
=======
        
>>>>>>> 5bc69cba34895972e6dbe6a292a799d6443d72bc
}
