/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_management_system;


import java.sql.Connection;
import java.beans.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author fkr77
 */
public class Signup_Page extends javax.swing.JFrame {

    /**
     * Creates new form Signup_Page
     */
    String fname, lname, uname, password, con_pass, contact_no;
    Date dob;
    int id=0;
    
          
    public Signup_Page() {
        initComponents();
        
    }
    public int getId(){
//        ResultSet rs= null
        int maxId = 0;
        try{
             Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
             Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "root");
             String sql= "select max(id) from signup";
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt(1);
            }
            id = (maxId == 0) ? 1 : maxId + 1; // Ensure id starts from 1 if no records found
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
//             Statement st = con.createStatement();
//             rs=  st.executeQuery(sql);
//             while(rs.next()){
//                id++;
//                id= rs.getInt(1);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return id;
//    }
    
      boolean validation()
      {
          fname= txt_firstname.getText();
          lname= txt_lastname.getText();
          uname= txt_username.getText();
          password= txt_password.getText();
          con_pass= txt_con_password.getText();
          contact_no= txt_contactno.getText();
          dob= txt_dob.getDate();
          
          if (fname.equals("")){
              JOptionPane.showMessageDialog(this, "Please enter First Name");
              return false;
          }
          if (lname.equals("")){
              JOptionPane.showMessageDialog(this, "Please enter Last Name");
              return false;
          }
          if (uname.equals("")){
              JOptionPane.showMessageDialog(this, "Please enter User Name");
              return false;
          }
          if (password.equals("")){
              JOptionPane.showMessageDialog(this, "Please enter Password");
              return false;
          }
          if (con_pass.equals("")){
              JOptionPane.showMessageDialog(this, "Please Confirm the Password");
              return false;
          }
          if (contact_no.equals("")){
              JOptionPane.showMessageDialog(this, "Please enter Contact Number");
              return false;
          }
          if (dob == null){
              JOptionPane.showMessageDialog(this, "Please enter Date of Birth");
              return false;
          }
          if (!password.equals(con_pass)){
              JOptionPane.showMessageDialog(this, "Password Not Matched");
              return false;
          }
         
          return true;
      }
      
      public void checkPassword(){
         password= txt_password.getText();
         if(password.length()<8){
              lbl_password_error.setText("Password must greater than 8 Characters");
          }
         else{
             lbl_password_error.setText("");
         }
      }
      
      public void checkContactNo(){
          contact_no= txt_contactno.getText();
          if (contact_no.length()==10){
              lbl_contact_error.setText("");
          }
          else{
              lbl_contact_error.setText("Contact No. should be of 10 digits");
          }
      }
      
      void insertDetails(){
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
          String myDob= format.format(dob);
          
                  
          try{
              Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
              Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "root");
              String sql= "insert into signup values(?,?,?,?,?,?,?)";
              PreparedStatement stmt= con.prepareStatement(sql);
              stmt.setInt(1,getId());
              stmt.setString(2, fname);
              stmt.setString(3, lname);
              stmt.setString(4, uname);
              stmt.setString(5, password);
              stmt.setString(6, myDob);
              stmt.setString(7, contact_no);
              int i=stmt.executeUpdate();
              if(i>0){
                  JOptionPane.showMessageDialog(this, "record inserted");
              }
              else{
                   JOptionPane.showMessageDialog(this, "record not inserted");
              }
          }catch(Exception e){
              e.printStackTrace();
          }
      }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jButton2 = new javax.swing.JButton();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_lastname = new javax.swing.JTextField();
        txt_contactno = new javax.swing.JTextField();
        txt_firstname = new javax.swing.JTextField();
        txt_con_password = new javax.swing.JPasswordField();
        txt_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        btn_signup = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lbl_password_error = new javax.swing.JLabel();
        lbl_contact_error = new javax.swing.JLabel();
        txt_dob = new com.toedter.calendar.JDateChooser();

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setText("Signup");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 102, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Signup");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(237, 237, 237)
                .addComponent(jLabel1)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 100));

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("First Name:");

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel3.setText("Last Name:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel4.setText("User Name:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Confirm Password:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setText("Password:");

        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel7.setText("Contact No.:");

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setText("D.O.B:");

        txt_username.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        txt_lastname.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_lastname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lastnameActionPerformed(evt);
            }
        });

        txt_contactno.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_contactno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contactnoActionPerformed(evt);
            }
        });
        txt_contactno.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_contactnoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_contactnoKeyReleased(evt);
            }
        });

        txt_firstname.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txt_firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_firstnameActionPerformed(evt);
            }
        });

        txt_con_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_con_passwordActionPerformed(evt);
            }
        });

        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_passwordKeyPressed(evt);
            }
        });

        btn_login.setBackground(new java.awt.Color(255, 102, 102));
        btn_login.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        btn_signup.setBackground(new java.awt.Color(255, 102, 102));
        btn_signup.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btn_signup.setText("Signup");
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });

        lbl_password_error.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        lbl_password_error.setForeground(new java.awt.Color(102, 0, 0));

        lbl_contact_error.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        lbl_contact_error.setForeground(new java.awt.Color(102, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_lastname, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_username, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_contactno)
                    .addComponent(txt_firstname)
                    .addComponent(txt_con_password)
                    .addComponent(txt_password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(txt_dob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_password_error, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_contact_error, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(btn_signup)
                .addGap(123, 123, 123)
                .addComponent(btn_login)
                .addContainerGap(222, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_firstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(lbl_password_error))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_con_password, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_dob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_contactno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_contact_error, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_login)
                    .addComponent(btn_signup))
                .addGap(17, 17, 17))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 640, 480));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_lastnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lastnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lastnameActionPerformed

    private void txt_contactnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contactnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactnoActionPerformed

    private void txt_firstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_firstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_firstnameActionPerformed

    private void txt_con_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_con_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_con_passwordActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_loginActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        if(validation()){
            insertDetails();
        }
    }//GEN-LAST:event_btn_signupActionPerformed

    private void txt_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyPressed
        checkPassword();
    }//GEN-LAST:event_txt_passwordKeyPressed

    private void txt_contactnoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactnoKeyReleased
checkContactNo();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactnoKeyReleased

    private void txt_contactnoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_contactnoKeyPressed
checkContactNo();         // TODO add your handling code here:
    }//GEN-LAST:event_txt_contactnoKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Signup_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Signup_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Signup_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Signup_Page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Signup_Page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JButton btn_signup;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_contact_error;
    private javax.swing.JLabel lbl_password_error;
    private javax.swing.JPasswordField txt_con_password;
    private javax.swing.JTextField txt_contactno;
    private com.toedter.calendar.JDateChooser txt_dob;
    private javax.swing.JTextField txt_firstname;
    private javax.swing.JTextField txt_lastname;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
