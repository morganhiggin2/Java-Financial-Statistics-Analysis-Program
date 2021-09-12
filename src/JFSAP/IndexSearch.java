/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//import org.jsoup.Jsoup;//String s = Jsoup.parse("&lt;Fran&ccedil;ais&gt;").text();

/**
 *
 * @author Morgan Higginbotham
 */
public class IndexSearch {
    
    private static String[] results = new String[2];
    
    /*public static boolean guessSearch(int year, int quarter, String company)
    {
        String[] searches = {"Inc", "Inc.", "Co", "Corp", "L.P."};
        
        String[] result = searchFile(year, quarter, company);
        
        if (result[0] == "NULL")
        {
            for (int i = 0; i < searches.length && result[0] == "NULL"; i++)
            {
                System.out.println(searches[i]);
                result = searchFile(year, quarter, company + " " + searches[i]);
                if (i + 1 == searches.length)
                {
                    return false;
                }
                
            }
            
            results = result;
            return true;
        }
        
        results = result;
        return true;
    }
    
    public static String[] getResults()
    {
        return results;
    }*/
    
    public static String[] searchFile(int year, int quarter, String formTypeRequest, String company)
    {
        String[] failed = {"NULL","NULL"};
        ArrayList<String> strings = new <String>ArrayList();
        ArrayList<String> companyarray = new <String>ArrayList();
        String companyAllCaps = company.toUpperCase();
        String current = "";
        char currentchar = 'a';
        boolean eof = false;
        
        out:
        for (int i = 0; i < companyAllCaps.length(); i++)
        {
            currentchar = companyAllCaps.charAt(i);
            
            if (currentchar == ' ')
            {
                while(currentchar == ' ')
                {
                    i++;
                    if (i >= companyAllCaps.length())
                    {
                        break out;
                    }
                    currentchar = companyAllCaps.charAt(i);
                }
                companyarray.add(current);
                current = "";
                current += currentchar;
            }
            else
            {
                current += currentchar;
            }
        }
        companyarray.add(current);
        
        current = "";
        FileReader file;
        
        try {
            file = new FileReader("Index\\" + year + "\\Q" + quarter + "\\companyform.idx");
            
            do
            {
                try 
                {
                    currentchar = (char)(file.read());
                } catch (IOException ex) {
                    Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
                    eof = true;
                    continue;
                }

                if ((int)(currentchar) == Character.MAX_VALUE)
                {
                    eof = true;
                    continue;
                }
                
                if (currentchar == ' ')
                {
                    do
                    {
                        try 
                        {
                            currentchar = (char)(file.read());
                        } catch (IOException ex) {
                            //Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
                            eof = true;
                            continue;
                        }
                    }
                    while(currentchar == ' ' || currentchar == '\n' || currentchar == '\r');
                    
                    strings.add(current.toUpperCase());
                    current = "";
                }
                if (currentchar != '\n' || currentchar != '\r')
                {
                    current += currentchar;
                }
            }
            while(!eof);
            
            try 
            {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String stringValue;
            String stringSecondValue;
            String url;
            String formType = "";
            boolean checking = false;
            boolean getURL = false;
            int a = 0;
            int pasti = 0;
            
            for (int i = 0; i < strings.size(); i++)
            {
                stringValue = strings.get(i);
                outtwo:
                if (i > 0)
                {
                    stringSecondValue = strings.get(i - 1);
                    
                    if (stringSecondValue.length() >= 11)
                    {
                        if(stringSecondValue.substring(0, 11).equals("EDGAR/DATA/"))
                        {
                            formType = stringValue;
                        }
                        
                    }
                }
                if (getURL == false)
                {
                    if (i - pasti > 1)
                    {
                        a = 0;
                    }
                    
                    if(stringValue.equals(companyarray.get(a)))
                    {
                        checking = true;
                        a++;
                        pasti = i;
                        
                        if(a == companyarray.size())
                        {
                            getURL = true;
                            a = 0;
                        }
                    }
                }
                else
                {
                    if (stringValue.length() >= 11)
                    {
                        if(stringValue.substring(0, 11).equals("EDGAR/DATA/"))
                        {
                            if (formTypeRequest.equals(formType))
                            {
                                url = stringValue.toLowerCase();
                                String[] finish = {url, formType};
                                return finish;
                            }
                            else
                            {
                                getURL = false;
                            }                            
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) 
        {
            //Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
            return failed;
        }
        return failed;
    }
    
    public static void searchHTMLJSOUP(String company, String fileType, int year)
    {
        String location = "Index\\" + fileType + "\\" + year;
        String companyTwo = company.replaceAll("\\s+","").toUpperCase();
        ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> newTables = new ArrayList<ArrayList<ArrayList<String>>>();
        
        File input = new File(location + "\\" + companyTwo + fileType + ".htm");
        
        String cellText = "";
        short tableCount = 0;
        short rowCount = 0;
                
        try
        {
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements allTables = doc.select("table");
            
            for (Element table : allTables)
            {
                Elements rows = table.select("tr");
                tables.add(new ArrayList<ArrayList<String>>());

                for (Element row : rows)
                {
                    Elements cells = row.select("td");
                    tables.get(tableCount).add(new ArrayList<String>());

                    for (Element cell : cells)
                    {
                        cellText = cell.text();
                        //cellText = cellText.replaceAll("\\(", "-");
                        //cellText = cellText.replaceAll("[^0-9.a-z.A-Z.-.\\s]", "");
                        
                        if (containsPattern(cellText))
                        {
                            cellText = cellText.replaceAll("[^0-9.a-z.A-Z.\\s]", "");
                            cellText = "-" + cellText;
                        }
                        else
                        {
                            cellText = cellText.replaceAll("[^0-9.a-z.A-Z.\\s]", "");
                        }
                        
                        if (cellText.length() >= 1)
                        {
                            tables.get(tableCount).get(rowCount).add(cellText);
                        }
                    }
                    rowCount++;
                }
                
                tableCount++;
                rowCount = 0;
            }
            
            rowCount = 0;
            tableCount = 0;
            
            for (int tab = 0; tab < tables.size(); tab++)
            { 
                newTables.add(new ArrayList<ArrayList<String>>());
                
                for (int row = 0; row < tables.get(tab).size(); row++)
                {
                    if (tables.get(tab).get(row).size() >= 3)
                    {
                        if (correctRow(tables.get(tab).get(row).get(0), tables.get(tab).get(row).get(1), tables.get(tab).get(row).get(2)))
                        {                            
                            newTables.get(tab).add(new ArrayList<String>());
                            
                            for (int cell = 0; cell < tables.get(tab).get(row).size(); cell++)
                            {                                
                                newTables.get(tab).get(rowCount).add(tables.get(tab).get(row).get(cell));
                            }
                            
                            rowCount++;
                        }
                    }
                }
                
                rowCount = 0;
            }
            
            tables = new ArrayList<ArrayList<ArrayList<String>>>();
            
            tableCount = 0;
            rowCount = 0;
            
            for (int tab = 0; tab < newTables.size(); tab++)
            { 
                
                if (newTables.get(tab).size() >= 3)
                {                    
                    tables.add(new ArrayList<ArrayList<String>>());
                
                    for (int row = 0; row < newTables.get(tab).size(); row++)
                    {                 
                        tables.get(tableCount).add(new ArrayList<String>());

                        for (int cell = 0; cell < newTables.get(tab).get(row).size(); cell++)
                        {                                
                            tables.get(tableCount).get(row).add(newTables.get(tab).get(row).get(cell));
                        }
                    }
                    tableCount++;
                }
            }
            
            /*for (int tab = 0; tab < tables.size(); tab++)
            {                
                for (int y = 0; y < tables.get(tab).size(); y++)
                {
                    for (int x = 0; x < tables.get(tab).get(y).size(); x++)
                    {
                        System.out.print(tables.get(tab).get(y).get(x) + "///");
                    }
                    System.out.println();
                }
                System.out.println("\n-----------------------------------------");
            }*/
            
        } catch (IOException ex)
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new TableFrame(tables, year, 0, company);
    }
    
    public static void searchHTMLJSOUP(String company, String fileType, int year, int quarter)
    {
        String location = "Index\\" + fileType + "\\" + year;
        String companyTwo = company.replaceAll("\\s+","").toUpperCase();
        ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<ArrayList<String>>> newTables = new ArrayList<ArrayList<ArrayList<String>>>();
        
        File input = new File(location + "\\" + quarter + companyTwo + fileType + ".htm");
        
        String cellText = "";
        short tableCount = 0;
        short rowCount = 0;
                
        try
        {
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements allTables = doc.select("table");
            
            for (Element table : allTables)
            {
                Elements rows = table.select("tr");
                tables.add(new ArrayList<ArrayList<String>>());

                for (Element row : rows)
                {
                    Elements cells = row.select("td");
                    tables.get(tableCount).add(new ArrayList<String>());

                    for (Element cell : cells)
                    {
                        cellText = cell.text();
                        
                        if (containsPattern(cellText))
                        {
                            cellText = cellText.replaceAll("[^0-9.a-z.A-Z.\\s]", "");
                            cellText = "-" + cellText;
                        }
                        else
                        {
                            cellText = cellText.replaceAll("[^0-9.a-z.A-Z.\\s]", "");
                        }

                        if (cellText.length() >= 1)
                        {
                            tables.get(tableCount).get(rowCount).add(cellText);
                        }
                    }
                    rowCount++;
                }
                
                tableCount++;
                rowCount = 0;
            }
            
            rowCount = 0;
            tableCount = 0;
            
            for (int tab = 0; tab < tables.size(); tab++)
            { 
                newTables.add(new ArrayList<ArrayList<String>>());
                
                for (int row = 0; row < tables.get(tab).size(); row++)
                {
                    if (tables.get(tab).get(row).size() >= 3)
                    {
                        if (correctRow(tables.get(tab).get(row).get(0), tables.get(tab).get(row).get(1), tables.get(tab).get(row).get(2)))
                        {                            
                            newTables.get(tab).add(new ArrayList<String>());
                            
                            for (int cell = 0; cell < tables.get(tab).get(row).size(); cell++)
                            {                                
                                newTables.get(tab).get(rowCount).add(tables.get(tab).get(row).get(cell));
                            }
                            
                            rowCount++;
                        }
                    }
                }
                
                rowCount = 0;
            }
            
            tables = new ArrayList<ArrayList<ArrayList<String>>>();
            
            tableCount = 0;
            rowCount = 0;
            
            for (int tab = 0; tab < newTables.size(); tab++)
            { 
                
                if (newTables.get(tab).size() >= 3)
                {                    
                    tables.add(new ArrayList<ArrayList<String>>());
                
                    for (int row = 0; row < newTables.get(tab).size(); row++)
                    {                 
                        tables.get(tableCount).add(new ArrayList<String>());

                        for (int cell = 0; cell < newTables.get(tab).get(row).size(); cell++)
                        {                                
                            tables.get(tableCount).get(row).add(newTables.get(tab).get(row).get(cell));
                        }
                    }
                    tableCount++;
                }
            }
            
            /*for (int tab = 0; tab < tables.size(); tab++)
            {                
                for (int y = 0; y < tables.get(tab).size(); y++)
                {
                    for (int x = 0; x < tables.get(tab).get(y).size(); x++)
                    {
                        System.out.print(tables.get(tab).get(y).get(x) + "///");
                    }
                    System.out.println();
                }
                System.out.println("\n-----------------------------------------");
            }*/
            
        } catch (IOException ex)
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new TableFrame(tables, year, quarter, company);
    }
    
    public static boolean correctRow(String one, String two, String three)
    {
        if (!one.matches(".*[a-zA-Z]+.*"))
        {
            return false;
        }
        else if(!two.matches(".*[0-9]+.*"))
        {
            return false;
        }
        else if(!three.matches(".*[0-9]+.*"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    private static boolean containsPattern(String str)
    {
        for (int x = 1; x < str.length(); x++)
        {
            if (str.charAt(x - 1) == '(' && Character.isDigit(str.charAt(x)))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /*public static void searchHTML(String company, String fileType, int year)
    {
        String location = "Index\\" + fileType + "\\" + year;
        String companyTwo = company.replaceAll("\\s+","").toUpperCase();
        FileReader file;
        
        ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<String> titles = new ArrayList<String>();
        int tablesIndex = 0;
        int tablesYIndex = 0; 
        //[for each table][rows(y)][colum (x)]
        try {
            file = new FileReader(location + "\\" + companyTwo + fileType + ".htm");
            
            long count = 0;
            int readInt = file.read();
            char readChar = (char)(readInt);
            String segment = "";
            String title = "";
            boolean table = false;
            boolean tr = false;
            boolean td = false;
            boolean storingData = false;
            boolean titleStart = false;
            String cacheTitle = "NULL";
            String strs;
            String realTitle = "";
            boolean font = false;
            
            String data = "";
        
            while(readInt != -1)
            {   
                if (readChar == '<')
                {
                    //read chars until reach '>'
                    readInt = file.read();
                    readChar = (char)(readInt);
                    
                    while(readChar != '>')
                    {
                        segment += readChar;
                        readInt = file.read();
                        readChar = (char)(readInt);
                    }
                    
                    font = (segment.length() >= 4 && segment.substring(0, 4).equals("font")) || (segment.length() >= 5 && segment.substring(1, 5).equals("font"));
                    
                    strs = searchKeyWord(title);

                    if (!strs.equals("NULL"))
                    {
                        /*System.out.println(strs);
                        System.out.println(title);
                        cacheTitle = strs;
                        //System.out.println(cacheTitle);
                    }
                    //System.out.println(segment);
                    
                    if (font)
                    {
                    
                    }
                    else if (segment.length() >= 5 && segment.substring(0,5).equals("table"))
                    {
                        
                        if (cacheTitle.equals("NULL"))
                        {
                            titles.add(realTitle);
                        }
                        else
                        {
                            titles.add(cacheTitle);
                        }
                        //System.out.println(cacheTitle);
                             
                        table = true;
                        tables.add(new ArrayList<ArrayList<String>>());
                    }
                    else if (table)
                    {
                        if (segment.length() >= 2 && segment.substring(0,2).equals("tr"))
                        {
                            tr = true;
                            tables.get(tablesIndex).add(new ArrayList<String>());
                        }
                        else if (tr)
                        {
                            if (segment.length() >= 2 && segment.substring(0,2).equals("td"))
                            {
                                td = true;
                            }
                            else if(segment.length() >= 3 && segment.substring(0,3).equals("/tr"))
                            {
                                tr = false;
                                //System.out.println();
                                tablesYIndex++;
                            }
                            else if(td)
                            {
                                if (segment.length() >= 3 && segment.substring(0,3).equals("/td"))
                                {
                                    td = false;
                                    data = data.replace("(", "");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    
                                    /*boolean foundIt;
                                    String replacer = "";
                                    
                                    for (int ss = 0; ss < data.length(); ss++)
                                    {
                                        if (data.charAt(ss) == '&')
                                        {
                                            if (data.substring(ss, ss + 1).equals("&#"))
                                            {
                                                replacer += "&#";
                                                for (int gg = ss + 2; gg < data.length(); gg++)
                                                {
                                                    switch(data.charAt(gg))
                                                    {
                                                        case(';'): 
                                                        default: data.
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                    if (data.length() <= 2)
                                    {
                                        
                                    }
                                    else
                                    {
                                        //System.out.print(data + "\n");
                                        tables.get(tablesIndex).get(tablesYIndex).add(data);
                                    }
                                    data = "";
                                    //tables.get(tablesIndex).get(tablesYIndex).add(data);
                                }
                                /*else if (data.length() > 0)
                                {
                                    if (!data.contains("&#"))
                                    {
                                        tables.get(tablesIndex).get(tablesYIndex).add(data);
                                        //int index = data.indexOf("&#");
                                        //System.out.print(data + " ");
                                    }

                                    data = "";
                                }
                            }
                            else
                            {
                                
                            }
                        }
                        else if (segment.length() >= 6 && segment.substring(0,6).equals("/table"))
                        {
                            table = false;
                            tablesYIndex = 0;
                            tablesIndex++;
                            cacheTitle = "NULL";
                            //System.out.println("-----------------------------------------");
                        }
                    }
                    
                    readInt = file.read();
                    readChar = (char)(readInt);
                    
                    segment = "";
                    
                    if (!font)
                    {
                        titleStart = false;
                        title = title.toLowerCase();
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        
                        if (title.length() < 90 && title.length() > 5 && !title.equals("table of contents"))
                        {
                            realTitle = title;
                        }
                    }
                }
                else if (readChar == '\n')
                {
                    readInt = file.read();
                    readChar = (char)(readInt);
                    //count++;
                }
                else if(td)
                {
                    data += readChar;
                    readInt = file.read();
                    readChar = (char)(readInt);
                    //store data
                }
                else
                {
                    if (!titleStart)
                    {
                        titleStart = true;
                        title = "";
                    }
                    title += readChar;
                    readInt = file.read();
                    readChar = (char)(readInt);
                }
            }
            
            file.close();
            new TableFrame(tables, titles);
            
            /*for (int tab = 0; tab < tables.size(); tab++)
            {
                System.out.println(titles.get(tab));
                
                for (int y = 0; y < tables.get(tab).size(); y++)
                {
                    for (int x = 0; x < tables.get(tab).get(y).size(); x++)
                    {
                        System.out.print(tables.get(tab).get(y).get(x) + "///");
                        
                        if (tab == 0 && y == 0 && x == 0)
                        {
                            System.out.println("hello in the field...");
                        } 
                    }
                    System.out.println();
                }
                System.out.println("\n-----------------------------------------");
            }*/
            
            /*for (String str : titles)
            {
                System.out.println(str);
            }
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void searchHTML(String company, String fileType, int year, int quarter)
    {
        String location = "Index\\" + fileType + "\\" + year;
        String companyTwo = company.replaceAll("\\s+","").toUpperCase();
        FileReader file;
        
        ArrayList<ArrayList<ArrayList<String>>> tables = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<String> titles = new ArrayList<String>();
        int tablesIndex = 0;
        int tablesYIndex = 0; 
        //[for each table][rows(y)][colum (x)]
        try {
            file = new FileReader(location + "\\" + quarter + companyTwo + fileType + ".htm");
            
            long count = 0;
            int readInt = file.read();
            char readChar = (char)(readInt);
            String segment = "";
            String title = "";
            boolean table = false;
            boolean tr = false;
            boolean td = false;
            boolean storingData = false;
            boolean titleStart = false;
            String cacheTitle = "NULL";
            String strs;
            String realTitle = "";
            boolean font = false;
            
            String data = "";
        
            while(readInt != -1)
            {   
                if (readChar == '<')
                {
                    //read chars until reach '>'
                    readInt = file.read();
                    readChar = (char)(readInt);
                    
                    while(readChar != '>')
                    {
                        segment += readChar;
                        readInt = file.read();
                        readChar = (char)(readInt);
                    }
                    
                    font = (segment.length() >= 4 && segment.substring(0, 4).equals("font")) || (segment.length() >= 5 && segment.substring(1, 5).equals("font"));
                    
                    strs = searchKeyWord(title);

                    if (!strs.equals("NULL"))
                    {
                        /*System.out.println(strs);
                        System.out.println(title);
                        cacheTitle = strs;
                        //System.out.println(cacheTitle);
                    }
                    //System.out.println(segment);
                    
                    if (font)
                    {
                    
                    }
                    else if (segment.length() >= 5 && segment.substring(0,5).equals("table"))
                    {
                        
                        if (cacheTitle.equals("NULL"))
                        {
                            titles.add(realTitle);
                        }
                        else
                        {
                            titles.add(cacheTitle);
                        }
                        //System.out.println(cacheTitle);
                             
                        table = true;
                        tables.add(new ArrayList<ArrayList<String>>());
                    }
                    else if (table)
                    {
                        if (segment.length() >= 2 && segment.substring(0,2).equals("tr"))
                        {
                            tr = true;
                            tables.get(tablesIndex).add(new ArrayList<String>());
                        }
                        else if (tr)
                        {
                            if (segment.length() >= 2 && segment.substring(0,2).equals("td"))
                            {
                                td = true;
                            }
                            else if(segment.length() >= 3 && segment.substring(0,3).equals("/tr"))
                            {
                                tr = false;
                                //System.out.println();
                                tablesYIndex++;
                            }
                            else if(td)
                            {
                                if (segment.length() >= 3 && segment.substring(0,3).equals("/td"))
                                {
                                    td = false;
                                    data = data.replace("(", "");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    data = data.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                                    
                                    /*boolean foundIt;
                                    String replacer = "";
                                    
                                    for (int ss = 0; ss < data.length(); ss++)
                                    {
                                        if (data.charAt(ss) == '&')
                                        {
                                            if (data.substring(ss, ss + 1).equals("&#"))
                                            {
                                                replacer += "&#";
                                                for (int gg = ss + 2; gg < data.length(); gg++)
                                                {
                                                    switch(data.charAt(gg))
                                                    {
                                                        case(';'): 
                                                        default: data.
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                    if (data.length() <= 2)
                                    {
                                        
                                    }
                                    else
                                    {
                                        //System.out.print(data + "\n");
                                        tables.get(tablesIndex).get(tablesYIndex).add(data);
                                    }
                                    data = "";
                                    //tables.get(tablesIndex).get(tablesYIndex).add(data);
                                }
                                /*else if (data.length() > 0)
                                {
                                    if (!data.contains("&#"))
                                    {
                                        tables.get(tablesIndex).get(tablesYIndex).add(data);
                                        //int index = data.indexOf("&#");
                                        //System.out.print(data + " ");
                                    }

                                    data = "";
                                }
                            }
                            else
                            {
                                
                            }
                        }
                        else if (segment.length() >= 6 && segment.substring(0,6).equals("/table"))
                        {
                            table = false;
                            tablesYIndex = 0;
                            tablesIndex++;
                            cacheTitle = "NULL";
                            //System.out.println("-----------------------------------------");
                        }
                    }
                    
                    readInt = file.read();
                    readChar = (char)(readInt);
                    
                    segment = "";
                    
                    if (!font)
                    {
                        titleStart = false;
                        title = title.toLowerCase();
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        title = title.replaceAll("&#[0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z][0-9.a-z.A-Z];", " ");
                        
                        if (title.length() < 90 && title.length() > 5 && !title.equals("table of contents"))
                        {
                            realTitle = title;
                        }
                    }
                }
                else if (readChar == '\n')
                {
                    readInt = file.read();
                    readChar = (char)(readInt);
                    //count++;
                }
                else if(td)
                {
                    data += readChar;
                    readInt = file.read();
                    readChar = (char)(readInt);
                    //store data
                }
                else
                {
                    if (!titleStart)
                    {
                        titleStart = true;
                        title = "";
                    }
                    title += readChar;
                    readInt = file.read();
                    readChar = (char)(readInt);
                }
            }
            
            file.close();
            
            new TableFrame(tables, titles);
            
            /*for (int tab = 0; tab < tables.size(); tab++)
            {
                System.out.println(titles.get(tab));
                
                for (int y = 0; y < tables.get(tab).size(); y++)
                {
                    for (int x = 0; x < tables.get(tab).get(y).size(); x++)
                    {
                        System.out.print(tables.get(tab).get(y).get(x) + "///");
                        
                        if (tab == 0 && y == 0 && x == 0)
                        {
                            System.out.println("hello in the field...");
                        } 
                    }
                    System.out.println();
                }
                System.out.println("\n-----------------------------------------");
            }
            
            /*for (String str : titles)
            {
                System.out.println(str);
            }
        } catch (FileNotFoundException ex) 
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(IndexSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public static String searchKeyWord(String str)
    {
        String[] options = {"balance sheet", "off balance sheet", "financial condition", "statements of income", "comprehensive income", "income statement", "statements of income", "cash flow", "inventories", "accrued liabilities", "fair value measurements", "long term debt", "income taxes", "common stock", "stock based compensation", "earnings per share", "risk management", "derivatives", "other comprehensive income", "operating segment", "results of operations", "operating results", "futures", "grosss margin", "administractive expense", "selling expense", "other (income) expense", "other expenses", "other income expense", "income tax", "operating segment", "north america", "western europe", "europe", "china", "japan", "emerging market", "global brand", "converse", "corporate", "obligations"};//some have - 
        str = str.replaceAll("-", " ").toLowerCase();
        
        for (String strs : options)
        {
            if (str.length() < 90 && str.contains(strs))
            {
                return strs;
            }
        }
        
        return "NULL";
    }
}
