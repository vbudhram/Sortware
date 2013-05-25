package GUI;

//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.util.ArrayList;

import sortingmethods.*;
import steps.Step;
import values.constant.*;
import utils.Logging;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class SetupWizardFrame extends javax.swing.JFrame {
    
   //Second sort is intially not enabled, only changes when chxbox is clicked
   private boolean enableSecondSort = false;
   
   //For testing, example array
   //private int[] initialValues = {2,22,33,4,55,6,16,28,9,40,14,23,13,19,15};
   //private int[] initialValues = {50,45,40,39,38,35,30,25,20,15,10,5,4,3,2};
   private int[] initialValues;
   public SetupWizardFrame() {
      for (SortingMethod sm : SortingMethod.getAllSortingMethods()) {
         Logging.debug("Adding Sorthing Method " + sm.getName()
               + " to the Selection Box");
      }
      /*
       *  TODO Load Profiles, serialization
       *  
       *  Unsigned Applet, package profiles in a jar
       *  SignedApplet Can save / load profiles from users hard drive
       *  
       */
     
      //Initalizes GUI Components
      /*
      try {
	    // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
        // handle exception
        }
        catch (ClassNotFoundException e) {
        // handle exception
        }
        catch (InstantiationException e) {
        // handle exception
        }
        catch (IllegalAccessException e) {
        // handle exception
        }
      */
      initComponents();
      pack();
      setVisible(true);
      
      // Set up tool tips
      // Yaz K.
      ArrayTypePullDown.setToolTipText("Sort data type entry");
      customArrayField.setToolTipText("Custom data entry area");
      exitButton.setToolTipText("Exit Program");
//      mainPanel.setToolTipText("4");
      secondSortChxBox.setToolTipText("Enable/Disable second sort");
      sortButton.setToolTipText("Simulate the selected sort(s)");
      sortOption1.setToolTipText("First sort selection");
      sortOption2.setToolTipText("Second sort selection");
        
    }
                 
   public void exec(int[] initialValues, SortingMethod method1,SortingMethod method2) {
     //Step[][] steps = method.solve(initialValues);
     //this.setVisible(false);
     //new SimulationFrame(steps);
      
       //checks to see if data is acceptable
       //   *length 1-15
       //   *min value 1, max value 50
       //-Quy
       boolean goodToGo = true;
       int i;
       for(i=0; i<initialValues.length; i++){
            if(initialValues[i] < 1 || initialValues[i] >50){
                JOptionPane.showMessageDialog(this,"Please enter values between 1 and 50",
                                                 "Invalid Input",JOptionPane.WARNING_MESSAGE);
                customArrayField.setText(null);
                goodToGo = false;
            }
       }
       
       if(initialValues.length > 15){
           JOptionPane.showMessageDialog(this,"Maximum array length is 15.",
                                            "Invalid Input",JOptionPane.WARNING_MESSAGE);
           customArrayField.setText(null);
           goodToGo = false;
       }
       
       else if(initialValues.length == 0){
           JOptionPane.showMessageDialog(this,"Array can not be empty.",
                                            "Invalid Input",JOptionPane.WARNING_MESSAGE);
           customArrayField.setText(null);
           goodToGo = false;
       }
       
       
       else if(goodToGo){
            //For testing
            this.setVisible(false);
            if(enableSecondSort){
                Step[][] steps1 = method1.solve(initialValues);
                Step[][] steps2 = method2.solve(initialValues);
                new SimulationFrame(steps1,steps2, method1.getName(), method2.getName());
            }
            else{
                Step[][] steps1 = method1.solve(initialValues);
                new SimulationFrame(steps1, method1.getName());
            }
            
      }
       
   }
   
   //Initalizes all the components on the first screen
   private void initComponents() {
        
        sortOption1 = new javax.swing.JComboBox();
        secondSortChxBox = new javax.swing.JCheckBox();
        sortOption2 = new javax.swing.JComboBox();
        customArrayField = new javax.swing.JTextField();
        ArrayTypePullDown = new javax.swing.JComboBox();
        sortButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SortWare");

        sortOption1.setMaximumRowCount(10);
        sortOption1.setModel(new javax.swing.DefaultComboBoxModel(values.constant.Sorts.SORTING_METHODS));
        sortOption1.setPreferredSize(new java.awt.Dimension(121, 20));

        secondSortChxBox.setText("2nd Sort");

        sortOption2.setMaximumRowCount(10);
        sortOption2.setModel(new javax.swing.DefaultComboBoxModel(values.constant.Sorts.SORTING_METHODS));
        sortOption2.setPreferredSize(new java.awt.Dimension(121, 20));
        sortOption2.setEnabled(false);

        ArrayTypePullDown.setMaximumRowCount(20);
        ArrayTypePullDown.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Random Array", "Custom Array", "Merge Sort Best", "Quick Sort Best", "Insertion Sort Best", "Heap Sort Best", "Rank Sort Best", "Modified Bin Sort Best", "Selection Sort Best", "Merge Sort Worst", "Quick Sort Worst", "Insertion Sort Worst", "Heap Sort Worst", "Rank Sort Worst", "Modified Bin Sort Worst", "Selection Sort Worst" }));
        ArrayTypePullDown.setPreferredSize(new java.awt.Dimension(121, 20));
        
        customArrayField.setEnabled(false);

        sortButton.setText("Sort");

        exitButton.setText("Exit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ArrayTypePullDown, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sortOption1, javax.swing.GroupLayout.Alignment.LEADING, 0, 125, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addComponent(secondSortChxBox))
                    .addComponent(customArrayField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortOption2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sortOption1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(secondSortChxBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ArrayTypePullDown, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(customArrayField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sortOption2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(sortButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
        
              //Action performed when user clicks exit button, exits program
      //-Vijay
      exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }

        });
       
      //Action performed when user clicks sort button, calls exec method with
      //whatever sort method is selected from the drop down menus.
      //-Vijay
      sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                
                //Variables that hold the sorting methods found in the drop downs
                SortingMethod method1 = null;
                SortingMethod method2 = null;
                
                //Creates the specific sorting method object depending on which item is
                //selected in the 2nd drop down, only if comparing two sorts
                if(enableSecondSort){
                   String SelectedItem2 = sortOption2.getSelectedItem().toString();
                   
                   if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.BUBBLE_SORT])){
                        method2 = new BubbleSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.MERGE_SORT])){
                        method2 = new MergeSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.QUICK_SORT])){
                        method2 = new QuickSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.INSERTION_SORT])){
                        method2 = new InsertionSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.HEAP_SORT])){
                        method2 = new HeapSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.RANK_SORT])){
                        method2 = new RankSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.BUCKET_SORT])){
                        method2 = new BucketSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.SIMPLE_SELECTION_SORT])){
                        method2 = new SimpleSelectionSort();
                   }
                   else if(SelectedItem2.contentEquals(Sorts.SORTING_METHODS[Sorts.SELECTION_SORT])){
                        method2 = new SelectionSort();
                   }
                }
                
                String SelectedItem1 = sortOption1.getSelectedItem().toString();
                
                
                if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.BUBBLE_SORT])){
                    method1 = new BubbleSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.MERGE_SORT])){
                    method1 = new MergeSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.QUICK_SORT])){
                    method1 = new QuickSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.INSERTION_SORT])){
                    method1 = new InsertionSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.HEAP_SORT])){
                    method1 = new HeapSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.RANK_SORT])){
                    method1 = new RankSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.BUCKET_SORT])){
                    method1 = new BucketSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.SIMPLE_SELECTION_SORT])){
                    method1 = new SimpleSelectionSort();
                }
                else if(SelectedItem1.contentEquals(Sorts.SORTING_METHODS[Sorts.SELECTION_SORT])){
                    method1 = new SelectionSort();
                }                
                
                //Array Type Pull Down Selction
                //-Quy
                String PresetSelection = ArrayTypePullDown.getSelectedItem().toString();
                          
                if(PresetSelection.contentEquals("Merge Sort Best")){
                    initialValues = PresetValues.MERGE_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Merge Sort Worst")){
                    initialValues = PresetValues.MERGE_SORT_WORST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Quick Sort Best")){
                    initialValues = PresetValues.QUICK_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Quick Sort Worst")){
                    initialValues = PresetValues.QUICK_SORT_WORST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Insertion Sort Best")){
                    initialValues = PresetValues.INSERTION_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Insertion Sort Worst")){
                    initialValues = PresetValues.INSERTION_SORT_WORST_CASE;
                }
                             
                else if(PresetSelection.contentEquals("Heap Sort Best")){
                    initialValues = PresetValues.HEAP_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Heap Sort Worst")){
                    initialValues = PresetValues.HEAP_SORT_WORST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Rank Sort Best")){
                    initialValues = PresetValues.RANK_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Rank Sort Worst")){
                    initialValues = PresetValues.RANK_SORT_WORST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Modified Bin Sort Best")){
                    initialValues = PresetValues.BUCKET_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Modified Bin Sort Worst")){
                    initialValues = PresetValues.BUCKET_SORT_WORST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Selection Sort Best")){
                    initialValues = PresetValues.SELECTION_SORT_BEST_CASE;
                }
                
                else if(PresetSelection.contentEquals("Selection Sort Worst")){
                    initialValues = PresetValues.SELECTION_SORT_WORST_CASE;
                }
                else if(PresetSelection.contentEquals("Random Array"))
                {
                    // Yaz K.
                    // Generate random array of numbers for user
                    customArrayField.setEnabled(false);
                    Random gen = new Random();
                    
                    initialValues = new int[15];
                    
                    for ( int i = 0; i < 15; ++i )
                        initialValues[i] = gen.nextInt(50) + 1; // 1 to 50
                }
                
                else if(PresetSelection.contentEquals("Custom Array")){
                    initialValues = readIntLine();
                }
                
                
                //Calls exec with sorting methods found above as well as initialValues chosen
                exec(initialValues,method1,method2);
            }

        });
      
        
       //Disables the text field for custom array input unless "Custom Array" is 
       //selected
       //-Quy 
       ArrayTypePullDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String PresetSelection = ArrayTypePullDown.getSelectedItem().toString();
                if(PresetSelection.contentEquals("Custom Array")){ 
                   customArrayField.setEnabled(true);
                                     customArrayField.setToolTipText("Enter up to 15 numbers to be sorted, each with a max value of 50 and separated by a space");
                }
                else{
                  customArrayField.setEnabled(false);

                }
            }
        }); 
        
      //Action performed when user clicks second Sort check box, updates enableSecondSort variable
      //when checkbox is checked enableSecondSort = true, else false, makes the dropmenu enabled
      //or disabled whether the checkbox is checked or not
      //-Vijay
      secondSortChxBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if(enableSecondSort){
                   enableSecondSort = false; 
                   sortOption2.setEnabled(false);
                }
                else{
                   enableSecondSort = true;
                   sortOption2.setEnabled(true);
                }
            }
        });
        
        //centers window
        //-Quy
        setLocationRelativeTo(null);
    }
   
   //reads input from custom array input field
   //-Quy
    public static int[] readIntLine( ){
       
        String inputString = customArrayField.getText();
	StringTokenizer st = new StringTokenizer( inputString );
	int numberOfValues = st.countTokens( );
	int[] values = new int[numberOfValues];

	for ( int index = 0; index < numberOfValues; index = index + 1 )
	{			
		String stringValue = st.nextToken( );
                try{
                    values[index] = Integer.parseInt( stringValue );
                }
                catch (NumberFormatException e)
		{
			JOptionPane.showMessageDialog(null,"Please enter integer values.",
                                            "Invalid Input",JOptionPane.WARNING_MESSAGE);
                        customArrayField.setText(null);
                        values = null;                   
		}
	}
	return values;
    }
      
   private javax.swing.JComboBox ArrayTypePullDown;
   private static javax.swing.JTextField customArrayField;
   private javax.swing.JButton exitButton;
   private javax.swing.JPanel mainPanel;
   private javax.swing.JCheckBox secondSortChxBox;
   private javax.swing.JButton sortButton;
   private javax.swing.JComboBox sortOption1;
   private javax.swing.JComboBox sortOption2;
           
           
}
