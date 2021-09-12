/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFSAP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Morgan Higginbotham
 */
public class FinancialsPanel extends JPanel implements ActionListener{
    public static String[] formTypes = {"1", "1-A", "1-E", "1-K", "1-N", "1-SA", "1-U", "1-Z", "2-E", "3", "4", "5", "6-K", "7-M", "8-A", "8-K", "8-M", "9-M", "10", "10-D", "10-K", "10-M", "10-Q", "11-K", "12b-25", "13F", "13H", "15", "15F", "17-H", "188", "18-K", "19b-4", "19b-4(e)", "19b-7", "20-F", "24F-2", "25", "40-F", "144", "ABS DD-15E", "ABS-15G", "ABS-EE", "ADV", "ADV-E", "ADV-H", "ADV-NR", "ADV-W", "ATS", "ATD-R", "BD", "BD-N", "BDW", "C", "CA-1", "CB", "AFPORTAL", "D", "F-1", "F-3", "F-4", "F-6", "F-7", "F-8", "F-10", "F80", "F-N", "F-X", "ID", "MA", "MA-I", "MA-NR", "MA-W", "MSD", "MSDW", "N-1A", "N-2", "N-3", "N-4", "N-5", "N-6", "N-6EI-1", "N-6F", "N-8A", "N-8B-2", "N-8B-4", "N-8F", "N-14", "N-17D-1", "N-17F-1", "N-17F-2", "N-18F-1", "N-23C-3", "N-27D-1", "N-54A", "N-54C", "N-CR", "N-CSR", "N-MFP", "N-PX", "N-Q", "N-SAR", "NRSRO", "PF", "PILOT", "R31", "S-1", "S-3", "S-6", "S-8", "S-11", "S-20", "SBSE", "SBSE-A", "SBSE-BD", "SBSE-C", "SBSE-W", "SCI", "SD", "SDR", "SE", "SIP", "T-1", "T-2", "T-3", "T-4", "T-6", "TA-1", "TA-2", "TA-W", "TCR", "TH", "WB-AAP", "X-17A-5 Part I", "X-17A-5 Part II", "X-17A-5 Part IIA", "X-17A-5 Part IIB", "X-17A-5 Part III", "X-17A-5 Schedule I", "X-17A-19", "X-17A-1A" };
    //public static String[] formTypes = {"1", "1-A", "1-E", "1-K", "1-N", "1-SA", "1-U", "1-Z", "2-E", "3", "4", "5", "6-K", "7-M", "8-A", "8-K", "8-M", "9-M", "10", "10-D", "10-K", "10-M", "10-Q", "11-K", "12b-25", "13F", "13H", "15", "15F", "17-H", "188", "18-K", "19b-4", "19b-4(e)", "19b-7", "20-F", "24F-2", "25", "40-F", "144", "ABS DD-15E", "ABS-15G", "ABS-EE", "ADV", "ADV-E", "ADV-H", "ADV-NR", "ADV-W", "ATS", "ATD-R", "BD", "BD-N", "BDW", "C", "CA-1", "CB", "AFPORTAL", "D", "F-1", "F-3", "F-4", "F-6", "F-7", "F-8", "F-10", "F80", "F-N", "F-X", "ID", "MA", "MA-I", "MA-NR", "MA-W", "MSD", "MSDW", "N-1A", "N-2", "N-3", "N-4", "N-5", "N-6", "N-6EI-1", "N-6F", "N-8A", "N-8B-2", "N-8B-4", "N-8F", "N-14", "N-17D-1", "N-17F-1", "N-17F-2", "N-18F-1", "N-23C-3", "N-27D-1", "N-54A", "N-54C", "N-CR", "N-CSR", "N-MFP", "N-PX", "N-Q", "N-SAR", "NRSRO", "PF", "PILOT", "R31", "S-1", "S-3", "S-6", "S-8", "S-11", "S-20", "SBSE", "SBSE-A", "SBSE-BD", "SBSE-C", "SBSE-W", "SCI", "SD", "SDR", "SE", "SIP", "T-1", "T-2", "T-3", "T-4", "T-6", "TA-1", "TA-2", "TA-W", "TCR", "TH", "WB-AAP", "X-17A-5 Part I", "X-17A-5 Part II", "X-17A-5 Part IIA", "X-17A-5 Part IIB", "X-17A-5 Part III", "X-17A-5 Schedule I", "X-17A-19", "X-17A-1A" }; 
    
    public FinancialsPanel()
    {        
        createGUIMenu();
    }
    
    
    
    public static int containsForStatements(String str)
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
    
    static String[] options = {"net income", "cost of sales", "sales", "deferred revenue", "revenue"};//put income tax before income, and other stuff like that
    static int[] indexInStatements = {2, 0, 1, 3, 0};
    static String statementStrings[] = {"sales", "general and administrative expenses", "net income", "deferred revemue"};
    static int statementValues[][];//one for each year
    
    private void createGUIMenu() {

        companyLabel = new javax.swing.JLabel();
        companyField = new javax.swing.JTextField();
        yearLabel = new javax.swing.JLabel();
        FormCombo = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        GetFinancials = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        companyLabel.setText("Company");

        yearLabel.setText("Year");

        FormCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10-K", "10-Q" }));

        jLabel1.setText("Form Type");

        GetFinancials.setText("Get Financials");
        GetFinancials.addActionListener(this);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", ""}));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(companyLabel)
                            .addComponent(yearLabel))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(FormCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(GetFinancials))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyLabel)
                    .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(FormCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GetFinancials)
                .addContainerGap(175, Short.MAX_VALUE))
        );
    }// </editor-fold>                        


    public void nothingFound()
    {
        JOptionPane.showMessageDialog(this, "No Results Found", "No Results", JOptionPane.ERROR_MESSAGE);
    }
    
    public void noIndexFileFound()
    {
        JOptionPane.showMessageDialog(this, "Missing Index File\ngo to Index Tab", "No Results", JOptionPane.ERROR_MESSAGE);
    }
     
    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> FormCombo;
    private javax.swing.JButton GetFinancials;
    private javax.swing.JTextField companyField;
    private javax.swing.JLabel companyLabel;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration             

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        
        if (source == GetFinancials)
        {
            int year = Integer.parseInt(jComboBox1.getItemAt(jComboBox1.getSelectedIndex()));
            int quarter = 1;
            String formType = FormCombo.getItemAt(FormCombo.getSelectedIndex());
            String company = companyField.getText();
            
            boolean exists = false;
            short count = 0;
            int[] quarters = new int[4];
            
            if (formType.equals("10-K"))
            {
                exists = IO.fileExistsAnnual(formType, company, year);
                
                if (exists)
                {
                    IndexSearch.searchHTMLJSOUP(company, formType, year);
                    return;
                }
            }
            else if (formType.equals("10-Q"))
            {
                boolean override[] = new boolean[4];
                override[0] = IO.fileExistsQuarterly(formType, company, year, 1);
                override[1] = IO.fileExistsQuarterly(formType, company, year, 2);
                override[2] = IO.fileExistsQuarterly(formType, company, year, 3);
                override[3] = IO.fileExistsQuarterly(formType, company, year, 4);
                
                short index = 0;
                
                for (short i = 0; i < 4; i++)
                {
                    if (override[i])
                    {
                        count++;
                        
                        quarters[index] = (int)(i + 1);
                        
                        index++;
                    }
                }
            }
              
            if (count > 0)
            {
                boolean downloadMoreQuarters = false;
                String[] options = new String[count + 1];
                //quarters = new short[count];
                
                for (int x = 0; x < count; x++)
                {
                    options[x] = "Q" + String.valueOf(quarters[x]);
                }
                
                options[options.length - 1] = "Download More Quarters";
                
                String input = (String)JOptionPane.showInputDialog(Frame.frame, "Which Quarterly Report Would You Like to View?", "Quarterly Report", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                int ind = 0;
                
                for (int index = 0; index < count + 1; index++)
                {
                    if (index == count)
                    {
                        downloadMoreQuarters = true;//download more quanters
                    }
                    if (input.equals(options[index]))
                    {
                        ind = index;
                        break;
                    }
                }
                
                quarter = quarters[ind];
                
                if (!downloadMoreQuarters)
                {
                    IndexSearch.searchHTMLJSOUP(company, formType, year, quarter);
                    return;
                }
                else
                {
                    String[] aaa = new String[4 - (options.length - 1)];
                    int counts = 0;
                    String strs;
                    
                    String optStr = "";
                    
                    for (int x = 0; x < options.length - 1; x++)
                    {
                        optStr += options[x] + " ";
                    }
                    
                    for (int x = 0; x < 4; x++)
                    {
                        strs = "Q" + (x + 1);
                        
                        if (!optStr.contains(strs))
                        {
                            aaa[counts] = strs;
                            counts++;
                        }
                    }
                    
                    input = (String)JOptionPane.showInputDialog(Frame.frame, "Which Quarterly Report Would You Like to View?", "Quarterly Report", JOptionPane.QUESTION_MESSAGE, null, aaa, aaa[0]);
                
                    input += " ";
                    
                    String[] result = IndexSearch.searchFile(year, Integer.parseInt(input.substring(1, 2)), formType, company);
                    
                    if (result[0].equals("NULL"))
                    {
                        nothingFound();
                    }
                    else
                    {
                        Storage.writeTo10QLog(year, quarter, company);
                        IO.fileFromHTML("https://www.sec.gov/Archives/" + result[0], result[1], company, year, Integer.parseInt(input.substring(1, 2)));
                        IndexSearch.searchHTMLJSOUP(company, formType, year);
                    }
                    
                    return;
                }
            }
            
            String[] result = IndexSearch.searchFile(year, 1, formType, company);
            
            if (result[0].equals("NULL"))
            {
                result = IndexSearch.searchFile(year, 2, formType, company);
                
                if (result[0].equals("NULL"))
                {
                    result = IndexSearch.searchFile(year, 3, formType, company);
                    
                    if (result[0].equals("NULL"))
                    {
                        result = IndexSearch.searchFile(year, 4, formType, company);
                         
                        if (result[0].equals("NULL"))
                        {
                            nothingFound();
                            return;
                        }
                        else if(result[0].equals("NO RESULTS FOUND"))
                        {
                            noIndexFileFound();
                        }
                        else
                        {
                            quarter = 4;
                        }
                    }
                    else if(result[0].equals("NO RESULTS FOUND"))
                    {
                        noIndexFileFound();
                    }
                    else
                    {
                        quarter = 3;
                    }
                }
                else if(result[0].equals("NO RESULTS FOUND"))
                {
                    noIndexFileFound();
                }
                else
                {
                    quarter = 2;
                }
            }
            else if(result[0].equals("NO RESULTS FOUND"))
            {
                noIndexFileFound();
            }
            else
            {
                quarter = 1;
            }
            
            if (result[1].equals("10-K"))
            {
                Storage.writeTo10KLog(year, company);
                IO.fileFromHTML("https://www.sec.gov/Archives/" + result[0], result[1], company, year);
                IndexSearch.searchHTMLJSOUP(company, formType, year);
                            }
            else if (result[1].equals("10-Q"))
            {
                Storage.writeTo10QLog(year, quarter, company);
                IO.fileFromHTML("https://www.sec.gov/Archives/" + result[0], result[1], company, year, quarter);
                IndexSearch.searchHTMLJSOUP(company, formType, year, quarter);
            }
            else
            {
                return;
            }
        }
    }
    
    
}
