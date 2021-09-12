/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

/**
 *
 * @author Morgan Higginbotham
 */
public class Ratio
{
    public static final String ratios[] = {"profit margin ratio", "invested capital return", "times interest charges earned", "times interest and dividends earned", 
        "depreciation on plant, property, and equipment", "depreciation to sales", "times dividends earned", "dividends to income", "inventory turnover", 
        "days accounts recivables outstanding", "current ratio", "quick assets ratio"};    
    public static final boolean percentOrDecimal[] = {true, true, false, false, true, true, false, true, false, false, false, false}; //true if percent, else, decimal format
    float calculations[];
    
    String[] financialSegments;
    int[] values;
    
    public Ratio(String[] fs, int[] v)
    {
        financialSegments = fs;
        values = v;
        calculations = new float[ratios.length];
        
        calculateRatios();
    }
    
    public void calculateLongTermDebt(int vals[])
    {
        int currentDebt = findValueInStatementValues("current liabilities"); 
        int liabilities = findValueInStatementValues("total liabilities");
        
        if (currentDebt != -1)
        {
            if (liabilities != -1)
            {
                vals[findIndexInStatementValues("long-term debt")] = liabilities - currentDebt;
            }
        }
        
        return;
    }
    
    public void calculateTotalDebt(int vals[])
    {
        int assets = findValueInStatementValues("total assets") - findValueInStatementValues("equity");
        vals[findIndexInStatementValues("total liabilities")] = assets;
    }
    
    private void checkIncome(int vals[])
    {
        int income = findValueInStatementValues("net income");
        int revenue = findValueInStatementValues("revenue");
    }
    
    private void calculateRatios()
    {
        for (int x = 0; x < ratios.length; x++)
        {
            switch (ratios[x])
            {
                case ("profit margin ratio"): 
                    calculations[x] = calculateProfitMargin();
                    break;
                case ("invested capital return"):
                    calculations[x] = calculateInvestedCapitalReturn();
                    break;
                case ("times interest charges earned"):
                    calculations[x] = calculateTimesInterestChargesEarned();
                    break;
                case ("times dividends earned"):
                    calculations[x] = calculateTimesDividendsEarned();
                    break;
                case ("times interest and dividends earned"):
                    calculations[x] = calculateTimesInterestandDividendsEarned();
                    break;
                case ("dividends to income"):
                    calculations[x] = calculateDividendstoIncome();
                    break;
                case ("depreciation on plant, property, and equipment"):
                    calculations[x] = calculateDepreciationonPlantsandEquipment();
                    break;
                case ("depreciation to sales"):
                    calculations[x] = calculateDepreciationtoSales();
                    break;
                case ("inventory turnover"):
                    calculations[x] = calculateInventoryTurnover();
                    break;
                case ("days accounts recivables outstanding"):
                    calculations[x] = calculateDaysAccountsRecivableOutstanding();
                    break;
                case ("current ratio"):
                    calculations[x] = calculateCurrentRatio();
                    break;
                case ("quick assets ratio"):
                    calculations[x] = calculateQuickAssetsRatio();
                default:
                    break;
            }
        }
        
        return;
    }
        
    private float calculateProfitMargin()
    {
        float revenue = (float)(findValueInStatementValues("revenue"));
        float income = (float)(findValueInStatementValues("net income"));
        
        if (income == -1 || revenue == -1)
        {
            return -1.0f;
        }
        else
        {
            return income / revenue;           
        }
    }
    
    private float calculateInvestedCapitalReturn()
    {
        float income = (float)(findValueInStatementValues("net income"));
        float commonStock = (float)(findValueInStatementValues("common stock"));
        float preferredStock = (float)(findValueInStatementValues("preferred stock"));
        float totalDebt = (float)(findValueInStatementValues("total debt"));
        float retainedEarnings = (float)(findValueInStatementValues("retained earnings"));
        
        preferredStock = preferredStock == -1 ? 0 : preferredStock;
        commonStock = commonStock == -1 ? 0 : commonStock;
        totalDebt = totalDebt == -1 ? 0 : totalDebt;
        retainedEarnings = retainedEarnings == -1 ? 0 : retainedEarnings; 
        
        if (income == -1)
        {
            return -1;
        }
        else
        {
            return income / (income + commonStock + preferredStock + totalDebt + retainedEarnings);
        }
    }
    
    private float calculateTimesInterestChargesEarned()
    {
        float interestPayments = (float)(findValueInStatementValues("interest payments"));
        float netIncome = (float)(findValueInStatementValues("net income"));
        
        if (interestPayments != -1 && netIncome != -1)
        {
            return netIncome / interestPayments;
        }
        
        return 0.0f;
    }
    
    private float calculateTimesDividendsEarned()
    {
        float dividends = (float)(findValueInStatementValues("payments for dividends"));
        float netIncome = (float)(findValueInStatementValues("net income"));
        
        if (netIncome != -1 && dividends != -1 && netIncome != 0 && dividends != 0)
        {
            return netIncome / dividends;    
        }
        
        return 0.0f;
    }
    
    private float calculateTimesInterestandDividendsEarned()
    {
        float dividends = (float)(findValueInStatementValues("payments for dividends"));
        float interestPayments = (float)(findValueInStatementValues("interest payments"));
        float netIncome = (float)(findValueInStatementValues("net income"));
        
        if (interestPayments != -1 && netIncome != -1 && dividends != -1)
        {
            return netIncome / (interestPayments + dividends);    
        }
        
        return 0.0f;
    }
    
    private float calculateDepreciationonPlantsandEquipment()
    {
        float property = (float)(findValueInStatementValues("plant property and equipment"));
        float depreciation = (float)(findValueInStatementValues("depreciation charges"));
        
        if (property != -1 && depreciation != -1 && property != 0 && depreciation != 0)
        {
            return depreciation / property;
        }
        
        return 0.0f;
    }
    
    private float calculateDividendstoIncome()
    {
        float dividends = (float)(findValueInStatementValues("payments for dividends"));
        float netIncome = (float)(findValueInStatementValues("net income"));
        
        if (netIncome != -1 && dividends != -1 && netIncome != 0 && dividends != 0)
        {
            return netIncome / dividends;    
        }
        
        return 0.0f;
    }
    
    private float calculateDepreciationtoSales()
    {
        float depreciation = (float)(findValueInStatementValues("depreciation charges"));
        float revenue = (float)(findValueInStatementValues("revenue"));
        
        if (depreciation != -1 && revenue != -1 && depreciation != 0 && revenue != 0)
        {
            return depreciation / revenue;
        }
        
        return 0.0f;
    }
    
    private float calculateInventoryTurnover()
    {
        float revenue = (float)(findValueInStatementValues("revenue"));
        float inventory = (float)(findValueInStatementValues("inventory"));
        
        if (revenue != -1 && inventory != -1 && revenue != 0 && inventory != 0)
        {
            return revenue / inventory;
        }
        
        return 0.0f;
    }
    
    private float calculateDaysAccountsRecivableOutstanding()
    {
        float accountsRecivable = (float)(findValueInStatementValues("accounts receivable"));
        float revenue = (float)(findValueInStatementValues("revenue"));
        
        if(accountsRecivable != -1 && revenue != -1 && accountsRecivable != 0 && revenue != 0)
        {
            return revenue / accountsRecivable;
        }
        
        return 0.0f;
    }
    
    private float calculateCurrentRatio()
    {
        float currentAssets = (float)(findValueInStatementValues("current assets"));
        float currentLiabilities = (float)(findValueInStatementValues("current liabilities"));
        
        if (currentAssets != -1 && currentLiabilities != -1 && currentAssets != 0 && currentLiabilities != 0)
        {
            return currentAssets / currentLiabilities;
        }
        
        return 0.0f;
    }
    
    private float  calculateQuickAssetsRatio()
    {
        float currentAssets = (float)(findValueInStatementValues("current assets"));
        float currentLiabilities = (float)(findValueInStatementValues("current liabilities"));
        float inventory = (float)(findValueInStatementValues("inventory"));
        
        if (currentAssets != -1 && currentLiabilities != -1 && inventory != -1 && currentAssets != 0 && currentLiabilities != 0 && inventory != 0)
        {
            return (currentAssets - inventory) / currentLiabilities;
        }
        
        return 0.0f;
    }
    
    private int findValueInStatementValues(String s)
    {
        for (int i = 0; i < financialSegments.length; i++)
        {
            if (s.equals(financialSegments[i]))
            {
                return values[i];
            }
        }
        
        return 0;
    }
    
    private int findIndexInStatementValues(String s)
    {
        for (int i = 0; i < financialSegments.length; i++)
        {
            if (s.equals(financialSegments[i]))
            {
                return i;
            }
        }
        
        return -1;
    }    
    
    public float[] getCalculations()
    {
        return calculations;
    }
}
