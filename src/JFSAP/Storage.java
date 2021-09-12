/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Morgan Higginbotham
 */
public class Storage {
    public static boolean writeToIndexLog(int year, int quarter)
    {
        IO.fileDirectoryCheckAndBalances("config");
        
        try {
            FileWriter fileWriter = new FileWriter("config\\indexlog.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            
            file.write(year + " Q" + quarter);
            file.newLine();
            file.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean writeTo10KLog(int year, String company)
    {
        IO.fileDirectoryCheckAndBalances("config");
        company = company.toUpperCase().replaceAll(" ", "");
        
        try {
            FileWriter fileWriter = new FileWriter("config\\10klog.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            
            file.write(year + " " + company);
            file.newLine();
            file.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean writeTo10QLog(int year, int quarter, String company)
    {
        company = company.toUpperCase().replaceAll(" ", "");
        IO.fileDirectoryCheckAndBalances("config");
        
        try {
            FileWriter fileWriter = new FileWriter("config\\10qlog.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            
            file.write(year + " " + company + " Q" + quarter);
            file.newLine();
            file.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean writeToTableLog(int year, String company)
    {
        //make a quarter one (this method)
        IO.fileDirectoryCheckAndBalances("config");
        company = company.toUpperCase().replaceAll(" ", "");
        
        try {
            FileWriter fileWriter = new FileWriter("config\\tablelog10k.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            
            file.write(year + " " + company);
            file.newLine();
            file.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public static boolean writeToTableLog(int year, int quarter, String company)
    {
        //make a quarter one (this method)
        IO.fileDirectoryCheckAndBalances("config");
        company = company.toUpperCase().replaceAll(" ", "");
        
        try {
            FileWriter fileWriter = new FileWriter("config\\tablelog10q.txt", true);
            BufferedWriter file = new BufferedWriter(fileWriter);
            
            file.write(year + " " + quarter + " " + company);
            file.newLine();
            file.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Storage.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
