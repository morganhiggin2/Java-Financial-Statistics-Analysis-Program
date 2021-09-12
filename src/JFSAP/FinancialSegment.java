/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Morgan Higginbotham
 */

public class FinancialSegment
{
    private ArrayList<String> financialSegments;//string is name of segment. Note that when this is converted into array, index position is the id
    private ArrayList<Boolean> alwaysPositive;
    private ArrayList<String[]> financialOptions; //name and name of financial segment
    
    private String[] finSegments;
    private String[] finOptions;
    private boolean[] alwaysPos;
    private int[] ids;
    private int incomeStatementCount;
    private boolean endISC;
    
    public FinancialSegment()
    {
        incomeStatementCount = 0;
        endISC = false;
        
        financialSegments = new ArrayList<String>();
        financialOptions = new ArrayList<String[]>();
        alwaysPositive = new ArrayList<Boolean>();
    }
    
    public void addFinancialSegment(String name, boolean positive)
    {
        financialSegments.add(name);
        alwaysPositive.add(positive);
        
        if (!endISC)
        {
            incomeStatementCount++;
        }
    }
    
    public void endIncomeStatementCount()
    {
        endISC = true;
    }
    
    public void addFinancialOption(String optionName, String segmentName)
    {
        String bundle[] = {optionName, segmentName};
        financialOptions.add(bundle);
    }
    
    public void convert()
    {
        finSegments = new String[financialSegments.size()];
        
        for (int x = 0; x < financialSegments.size(); x++)
        {
            finSegments[x] = financialSegments.get(x);
        }
        
        finOptions = new String[financialOptions.size()];
        ids = new int[financialOptions.size()];
        
        for (int x = 0; x < financialOptions.size(); x++)
        {
            finOptions[x] = financialOptions.get(x)[0];
            ids[x] = getID(financialOptions.get(x)[1]);
        }
        
        alwaysPos = new boolean[alwaysPositive.size()];
        
        for (int x = 0; x < alwaysPositive.size(); x++)
        {
            alwaysPos[x] = alwaysPositive.get(x);
        }
    }
    
    private int getID(String name)
    {
        for (int x = 0; x < finSegments.length; x++)
        {
            if (name.equals(finSegments[x]))
            {
                return x;
            }
        }
        
        return -1;
    }
    
    public String[] getFinancialSegments()
    {
        return finSegments;
    }
    
    public String[] getFinancialOptions()
    {
        return finOptions;
    }
    
    public boolean[] getPositiveArray()
    {
        return alwaysPos;
    }
    
    public int[] getFianncialIDs()
    {
        return ids;
    }
    
    public int getIncomeStatementCount()
    {
        return incomeStatementCount;
    }
}
