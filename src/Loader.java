/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Loader.java
 *
 * Created on May 7, 2010, 12:13:41 PM
 */

import javax.swing.Timer;


/**
 *
 * @author bare bones
 */
public class Loader extends javax.swing.JFrame {
  private static String language = System.getProperty("user.language"), loadingLanguage = (language.equals("es") ? "Cargando..." : (language.equals("de") ? "Wird geladen..." : "Loading..."));
  private static int progress = 0;

    /** Creates new form Loader */
    public Loader() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jProgressBar1 = new javax.swing.JProgressBar();
    jLabel1 = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle(loadingLanguage);
    setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
    setLocationByPlatform(true);
    setResizable(false);
    setUndecorated(true);

    jProgressBar1.setMaximum(100);
    jProgressBar1.setToolTipText(loadingLanguage);
    jProgressBar1.setValue(progress);
    jProgressBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
    jProgressBar1.setString(loadingLanguage);
    jProgressBar1.setStringPainted(true);

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BHC logo.png"))); // NOI18N
    jLabel1.setToolTipText(loadingLanguage);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    pack();
  }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loader().setVisible(true);
                progress = jProgressBar1.getMinimum();
            }
        });
        Timer timer = new Timer(50, new java.awt.event.ActionListener()
        {
          public void actionPerformed(java.awt.event.ActionEvent evt)
          {
            if (progress < jProgressBar1.getMaximum())
              progress++;
            else
              progress = jProgressBar1.getMinimum();
            jProgressBar1.setValue(progress);
            jProgressBar1.repaint();
          }
        });

        timer.start();
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  public static javax.swing.JProgressBar jProgressBar1;
  // End of variables declaration//GEN-END:variables

}
