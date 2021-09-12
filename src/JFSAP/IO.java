/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author Morgan Higginbotham
 */
public class IO {
    
    public static boolean downloadFile(String url, String location, String filename)
    {
        try 
        {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            
            fileDirectoryCheckAndBalances(location);
            FileOutputStream fos = new FileOutputStream(location + "\\" + filename);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            rbc.close();
            return true;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static boolean getPageToFile(String urlName, String location, String fileName)
    {
        
        StringBuilder text = new StringBuilder();
        
        try
        {
            URL page = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
            conn.connect();
            InputStreamReader in = new InputStreamReader((InputStream)conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            
            fileDirectoryCheckAndBalances(location);
            
            //FileWriter file = new FileWriter();
            
            do{
                line = buff.readLine();
                text.append(line);
                text.append("\n");
            }
            while(line != null);
            
            
            
            return true;
        }
        catch(IOException ioe){
            System.out.println("IO Error: " + ioe.getMessage() + "352843");
            return false;
        }
    }
    
    public static boolean fileFromHTML(String urlName, String fileType, String companyOne, int year)
    {
        try{
            String companyTwo = companyOne.replaceAll("\\s+","").toUpperCase();
            String location = "Index\\" + fileType + "\\" + year;
            
            URL page = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
            conn.connect();
            InputStreamReader in = new InputStreamReader((InputStream)conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            
            fileDirectoryCheckAndBalances(location);
            
            FileWriter file = new FileWriter(location + "\\" + companyTwo + fileType + ".htm");
            
            line = buff.readLine();
            
            do{
                file.append(line);
                
                if (line.contains("</html>"))
                {
                    file.close();
                }
                
                file.append("\n");
                line = buff.readLine();
            }
            while(line != null);
            
            file.close();
            return true;
        }
        catch(IOException ioe){
            System.out.println("IO Error: " + ioe.getMessage() + "327683");
            return false;
        }
    }
    
    public static boolean fileFromHTML(String urlName, String fileType, String companyOne, int year, int quarter)
    {
        try{
            String companyTwo = companyOne.replaceAll("\\s+","").toUpperCase();
            String location = "Index\\" + fileType + "\\" + year;
            
            URL page = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
            conn.connect();
            InputStreamReader in = new InputStreamReader((InputStream)conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            
            fileDirectoryCheckAndBalances(location);
            
            FileWriter file = new FileWriter(location + "\\" + quarter + companyTwo + fileType + ".htm");
            System.out.println(location + "\\" + quarter + companyTwo + fileType + ".htm");
            
            line = buff.readLine();
            
            do{
                file.append(line);
                
                if (line.contains("</html>"))
                {
                    file.close();
                }
                
                file.append("\n");
                line = buff.readLine();
            }
            while(line != null);
            
            file.close();
            return true;
        }
        catch(IOException ioe){
            System.out.println("IO Error: " + ioe.getMessage());
            return false;
        }
    }
    
    public static boolean fileDirectoryCheckAndBalances(String location)
    {
        Path path = Paths.get(location);
        //if directory exists?
        if (!Files.exists(path)) 
        {
            try 
            {
                Files.createDirectories(path);
            } catch (IOException ex) 
            {
                Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }
    
    public static boolean fileExistsAnnual(String fileType, String company, int year)
    {   
        String searchLine  = "";
        int chInt;
        char ch;
        int count = 0;
        int going = 0;
        String trueFileType;
        
        if (fileType == "10-K")
        {
            trueFileType = "10k";
            company = company.replaceAll(" ", "").toUpperCase();
            searchLine = year + " " + company;
        }
        else
        {
            return false;
        }
        
        try {
            FileReader file = new FileReader("config\\" + trueFileType + "log.txt");
            
            chInt = file.read();
            
            while(chInt != -1)
            {
                ch = (char)(chInt);
                
                if (count == searchLine.length())
                {
                    count = 0;
                }

                if (searchLine.charAt(count) == ch)
                {
                    going++;
                }
                else
                {
                    going = 0;
                    count = -1;
                }

                if (going == searchLine.length())
                {
                    return true;
                }

                count++;
                
                
                chInt = file.read();
            }
            
            file.close();
            
            return false;
            //file.write(year + " " + company + " " + url);
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean fileExistsQuarterly(String fileType, String company, int year, int quarter)
    {   
        String searchLine  = "";
        int chInt;
        char ch;
        int count = 0;
        int going = 0;
        String trueFileType;
        
        if(fileType == "10-Q")
        {
            trueFileType = "10q";
            company = company.replaceAll(" ", "").toUpperCase();
            searchLine = year + " " + company + " Q" + quarter;
        }
        else
        {
            return false;
        }
        
        try {
            FileReader file = new FileReader("config\\" + trueFileType + "log.txt");
            
            chInt = file.read();
            
            while(chInt != -1)
            {
                ch = (char)(chInt);
                
                if (count == searchLine.length())
                {
                    count = 0;
                }

                if (searchLine.charAt(count) == ch)
                {
                    going++;
                }
                else
                {
                    going = 0;
                    count = -1;
                }

                if (going == searchLine.length())
                {
                    return true;
                }

                count++;
                
                
                chInt = file.read();
            }
            
            file.close();
            
            return false;
            //file.write(year + " " + company + " " + url);
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean tableExistsQuarterly(String fileType, String company, int year, int quarter)
    {   
        String searchLine  = "";
        int chInt;
        char ch;
        int count = 0;
        int going = 0;
        String trueFileType;
        
        if(fileType == "10-Q")
        {
            trueFileType = "10q";
            company = company.replaceAll(" ", "").toUpperCase();
            searchLine = year + " " + company + " Q" + quarter;
        }
        else
        {
            return false;
        }
        
        try {
            FileReader file = new FileReader("config\\" + trueFileType + "tablelog10q.txt");
            
            chInt = file.read();
            
            while(chInt != -1)
            {
                ch = (char)(chInt);
                
                if (count == searchLine.length())
                {
                    count = 0;
                }

                if (searchLine.charAt(count) == ch)
                {
                    going++;
                }
                else
                {
                    going = 0;
                    count = -1;
                }

                if (going == searchLine.length())
                {
                    return true;
                }

                count++;
                
                
                chInt = file.read();
            }
            
            file.close();
            
            return false;
            //file.write(year + " " + company + " " + url);
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean tableExistsAnnual(String fileType, String company, int year)
    {   
        String searchLine  = "";
        int chInt;
        char ch;
        int count = 0;
        int going = 0;
        String trueFileType;
        
        if (fileType == "10-K")
        {
            trueFileType = "10k";
            company = company.replaceAll(" ", "").toUpperCase();
            searchLine = year + " " + company;
        }
        else
        {
            return false;
        }
        
        try {
            FileReader file = new FileReader("config\\" + trueFileType + "table10klog.txt");
            
            chInt = file.read();
            
            while(chInt != -1)
            {
                ch = (char)(chInt);
                
                if (count == searchLine.length())
                {
                    count = 0;
                }

                if (searchLine.charAt(count) == ch)
                {
                    going++;
                }
                else
                {
                    going = 0;
                    count = -1;
                }

                if (going == searchLine.length())
                {
                    return true;
                }

                count++;
                
                
                chInt = file.read();
            }
            
            file.close();
            
            return false;
            //file.write(year + " " + company + " " + url);
            
        } catch (IOException ex) {
            return false;
        }
    }
    
    public static boolean containsNumbers(String str, int cnt)
    {
        short num;
        int count = 0;
        for(int x = 0; x < str.length(); x++)
        {
            num = (short)(str.charAt(x));
            
            if(num >= 48 && num <= 57)
            {
                count++;
            }
            else
            {
                count = 0;
            }
            
            if (count == cnt)
            {
                return true;
            }
        }
        return false;
    }
    
}
