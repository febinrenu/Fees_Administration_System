/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fees_management_system;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 *
 * @author fkr77
 */
public class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public UpdateFeesDetails() {
        initComponents();
        diplayCashFirst();
        fillComboBox();
        
        int receiptNo= getReceiptNo();
        txt_receiptNo.setText(Integer.toString(receiptNo));
        
        setRecords();
    }
    
    public void diplayCashFirst(){
        lbl_DDNo.setVisible(false);
        lbl_chequeNo.setVisible(false);
        lbl_bankName.setVisible(false);
        
        txt_DDNo.setVisible(false);
        txt_ChequeNo.setVisible(false);
        txt_bankName.setVisible(false);
    }
    
    public boolean validation(){
        if(txt_receivedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter User Name");
            return false;
        }
        if(dateChooser.getDate()== null){
            JOptionPane.showMessageDialog(this, "Please selct a date");
            return false;
        }
        if(txt_amount.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Please enter an amount(numerically)");
            return false;
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("Cheque")){
            if(txt_ChequeNo.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter Cheque Number");
                return false;
            }
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter Bank Name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("dd")){
            if(txt_DDNo.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter DD Number");
                return false;
            }
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter Bank Name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("card")){
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Please enter Bank Name");
                return false;
            }
        }
        return true;
    }
    
    public void fillComboBox(){
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "root");
            PreparedStatement pst= con.prepareStatement("select cname from course");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                comboCourse.addItem(rs.getString("cname"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int getReceiptNo(){ 
        int receiptNo = 0;
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("select max(reciept_no) from fees_Details");
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()== true){
                receiptNo=rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return receiptNo+1;
    }
    
    public String updateData(){
        String status = "";
        
        int recieptNo = Integer.parseInt(txt_receiptNo.getText());
        String studentName = txt_receivedFrom.getText();
        String rollNo = txt_rollNo.getText();
        String paymentMode = combo_PaymentMode.getSelectedItem().toString();
        String chequeNo = txt_ChequeNo.getText();
        String bankName = txt_bankName.getText();
        String ddNo = txt_DDNo.getText();
        String courseName = txt_courseName.getText();
        String gstin = txt_gstin.getText();
        float totalAmount = Float.parseFloat(txt_total.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String date= dateFormat.format(dateChooser.getDate());
        float initialAmount = Float.parseFloat(txt_amount.getText());
        float cgst = Float.parseFloat(txt_cgst.getText());
        float sgst = Float.parseFloat(txt_sgst.getText());
        String totalInWords = txt_total_words.getText();
        String remark = txt_remark.getText();
        int year1 = Integer.parseInt(txt_year1.getText());
        int year2 = Integer.parseInt(txt_year2.getText());
        
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement("update fees_details set student_name=?, roll_no=?, payment_mode=?, cheque_no=?, bank_name=?, dd_no=?, course_name=?, gstin=?, total_amount=?, date=?, amount=?, cgst=?, sgst=?, total_in_words=?, remark=?, year1=?, year2=? where reciept_no=?");
            
            
            pst.setString(1, studentName);
            pst.setString(2, rollNo);
            pst.setString(3, paymentMode);
            pst.setString(4, chequeNo);
            pst.setString(5, bankName);
            pst.setString(6, ddNo);
            pst.setString(7, courseName);
            pst.setString(8, gstin);
            pst.setFloat(9, totalAmount);
            pst.setString(10, date);
            pst.setFloat(11, initialAmount);
            pst.setFloat(12, cgst);
            pst.setFloat(13, sgst);
            pst.setString(14, totalInWords);
            pst.setString(15, remark);
            pst.setInt(16,year1);
            pst.setInt(17,year2);
            pst.setInt(18, recieptNo);
            
           int rowCount = pst.executeUpdate();
           if (rowCount == 1){
               status = "success";
           }else{
            status = "failed";
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }
    
     public void setRecords(){
        try{
            Class.forName("org.apache.derby.iapi.jdbc.AutoloadedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "root");
            PreparedStatement pst= con.prepareStatement("select * from fees_details order by reciept_no desc fetch first 1 rows only");
            ResultSet rs = pst.executeQuery();
            rs.next();
            txt_receiptNo.setText(rs.getString("reciept_no"));
            combo_PaymentMode.setSelectedItem(rs.getString("payment_mode"));
            txt_ChequeNo.setText(rs.getString("cheque_no"));
            txt_DDNo.setText(rs.getString("dd_no"));
            txt_bankName.setText(rs.getString("bank_name"));
            txt_receivedFrom.setText(rs.getString("student_name"));
            txt_gstin.setText(rs.getString("gstin"));
            dateChooser.setDate(rs.getDate("date"));
            txt_year1.setText(rs.getString("year1"));
            txt_year2.setText(rs.getString("year2"));
            txt_rollNo.setText(rs.getString("roll_no"));
            txt_amount.setText(rs.getString("amount"));
            txt_cgst.setText(rs.getString("cgst"));
            txt_sgst.setText(rs.getString("sgst"));
            txt_total.setText(rs.getString("total_amount"));
            txt_total_words.setText(rs.getString("total_in_words"));
            txt_remark.setText(rs.getString("remark"));
            comboCourse.setSelectedItem(rs.getString("course_name"));
            
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

        panelsideBar = new javax.swing.JPanel();
        panelEdit = new javax.swing.JPanel();
        btnEdit = new javax.swing.JLabel();
        panelSearch = new javax.swing.JPanel();
        btnSearch = new javax.swing.JLabel();
        panelHome = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        panelHome3 = new javax.swing.JPanel();
        panelBack = new javax.swing.JPanel();
        btnBack = new javax.swing.JLabel();
        panelViewAllRecord = new javax.swing.JPanel();
        btnViewAllRecord = new javax.swing.JLabel();
        panelCourseList = new javax.swing.JPanel();
        btnCourseList = new javax.swing.JLabel();
        panelLogout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        panelParent = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_DDNo = new javax.swing.JLabel();
        lbl_bankName = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_chequeNo = new javax.swing.JLabel();
        txt_receiptNo = new javax.swing.JTextField();
        txt_ChequeNo = new javax.swing.JTextField();
        txt_DDNo = new javax.swing.JTextField();
        txt_bankName = new javax.swing.JTextField();
        combo_PaymentMode = new javax.swing.JComboBox<>();
        dateChooser = new com.toedter.calendar.JDateChooser();
        panelChild = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_year1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_year2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        comboCourse = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txt_courseName = new javax.swing.JTextField();
        txt_amount = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        txt_sgst = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_total = new javax.swing.JTextField();
        txt_total_words = new javax.swing.JTextField();
        txt_remark = new javax.swing.JTextField();
        txt_rollNo = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        txt_receivedFrom = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_gstin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelsideBar.setBackground(new java.awt.Color(255, 102, 102));

        panelEdit.setBackground(new java.awt.Color(255, 102, 102));
        panelEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelEditMouseEntered(evt);
            }
        });
        panelEdit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/edit2.png"))); // NOI18N
        btnEdit.setText("Edit Course");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });
        panelEdit.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, -1));

        panelSearch.setBackground(new java.awt.Color(255, 102, 102));
        panelSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelSearchMouseEntered(evt);
            }
        });
        panelSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/search2.png"))); // NOI18N
        btnSearch.setText("Search Record");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSearchMouseExited(evt);
            }
        });
        panelSearch.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 280, 70));

        panelHome.setBackground(new java.awt.Color(255, 102, 102));
        panelHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHomeMouseEntered(evt);
            }
        });
        panelHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/home.png"))); // NOI18N
        btnHome.setText("  HOME");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        panelHome.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, -1));

        panelHome3.setBackground(new java.awt.Color(255, 102, 102));
        panelHome3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHome3MouseEntered(evt);
            }
        });
        panelHome3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelBack.setBackground(new java.awt.Color(255, 102, 102));
        panelBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelBackMouseEntered(evt);
            }
        });
        panelBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/left-arrow.png"))); // NOI18N
        btnBack.setText("Back");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
            }
        });
        panelBack.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 250, -1));

        panelViewAllRecord.setBackground(new java.awt.Color(255, 102, 102));
        panelViewAllRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelViewAllRecordMouseEntered(evt);
            }
        });
        panelViewAllRecord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnViewAllRecord.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnViewAllRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAllRecord.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnViewAllRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/view all record.png"))); // NOI18N
        btnViewAllRecord.setText("View all Record");
        btnViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseExited(evt);
            }
        });
        panelViewAllRecord.add(btnViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 290, 70));

        panelCourseList.setBackground(new java.awt.Color(255, 102, 102));
        panelCourseList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelCourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelCourseListMouseEntered(evt);
            }
        });
        panelCourseList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCourseList.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnCourseList.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseList.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCourseList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/list_1.png"))); // NOI18N
        btnCourseList.setText("Course List");
        btnCourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCourseListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCourseListMouseExited(evt);
            }
        });
        panelCourseList.add(btnCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 260, -1));

        panelLogout.setBackground(new java.awt.Color(255, 102, 102));
        panelLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelLogoutMouseEntered(evt);
            }
        });
        panelLogout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setFont(new java.awt.Font("Britannic Bold", 1, 30)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        panelLogout.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 260, -1));

        javax.swing.GroupLayout panelsideBarLayout = new javax.swing.GroupLayout(panelsideBar);
        panelsideBar.setLayout(panelsideBarLayout);
        panelsideBarLayout.setHorizontalGroup(
            panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsideBarLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(panelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelsideBarLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panelEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                                .addComponent(panelCourseList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelViewAllRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(panelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(111, Short.MAX_VALUE))
            .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelsideBarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelHome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelsideBarLayout.setVerticalGroup(
            panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsideBarLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(panelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(panelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(panelEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(panelCourseList, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(panelViewAllRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(panelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(314, Short.MAX_VALUE))
            .addGroup(panelsideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelsideBarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelHome3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(panelsideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 1040));

        panelParent.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Mode of Payment:");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel2.setText("Receipt No.: ");

        lbl_DDNo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lbl_DDNo.setText("DD No.: ");

        lbl_bankName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lbl_bankName.setText("Bank Name:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel5.setText("Date:");

        lbl_chequeNo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lbl_chequeNo.setText("Cheque No.: ");

        txt_receiptNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_receiptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receiptNoActionPerformed(evt);
            }
        });

        txt_ChequeNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_ChequeNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ChequeNoActionPerformed(evt);
            }
        });

        txt_DDNo.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_DDNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DDNoActionPerformed(evt);
            }
        });

        txt_bankName.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_bankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bankNameActionPerformed(evt);
            }
        });

        combo_PaymentMode.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        combo_PaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_PaymentMode.setSelectedIndex(2);
        combo_PaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_PaymentModeActionPerformed(evt);
            }
        });

        panelChild.setBackground(new java.awt.Color(255, 204, 204));

        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel8.setText("Recieved From:");

        jLabel9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel9.setText("The following payment for the Academic Year from");

        txt_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel10.setText("to");

        txt_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year2ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel11.setText("Course:");

        jLabel12.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel12.setText("Registration No.:");

        comboCourse.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        comboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCourseActionPerformed(evt);
            }
        });

        txt_courseName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txt_courseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_courseNameActionPerformed(evt);
            }
        });

        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });

        txt_cgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstActionPerformed(evt);
            }
        });

        txt_sgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstActionPerformed(evt);
            }
        });

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        txt_total_words.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_wordsActionPerformed(evt);
            }
        });

        txt_remark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_remarkActionPerformed(evt);
            }
        });

        txt_rollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rollNoActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel14.setText("Sr. No.:");

        jLabel15.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel15.setText("Heads");

        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setText("Amount(Rs.)");

        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel17.setText("Total in words:");

        jLabel18.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel18.setText("Remarks:");

        jLabel19.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel19.setText("SGST 9%");

        jLabel20.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel20.setText("CGST 9%");

        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel21.setText("Reciever's Signature");

        btn_print.setFont(new java.awt.Font("Segoe UI Black", 0, 17)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        txt_receivedFrom.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_receivedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receivedFromActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelChildLayout = new javax.swing.GroupLayout(panelChild);
        panelChild.setLayout(panelChildLayout);
        panelChildLayout.setHorizontalGroup(
            panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChildLayout.createSequentialGroup()
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 1110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_courseName, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(54, 54, 54)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(463, 463, 463)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(580, 580, 580)
                        .addComponent(jLabel21)))
                .addGap(0, 556, Short.MAX_VALUE))
            .addGroup(panelChildLayout.createSequentialGroup()
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_rollNo, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel14)
                                .addGap(177, 177, 177)
                                .addComponent(jLabel15)
                                .addGap(252, 252, 252)
                                .addComponent(jLabel16))
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(panelChildLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_receivedFrom))
                                    .addComponent(jLabel9))
                                .addComponent(txt_year1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_year2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_total_words, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelChildLayout.createSequentialGroup()
                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_print)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_remark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelChildLayout.setVerticalGroup(
            panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChildLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_receivedFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_year1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txt_year2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboCourse)
                    .addComponent(jLabel12)
                    .addComponent(txt_rollNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txt_courseName, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                    .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(panelChildLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txt_total_words, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_remark, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(96, 96, 96)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelChildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(btn_print))
                .addGap(163, 163, 163))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel6.setText("GSTIN:");

        txt_gstin.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        txt_gstin.setText("33AAATK0225H1Z7");

        javax.swing.GroupLayout panelParentLayout = new javax.swing.GroupLayout(panelParent);
        panelParent.setLayout(panelParentLayout);
        panelParentLayout.setHorizontalGroup(
            panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParentLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(lbl_chequeNo)
                    .addComponent(lbl_DDNo)
                    .addComponent(lbl_bankName))
                .addGap(21, 21, 21)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_ChequeNo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_PaymentMode, javax.swing.GroupLayout.Alignment.LEADING, 0, 259, Short.MAX_VALUE)
                    .addComponent(txt_receiptNo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_DDNo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_bankName))
                .addGap(30, 30, 30)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelParentLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelParentLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_gstin)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelParentLayout.createSequentialGroup()
                .addComponent(panelChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelParentLayout.setVerticalGroup(
            panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelParentLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txt_receiptNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_PaymentMode)
                        .addComponent(jLabel1))
                    .addGroup(panelParentLayout.createSequentialGroup()
                        .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_gstin))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_chequeNo)
                    .addComponent(txt_ChequeNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_DDNo)
                    .addComponent(txt_DDNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelParentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_bankName)
                    .addComponent(txt_bankName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelChild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(693, 693, 693))
        );

        getContentPane().add(panelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 1110, 1040));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelEditMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelEditMouseEntered

    private void btnSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseEntered
        Color clr = new Color(255, 153, 153);
        panelSearch.setBackground(clr);
    }//GEN-LAST:event_btnSearchMouseEntered

    private void btnSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseExited
        Color clr = new Color(255, 102, 102);
        panelSearch.setBackground(clr);
    }//GEN-LAST:event_btnSearchMouseExited

    private void panelSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSearchMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelSearchMouseEntered

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        Color clr = new Color(255, 153, 153);
        panelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        Color clr = new Color(255, 102, 102);
        panelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseExited

    private void panelHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHomeMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelHomeMouseEntered

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        Color clr = new Color(255, 153, 153);
        panelEdit.setBackground(clr);
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        Color clr = new Color(255, 102, 102);
        panelEdit.setBackground(clr);
    }//GEN-LAST:event_btnEditMouseExited

    private void panelHome3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHome3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelHome3MouseEntered

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        Color clr = new Color(255, 153, 153);
        panelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        Color clr = new Color(255, 102, 102);
        panelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseExited

    private void panelBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelBackMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelBackMouseEntered

    private void btnViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseEntered
        Color clr = new Color(255, 153, 153);
        panelViewAllRecord.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordMouseEntered

    private void btnViewAllRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseExited
        Color clr = new Color(255, 102, 102);
        panelViewAllRecord.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordMouseExited

    private void panelViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelViewAllRecordMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelViewAllRecordMouseEntered

    private void btnCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseEntered
        Color clr = new Color(255, 153, 153);
        panelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseEntered

    private void btnCourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseExited
        Color clr = new Color(255, 102, 102);
        panelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseExited

    private void panelCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCourseListMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCourseListMouseEntered

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        Color clr = new Color(255, 153, 153);
        panelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        Color clr = new Color(255, 102, 102);
        panelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void panelLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogoutMouseEntered
        //jhg
    }//GEN-LAST:event_panelLogoutMouseEntered

    private void txt_receiptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receiptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_receiptNoActionPerformed

    private void txt_ChequeNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ChequeNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ChequeNoActionPerformed

    private void txt_DDNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DDNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DDNoActionPerformed

    private void txt_bankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bankNameActionPerformed

    private void txt_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year1ActionPerformed

    private void txt_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year2ActionPerformed

    private void txt_courseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_courseNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_courseNameActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
        Float amnt = Float.parseFloat(txt_amount.getText());
        
        Float cgst= (float)(amnt*0.09);
        Float sgst= (float)(amnt*0.09);
        
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        
        float total = amnt+cgst+sgst;
        txt_total.setText(Float.toString(total));
        
        txt_total_words.setText(NumberToWordsConverter.convert((int)total) + " " +"only");
        
    }//GEN-LAST:event_txt_amountActionPerformed

    private void txt_cgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstActionPerformed

    private void txt_sgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_total_wordsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_wordsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_wordsActionPerformed

    private void txt_remarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_remarkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_remarkActionPerformed

    private void txt_rollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rollNoActionPerformed

    private void txt_receivedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receivedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_receivedFromActionPerformed

    private void combo_PaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_PaymentModeActionPerformed
        if(combo_PaymentMode.getSelectedIndex()==0){
            lbl_DDNo.setVisible(true);
            txt_DDNo.setVisible(true);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        if(combo_PaymentMode.getSelectedIndex()==1){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(true);
            txt_ChequeNo.setVisible(true);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        if(combo_PaymentMode.getSelectedIndex()==2){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(false);
            txt_bankName.setVisible(false);
        }
        if(combo_PaymentMode.getSelectedItem().equals("Card")){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            lbl_chequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
    }//GEN-LAST:event_combo_PaymentModeActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
        if (validation()==true){
            String result = updateData();
            if (result.equals("success")){
                JOptionPane.showMessageDialog(this, "Record updated successfully");
                PrintReciept p = new PrintReciept();
                p.setVisible(true);
                this.dispose();
            }else{
                JOptionPane.showMessageDialog(this, "Record updation failed");
            }  
        }
    }//GEN-LAST:event_btn_printActionPerformed

    private void comboCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCourseActionPerformed
        txt_courseName.setText(comboCourse.getSelectedItem().toString());
    }//GEN-LAST:event_comboCourseActionPerformed

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        home Home= new home();
        Home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnViewAllRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseClicked
        ViewAllRecords allRecords = new ViewAllRecords();
        allRecords.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnViewAllRecordMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        SearchRecord search = new SearchRecord();
        search.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSearchMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        EditCourse edit = new EditCourse();
        edit.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
 int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Navigate back to login screen
        Login login = new Login(); // Create a new instance of Login
        login.setVisible(true); // Show the login window
        this.dispose(); // Close the current window
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnLogoutMouseClicked

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
       
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UpdateFeesDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBack;
    private javax.swing.JLabel btnCourseList;
    private javax.swing.JLabel btnEdit;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JLabel btnViewAllRecord;
    private javax.swing.JButton btn_print;
    private javax.swing.JComboBox<String> comboCourse;
    private javax.swing.JComboBox<String> combo_PaymentMode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_DDNo;
    private javax.swing.JLabel lbl_bankName;
    private javax.swing.JLabel lbl_chequeNo;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelChild;
    private javax.swing.JPanel panelCourseList;
    private javax.swing.JPanel panelEdit;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelHome3;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelParent;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelViewAllRecord;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTextField txt_ChequeNo;
    private javax.swing.JTextField txt_DDNo;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_bankName;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_courseName;
    private javax.swing.JLabel txt_gstin;
    private javax.swing.JTextField txt_receiptNo;
    private javax.swing.JTextField txt_receivedFrom;
    private javax.swing.JTextField txt_remark;
    private javax.swing.JTextField txt_rollNo;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_words;
    private javax.swing.JTextField txt_year1;
    private javax.swing.JTextField txt_year2;
    // End of variables declaration//GEN-END:variables
}
