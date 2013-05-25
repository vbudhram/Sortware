package GUI;

//import java.awt.Dimension;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Visuals.*;
import values.constant.*;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import steps.Step;

import javax.swing.Timer;
import javax.swing.event.ChangeListener;


public class SimulationFrame extends javax.swing.JFrame implements ActionListener
{
    final Step[][] steps1;
    final Step[][] steps2;
    private VisualizationGraph playArea1Graph;
    private VisualizationGraph playArea2Graph;
    private boolean secondSortEnabled;
    private int panelArea1index1;
    private int panelArea1index2;
    private int panelArea2index1;
    private int panelArea2index2;
    private boolean isPlaying = false;
    
    private javax.swing.Timer timer;
    private final int BaseTimerLength = 500;
    
    public class SpeedAccessor {
    	SimulationFrame frame;
    	public SpeedAccessor(SimulationFrame f) {
    		frame = f;
    	}
    	public int getSpeed() {
    		return frame.getTimerLength();
    	}
    }
    
    /*
     * This is a testing constructor for Simulation Screen. Since most of the functions
     * for the program are not made yet you can still run this and test the button
     * controls.
     */
    public SimulationFrame(Step[][] steps1, String sort1){
	//TODO Add GUI Components
	setVisible(true);
	pack();
        initComponents();
        
        this.steps1 = steps1;
        this.steps2 = steps1;
        System.out.println("sort = "+sort1);
        System.out.println("bucket = "+Sorts.SORTING_METHODS[Sorts.BUCKET_SORT]);
        /* Check what type of sort we are showing and create the appropriate graph type */
        SpeedAccessor sa = new SpeedAccessor(this);
        if (sort1.equals(Sorts.SORTING_METHODS[Sorts.HEAP_SORT])) {
        	playArea1Graph = new TreeGraph(this.steps1,panelArea1,sa);
        	playArea2Graph = new TreeGraph(this.steps2,panelArea2,sa);
        }
        else if (sort1.equals(Sorts.SORTING_METHODS[Sorts.RANK_SORT])) {
        	System.out.println("Rank sort recognized");
        	playArea1Graph = new RankGraph(this.steps1,panelArea1, sa);
            playArea2Graph = new RankGraph(this.steps2,panelArea2, sa);
        }
        else if (sort1.equals(Sorts.SORTING_METHODS[Sorts.BUCKET_SORT])) {
        	System.out.println("bucket sort");
        	playArea1Graph = new BucketGraph(this.steps1,panelArea1, sa);
        	playArea2Graph = new BucketGraph(this.steps2,panelArea2, sa);
        }
        else {
        	playArea1Graph = new BarGraph(this.steps1,panelArea1, sa);
            playArea2Graph = new BarGraph(this.steps2,panelArea2, sa);
        }
        
        panelArea1.setGraph(playArea1Graph);
        panelArea2.setGraph(new BarGraph(values.constant.PresetValues.EMPTY_STEP_ARRAY, panelArea2, sa));
        panelArea1Lbl.setText(sort1);
        panelArea2Lbl.setVisible(false);
        
        secondSortEnabled = false;
        
        panelArea1.draw(0, 0);
        txtBoxDialogPanel1();
        
        setupToolTips();
	}
    
    /*
     * This constructor is used if the user selects to have two simulations. Initalizes
     * both steps1 and steps2, and creates BarGraph for both.
     */
    public SimulationFrame(Step[][] steps1,Step[][] steps2, String sort1, String sort2){
	//TODO Add GUI Components
	setVisible(true);
	pack();
        initComponents();
        
        this.steps1=steps1;
        this.steps2=steps2;

        /* Check what type of sort we are showing and create the appropriate graph type */
    	SpeedAccessor sa = new SpeedAccessor(this);
        if (sort1.equals(Sorts.SORTING_METHODS[Sorts.HEAP_SORT])) {
        	playArea1Graph = new TreeGraph(this.steps1,panelArea1,sa);
        }
        else if (sort1.equals(Sorts.SORTING_METHODS[Sorts.RANK_SORT])) {
        	System.out.println("Rank sort recognized");
        	playArea1Graph = new RankGraph(this.steps1,panelArea1, sa);
        }
        else if (sort1.equals(Sorts.SORTING_METHODS[Sorts.BUCKET_SORT])) {
        	playArea1Graph = new BucketGraph(this.steps1,panelArea1, sa);
        }
        else {
        	playArea1Graph = new BarGraph(this.steps1,panelArea1, sa);
        }
        if (sort2.equals(Sorts.SORTING_METHODS[Sorts.HEAP_SORT])) {
        	playArea2Graph = new TreeGraph(this.steps2,panelArea2,sa);
        }
        else if (sort2.equals(Sorts.SORTING_METHODS[Sorts.RANK_SORT])) {
        	System.out.println("Rank sort recognized");
            playArea2Graph = new RankGraph(this.steps2,panelArea2, sa);
        }
        else if (sort2.equals(Sorts.SORTING_METHODS[Sorts.BUCKET_SORT])) {
        	playArea2Graph = new BucketGraph(this.steps2,panelArea2, sa);
        }
        else {
        	playArea2Graph = new BarGraph(this.steps2,panelArea2, sa);
        }
        
        panelArea1.setGraph(playArea1Graph);
        panelArea2.setGraph(playArea2Graph);
        
        panelArea1Lbl.setText(sort1);
        panelArea2Lbl.setText(sort2);
        
        txtBoxDialogPanel1();
        txtBoxDialogPanel2();
        
        secondSortEnabled = true;  
        setupToolTips();
    }
    
    //Yaz K.
    private void setupToolTips()
    {
        backwardButton.setToolTipText("Go back one compare/swap");
        fowardButton.setToolTipText("Go forward one compare/swap");
        fowardPassButton.setToolTipText("Go forward one iteration");
        backwardPassButton.setToolTipText("Go back one iteration");
        exitButton.setToolTipText("Exit program");
        gotoBeginButton.setToolTipText("Go to first step (Begin sort(s))");
        gotoEndButton.setToolTipText("Go to last step (Finish sort(s))");
        helpButton.setToolTipText("Help");
        panelArea1.setToolTipText("Progress of sort(s)");
        panelArea2.setToolTipText("Progress of sort(s)");
        playPauseButton.setToolTipText("Play/Pause sort(s)");
        //setupButton.setToolTipText("");
        speedSlider.setToolTipText("Control speed of Play");
        speedSliderlbl.setToolTipText("Control speed of Play");
        panelArea1Lbl.setToolTipText("Currently selected sort(s)");
        panelArea2Lbl.setToolTipText("Currently selected sort(s)");
        //menuBar.setToolTipText("");
        //fileMenuButton.setToolTipText("");
        //helpMenuButton.setToolTipText("");
        aboutMenuItem.setToolTipText("About");
        exitMenuItem.setToolTipText("Exit program");
        setupMenuItem.setToolTipText("Setup Wizard");
        helpMenuItem.setToolTipText("Program Help");
        txtDisplayBoxPanel1.setToolTipText("Description of current step");
        txtDisplayBoxPanel2.setToolTipText("Description of current step");
    }
    
    //Closes Simulation Screen and creates a new Setup Screen
    public void gotoSetup(){
        this.dispose();
        new SetupWizardFrame();
    }
    
    /**
     * Calculate timer length based on speed slider value
     * @returns timer length
     */
    private int getTimerLength()
    {
        int speedSliderValue = speedSlider.getValue() - 50;
        int TimerLength;
            
        if ( speedSlider.getValue() < 50 )
        {
            speedSliderValue = speedSlider.getValue();
            TimerLength = BaseTimerLength + BaseTimerLength * ( 50 - speedSliderValue ) / 50;
        }
        else
        {
            speedSliderValue = speedSlider.getValue() - 50;
            TimerLength = BaseTimerLength - ( BaseTimerLength / 2 ) * speedSliderValue / 50;

        }
        
        return TimerLength;
    }
    
    /**
     * Displays the first step in the
     * list at step[0][0]
     */
    public void gotoFirst()
    {
        //Sets the indexes to the first step in the first pass, and paints it on the screen
        panelArea1index1 = panelArea1index2 = panelArea2index1 = panelArea2index2 = 0;
        panelArea1.draw(panelArea1index1, panelArea1index2);
        
        //Out the current position of the indexes
        txtBoxDialogPanel1();       
        
        //Paints second sort image only if it is enabled
        if ( secondSortEnabled ){
            panelArea2.draw(panelArea2index1, panelArea2index2);            
            txtBoxDialogPanel2();  
        }
        

    }
    
    /**
     * Displays the step directly before 
     * the current step
     */
    public void gotoPrevious(){  
        
        /*
         * Goes to the previous step of sort 1, by decrementing the indexes
         * Decrements index2, if index2 is -1 then it decrements index1, if index1 is -1
         * that means your at the first step and its sets both indexes to 0.
         */ 
        if(panelArea1index2>=0)
            --panelArea1index2;
        if(panelArea1index2==-1){
            --panelArea1index1;
            
            if(panelArea1index1==-1){
                panelArea1index1=panelArea1index2=0;
            }
            else  
                panelArea1index2=steps1[panelArea1index1].length-1;
        }
        
        //Paints the image found from the above indexes
        panelArea1.draw(panelArea1index1, panelArea1index2);
        txtBoxDialogPanel1(); 
      
        /*
         * Goes to the previous step of sort 2 if enabled, by decrementing the indexes
         * Decrements index2, if index2 is -1 then it decrements index1, if index1 is -1
         * that means your at the first step and its sets both indexes to 0.
         */ 
        if ( secondSortEnabled ){
            if(panelArea2index2>=0)
                --panelArea2index2;
            if(panelArea2index2==-1){
                --panelArea2index1;
            
                if(panelArea2index1==-1){
                    panelArea2index1=panelArea2index2=0;
                }
                else  
                    panelArea2index2=steps2[panelArea2index1].length-1;
            }
        //Paints second sort image if enabled
        panelArea2.draw(panelArea2index1, panelArea2index2);
        txtBoxDialogPanel2(); 
        }    
    }
    
    /**
     * Will play if paused and pause if playing
     */
    public void gotoPlayPause()
    {
        if ( !isPlaying )
        {
            isPlaying = true;
            playPauseButton.setText("Pause");
            //txtDisplayBox1.setText("Playing\n");
            
            //txtDisplayBox1.setText("Slider= " + speedSlider.getValue() + "\n");
            //txtDisplayBox1.setText("Slider Speed= " + getTimerLength() + "\n");
            
            timer = new Timer(getTimerLength(), this); // Timer Setup
            timer.start();
        }
        
        else
        {
            isPlaying = false;
            playPauseButton.setText("Play");
            //txtDisplayBox1.setText("Paused\n");
            
            timer.stop();
        }
        
        
    }
    
    /**
     * Displays the step directly
     * after the next step
     */
    public void gotoNext()
    {        
        //Increments panelArea1index1 as a step increment, if your at the end of that pass, it goes to the next pass
        if(panelArea1index1 < steps1.length)
        {
            if(panelArea1index2 < steps1[panelArea1index1].length)
            {                                
                panelArea1index2++;
                if(panelArea1index2 < steps1[panelArea1index1].length){                
                    txtBoxDialogPanel1();
                }
                 
            }
            if(panelArea1index2 >= steps1[panelArea1index1].length)
            {
                panelArea1index1++;
                panelArea1index2=0;
                if(panelArea1index1 < steps1.length){
                    txtBoxDialogPanel1();
                }
            }
        }
        
        //Draws the next step on the panel or the last step if your at the end
        if(panelArea1index1==steps1.length){
            panelArea1index1 = steps1.length-1;
            panelArea1index2 = steps1[panelArea1index1].length-1;
            panelArea1.draw(panelArea1index1,panelArea1index2);
        }
        else
             panelArea1.draw(panelArea1index1, panelArea1index2);                 
        
        if ( secondSortEnabled ){
            if(panelArea2index1 < steps2.length){
                if(panelArea2index2 < steps2[panelArea2index1].length)
                {                    
                    panelArea2index2++;
                    if(panelArea2index2 < steps2[panelArea2index1].length){
                        txtBoxDialogPanel2();
                    }
                    
                }
                if(panelArea2index2 >= steps2[panelArea2index1].length)
                {
                    panelArea2index1++;
                    panelArea2index2=0;
                    if(panelArea2index1 < steps2.length){
                        txtBoxDialogPanel2();
                    }
                }
            }   
         
            if(panelArea2index1==steps2.length){
                panelArea2index1 = steps2.length-1;
                panelArea2index2 = steps2[panelArea2index1].length-1;
                panelArea2.draw(panelArea2index1,panelArea2index2);
            }
            else
             panelArea2.draw(panelArea2index1, panelArea2index2);     
        }
    }
    
    /**
     * Displays the last step in the list
     * at step[step.length-1][step[step.length-1].length-1]
     */
    public void gotoLast()
    {
        //Go to Last Step
        
        //Makes panelArea1index1,panelAreaindex2 the last step, in the last pass
        panelArea1index1 = steps1.length-1;
        panelArea1index2 = steps1[panelArea1index1].length-1;
        panelArea1.draw(panelArea1index1, panelArea1index2);
        
        txtBoxDialogPanel1();             
            
        //Makes panelArea2index1,panelArea2index2 the last step, in the last pass
        //if second sort is enabled
        if ( secondSortEnabled ){
            panelArea2index1 = steps2.length-1;
            panelArea2index2 = steps2[panelArea2index1].length-1;
            panelArea2.draw(panelArea2index1, panelArea2index2);
                        
            txtBoxDialogPanel2(); 
        }
           
    }
    
    /**
     * Creates and displays a new Jframe to display 
     * instructions on how to use the program
     */
    public void gotoHelp()
    {
        new HelpDialog(this);
    }
    
    /**
     * Creates and displays a new Jframe to display 
     * information about who created the program
     */
    public void gotoAbout()
    {
        new AboutBox(this);
    }    
    
    //Goes back to the next pass of the sorting method
    public void gotoFowardPass(){
        
        /*
         * Goes to the next pass of the first sorting method, by incrementing index1,
         * If index is equal to the lenght of the steps-1, that means your at the last pass,
         * and it just paints the last pass
         */
        if ( panelArea1index1 < (steps1.length-1))
        {
            panelArea1index1++;
            panelArea1index2=0;
        }
        else{
            panelArea1index1 = steps1.length-1;
            panelArea1index2 = steps1[panelArea1index1].length-1;
        }
        
        //Paints the next pass for the first sort
        panelArea1.draw(panelArea1index1, panelArea1index2);
        
        //Updates text box with what pass your currently in
        txtBoxDialogPanel1();                    
        
        /*
         * Goes to the next pass of the second sorting method, by incrementing index1,
         * If index is equal to the lenght of the steps-1, that means your at the last pass,
         * and it just paints the last pass
         */
        if(secondSortEnabled){
            if ( panelArea2index1 < (steps2.length-1)){
                panelArea2index1++;
                panelArea2index2=0;
            }
            else{
                panelArea2index1 = steps2.length-1;
                panelArea2index2 = steps2[panelArea2index1].length-1;
            } 
            //Paints the next pass for the first sort
            panelArea2.draw(panelArea2index1, panelArea2index2);
            
            //Updates text box with what pass your currently in for the second sort
            txtBoxDialogPanel2();            
        }
        
    }
    
    //Goes back to the previous pass of the sorting method,will give detailed comments later
    public void gotoBackwardPass(){
        
        /*
         Drecrements index1 and sets index2=0, this is equivalent to being in the first
         step of the previous pass
        */
        if ( panelArea1index1 > 0)
        {
            --panelArea1index1;
            panelArea1index2=0;
        }
        else{  //if your at the first pass
            panelArea1index1 = 0;
            panelArea1index2 = 0;
        }
        
        //Paints the graph to the screen
        panelArea1.draw(panelArea1index1, panelArea1index2);
        
        //Updates textBox with what happened
        txtBoxDialogPanel1();                    
        
        //Perform the extact operation on the second sort if enabled
        if(secondSortEnabled){
            if ( panelArea2index1 > 0){
                --panelArea2index1;
                panelArea2index2=0;
            }
            else{
                panelArea2index1 = 0;
                panelArea2index2 = 0;
            } 
            
            panelArea2.draw(panelArea2index1, panelArea2index2);
           
            txtBoxDialogPanel2();            
        }
    }
    
    /**
     * The action performed when the program is playing
     */
    public void actionPerformed(ActionEvent event)
    {
        gotoNext();
    }
  
    /*
     * This displays to the text area
     * the details of the current step 
     * occuring in panel 1
     */
    public void txtBoxDialogPanel1()
    {
        //txtDisplayBox1.setText("Passes occured in Panel One= " + (panelArea1index1) + "\n");
        //txtDisplayBox1.append("Events occured in Panel One= " + (panelArea1index2) + "\n");
        //txtDisplayBox1.setText(steps1[panelArea1index1][panelArea1index2].toString() + "\n"); 
        txtDisplayBoxPanel1.draw(steps1[panelArea1index1][panelArea1index2].toString());
    }    

    /*
     * This displays to the text area
     * the details of the current step 
     * occuring in panel 2
     */
    public void txtBoxDialogPanel2()
    {
        //txtDisplayBox2.setText("\nPasses occured in Panel Two= " + (panelArea2index1) + "\n");
        //txtDisplayBox2.append("Events occured in Panel Two= " + (panelArea2index2) + "\n");
        //txtDisplayBox2.setText(steps2[panelArea2index1][panelArea2index2].toString() + "\n"); 
        txtDisplayBoxPanel2.draw(steps2[panelArea2index1][panelArea2index2].toString());
    }
      
    //Initalizes GUI components
   private void initComponents() {
        this.setTitle("SortWare");
        
        panelArea2 = new GUI.Visuals.VisualizationPanel(playArea2Graph);
        panelArea2Lbl = new javax.swing.JLabel("Sort2");
        panelArea1 = new GUI.Visuals.VisualizationPanel(playArea1Graph);
        panelArea1Lbl = new javax.swing.JLabel("Sort1");
        
        txtDisplayBoxPanel1 = new GUI.Text.TextPanel();
        txtDisplayBoxPanel2 = new GUI.Text.TextPanel();
                               
        menuBar = new javax.swing.JMenuBar();
        fileMenuButton = new javax.swing.JMenu();
        setupMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenuButton = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();        
        
        gotoBeginButton = new javax.swing.JButton("<<<");
        backwardPassButton = new javax.swing.JButton("<<");
        backwardButton = new javax.swing.JButton("<");
        playPauseButton = new javax.swing.JButton("Play");
        fowardButton = new javax.swing.JButton(">");
        fowardPassButton = new javax.swing.JButton(">>");
        gotoEndButton = new javax.swing.JButton(">>>");
        helpButton = new javax.swing.JButton("Help");
        setupButton = new javax.swing.JButton("Setup");
        exitButton = new javax.swing.JButton("Exit");

        speedSliderlbl = new javax.swing.JLabel("Speed");
        speedSlider = new javax.swing.JSlider(JSlider.HORIZONTAL, 0, 100, 20);   
        
        fileMenuButton.setText("File");
        setupMenuItem.setText("Setup");
        fileMenuButton.add(setupMenuItem);
        exitMenuItem.setText("Exit");
        fileMenuButton.add(exitMenuItem);
        menuBar.add(fileMenuButton);
        
        helpMenuButton.setText("Help");
        helpMenuItem.setText("Help");
        helpMenuButton.add(helpMenuItem);
        aboutMenuItem.setText("About");
        helpMenuButton.add(aboutMenuItem);
        menuBar.add(helpMenuButton);
        
        setJMenuBar(menuBar);     
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelArea1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelArea1Layout = new javax.swing.GroupLayout(panelArea1);
        panelArea1.setLayout(panelArea1Layout);
        panelArea1Layout.setHorizontalGroup(
            panelArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );
        panelArea1Layout.setVerticalGroup(
            panelArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        panelArea2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelArea2Layout = new javax.swing.GroupLayout(panelArea2);
        panelArea2.setLayout(panelArea2Layout);
        panelArea2Layout.setHorizontalGroup(
            panelArea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 364, Short.MAX_VALUE)
        );
        panelArea2Layout.setVerticalGroup(
            panelArea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        txtDisplayBoxPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout txtDisplayBoxPanel1Layout = new javax.swing.GroupLayout(txtDisplayBoxPanel1);
        txtDisplayBoxPanel1.setLayout(txtDisplayBoxPanel1Layout);
        txtDisplayBoxPanel1Layout.setHorizontalGroup(
            txtDisplayBoxPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        txtDisplayBoxPanel1Layout.setVerticalGroup(
            txtDisplayBoxPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        txtDisplayBoxPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout txtDisplayBoxPanel2Layout = new javax.swing.GroupLayout(txtDisplayBoxPanel2);
        txtDisplayBoxPanel2.setLayout(txtDisplayBoxPanel2Layout);
        txtDisplayBoxPanel2Layout.setHorizontalGroup(
            txtDisplayBoxPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );
        txtDisplayBoxPanel2Layout.setVerticalGroup(
            txtDisplayBoxPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(509, 509, 509)
                                .addComponent(panelArea2Lbl))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(160, 160, 160)
                                .addComponent(panelArea1Lbl))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtDisplayBoxPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDisplayBoxPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(panelArea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(speedSliderlbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gotoBeginButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backwardPassButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backwardButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(playPauseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fowardButton)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fowardPassButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gotoEndButton)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelArea2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(panelArea1Lbl)
                    .addComponent(panelArea2Lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDisplayBoxPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDisplayBoxPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gotoBeginButton)
                    .addComponent(backwardPassButton)
                    .addComponent(backwardButton)
                    .addComponent(playPauseButton)
                    .addComponent(fowardButton)
                    .addComponent(fowardPassButton)
                    .addComponent(gotoEndButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(speedSliderlbl)
                    .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        pack();
        
        //Action performed when user selects exit from menu bar
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        
        //Action performed when user selects setup from menu bar
        //See comments on gotoSetup() function
        setupMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gotoSetup();
            }
        });
        
        //Action performed when user selects about from menu bar
        //See comments on gotoAbout() function
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gotoAbout();
            }
        });
        
        //Action performed when user clicks '<<<' button
        //See comments on gotoFirst() function
        gotoBeginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }
                gotoFirst();
            }
        });
        
        //Action performed when user clicks '<<' button
        //See comments on gotoPrevious() function
        backwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }                
                gotoPrevious();
            }
        });
        
        //Action performed when user clicks 'PlayPause' button
        //See comments on gotoPlayPause() function
        playPauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                gotoPlayPause();
            }
        });

        //Action performed when user clicks slides speed slider
        //See comments on gotoPlayPause() function
        //Allows speed to be updated immediately
        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    gotoPlayPause();
                    gotoPlayPause();
                }
            }
        });        
        
        //Action performed when user clicks '>>' button
        //See comments on gotoNext() function
        fowardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }                
                gotoNext();
            }
        });
        
        //Action performed when user clicks '>>>' button
        //See comments on gotoLast() function
        gotoEndButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }                
                gotoLast();
            }
        });
        
        //Action performed when user selects help from menu bar
        //See comments on gotoHelp() function
        helpMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {              
                gotoHelp();
            }
        });
        
        //Action performed when user clicks '>>' button
        //See comments on gotoFowardPass() function
        fowardPassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }                
                gotoFowardPass();
            }
        });
        
        //Action performed when user clicks '<<' button
        //See comments on gotoBackwardPass() function
        backwardPassButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (isPlaying){
                    gotoPlayPause();
                }                
                gotoBackwardPass();
            }
        });
        
        
        //Centers Window
        //-Quy
        setLocationRelativeTo(null);
        
	}
         
   
    private javax.swing.JButton backwardButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton fowardButton;
    private javax.swing.JButton gotoBeginButton;
    private javax.swing.JButton gotoEndButton;
    private javax.swing.JButton helpButton;
    private GUI.Visuals.VisualizationPanel panelArea1;
    private GUI.Visuals.VisualizationPanel panelArea2;
    private javax.swing.JButton playPauseButton;
    private javax.swing.JButton setupButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton fowardPassButton;
    private javax.swing.JButton backwardPassButton;
    private javax.swing.JLabel speedSliderlbl;
    private javax.swing.JLabel panelArea1Lbl;
    private javax.swing.JLabel panelArea2Lbl;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu fileMenuButton;
    private javax.swing.JMenu helpMenuButton;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem setupMenuItem;
    private javax.swing.JMenuItem helpMenuItem;            
    private GUI.Text.TextPanel txtDisplayBoxPanel1;
    private GUI.Text.TextPanel txtDisplayBoxPanel2;    

}
