package GUI;

public class AboutBox extends javax.swing.JDialog {
    
    /** Creates new form AboutBox */
    public AboutBox(java.awt.Frame parent) {
        super(parent);
        initComponents();
        this.setVisible(true);
        this.setTitle("About SortWare");
    }
    
    /** This method is called from within the constructor to
     * initialize the components on the about window
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        devTextLbl = new javax.swing.JLabel();
        pictureLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        devTextLbl.setText("<html><b>Developers:</b> <br><br>Andrew Crites, Vijay Budhram, Quy Bui, Harris Cohen, Jason Dobrinski, Joey Geraci, Eden JnBaptiste, Yaz Khabiri, Micheal Marandici, Robert Marra, Zachary McMullen, Eric Snellman, Micheal Wilder");

        pictureLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/about.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pictureLbl)
                .addGap(18, 18, 18)
                .addComponent(devTextLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(devTextLbl)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel devTextLbl;
    private javax.swing.JLabel pictureLbl;
    // End of variables declaration//GEN-END:variables
    
}
