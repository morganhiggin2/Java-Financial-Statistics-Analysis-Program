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

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class Frame extends JFrame
{
    static Frame frame;
    
    public Frame()
    {
        super("Java Financial Statistics Analysis Program");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
                
        makeGUI();    
        init();
        
        setVisible(true);
    }
    
    private void makeGUI()
    {
        JOptionPane.showMessageDialog(null, "Some Processes Take A While... Be Patient");
        
        DownloadFileViaURLPanel downloadFileViaURL = new DownloadFileViaURLPanel();
        FinancialsPanel financialsPanel = new FinancialsPanel();
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Download Form File", downloadFileViaURL);
        tabs.add("Get Financials", financialsPanel);
        add(tabs);
    }
    
    private void init()
    {
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        frame = new Frame();
    }
}
