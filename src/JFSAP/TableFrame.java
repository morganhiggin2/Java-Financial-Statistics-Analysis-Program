/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Morgan Higginbotham
 */
public class TableFrame extends JFrame
{
    
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    
    ArrayList<ArrayList<ArrayList<String>>> tables;
    int year;
    String company;
    int quarter;
    //String statementStrings[] = {"sales", "net income", "income tax", "operating income", "interest payments", "deferred income tax", "bullshit", "selling, general, and administrative", "depreciation"};
    //int statementValues[][];//one for each year
    //String[] options = {"deprctiation", "selling, general and administrative", "provision for income tax", "deferred income tax", "income tax", "operating income", "net income", "sales", "revenue", "interest expense"};//put income tax before income, and other stuff like that
    //int[] indexInStatements = {9, 8, 7, 5, 2, 3, 1, 0, 0, 4};
    String statementStrings[];
    int statementValues[][];
    int indexInStatements[];
    String options[];
    
    FinancialSegment fs;
    Ratio ratiosYearOne;
    Ratio ratiosYearTwo;
    
    public TableFrame(ArrayList<ArrayList<ArrayList<String>>> ta, int year, int quarter, String company)
    {
        super("Financials");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.year = year;
        this.company = company;
        this.quarter = quarter;
        
        if (quarter == 0)
        {
            setTitle(company.toUpperCase() + " " + year);
        }
        else
        {
            setTitle(company.toUpperCase() + " Q" + quarter + " " +  year);
        }
        
        fs = new FinancialSegment();        
        
        //fs.addFinancialSegment("bullshit");
        
        //poop
        //fs.addFinancialSegment("poop");
        //fs.addFinancialOption("liabilities and equity", "poop");
        
        //INCOME STATEMENT
        //revenue
        fs.addFinancialSegment("revenue", true);
        fs.addFinancialOption("total revenues", "revenue");
        fs.addFinancialOption("revenues", "revenue");
        fs.addFinancialOption("operating revenue", "revenue");
        fs.addFinancialOption("sales", "revenue");
        fs.addFinancialOption("revenue", "revenue");
        
        //selling, general, and administrative
        fs.addFinancialSegment("selling, general, and administrative", true);
        
        fs.addFinancialOption("selling general and administrative", "selling, general, and administrative");
        fs.addFinancialOption("general selling and administrative", "selling, general, and administrative");
        fs.addFinancialOption("selling general administrative", "selling, general, and administrative");
        fs.addFinancialOption("selling and administrative expense", "selling, general, and administrative");
        fs.addFinancialOption("salaries general and administrative expenses", "selling, general, and administrative");
        
        //depreciation (down in balance sheet area)
        fs.addFinancialSegment("depreciation charges", true);
        fs.addFinancialOption("depreciation charges", "depreciation charges");
        fs.addFinancialOption("depreciation and amortization", "depreciation charges");
        fs.addFinancialOption("depreciation", "depreciation charges");
        
        //operating income
        fs.addFinancialSegment("operating income", true);
        fs.addFinancialOption("operating income", "operating income");
        fs.addFinancialOption("income from operations", "operating income");
        
        //fs.addFinancialOption("accumulated depreciation and amortization", "reserve for depreciation");
        
        //interest payments (interest charges)
        fs.addFinancialSegment("interest payments", true);
        fs.addFinancialOption("cash payed for payments", "interest payments");
        fs.addFinancialOption("interest expense", "interest payments");
        fs.addFinancialOption("interest payments", "interest payments");
        fs.addFinancialOption("interest income", "interest payments");
        //fs.addFinancialOption("repayments", company);
        
        //payments for dividends
        fs.addFinancialSegment("payments for dividends", true);
        fs.addFinancialOption("dividend payments", "payments for dividends");
        fs.addFinancialOption("payments for dividends", "payments for dividends");
        fs.addFinancialOption("dividends", "payments for dividends");
        fs.addFinancialOption("dividends payed", "payments for dividends");
        
        //income tax
        fs.addFinancialSegment("income tax", true);
        fs.addFinancialOption("income tax", "income tax");
                    
        //net income
        fs.addFinancialSegment("net income", false);
        fs.addFinancialOption("net income", "net income");
        fs.addFinancialOption("net loss", "net income");
        fs.addFinancialOption("net earnings", "net income");
                
        fs.endIncomeStatementCount();
        
        //BALANCE SHEET
        //cash and marketable securites (current assets)
        fs.addFinancialSegment("cash and marketable securites", true);
        fs.addFinancialOption("cash", "cash and marketable securites");
        fs.addFinancialOption("cash and marketable securites", "cash and marketable securites");
        fs.addFinancialOption("cash and cash equivalents", "cash and marketable securites");
        
        //accounts / notes recivable
        fs.addFinancialSegment("accounts receivable", true);
        fs.addFinancialOption("accounts receivable", "accounts receivable");
        fs.addFinancialOption("receivables", "accounts receivable");
        
        //inventory
        fs.addFinancialSegment("inventory", true);
        fs.addFinancialOption("inventory", "inventory");
        fs.addFinancialOption("inventories", "inventory");
        
        //current assets
        fs.addFinancialSegment("current assets", true);
        fs.addFinancialOption("current assets", "current assets");
                
        //reserve for depreciation and depletion
        //fs.addFinancialSegment("reserve for depreciation");
        
        //property, plant, and equipment
        fs.addFinancialSegment("plant property and equipment", true);
        fs.addFinancialOption("plant property and equipment", "plant property and equipment");
        fs.addFinancialOption("property plant and equipment", "plant property and equipment");
        fs.addFinancialOption("property plants and equipment", "plant property and equipment");
        fs.addFinancialOption("property", "plant property and equipment");
        fs.addFinancialOption("property and equipment", "plant property and equipment");
        
        //total assets
        fs.addFinancialSegment("total assets", true);
        fs.addFinancialOption("total assets", "total assets");
        
        //current liabilites
        fs.addFinancialSegment("current liabilities", true);
        fs.addFinancialOption("current liabilities", "current liabilities");
        
        //accounts payable and accured liabilities
        fs.addFinancialSegment("accounts payable", true);
        fs.addFinancialOption("accounts payable", "accounts payable");
        
        //bond interest accrued
        //fs.addFinancialSegment("interest accrued", true);
        //fs.addFinancialOption("interest accrued", "interest accrued");
        
        //dividend payable
        //fs.addFinancialSegment("dividends payable", true);
        //fs.addFinancialOption("dividends payable", "dividends payable");
        
        //long-term debt
        fs.addFinancialSegment("long-term debt", true);
        fs.addFinancialOption("longterm debt", "long-term debt");
        fs.addFinancialOption("long term debt", "long-term debt");
        fs.addFinancialOption("non current liabilities", "long-term debt");
        
        //current assets
        fs.addFinancialSegment("total liabilities", true);
        fs.addFinancialOption("total liabilities", "total liabilities");
        
        //common stock
        fs.addFinancialSegment("common stock", true);
        fs.addFinancialOption("common stock", "common stock");
        
        //preferred stock
        fs.addFinancialSegment("preferred stock", true);
        fs.addFinancialOption("preferred stock", "preferred stock");
        
        //retained earnings
        //fs.addFinancialSegment("retained earnings", true);
        //fs.addFinancialOption("retained earnings", "retained earnings");
           
        //deferred income tax
        //fs.addFinancialSegment("deferred income tax", true);
        //fs.addFinancialOption("deferred income tax", "deferred income tax");
        //fs.addFinancialOption("deferred income taxes", "deferred income tax");
        
        //total liabilities and equity
        //fs.addFinancialSegment("total liabilities and equity");
        //fs.addFinancialOption("total liabilities and equity", "total liabilities and equity");
        //fs.addFinancialOption("total liabilities and shareholders' equity", "total liabilities and equity");
        
        //equity
        fs.addFinancialSegment("equity", false);
        fs.addFinancialOption("equity", "equity");
                
        fs.convert();
        
        statementStrings = fs.getFinancialSegments();
        options = fs.getFinancialOptions();
        indexInStatements = fs.getFianncialIDs();
        
        statementValues = new int[2][statementStrings.length];//mabey 3  
        
        for (int x = 0; x < statementValues.length; x++)
        {
            for (int y = 0; y < statementStrings.length; y++)
            {
                statementValues[x][y] = -1;
            }
        }
        
        tables = ta;
        
        makeGUI();
        
        setVisible(true);
    }
    
    public void getStatements()
    {
        //System.out.println(containsForStatements(tables.get(3).get(2).get(0)));
        
        int indexOfStatements;
        String value;
        int intValue;
        boolean isPositive[] = fs.getPositiveArray();
        for (int table = 0; table < tables.size(); table++)
        {
            for (int y = 0; y < tables.get(table).size(); y++)
            {
                indexOfStatements = containsForStatements(tables.get(table).get(y).get(0));
                
                /*if (tables.get(table).get(y).get(0).equals("Net income"))
                {
                    System.out.println(indexOfStatements);
                }*/
                
                
                /*if (table == 3 && y == 2)
                {
                    System.out.print("here");
                }*/
                
                if (indexOfStatements != -1 && tables.get(table).get(y).size() >= 3)
                {
                    //System.out.println(tables.get(table).get(y).get(0));
                    for (int x = 1; x < 3; x++)//2 years and title
                    {
                        try
                        {
                            if (!tables.get(table).get(y).get(1).equals(tables.get(table).get(y).get(2)))
                            {
                                value = tables.get(table).get(y).get(x);
                                                            
                                //value = keepNumbers(value);
                                //System.out.println(value + " " + statementValues[x - 1][indexOfStatements]);
                                intValue = Integer.parseInt(value);
                                
                                if (statementValues[x - 1][indexOfStatements] == -1)
                                {                                  
                                    if (isPositive[indexOfStatements])
                                    {
                                        statementValues[x - 1][indexOfStatements] = Math.abs(intValue);
                                    }
                                    else
                                    {
                                        statementValues[x - 1][indexOfStatements] = intValue;
                                    }
                                }
                                else if(intValue > statementValues[x - 1][indexOfStatements] && tables.get(table).get(y).get(0).length() <= 35)
                                {
                                    if (isPositive[indexOfStatements])
                                    {
                                        statementValues[x - 1][indexOfStatements] = Math.abs(intValue);
                                    }
                                    else
                                    {
                                        statementValues[x - 1][indexOfStatements] = intValue;
                                    }
                                }
                            }
                        }
                        catch(Exception e)
                        {
                            //statementValues[x - 1][indexOfStatements] = -1;
                        }
                    }
                }
                /*if (indexOfStatements != -1 && tables.get(table).get(y).size() >= 2)
                {
                    statementValues[1][indexOfStatements] = 0;
                }*/
            }
        }
        
        //System.out.println("---------------------------------------------------------------------------------------------------------------");
        
        /*for (int x = 0; x < statementStrings.length; x++)
        {
            System.out.println(statementStrings[x] + " $" + statementValues[0][x]);
        }*/
    }
    
    public int containsForStatements(String str)
    {
        str = str.replaceAll("-", " ").toLowerCase();
        
        for (int x = 0; x < options.length; x++)
        {
            if (str.contains(options[x]))
            {
                return indexInStatements[x];
            }
        }
        
        return -1;
    }
    
    public static String keepNumbers(String str)
    {
        int charValue;
        String newStr = "";
        for (int x = 0; x < str.length(); x++)
        {
            charValue = (int)(str.charAt(x));
            if (charValue >= 48 && charValue <= 57)
            {
                newStr += str.charAt(x);
            }
        }
        return newStr;
    }
    
    /*public void cleanTableArray()
    {
        
        ArrayList<ArrayList<ArrayList<String>>> tablesNew = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<String> titlesNew = new ArrayList<String>();
        
        ArrayList<ArrayList<ArrayList<String>>> tablesNoEmpty= new ArrayList<ArrayList<ArrayList<String>>>();
        
        String[] headers;
        
        //System.out.println(tables.size());
        for (int table = 0; table < tables.size(); table++)
        {
            tablesNoEmpty.add(new ArrayList<ArrayList<String>>());
            for (int y = 0; y < tables.get(table).size(); y++)
            {
                if (tables.get(table).get(y).size() != 0)
                {
                    tablesNoEmpty.get(table).add(tables.get(table).get(y));
                }
            }
        }
        //System.out.println(tablesNoEmpty.size() + " " + tables.size());
        
        String keyWord;
        for (int table = 0; table < tablesNoEmpty.size(); table++)
        {
            if (tablesNoEmpty.get(table).size() > 2)
            {
                keyWord = IndexSearch.searchKeyWord(tablesNoEmpty.get(table).get(0).get(0));
                //System.out.println(" " + "made it");
                //tablesNew.add(tablesNoEmpty.get(table));
                if (table > 0 && !keyWord.equals("NULL"))
                {
                    titlesNew.add(keyWord);
                }
                else
                {
                    titlesNew.add(titles.get(table));
                }
            }
            else
            {
                //System.out.println(" " + "nope");
            }
        }

        boolean hasNumber = false;
        
        for (int table = 0; table < tablesNoEmpty.size(); table++)
        {
            hasNumber = false;
            for (int y = 0; y < tablesNoEmpty.get(table).size(); y++)
            {
                if (tablesNoEmpty.get(table).get(y).size() >= 2 && IO.containsNumbers(tablesNoEmpty.get(table).get(y).get(1), 3))
                {
                    hasNumber = true;
                }
            }
            
            if (hasNumber)
            {
                tablesNew.add(tablesNoEmpty.get(table));
            }
        }
        
        tables.clear(); //ArrayList<ArrayList<ArrayList<String>>> ta, ArrayList<String> tit
        titles.clear();
        
        for (int tab = 0; tab < tablesNew.size(); tab++)
        {
            tables.add(tablesNew.get(tab));
            
            if (tab >= titlesNew.size())
            {
                titles.add("null");
            }
            else
            {
                titles.add(titlesNew.get(tab));
            }
        }
        
        for (int tab = 0; tab < tables.size(); tab++)
        {
            System.out.println(titles.get(tab));

            for (int y = 0; y < tables.get(tab).size(); y++)
            {
                if (tables.get(tab).get(y).size() != 0)
                {
                    for (int x = 0; x < tables.get(tab).get(y).size(); x++)
                    {
                        System.out.print(tables.get(tab).get(y).get(x) + "///");
                    }
                    System.out.println();
                }
            }
            System.out.println("-----------------------------------------");
        }
    }*/
    
    public void makeGUI()
    {
        getStatements();
        
        ratiosYearOne = new Ratio(statementStrings, statementValues[0]);
        ratiosYearTwo = new Ratio(statementStrings, statementValues[1]);
        
        ratiosYearOne.calculateTotalDebt(statementValues[0]);
        ratiosYearOne.calculateLongTermDebt(statementValues[0]);
        ratiosYearTwo.calculateTotalDebt(statementValues[1]);
        ratiosYearTwo.calculateLongTermDebt(statementValues[1]);
        
        //this is for creating the ratio table
        String ratiosColums[] = {"Ratio Name", String.valueOf(year), String.valueOf(year - 1)};
        boolean percentOrDecimal[] = Ratio.percentOrDecimal;
        String ratioNames[] = Ratio.ratios;
        float ratioCalculations[][] = {ratiosYearOne.getCalculations(), ratiosYearTwo.getCalculations()};
        String stringRatioCalculations[][] = new String[ratioCalculations[0].length][ratioCalculations.length + 1];
        
        //creating the data value array (backwards opertation than expected)
        for (int y = 0; y < ratioCalculations.length + 1; y++)
        {
            for (int x = 0; x < ratioNames.length; x++)
            {
                if (y == 0)
                {
                    stringRatioCalculations[x][y] = ratioNames[x];
                }
                else
                {                  
                    if (percentOrDecimal[x])
                    {
                        stringRatioCalculations[x][y] = String.valueOf((float)((int)(ratioCalculations[y - 1][x] * 1000)/ 10.0f)) + "%";
                    }
                    else
                    {
                        stringRatioCalculations[x][y] = String.valueOf((float)(((int)(100 * ratioCalculations[y - 1][x])) / 100.0f));
                    }
                }
            }
        }
                
        //creating ratio table
        JTable ratiosTable = new JTable(stringRatioCalculations, ratiosColums);
              
        JScrollPane ratiosScrollPane = new JScrollPane(ratiosTable);
        ratiosScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ratiosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        //creating income statement and balance statement
        int incomeStatementCount = fs.getIncomeStatementCount();
        
        String financialSegments[] = fs.getFinancialSegments();
        //statementValues
        
        String statementColums[] = {"statement name", String.valueOf(year), String.valueOf(year - 1)};
        
        String incomeStatement[][] = new String[incomeStatementCount][statementValues.length + 1];
        
        for (int y = 0; y < statementValues.length + 1; y++)
        {
            for (int x = 0; x < incomeStatementCount; x++)
            {
                if (y == 0)
                {
                    incomeStatement[x][y] = financialSegments[x];
                }
                else
                {
                    incomeStatement[x][y] = String.valueOf(statementValues[y - 1][x]);
                }
            }
        }
        
        System.out.println(financialSegments.length + " " + incomeStatementCount);
        
        String balanceStatement[][] = new String[financialSegments.length - incomeStatementCount][statementValues.length + 1];
        
        for (int y = 0; y < statementValues.length + 1; y++)
        {
            for (int x = 0; x < financialSegments.length - incomeStatementCount; x++)
            {
                if (y == 0)
                {
                    balanceStatement[x][y] = financialSegments[x + incomeStatementCount];
                }
                else
                {
                    balanceStatement[x][y] = String.valueOf(statementValues[y - 1][x + incomeStatementCount]);
                }
            }
        }
        
        JTable incomeTable = new JTable(incomeStatement, statementColums);
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        incomeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        incomeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JTable balanceTable = new JTable(balanceStatement, statementColums);
        JScrollPane balanceScrollPane = new JScrollPane(balanceTable);
        balanceScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        balanceScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        //GridLayout layout = new GridLayout(0, 1);
        //panel.setLayout(layout);
        
        //adding tables
        panel.add(new JLabel("Ratios"));
        panel.add(ratiosScrollPane);
        panel.add(new JLabel("Income Statement"));
        panel.add(incomeScrollPane);
        panel.add(new JLabel("Balance Sheet"));
        panel.add(balanceScrollPane);
        panel.setMaximumSize( panel.getPreferredSize() );
        add(panel);
        
        /*ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
        array.add(new ArrayList<String>());
        array.add(new ArrayList<String>());
        array.get(1).add("hi");
        
        for (int y = 0; y < array.size(); y++)
        {
            if (tables.get(y).size() == 0)
            {
                System.out.println("hi");
                tables.get(y).clear();
            }
        }
        
        
        for (int y = 0; y < array.size(); y++)
        {
            if (array.get(y).size() != 0)
            {
                for (int x = 0; x < array.get(y).size(); x++)
                {
                    System.out.print(array.get(y).get(x));
                }
            }
            else
            {
                
            }
        }*/
    }
}
