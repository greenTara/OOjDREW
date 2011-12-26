// OO jDREW - An Object Oriented extension of the Java Deductive Reasoning Engine for the Web
// Copyright (C) 2005 Marcel Ball
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

package org.ruleml.oojdrew.GUI;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.ruleml.oojdrew.Config;
import org.ruleml.oojdrew.Configuration;
import org.ruleml.oojdrew.BottomUp.ForwardReasoner;
import org.ruleml.oojdrew.BottomUp.ForwardReasoner.RuleDescriptionLanguage;
import org.ruleml.oojdrew.parsing.POSLParser;
import org.ruleml.oojdrew.parsing.RDFSParser;
import org.ruleml.oojdrew.parsing.RuleMLFormat;
import org.ruleml.oojdrew.parsing.RuleMLParser;
import org.ruleml.oojdrew.parsing.SubsumesParser;
import org.ruleml.oojdrew.util.DefiniteClause;
import org.ruleml.oojdrew.util.SymbolTable;
import org.ruleml.oojdrew.util.Types;

/**
 * This class implements a GUI front-end for the OO jDREW Bottom up module.
 * This gui is ment for demonstration purposes only; but it's code can be used
 * as an example of how to integrate a Bottom-Up engine into a Java application.
 *
 * <p>Title: OO jDREW</p>
 *
 * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
 * 0.88</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * @author Marcel A. Ball
 * @version 0.89
 */

public class BottomUpGUI extends javax.swing.JFrame {

    ForwardReasoner fr;
    
    int posl = 0;
   
    private RuleMLFormat ruleMLversion = RuleMLFormat.RuleML88;
    
    private RuleMLParser rmlParser;

    //Logger logger = Logger.getLogger("jdrew.oo.gui.BottomUpGUI");

    /** Creates new form BottomUpGUI */
    public BottomUpGUI(RuleMLParser rmlParser) {
        initComponents();
        fr = new ForwardReasoner();
        this.rmlParser = rmlParser;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {
        
        JMenu fileMenu = new JMenu("File");
        //creating a ExitAction and a ConnAction both are defined later
        ExitActionBU exitAction = new ExitActionBU("Exit");
        OpenActionBU openAction = new OpenActionBU("Open File");
        WriteActionBU writeAction = new WriteActionBU("Write File");
        OpenWebSourceActionBU webAction = new OpenWebSourceActionBU("Open Web Source");
        
        //adding the connection action and exit action to the menu
        fileMenu.add(openAction);
        fileMenu.add(webAction);
        fileMenu.addSeparator();  
        fileMenu.add(writeAction);
        fileMenu.addSeparator();            
        fileMenu.add(exitAction);
                
        //making a new menu bar and adding the file menu to it                
        JMenuBar sysMenu = new JMenuBar();
        sysMenu.add(fileMenu);
        setJMenuBar(sysMenu);
        
        rdfTypes = new javax.swing.JRadioButton();
        poslTypes = new javax.swing.JRadioButton();
        buttonGroupTypes = new javax.swing.ButtonGroup();
        
        formatBG = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        typedeftab = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        typetext = new javax.swing.JTextArea();
        parseTypeBtn = new javax.swing.JButton();
        kbtab = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        kbtext = new javax.swing.JTextArea();
        parseKBBtn = new javax.swing.JButton();
        outtab = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        outputtext = new javax.swing.JTextArea();
        runBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelTypes = new javax.swing.JLabel();
        jrbPOSL = new javax.swing.JRadioButton();
        jrbRML88 = new javax.swing.JRadioButton();
        jrbRML91 = new javax.swing.JRadioButton();
        jrbRULE = new javax.swing.JCheckBox();
        jrbOLDNEW = new javax.swing.JCheckBox();
        jrbCHECK = new javax.swing.JCheckBox();
        showdbgBtn = new javax.swing.JButton();
        inputLoopCounter = new javax.swing.JTextField();
        
        
        dbgcon = new DebugConsole();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OO jDREW Bottom Up");
        getAccessibleContext().setAccessibleName("OO jDREW Bottom-Up Engine");
        typedeftab.setLayout(null);

        jScrollPane1.setViewportView(typetext);

        typedeftab.add(jScrollPane1);
        jScrollPane1.setBounds(12, 10, 760, 510);

        parseTypeBtn.setText("Parse Type Information");
        parseTypeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseTypeBtnActionPerformed(evt);
            }
        });
		
        typedeftab.add(parseTypeBtn);
        parseTypeBtn.setBounds(591, 530, 180, 23);
        
        buttonGroupTypes.add(rdfTypes);
        rdfTypes.setText("RDFS");
        rdfTypes.setToolTipText("Select RDFS syntax for Type Input");
        typedeftab.add(rdfTypes);
        rdfTypes.setBounds(130, 530, 80, 23);
        
        buttonGroupTypes.add(poslTypes);
        poslTypes.setText("POSL");
        poslTypes.setToolTipText("Select POSL syntax for Type Input");
        typedeftab.add(poslTypes);
        poslTypes.setBounds(210, 530, 140, 23);
        
        poslTypes.setSelected(true);
        
        jLabelTypes.setText("Input Type Format: ");
        typedeftab.add(jLabelTypes);
        jLabelTypes.setBounds(10, 530, 270, 20);
        
        //need to add things here
        
        jTabbedPane1.addTab("Type Definitions", typedeftab);

        kbtab.setLayout(null);

        jScrollPane2.setViewportView(kbtext);

        kbtab.add(jScrollPane2);
        jScrollPane2.setBounds(12, 10, 760, 510);
                
        parseKBBtn.setText("Parse Knowledge Base");
        parseKBBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseKBBtnActionPerformed(evt);
            }
        });

        kbtab.add(parseKBBtn);
        parseKBBtn.setBounds(591, 530, 180, 23);
               
        kbtab.setName("Knowledge Base");
        jTabbedPane1.addTab(kbtab.getName(), kbtab);
        
        outtab.setLayout(null);

        jScrollPane3.setViewportView(outputtext);

        outtab.add(jScrollPane3);
        jScrollPane3.setBounds(12, 10, 760, 510);

        runBtn.setText("Run Forward Reasoner");
        runBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runBtnActionPerformed(evt);
            }
        });

        outtab.add(runBtn);
        runBtn.setBounds(591, 530, 180, 23);

        jTabbedPane1.addTab("Output", outtab);

        getContentPane().add(jTabbedPane1);
        jTabbedPane1.setBounds(10, 10, 790, 590);
        //Moving
		jLabel1.setText("Input/Output Format:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 610, 130, 20);
        
        formatBG.add(jrbPOSL);
        jrbPOSL.setText("POSL");
        getContentPane().add(jrbPOSL);
        jrbPOSL.setBounds(160, 610, 110, 23);

        formatBG.add(jrbRML88);
        jrbRML88.setText("RuleML 0.88");
        getContentPane().add(jrbRML88);
        jrbRML88.setBounds(270, 610, 150, 23);
        //Moving 
        formatBG.add(jrbRML91);
        jrbRML91.setSelected(true);
        jrbRML91.setText("RuleML 0.91+");
        getContentPane().add(jrbRML91);
        jrbRML91.setBounds(270, 635, 150, 23);
        
        jLabel2.setText("Set Loop Counter:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(20, 635, 200, 20);
        
        inputLoopCounter.setText("0");
        getContentPane().add(inputLoopCounter);
	    inputLoopCounter.setBounds(150, 635, 100, 20);
	    
	    jrbRULE.setText("Print Rules");
        getContentPane().add(jrbRULE);
        jrbRULE.setBounds(440, 610, 150, 23);
	    
	    jrbCHECK.setText("Test for Stratification");
	    getContentPane().add(jrbCHECK);

	    jrbCHECK.setBounds(440,635,150,23);

        jrbOLDNEW.setText("Seperate Facts");
		getContentPane().add(jrbOLDNEW);
		jrbOLDNEW.setBounds(440, 660, 150, 23);
		
        showdbgBtn.setText("Show Debug Console");
        showdbgBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showdbgBtnActionPerformed(evt);
            }
        });

        int kbTabIndex = jTabbedPane1.indexOfTab(kbtab.getName());
        jTabbedPane1.setSelectedIndex(kbTabIndex);
        
        getContentPane().add(showdbgBtn);
        showdbgBtn.setBounds(621, 610, 180, 23);

        pack();

        this.setBounds(0, 0, 810, 700);
        this.setResizable(false);
    }

    /**
     * This method is used to run the engine. This is done by calling the
     * runForwardReasoner() method of the ForwardReasoner object, this should
     * only be done after loading type information (using an RDFSParser) and
     * parsing (using POSLParser or RuleMLParser) and loading the knowledge
     * base using the loadClauses(Iterator clauses) method of the
     * ForwardReasoner object.
     */
    private void runBtnActionPerformed(java.awt.event.ActionEvent evt) {
      
           try {
                fr.setLoopCounter(inputLoopCounter.getText());
            } catch (Exception ex1) {
                //this.logger.error(ex1.getMessage(), ex1);
                JOptionPane.showMessageDialog(this, ex1.getMessage(), "Invalid Number Input",
                                              JOptionPane.ERROR_MESSAGE);
            return;
            }
      
 		fr.printClauses(RuleDescriptionLanguage.POSL, ruleMLversion);
 		
        //check the forward reasoner class now
        
        fr.runForwardReasoner();
		
        System.err.println("Ran Reasoner");

        Hashtable oldFacts = fr.getOldFacts();

        if(oldFacts.containsKey(SymbolTable.IINCONSISTENT)){
            Vector v = (Vector)oldFacts.get(SymbolTable.IINCONSISTENT);
            if(v.size() > 0){
                //logger.warn("Knowledge base is inconsistent.");
                JOptionPane.showMessageDialog(this, "Knowledge base is inconsistent", "Consistency Check", JOptionPane.WARNING_MESSAGE);
            }
        }
		

        Hashtable rules = fr.getRules();
        Enumeration e1 = oldFacts.elements();
        
       if(this.jrbOLDNEW.isSelected()){
        	StringBuffer sb = new StringBuffer(4096);
        	
        	if(this.jrbPOSL.isSelected()){
        		String poslFacts = fr.printClauses(RuleDescriptionLanguage.POSL, ruleMLversion);
        		sb.append(poslFacts);

        		if(jrbRULE.isSelected()){
            
           		  sb.append("\n % Rules : \n");
           		  e1 = rules.elements();
          			  while (e1.hasMoreElements()) {
            		    Vector rulesv = (Vector) e1.nextElement();
            		    Iterator it = rulesv.iterator();
             			   while (it.hasNext()) {
               				     DefiniteClause dc = (DefiniteClause) it.next();
                  				 sb.append(dc.toPOSLString() + "\n");
               		 		}						
            			}		
           		}
	    		this.outputtext.setText(sb.toString()); 
        	}
        	
        	else{
        		String ruleMLFacts = "";
        		//Can add here to change the current parser so that
        		//you can exchange between ruleml 0.88 and 0.91
        		if(this.jrbRML91.isSelected()){
        			
        			ruleMLversion = RuleMLFormat.RuleML91;
        			ruleMLFacts = fr.printClauses(RuleDescriptionLanguage.RuleML, ruleMLversion);
        		}
        		if(this.jrbRML88.isSelected()){
        			
        			ruleMLversion = RuleMLFormat.RuleML88;
        			ruleMLFacts = fr.printClauses(RuleDescriptionLanguage.RuleML, ruleMLversion);
        		}   
        		     			
        		
        		sb.append(ruleMLFacts);

        		  if(jrbRULE.isSelected()){
            
           		  sb.append("\n% Rules : \n");
           		  e1 = rules.elements();
          			  while (e1.hasMoreElements()) {
            		    Vector rulesv = (Vector) e1.nextElement();
            		    Iterator it = rulesv.iterator();
             			   while (it.hasNext()) {
               				     DefiniteClause dc = (DefiniteClause) it.next();
                  				 sb.append(dc.toRuleMLString(ruleMLversion) + "\n");
               		 		}						
            			}		
           		}
        		
        		this.outputtext.setText(sb.toString());
        	}
        
        	return;
        }
        
          if (this.jrbPOSL.isSelected()) {
        	       	
            StringBuffer sb = new StringBuffer(4096);
            
            sb.append("% Derived Facts:\n\n");
            Enumeration e = oldFacts.elements();
            while (e.hasMoreElements()) {
               Vector facts = (Vector) e.nextElement();
               Iterator it = facts.iterator();
                while (it.hasNext()) {
                    DefiniteClause dc = (DefiniteClause) it.next();
                   sb.append(dc.toPOSLString() + "\n");    
              }
            
            }
            //Add the option to print rules or not
            if(jrbRULE.isSelected()){
            
            sb.append("\n % Rules : \n");
            e = rules.elements();
            while (e.hasMoreElements()) {
                Vector rulesv = (Vector) e.nextElement();
                Iterator it = rulesv.iterator();
                while (it.hasNext()) {
                    DefiniteClause dc = (DefiniteClause) it.next();
                    sb.append(dc.toPOSLString() + "\n");
                }
            }
           }
           
            this.outputtext.setText(sb.toString());
        } 
        
        else  {
        	
        	//Can add here to change the current parser so that
        	//you can exchange between ruleml 0.88 and 0.91
        	 Element el = new Element("Assert");
        	 Element and = null;
        	    if(this.jrbRML91.isSelected()){
        			and = new Element("Rulebase");
        			Attribute a = new Attribute("mapClosure", "universal");
       				and.addAttribute(a);
        			
        			ruleMLversion = RuleMLFormat.RuleML91;
        		}
        		if(this.jrbRML88.isSelected()){
        			and = new Element("And");
        			Attribute a = new Attribute("mapClosure", "universal");
       				and.addAttribute(a);        	
        			ruleMLversion = RuleMLFormat.RuleML88;
        		} 
         
            el.appendChild(and);
            
            Enumeration e = oldFacts.elements();
            while (e.hasMoreElements()) {

                Vector facts = (Vector) e.nextElement();
                Iterator it = facts.iterator();
                while (it.hasNext()) {
                    DefiniteClause dc = (DefiniteClause) it.next();
                                 
                    if(dc.atoms[0].symbol == SymbolTable.IINCONSISTENT)
                        continue;
                        and.appendChild(dc.toRuleML(ruleMLversion));
                }
            }
            //Add the option to print rules or not
            if(jrbRULE.isSelected()){
            
            e = rules.elements();
            while (e.hasMoreElements()) {
                Vector rulesv = (Vector) e.nextElement();
                Iterator it = rulesv.iterator();
                while (it.hasNext()) {
                    DefiniteClause dc = (DefiniteClause) it.next();                  
                    if(dc.atoms[0].symbol == SymbolTable.IINCONSISTENT)
                        continue;
                    and.appendChild(dc.toRuleML(ruleMLversion));
                }
              }
			}
           
            ByteArrayOutputStream os = new ByteArrayOutputStream();            
            nu.xom.Serializer sl = new nu.xom.Serializer(os);
            sl.setIndent(3);
            sl.setLineSeparator("\n");
            try {
                Document doc = new Document(el);
                sl.write(doc);
            } catch (java.io.IOException ex) {
                //this.logger.error(ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

            this.outputtext.setText(os.toString());
        }
    }

    /**
     * This method implements a knowledge base parsing routine.
     *
     * The first step is to reset the symbol table (call the static reset()
     * method of the SymbolTable class) and create a new ForwardReasoner
     * object.
     *
     * Then the knowledge base and the format of the knowledge base are
     * retrieved from the user interface; once this is done an appropriate
     * parser is created and the knowledge base is parse (using the
     * parseDefiniteClauses(String) method of the POSLParser class or the
     * parseRuleMLString(int FORMAT, String) method of the RuleMLParser class).
     *
     * Once the knowledge base is parsed an iterator over the parsed clauses is
     * created using the iterator() method of the RuleMLParser or POSLParser
     * class; this Iterator is then passed to the loadClauses(Iterator) method
     * of the ForwardReasoner object.
     *
     * It also checks to see if the knowledgebase is stratifiable.(If the user wants)
     */
    private void parseKBBtnActionPerformed(java.awt.event.ActionEvent evt) {
           try {
                fr.setLoopCounter(inputLoopCounter.getText());
            } catch (Exception ex1) {
                //this.logger.error(ex1.getMessage(), ex1);
                JOptionPane.showMessageDialog(this, ex1.getMessage(), "Invalid Number Input",
                                              JOptionPane.ERROR_MESSAGE);
            return;
            }
      
        //logger.debug("Parsing Knowledge Base");
        String kbstr = this.kbtext.getText();

        SymbolTable.reset();
        fr = new ForwardReasoner();

        if (kbstr.trim().equals("")) {
            return;
        }

        if (this.jrbPOSL.isSelected()) {
            POSLParser pp = new POSLParser();
            try {
                pp.parseDefiniteClauses(kbstr);
            } catch (Exception ex1) {
              //  this.logger.error(ex1.getMessage(), ex1);
                JOptionPane.showMessageDialog(this, ex1.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

            fr.loadClauses(pp.iterator());
        } // RuleML
         if (this.jrbRML88.isSelected() || this.jrbRML91.isSelected()) {
        	 
            ruleMLversion = jrbRML88.isSelected() ? RuleMLFormat.RuleML88 : RuleMLFormat.RuleML91;

            try {
                rmlParser.parseRuleMLString(RuleMLFormat.RuleML91, kbstr);
            } catch (Exception ex) {
                //this.logger.error(ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }

			Iterator it = rmlParser.iterator();

            fr.loadClauses(rmlParser.iterator());
          //ruleml 0.91
        }
         
		//Testing for stratification if user wants to.
		if(jrbCHECK.isSelected()){
				
    		boolean strat = fr.isStratifiable();
    		System.out.println("Is stratifiable: " + strat);
    		//data base is not stratifiable
    		if(!strat){
    			//allowing the user to see the details on why stratification failed
    			int ans = JOptionPane.showConfirmDialog(this, "Knowledge base is not stratifiable. \nWould you like to see more detials?\n", "Non-Stratfiable",
                                	             JOptionPane.YES_NO_OPTION);
    			//if the user wants to see the details they can
    			if(ans == 0){
    				Vector msg = fr.getMessage();
    				Iterator msgIterator = msg.iterator();
    				String message = "";
    				int count = 1;
    				while(msgIterator.hasNext()){
    			
    					message = message + count + ")" + (String)msgIterator.next() + "\n";	
    					count++;
    				}
    				JOptionPane.showMessageDialog(this, message, "Stratification Violations",
                                	             JOptionPane.INFORMATION_MESSAGE);	
    				
    			}
    			
    		}
    		//just tell the user the knowledge base is stratifiable
    		if(strat){
    					JOptionPane.showMessageDialog(this, "Knowledge base is stratifiable.", "Stratfiable",
                                	             JOptionPane.INFORMATION_MESSAGE);
    		}
    	}
    }
	
	/**
	 * This method shows the Debug Console.
	 */
    private void showdbgBtnActionPerformed(java.awt.event.ActionEvent evt) {
        this.dbgcon.setVisible(true);
    }

    /**
     * This method is used to load type information into the term typing
     * system.
     *
     * The first thing that is done is that the type input is retrieved from
     * the user interface, then the type system is reset. Once this is done
     * the static parseRDFSString(String) method of the RDFSParser class is
     * called to parse and load the type information.
     *
     * Since any previously parsed clauses that used types are no longer valid
     * (the types system was reset), the knowledge base is reset by calling
     * the parseKBBtnActionPerformed(ActionEvent) method of the GUI.
     */
    private void parseTypeBtnActionPerformed(java.awt.event.ActionEvent evt) {
          try {
                fr.setLoopCounter(inputLoopCounter.getText());
            } catch (Exception ex1) {
               // this.logger.error(ex1.getMessage(), ex1);
                JOptionPane.showMessageDialog(this, ex1.getMessage(), "Invalid Loop Counter Input",
                                              JOptionPane.ERROR_MESSAGE);
            	return;
            }
        
        String typestr = this.typetext.getText();
        
        if(typestr.equalsIgnoreCase("")){
        	JOptionPane.showMessageDialog(this, "Type Definition is Blank", "Error In parsing Types",
                    JOptionPane.ERROR_MESSAGE);
        	return;
        }
        
        Types.reset();

        if(rdfTypes.isSelected() == true){
               
        //logger.debug("Parsing Datatypes.");
        	try {
        	
        		RDFSParser.parseRDFSString(typestr);
            
        	} catch (Exception ex) {
            //this.logger.error(ex.getMessage(), ex);
        		JOptionPane.showMessageDialog(this, ex.getMessage(), "Error In parsing Types",
                                          JOptionPane.ERROR_MESSAGE);
        	}
        }else if(poslTypes.isSelected() == true){
        	SubsumesParser sp = new SubsumesParser(typestr);
        	
        	try {
    			sp.parseSubsumes();
    		} catch (Exception ex) {
    		
    			if(ex.getMessage() == null){
        			JOptionPane.showMessageDialog(this, "Invalid POSL Format", "Error",
	                         JOptionPane.ERROR_MESSAGE);
    			}
    			else{	
    			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
    			                         JOptionPane.ERROR_MESSAGE);
    			}
    		}
        	
        }
        //logger.debug("Datatypes updated - must reparse clauses.");


        parseKBBtnActionPerformed(evt);
    }

    /**
     * This is the main method that is called when the BottomUpGUI is ran.
     *
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    javax.swing.UIManager.setLookAndFeel(
                            javax.swing.UIManager.
                            getCrossPlatformLookAndFeelClassName());
                } catch (Exception e) {}

                BasicConfigurator.configure();
                Logger root = Logger.getRootLogger();
                root.setLevel(Level.DEBUG);
                
                Configuration config = new Config();
                RuleMLParser rmlParser = new RuleMLParser(config);

                BottomUpGUI frame = new BottomUpGUI(rmlParser);
                frame.setSize(800,750);
                TextPaneAppender tpa = new TextPaneAppender(new PatternLayout(
                        "%-5p %d [%t]:  %m%n"), "Debug");
                tpa.setTextPane(frame.dbgcon.getTextPane());

                root.addAppender(tpa);
                Enumeration e = root.getAllAppenders();
                while (e.hasMoreElements()) {
                    System.out.println(e.nextElement());
                }

                org.ruleml.oojdrew.Config.PRINTGENOIDS = false;
				frame.setResizable(true);
                frame.setVisible(true);

            }
        });
    }

    /**
     * This method first prompts the user where they want to save the file
     * and name the file.  It then writes the contents after running the forward
     * reasoner.
     */
     
   public static void writeFile(){
        
        Frame parent = new Frame();
        
        FileDialog fd = new FileDialog(parent, "Please choose a file:",
                   FileDialog.SAVE);
        fd.show();
        String inputValue = fd.getFile();
        String fileName = fd.getDirectory() + inputValue;
                
        FileOutputStream out;
    	PrintStream print;

            if(inputValue != null){
    
            try
               {
                       //false = new
                       //true = append
                      
                out = new FileOutputStream(fileName,false);
                print = new PrintStream(out);
                        
				print.println(outputtext.getText());        
                       
				print.close();
              }                            
  
               catch (IOException e)
                {
                System.err.println("error with file");
                }
         }
        
        }//write file
    
    /**
     * This method prompts the user to the url to a file and then,
     * places its contents in the selected text area(knowledge base, Types).
     */  
     	  
    public static void openWebSource(){
    	
         int append = JOptionPane.showConfirmDialog(null, 
         	"Open for append?", "Append to current text",
             JOptionPane.YES_NO_OPTION);
		 //0 = yes
		 //1 = no
		    	
 	     Object[] possibleValues = {"KnowledgeBase", "Types"};
         Object selectedValue = JOptionPane.showInputDialog(null,

            "Select one", "Type of File",

            JOptionPane.INFORMATION_MESSAGE, null,

            possibleValues, possibleValues[0]);
            
    	  if(selectedValue != null){
			
	 		String url = JOptionPane.showInputDialog("Please enter a url");
	  			  
			try {
    			HttpClient client = new HttpClient();
   	 			GetMethod method = new GetMethod( url );
    			method.setFollowRedirects( true );

    			// Execute the GET method
    			int statusCode = client.executeMethod( method );
    			if( statusCode != -1 ) {
     		 		String contents = method.getResponseBodyAsString();
      		 		method.releaseConnection();
                    
                    if(selectedValue.equals("KnowledgeBase")){
                    	if(append == 1)
                    		kbtext.setText(contents);
                    	if(append == 0)
                    		kbtext.append(contents + "\n");
                    }
                    
                    if(selectedValue.equals("Types")){
                    	if(append == 1)
                    		typetext.setText(contents);
                    	if(append == 0)
                    		typetext.append(contents + "\n");
                    }  				
    			}		
   			}
   			catch( Exception e ) {
    			e.printStackTrace();
   			}
    	}   	
    }
    
    
    /**
     * This method prompts the user to select a file and then,
     * places its contents in the selected text area(knowledge base, Types).
     */
    
    public static void openFile(){
        
         int append = JOptionPane.showConfirmDialog(null, 
         	"Open for append?", "Append to current text",
             JOptionPane.YES_NO_OPTION);
                    
         JFrame f1 = new JFrame();
        
         Object[] possibleValues = {"KnowledgeBase", "Types"};
         Object selectedValue = JOptionPane.showInputDialog(null,

            "Select one", "Type of File",

            JOptionPane.INFORMATION_MESSAGE, null,

            possibleValues, possibleValues[0]);
            
    if(selectedValue != null){
    
    
    System.out.println(selectedValue);

        Frame parent = new Frame();
        
        FileDialog fd = new FileDialog(parent, "Please choose a file:",
                   FileDialog.LOAD);
        fd.show();

        String selectedItem = fd.getFile();        
        String fileName = fd.getDirectory() + fd.getFile();
        
                try {

                        FileReader inFile = new FileReader(fileName);
                        BufferedReader in = new BufferedReader(inFile);
                        String read ="";
                        String contents="";
                        
                        while((read = in.readLine()) != null)
                        {
                                contents = contents + read + '\n';
                        }
                        in.close();

                                if(selectedValue.equals("KnowledgeBase")){
                                	if(append == 1)
                                		kbtext.setText(contents);
                                    if(append == 0)
                                    	kbtext.append(contents + "\n");
                                }
                                if(selectedValue.equals("Types")){
                                	if(append ==1)
                                		typetext.setText(contents);
                                    if(append == 0)
                                    	kbtext.append(contents + "\n");
                                }                                
                                
                } catch (Exception e) {
                      
                        System.out.println(e.toString());
                }              
        }//selected value != null
  }//openFile

    // Variables declaration - do not modify
    private javax.swing.JRadioButton rdfTypes;
    private javax.swing.JRadioButton poslTypes;
    private javax.swing.ButtonGroup buttonGroupTypes;
    private javax.swing.ButtonGroup formatBG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelTypes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton jrbPOSL;
    private javax.swing.JRadioButton jrbRML88;
    private javax.swing.JRadioButton jrbRML91;
    private javax.swing.JCheckBox jrbRULE;
    private javax.swing.JCheckBox jrbCHECK;
    private javax.swing.JCheckBox jrbOLDNEW;
    private javax.swing.JPanel kbtab;
    static private javax.swing.JTextArea kbtext;
    static private javax.swing.JTextArea outputtext;
    private javax.swing.JPanel outtab;
    private javax.swing.JButton parseKBBtn;
    private javax.swing.JButton parseTypeBtn;
    private javax.swing.JButton runBtn;
    private javax.swing.JButton showdbgBtn;
    private javax.swing.JPanel typedeftab;
    static private javax.swing.JTextArea typetext;
    private javax.swing.JTextField inputLoopCounter;
    private DebugConsole dbgcon;
    // End of variables declaration

}
  /**
  * This class implements a OpenWebSourceActionBU which is used by the Menu bar in the
  * BottomUpGUI.
  *
  * <p>Title: OO jDREW</p>
  *
  * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
  * 0.88</p>
  *
  * <p>Copyright: Copyright (c) 2005</p>
  *
  * @author Ben Craig
  * @version 0.89
  */
 class OpenWebSourceActionBU extends AbstractAction
 {

/**
 * This is the contructor for a OpenWebSourceActionBU.
 * It calls the contructor for a AbstractAction.
 * @param String name - the name for the action
 */
                
	OpenWebSourceActionBU(String name)
    {
        super(name);
    }

    /**
     * This method is called when a OpenWebSourceActionBU is performed
     * When an OpenWebSourceActionBU is performed the user can open a 
     * websource file
     * @param event - the event that occured
     */

      public void actionPerformed(ActionEvent event)
      {
           BottomUpGUI.openWebSource();
      }
   }//Open Action
 
 /**
  * This class implements a ExitActionBU which is used by the Menu bar in the
  * BottomUPGUI.
  *
  * <p>Title: OO jDREW</p>
  *
  * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
  * 0.88</p>
  *
  * <p>Copyright: Copyright (c) 2005</p>
  *
  * @author Ben Craig
  * @version 0.89
  */
  
 class ExitActionBU extends AbstractAction
 {
 	        /**
  			 * This is the contructor for a ExitAction.
  			 * It calls the contructor for a AbstractAction.
  			 * @param String name - the name for the action
  			 */
                
                ExitActionBU(String name)
                {
                        super(name);
                }

                /**
                 * This method is called when a ExitAction is performed
                 *When an exitAction is performed it will exit the program.
                 *@param event - the event that occured
                 */

                public void actionPerformed(ActionEvent event)
                {
                        System.exit(0);
                }
        }//ExitAction
        
 /**
  * This class implements a OpenActionBU which is used by the Menu bar in the
  * BottomUPGUI.
  *
  * <p>Title: OO jDREW</p>
  *
  * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
  * 0.88</p>
  *
  * <p>Copyright: Copyright (c) 2005</p>
  *
  * @author Ben Craig
  * @version 0.89
  */    
        class OpenActionBU extends AbstractAction
        {
                /**
                 * This is the contructor for a OpenAction.
                 * It calls the contructor for a AbstractAction.
                 * @param String name - the name for the action
                 */
                
                OpenActionBU(String name)
                {
                        super(name);
                }

                /**
                 * This method is called when a OpenAction is performed
                 * When an openAction is performed it will open a file.
                 * @param event - the event that occured
                 */

                public void actionPerformed(ActionEvent event)
                {
                        BottomUpGUI.openFile();
                }
        }//Open Action
 /**
  * This class implements a WriteActionBU which is used by the Menu bar in the
  * BottomupGUI.
  *
  * <p>Title: OO jDREW</p>
  *
  * <p>Description: Reasoning Engine for the Semantic Web - Supporting OO RuleML
  * 0.88</p>
  *
  * <p>Copyright: Copyright (c) 2005</p>
  *
  * @author Ben Craig
  * @version 0.89
  */       
        class WriteActionBU extends AbstractAction
        {
                /**
                 * This is the contructor for a WriteAction.
                 * It calls the contructor for a AbstractAction.
                 * @param String name - the name for the action
                 */
                
                WriteActionBU(String name)
                {
                        super(name);
                }

                /**
                 * This method is called when a WriteAction is performed
                 * When an exitAction is performed it will write the text
                 * out to a file.
                 * @param event - the event that occured
                 */

                public void actionPerformed(ActionEvent event)
                {
                        BottomUpGUI.writeFile();
                }
        }//Open Action