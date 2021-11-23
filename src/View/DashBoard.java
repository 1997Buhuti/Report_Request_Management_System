/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Report_Requests_Controller;
import DB.DBConnection;
import Model.Report_Requests_Model;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author dpman
 */
public class DashBoard extends javax.swing.JFrame {

    /**
     * Creates new form RequestForm
     */
    CardLayout cardLayout;
    public DashBoard() {
        initComponents();
        txt_developer_id.disable();
        load_developers();
        Request_Form_Id_generate();
        this.setLocationRelativeTo(null);
        scaleImage();
        try {
            loadTable();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        setResizable(false);
        load_date();
        
        lbl_create_request1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl_view_request.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardLayout = (CardLayout)(pri_CardLayout.getLayout());
    }
    Single_Row raw_data = new Single_Row();
    Color DefaultColor,ClickedColor,HoveredColor;
    
        /*    This is used to load the curent dat in the label */
    
    public void load_date(){
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
           LocalDateTime now = LocalDateTime.now();  
           String format = dtf.format(now);  
           lbl_current_date.setText(format);
       
    }
    
    public void clearTable(){
        
        tbl_requests.setModel(new DefaultTableModel(null,new String[]
        {"Project_Id","Project_Name" , "Recieve dDate" , "Created_Date",
         "Start Date","Completion Date", "Current Status", "Remarks","Task Details",
         "Department Name" ,"Branch Name" ,"Developer Name","Developer_Id"}));
    }
    
    /*    This is used to clear all the data in form*/
    
     public void clearAll() throws ClassNotFoundException, SQLException{
        raw_data.txt_proj_id.setText("");
        raw_data.txt_recieved_date.setText("");
        raw_data.txt_proj_name.setText("");
        raw_data.txt_branch_name.setText("");
        raw_data.txt_completion_date.setText("");
        raw_data.txt_remarks.setText("");
        raw_data.txt_recieved_date.setText("");
        raw_data.txt_starting_date.setText("");
        raw_data.txt_developer_id.setText("");
        raw_data.txt_department.setText("");
        combo_proj_status.getModel().setSelectedItem("");
    }
     
 /*    This is used to scale the image*/
     
    public void scaleImage(){
        //ImageIcon icon = new ImageIcon("C:\\Users\\dpman\\Documents\\NetBeansProjects\\PBApp\\src\\images\\PB LOG.jpg");
        ImageIcon icon = new ImageIcon("C:\\Users\\dpman\\Desktop\\PB Logo.png");
        Image img = icon.getImage();
        Image imageScale = img.getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(),Image.SCALE_SMOOTH);
        lbl_logo.setIcon(icon);
        ImageIcon scaled = new ImageIcon(imageScale);
        lbl_logo.setIcon(scaled);
        
        DefaultColor= new Color(255, 255, 255);
        ClickedColor= new Color(255, 160, 122);
        HoveredColor= new Color(255, 191, 0);
        
        lbl_create_request1.setBackground(DefaultColor);
        lbl_view_request.setBackground(DefaultColor);
        //Order.setBackground(DefaultColor);
    }
    
    public String Request_Form_Id_generate(){
            String UID = "Proj" ;
            String uuid=UUID.randomUUID().toString();
            uuid=uuid.substring(0,3);
            UID=UID+uuid;
            txt_projID.setText(UID);
            txt_projID.disable();
            return UID;
            
        }
    
     /*    This is used to load devlopers in comboBox*/
    public void load_developers(){
        try {
                Report_Requests_Controller controller = new Report_Requests_Controller();
                ArrayList <String> userNames = controller.loadUserNames();
                for(String name:userNames){
                    
                    combo_developers.addItem(name);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
     /*    This is used to load all the request objects inside the table */
    public void loadTable() throws ClassNotFoundException, SQLException{
        
           Report_Requests_Controller controller= new Report_Requests_Controller();
           DefaultTableModel dtm= (DefaultTableModel)tbl_requests.getModel();
           ArrayList<Report_Requests_Model> report_requests = controller.getAllReportRequests();
           
           for(Report_Requests_Model i: report_requests){
               
                Object arr[]={i.getProject_id(),i.getProject_name(),i.getRecieved_date(),
                i.getCreated_date(),i.getStart_date(), i.getCompletion_date(),i.getCurent_status(),
                i.getRemarks(),i.getTask_details(),i.getDepartment_name(),i.getBranch_name(),i.getDeveloper_name(),i.getDeveloper_id()};
                dtm.addRow(arr);
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

        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jFrame1 = new javax.swing.JFrame();
        jPanel_main1 = new javax.swing.JPanel();
        menu_header1 = new javax.swing.JPanel();
        red_line1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lbl_logo1 = new javax.swing.JLabel();
        jSplitPane_menu1 = new javax.swing.JSplitPane();
        menu_bar1 = new javax.swing.JPanel();
        lbl_create_request2 = new javax.swing.JLabel();
        lbl_view_request1 = new javax.swing.JLabel();
        pri_CardLayout1 = new javax.swing.JPanel();
        card3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_requests1 = new javax.swing.JTable();
        card4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_proj_id2 = new javax.swing.JLabel();
        lbl_proj_id3 = new javax.swing.JLabel();
        lbl_proj_name1 = new javax.swing.JLabel();
        lbl_proj_completion_date4 = new javax.swing.JLabel();
        lbl_proj_dept_name4 = new javax.swing.JLabel();
        txt_proj_name1 = new javax.swing.JTextField();
        txt_projID1 = new javax.swing.JTextField();
        txt_completion_date1 = new javax.swing.JTextField();
        txt_dept_name1 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_remarks1 = new javax.swing.JTextArea();
        txt_recieved_date1 = new javax.swing.JTextField();
        lbl_proj_completion_date5 = new javax.swing.JLabel();
        lbl_proj_completion_date6 = new javax.swing.JLabel();
        txt_starting_date1 = new javax.swing.JTextField();
        lbl_proj_completion_date7 = new javax.swing.JLabel();
        combo_proj_status1 = new javax.swing.JComboBox<>();
        lbl_current_date1 = new javax.swing.JLabel();
        btn_update1 = new javax.swing.JButton();
        btn_submit2 = new javax.swing.JButton();
        btn_clear2 = new javax.swing.JButton();
        lbl_proj_dept_name5 = new javax.swing.JLabel();
        lbl_proj_dept_name6 = new javax.swing.JLabel();
        txt_branch_name1 = new javax.swing.JTextField();
        combo_developers1 = new javax.swing.JComboBox<>();
        lbl_proj_dept_name7 = new javax.swing.JLabel();
        txt_developer_id1 = new javax.swing.JTextField();
        jPanel_main = new javax.swing.JPanel();
        menu_header = new javax.swing.JPanel();
        red_line = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        jSplitPane_menu = new javax.swing.JSplitPane();
        menu_bar = new javax.swing.JPanel();
        lbl_create_request1 = new javax.swing.JLabel();
        lbl_view_request = new javax.swing.JLabel();
        pri_CardLayout = new javax.swing.JPanel();
        card2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_requests = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        card1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbl_proj_id = new javax.swing.JLabel();
        lbl_proj_id1 = new javax.swing.JLabel();
        lbl_proj_name = new javax.swing.JLabel();
        lbl_proj_completion_date = new javax.swing.JLabel();
        lbl_proj_dept_name = new javax.swing.JLabel();
        txt_proj_name = new javax.swing.JTextField();
        txt_projID = new javax.swing.JTextField();
        txt_completion_date = new javax.swing.JTextField();
        txt_dept_name = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_remarks = new javax.swing.JTextArea();
        txt_recieved_date = new javax.swing.JTextField();
        lbl_proj_completion_date1 = new javax.swing.JLabel();
        lbl_proj_completion_date2 = new javax.swing.JLabel();
        txt_starting_date = new javax.swing.JTextField();
        lbl_proj_completion_date3 = new javax.swing.JLabel();
        combo_proj_status = new javax.swing.JComboBox<>();
        lbl_current_date = new javax.swing.JLabel();
        btn_update = new javax.swing.JButton();
        btn_submit1 = new javax.swing.JButton();
        btn_clear1 = new javax.swing.JButton();
        lbl_proj_dept_name1 = new javax.swing.JLabel();
        lbl_proj_dept_name2 = new javax.swing.JLabel();
        txt_branch_name = new javax.swing.JTextField();
        combo_developers = new javax.swing.JComboBox<>();
        lbl_proj_dept_name3 = new javax.swing.JLabel();
        txt_developer_id = new javax.swing.JTextField();

        jTextField2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Current Status :");

        jComboBox1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame1.setResizable(false);

        jPanel_main1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_main1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_header1.setBackground(new java.awt.Color(255, 174, 1));

        red_line1.setBackground(new java.awt.Color(206, 0, 0));
        red_line1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Report Requests Management System");

        javax.swing.GroupLayout menu_header1Layout = new javax.swing.GroupLayout(menu_header1);
        menu_header1.setLayout(menu_header1Layout);
        menu_header1Layout.setHorizontalGroup(
            menu_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_header1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(menu_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menu_header1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(red_line1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menu_header1Layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menu_header1Layout.setVerticalGroup(
            menu_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_header1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menu_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menu_header1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(31, 31, 31)
                        .addComponent(red_line1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_main1.add(menu_header1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1410, 100));

        menu_bar1.setBackground(new java.awt.Color(255, 174, 1));

        lbl_create_request2.setBackground(new java.awt.Color(255, 255, 255));
        lbl_create_request2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_create_request2.setForeground(new java.awt.Color(0, 0, 0));
        lbl_create_request2.setText("Create Request");
        lbl_create_request2.setOpaque(true);
        lbl_create_request2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_create_request2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_create_request2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_create_request2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_create_request2MousePressed(evt);
            }
        });

        lbl_view_request1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_view_request1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_view_request1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_view_request1.setText(" View Requests");
        lbl_view_request1.setOpaque(true);
        lbl_view_request1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_view_request1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_view_request1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_view_request1MousePressed(evt);
            }
        });
        lbl_view_request1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl_view_request1KeyPressed(evt);
            }
        });

        pri_CardLayout1.setBackground(new java.awt.Color(255, 255, 255));
        pri_CardLayout1.setLayout(new java.awt.CardLayout());

        card3.setBackground(new java.awt.Color(255, 255, 255));

        tbl_requests1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project_Id", "Project_Name", "Recieved Date", "Created_Date", "Start Date", "Completion Date", "Current Status", "Remarks", "Task Details", "Department Name", "Branch Name", "Developer Name"
            }
        ));
        tbl_requests1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_requests1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_requests1);

        javax.swing.GroupLayout card3Layout = new javax.swing.GroupLayout(card3);
        card3.setLayout(card3Layout);
        card3Layout.setHorizontalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        card3Layout.setVerticalGroup(
            card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 100, Short.MAX_VALUE))
        );

        pri_CardLayout1.add(card3, "card2");

        card4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(new java.awt.Color(255, 153, 0));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("                                            Report Request Form");
        jLabel4.setOpaque(true);

        lbl_proj_id2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id2.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id2.setText("Remarks :");

        lbl_proj_id3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id3.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id3.setText("Project ID :");

        lbl_proj_name1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_name1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_name1.setText("Project Name :");

        lbl_proj_completion_date4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date4.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date4.setText("Completion Date :");

        lbl_proj_dept_name4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name4.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name4.setText("Department :");

        txt_proj_name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_proj_name1ActionPerformed(evt);
            }
        });

        txt_projID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_projID1ActionPerformed(evt);
            }
        });

        txt_completion_date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_completion_date1ActionPerformed(evt);
            }
        });

        txt_dept_name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dept_name1ActionPerformed(evt);
            }
        });

        txt_remarks1.setColumns(20);
        txt_remarks1.setRows(5);
        jScrollPane5.setViewportView(txt_remarks1);

        txt_recieved_date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_recieved_date1ActionPerformed(evt);
            }
        });

        lbl_proj_completion_date5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date5.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date5.setText("Recieved Date :");

        lbl_proj_completion_date6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date6.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date6.setText("Starting  Date :");

        txt_starting_date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_starting_date1ActionPerformed(evt);
            }
        });

        lbl_proj_completion_date7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date7.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date7.setText(" Project Status");

        combo_proj_status1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Ongoing", "Done", "Blocked" }));

        lbl_current_date1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_current_date1.setForeground(new java.awt.Color(0, 0, 0));

        btn_update1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_update1.setText("Update");

        btn_submit2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_submit2.setText("Submit");
        btn_submit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit2ActionPerformed(evt);
            }
        });

        btn_clear2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_clear2.setText("Clear");

        lbl_proj_dept_name5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name5.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name5.setText("Developer Name");

        lbl_proj_dept_name6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name6.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name6.setText("Branch Name");

        txt_branch_name1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_branch_name1ActionPerformed(evt);
            }
        });

        combo_developers1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_developers1ActionPerformed(evt);
            }
        });

        lbl_proj_dept_name7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name7.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name7.setText("Developer Id");

        txt_developer_id1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_developer_id1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card4Layout = new javax.swing.GroupLayout(card4);
        card4.setLayout(card4Layout);
        card4Layout.setHorizontalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_current_date1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                                .addComponent(lbl_proj_id2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_submit2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txt_starting_date1, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                                .addComponent(txt_recieved_date1))
                                            .addGroup(card4Layout.createSequentialGroup()
                                                .addComponent(btn_clear2)
                                                .addGap(69, 69, 69)
                                                .addComponent(btn_update1))))
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(card4Layout.createSequentialGroup()
                                                .addComponent(lbl_proj_dept_name6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_branch_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(card4Layout.createSequentialGroup()
                                                .addComponent(lbl_proj_dept_name7, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_developer_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(card4Layout.createSequentialGroup()
                                            .addComponent(lbl_proj_id3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_projID1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(card4Layout.createSequentialGroup()
                                            .addComponent(lbl_proj_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_proj_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_completion_date4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_completion_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_dept_name4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_dept_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(113, 113, 113)
                                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_completion_date7, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(combo_proj_status1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbl_proj_completion_date5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_proj_completion_date6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(card4Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_dept_name5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(combo_developers1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(91, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card4Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        card4Layout.setVerticalGroup(
            card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card4Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_current_date1)
                .addGap(37, 37, 37)
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_recieved_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_projID1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_id3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_proj_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_proj_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_starting_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_completion_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_proj_status1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dept_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(card4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_proj_dept_name5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_developers1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_developer_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_branch_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_update1)
                            .addComponent(btn_clear2)
                            .addComponent(btn_submit2)))
                    .addGroup(card4Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_id2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(347, Short.MAX_VALUE))
        );

        pri_CardLayout1.add(card4, "card1");

        javax.swing.GroupLayout menu_bar1Layout = new javax.swing.GroupLayout(menu_bar1);
        menu_bar1.setLayout(menu_bar1Layout);
        menu_bar1Layout.setHorizontalGroup(
            menu_bar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_bar1Layout.createSequentialGroup()
                .addGroup(menu_bar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_view_request1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_create_request2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pri_CardLayout1, javax.swing.GroupLayout.PREFERRED_SIZE, 1181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        menu_bar1Layout.setVerticalGroup(
            menu_bar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_bar1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_create_request2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_view_request1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_bar1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pri_CardLayout1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane_menu1.setLeftComponent(menu_bar1);

        jPanel_main1.add(jSplitPane_menu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1420, 810));

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame1Layout.createSequentialGroup()
                .addComponent(jPanel_main1, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_main1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel_main.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_header.setBackground(new java.awt.Color(255, 174, 1));

        red_line.setBackground(new java.awt.Color(206, 0, 0));

        javax.swing.GroupLayout red_lineLayout = new javax.swing.GroupLayout(red_line);
        red_line.setLayout(red_lineLayout);
        red_lineLayout.setHorizontalGroup(
            red_lineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1407, Short.MAX_VALUE)
        );
        red_lineLayout.setVerticalGroup(
            red_lineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Report Requests Management System");

        lbl_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_logoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_headerLayout = new javax.swing.GroupLayout(menu_header);
        menu_header.setLayout(menu_headerLayout);
        menu_headerLayout.setHorizontalGroup(
            menu_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_headerLayout.createSequentialGroup()
                .addComponent(red_line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(menu_headerLayout.createSequentialGroup()
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(227, 227, 227))
        );
        menu_headerLayout.setVerticalGroup(
            menu_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_headerLayout.createSequentialGroup()
                .addGroup(menu_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menu_headerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(menu_headerLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(red_line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_main.add(menu_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1410, 100));

        menu_bar.setBackground(new java.awt.Color(255, 153, 0));

        lbl_create_request1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_create_request1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_create_request1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_create_request1.setText("Create Request");
        lbl_create_request1.setOpaque(true);
        lbl_create_request1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_create_request1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_create_request1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_create_request1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_create_request1MousePressed(evt);
            }
        });

        lbl_view_request.setBackground(new java.awt.Color(255, 255, 255));
        lbl_view_request.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lbl_view_request.setForeground(new java.awt.Color(0, 0, 0));
        lbl_view_request.setText(" View Requests");
        lbl_view_request.setOpaque(true);
        lbl_view_request.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_view_requestMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_view_requestMouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_view_requestMousePressed(evt);
            }
        });
        lbl_view_request.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl_view_requestKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout menu_barLayout = new javax.swing.GroupLayout(menu_bar);
        menu_bar.setLayout(menu_barLayout);
        menu_barLayout.setHorizontalGroup(
            menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_barLayout.createSequentialGroup()
                .addGroup(menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_view_request, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_create_request1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menu_barLayout.setVerticalGroup(
            menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_barLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbl_create_request1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_view_request, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane_menu.setLeftComponent(menu_bar);

        pri_CardLayout.setBackground(new java.awt.Color(255, 255, 255));
        pri_CardLayout.setLayout(new java.awt.CardLayout());

        card2.setBackground(new java.awt.Color(255, 255, 255));
        card2.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        tbl_requests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project_Id", "Project_Name", "Recieved Date", "Created_Date", "Start Date", "Completion Date", "Current Status", "Remarks", "Department Name", "Branch Name", "Developer Name", "Developer_Id"
            }
        ));
        tbl_requests.setMaximumSize(new java.awt.Dimension(32763, 32763));
        tbl_requests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_requestsMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_requestsMousePressed(evt);
            }
        });
        tbl_requests.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_requestsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_requests);

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(147, 147, 147))
            .addGroup(card2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 91, Short.MAX_VALUE))
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pri_CardLayout.add(card2, "card2");

        card1.setBackground(new java.awt.Color(255, 255, 255));
        card1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel2.setBackground(new java.awt.Color(255, 153, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("                                            Report Request Form");
        jLabel2.setOpaque(true);

        lbl_proj_id.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id.setText("Remarks :");

        lbl_proj_id1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id1.setText("Project ID :");

        lbl_proj_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_name.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_name.setText("Project Name :");

        lbl_proj_completion_date.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date.setText("Completion Date :");

        lbl_proj_dept_name.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name.setText("Department :");

        txt_proj_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_proj_nameActionPerformed(evt);
            }
        });

        txt_projID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_projIDActionPerformed(evt);
            }
        });

        txt_completion_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_completion_dateActionPerformed(evt);
            }
        });

        txt_dept_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dept_nameActionPerformed(evt);
            }
        });

        txt_remarks.setColumns(20);
        txt_remarks.setRows(5);
        jScrollPane2.setViewportView(txt_remarks);

        txt_recieved_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_recieved_dateActionPerformed(evt);
            }
        });

        lbl_proj_completion_date1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date1.setText("Recieved Date :");

        lbl_proj_completion_date2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date2.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date2.setText("Starting  Date :");

        txt_starting_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_starting_dateActionPerformed(evt);
            }
        });

        lbl_proj_completion_date3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date3.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date3.setText(" Project Status");

        combo_proj_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Ongoing", "Done", "Blocked" }));

        lbl_current_date.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_current_date.setForeground(new java.awt.Color(0, 0, 0));

        btn_update.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_submit1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_submit1.setText("Submit");
        btn_submit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit1ActionPerformed(evt);
            }
        });

        btn_clear1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_clear1.setText("Clear");
        btn_clear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear1ActionPerformed(evt);
            }
        });

        lbl_proj_dept_name1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name1.setText("Developer Name");

        lbl_proj_dept_name2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name2.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name2.setText("Branch Name");

        txt_branch_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_branch_nameActionPerformed(evt);
            }
        });

        combo_developers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Not Assigned" }));
        combo_developers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_developersItemStateChanged(evt);
            }
        });
        combo_developers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_developersActionPerformed(evt);
            }
        });

        lbl_proj_dept_name3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name3.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name3.setText("Developer Id");

        txt_developer_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_developer_idActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lbl_current_date))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                                .addComponent(lbl_proj_id, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_submit1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txt_starting_date, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                                                .addComponent(txt_recieved_date))
                                            .addGroup(card1Layout.createSequentialGroup()
                                                .addComponent(btn_clear1)
                                                .addGap(69, 69, 69)
                                                .addComponent(btn_update))))
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(card1Layout.createSequentialGroup()
                                                .addComponent(lbl_proj_dept_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_branch_name, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(card1Layout.createSequentialGroup()
                                                .addComponent(lbl_proj_dept_name3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(txt_developer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card1Layout.createSequentialGroup()
                                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(card1Layout.createSequentialGroup()
                                            .addComponent(lbl_proj_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_projID, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(card1Layout.createSequentialGroup()
                                            .addComponent(lbl_proj_name, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_proj_name, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_completion_date, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_completion_date, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_dept_name, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_dept_name, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(113, 113, 113)
                                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_completion_date3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(combo_proj_status, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbl_proj_completion_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_proj_completion_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(card1Layout.createSequentialGroup()
                                        .addComponent(lbl_proj_dept_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(combo_developers, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(124, Short.MAX_VALUE))
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_current_date)
                .addGap(37, 37, 37)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_recieved_date, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_projID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_proj_name, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_proj_name, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_starting_date, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_completion_date, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_proj_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_dept_name, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(card1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_proj_dept_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_developers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_developer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_branch_name, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_dept_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_update)
                            .addComponent(btn_clear1)
                            .addComponent(btn_submit1)))
                    .addGroup(card1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_proj_id, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(249, Short.MAX_VALUE))
        );

        pri_CardLayout.add(card1, "card1");

        jSplitPane_menu.setRightComponent(pri_CardLayout);

        jPanel_main.add(jSplitPane_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1500, 810));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_main, javax.swing.GroupLayout.PREFERRED_SIZE, 1407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_create_request1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MousePressed
        
    }//GEN-LAST:event_lbl_create_request1MousePressed

    private void lbl_create_request1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MouseClicked
        lbl_create_request1.setBackground(ClickedColor);
        lbl_view_request.setBackground(DefaultColor);
        cardLayout.show(pri_CardLayout, "card1");
    }//GEN-LAST:event_lbl_create_request1MouseClicked

    private void lbl_view_requestMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMousePressed
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_view_request.setBackground(ClickedColor);
    }//GEN-LAST:event_lbl_view_requestMousePressed

    private void lbl_create_request1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MouseEntered
        // TODO add your handling code here:
        lbl_create_request1.setBackground(HoveredColor);
        lbl_view_request.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_create_request1MouseEntered

    private void lbl_create_request1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MouseExited
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_view_request.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_create_request1MouseExited

    private void lbl_view_requestMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMouseEntered
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_view_request.setBackground(HoveredColor);
    }//GEN-LAST:event_lbl_view_requestMouseEntered

    private void lbl_view_requestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMouseClicked
        // TODO add your handling code here:
        cardLayout.show(pri_CardLayout, "card2");
    }//GEN-LAST:event_lbl_view_requestMouseClicked

    private void lbl_view_requestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_view_requestKeyPressed
        // TODO add your handling code here:
        cardLayout.show(pri_CardLayout, "card2");
    }//GEN-LAST:event_lbl_view_requestKeyPressed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void txt_proj_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_proj_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_proj_nameActionPerformed

    private void txt_projIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_projIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_projIDActionPerformed

    private void txt_completion_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_completion_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_completion_dateActionPerformed

    private void txt_dept_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dept_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dept_nameActionPerformed

    private void txt_recieved_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_recieved_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_recieved_dateActionPerformed

    private void txt_starting_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_starting_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_starting_dateActionPerformed

    private void tbl_requestsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_requestsMouseClicked

        int index = tbl_requests.getSelectedRow();
        TableModel model = tbl_requests.getModel();
        
        String project_id = model.getValueAt(index,0).toString();
        String project_name = model.getValueAt(index,1).toString();
        String recieved_date = model.getValueAt(index,2).toString();
        String created_date = model.getValueAt(index,3).toString();
        String start_date = model.getValueAt(index,4).toString();
        String completion_date;
        if(model.getValueAt(index,5)!=null){
            completion_date = model.getValueAt(index,5).toString();
        }
        else{
            completion_date = "";
        }
        String current_status = model.getValueAt(index,6).toString();
        String remarks = model.getValueAt(index,7).toString();
        String task_details = model.getValueAt(index,8).toString();
        String department_name = model.getValueAt(index,9).toString(); 
        String branch_name = model.getValueAt(index,10).toString();
        String developer_name = model.getValueAt(index,11).toString();
        String developer_id = model.getValueAt(index,12).toString();
        raw_data.setVisible(true);
        raw_data.pack();
        raw_data.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        raw_data.txt_proj_id.setText(project_id);
        raw_data.txt_proj_name.setText(project_name);
        raw_data.txt_branch_name.setText(branch_name);
        raw_data.txt_completion_date.setText(completion_date);
        raw_data.txt_remarks.setText(remarks);
        raw_data.txt_recieved_date.setText(recieved_date);
        raw_data.combo_dev_names.setSelectedItem(developer_name);
        raw_data.txt_request_date.setText(created_date);
        raw_data.txt_starting_date.setText(start_date);
        raw_data.txt_developer_id.setText(developer_id);
        raw_data.txt_department.setText(department_name);
        combo_proj_status.getModel().setSelectedItem(current_status);
    }//GEN-LAST:event_tbl_requestsMouseClicked

    private void txt_branch_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_branch_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_branch_nameActionPerformed
    
    private boolean validate_fields(String ProjId,String ProjName, String 
    recieved_date, String created_date, String starting_date, String proj_status, 
    String remarks, String task_details, String department, String branch_name, 
    String developers, String dev_Id){
        
        System.out.println("inside validate_fields");
        if( (ProjId!=null)||(ProjName!=null)||(recieved_date!=null)||(created_date!=null)||(starting_date!=null)||
        (proj_status!=null)||(remarks!=null)||(task_details!=null)||(department!=null)||(branch_name!=null)||
        (developers!=null)||(dev_Id!=null))
        {
            return true;
        }
        System.out.println("Error all the mandotory fields must be filled");
        JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled");
        System.out.println("Error all the mandotory fields must be filled");
        return false;
    }
    private void btn_submit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit1ActionPerformed
            
            if(txt_projID.getText().isEmpty()||txt_proj_name.getText().isEmpty()||
               lbl_current_date.getText().isEmpty()||txt_dept_name.getText().isEmpty()||
               txt_starting_date.getText().isEmpty()||
               combo_proj_status.getSelectedItem().toString().isEmpty()
               ||txt_remarks.getText().isEmpty()||txt_dept_name.getText().isEmpty()
               ||txt_branch_name.getText().isEmpty()||txt_developer_id.getText().isEmpty())
            {
                   JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled"); 
            }
            
            String ProjId = txt_projID.getText();
            String ProjName = txt_proj_name.getText();
            String completion_date =  txt_completion_date.getText();
            String created_date = lbl_current_date.getText();
            String department = txt_dept_name.getText();
            String remarks = txt_remarks.getText();
            String recieved_date = txt_recieved_date.getText();
            String starting_date = txt_starting_date.getText();
            String proj_status = (String) combo_proj_status.getSelectedItem();
            String developers = (String) combo_developers.getSelectedItem();
            String branch_name = txt_branch_name.getText();
            String task_details = "";
            String dev_Id = txt_developer_id.getText();

            if( txt_completion_date.getText().isEmpty()){
                
                Report_Requests_Model request = new Report_Requests_Model(ProjId,ProjName,recieved_date,created_date,starting_date,proj_status,remarks,task_details,department,branch_name,developers,dev_Id);
                Report_Requests_Controller controller= new Report_Requests_Controller();

                try {

                    if(controller.saveReportRequestWithoutCompletion(request)){

                        JOptionPane.showMessageDialog(this, "The record inserted");
                        clearAll();
                    }
                    
                    } catch (SQLException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
                Report_Requests_Model request = new  Report_Requests_Model(
                ProjId,ProjName,recieved_date,created_date,starting_date,
                completion_date,proj_status,remarks,task_details,department,
                branch_name,developers,dev_Id);
                
                Report_Requests_Controller controller= new Report_Requests_Controller();

                try {
                    if(controller.saveReportRequest(request)){

                        JOptionPane.showMessageDialog(this, "The record inserted");
                        clearAll();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }


            
        
    }//GEN-LAST:event_btn_submit1ActionPerformed

    private void txt_developer_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_developer_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_developer_idActionPerformed

    private void combo_developersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_developersActionPerformed
      /* try {
            
            String developer_Name = combo_developers.getSelectedItem().toString();
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            
            if(rst.next()){
                txt_developer_id.setText(String.valueOf(rst.getObject("developer_id")));
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }//GEN-LAST:event_combo_developersActionPerformed

    private void lbl_create_request2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_create_request2MouseClicked

    private void lbl_create_request2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_create_request2MouseEntered

    private void lbl_create_request2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_create_request2MouseExited

    private void lbl_create_request2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_create_request2MousePressed

    private void lbl_view_request1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_request1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_view_request1MouseClicked

    private void lbl_view_request1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_request1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_view_request1MouseEntered

    private void lbl_view_request1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_request1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_view_request1MousePressed

    private void lbl_view_request1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_view_request1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_view_request1KeyPressed

    private void tbl_requests1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_requests1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_requests1MouseClicked

    private void txt_proj_name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_proj_name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_proj_name1ActionPerformed

    private void txt_projID1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_projID1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_projID1ActionPerformed

    private void txt_completion_date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_completion_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_completion_date1ActionPerformed

    private void txt_dept_name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dept_name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dept_name1ActionPerformed

    private void txt_recieved_date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_recieved_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_recieved_date1ActionPerformed

    private void txt_starting_date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_starting_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_starting_date1ActionPerformed

    private void btn_submit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_submit2ActionPerformed

    private void txt_branch_name1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_branch_name1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_branch_name1ActionPerformed

    private void combo_developers1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_developers1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_developers1ActionPerformed

    private void txt_developer_id1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_developer_id1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_developer_id1ActionPerformed

    private void tbl_requestsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_requestsMousePressed
        // TODO add your handling code here:
        try {
            
            String developer_Name = combo_developers.getSelectedItem().toString();
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                
                txt_developer_id.setText(String.valueOf(rst.getObject("developer_id")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbl_requestsMousePressed

    private void tbl_requestsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_requestsKeyPressed
        // TODO add your handling code here:
        try {
            
            String developer_Name = combo_developers.getSelectedItem().toString();
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                
                txt_developer_id.setText(String.valueOf(rst.getObject("developer_id")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbl_requestsKeyPressed

    private void btn_clear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear1ActionPerformed
        txt_projID.setText("");
        txt_proj_name.setText("");
        txt_completion_date.setText("");
        lbl_current_date.setText("");
        txt_dept_name.setText("");
        txt_remarks.setText("");
        txt_recieved_date.setText("");
        txt_starting_date.setText("");
    }//GEN-LAST:event_btn_clear1ActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
            String ProjId = txt_projID.getText();
            String ProjName = txt_proj_name.getText();
            String completion_date =  txt_completion_date.getText();
            String created_date = lbl_current_date.getText();
            String department = txt_dept_name.getText();
            String remarks = txt_remarks.getText();
            String recieved_date = txt_recieved_date.getText();
            String starting_date = txt_starting_date.getText();
            String proj_status = (String) combo_proj_status.getSelectedItem();
            String developers = (String) combo_developers.getSelectedItem();
            String branch_name = txt_branch_name.getText();
            String task_details = "";
            String dev_Id = txt_developer_id.getText();
            
        Report_Requests_Model Model = new Report_Requests_Model (ProjId,ProjName,recieved_date,created_date,starting_date,completion_date,proj_status,remarks,task_details,department,branch_name,developers,dev_Id);
        Report_Requests_Controller controller= new Report_Requests_Controller();
     
            
        try {
            if(controller.updateReport_requests(Model)){
                
                JOptionPane.showMessageDialog(this, "The record updated");
                clearAll();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        try {
            clearTable();
            loadTable();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshActionPerformed

    private void lbl_logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_logoMouseClicked

    private void combo_developersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_developersItemStateChanged
        // TODO add your handling code here:
        try {
            
            String developer_Name = combo_developers.getSelectedItem().toString();
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("select * from users where user_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                txt_developer_id.setText(String.valueOf(rst.getObject("user_id")));
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_combo_developersItemStateChanged

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
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear1;
    private javax.swing.JButton btn_clear2;
    private javax.swing.JButton btn_submit1;
    private javax.swing.JButton btn_submit2;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_update1;
    private javax.swing.JPanel card1;
    private javax.swing.JPanel card2;
    private javax.swing.JPanel card3;
    private javax.swing.JPanel card4;
    private javax.swing.JComboBox<String> combo_developers;
    private javax.swing.JComboBox<String> combo_developers1;
    private javax.swing.JComboBox<String> combo_proj_status;
    private javax.swing.JComboBox<String> combo_proj_status1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel_main;
    private javax.swing.JPanel jPanel_main1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane_menu;
    private javax.swing.JSplitPane jSplitPane_menu1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lbl_create_request1;
    private javax.swing.JLabel lbl_create_request2;
    private javax.swing.JLabel lbl_current_date;
    private javax.swing.JLabel lbl_current_date1;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_logo1;
    private javax.swing.JLabel lbl_proj_completion_date;
    private javax.swing.JLabel lbl_proj_completion_date1;
    private javax.swing.JLabel lbl_proj_completion_date2;
    private javax.swing.JLabel lbl_proj_completion_date3;
    private javax.swing.JLabel lbl_proj_completion_date4;
    private javax.swing.JLabel lbl_proj_completion_date5;
    private javax.swing.JLabel lbl_proj_completion_date6;
    private javax.swing.JLabel lbl_proj_completion_date7;
    private javax.swing.JLabel lbl_proj_dept_name;
    private javax.swing.JLabel lbl_proj_dept_name1;
    private javax.swing.JLabel lbl_proj_dept_name2;
    private javax.swing.JLabel lbl_proj_dept_name3;
    private javax.swing.JLabel lbl_proj_dept_name4;
    private javax.swing.JLabel lbl_proj_dept_name5;
    private javax.swing.JLabel lbl_proj_dept_name6;
    private javax.swing.JLabel lbl_proj_dept_name7;
    private javax.swing.JLabel lbl_proj_id;
    private javax.swing.JLabel lbl_proj_id1;
    private javax.swing.JLabel lbl_proj_id2;
    private javax.swing.JLabel lbl_proj_id3;
    private javax.swing.JLabel lbl_proj_name;
    private javax.swing.JLabel lbl_proj_name1;
    private javax.swing.JLabel lbl_view_request;
    private javax.swing.JLabel lbl_view_request1;
    private javax.swing.JPanel menu_bar;
    private javax.swing.JPanel menu_bar1;
    private javax.swing.JPanel menu_header;
    private javax.swing.JPanel menu_header1;
    private javax.swing.JPanel pri_CardLayout;
    private javax.swing.JPanel pri_CardLayout1;
    private javax.swing.JPanel red_line;
    private javax.swing.JPanel red_line1;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tbl_requests;
    private javax.swing.JTable tbl_requests1;
    private javax.swing.JTextField txt_branch_name;
    private javax.swing.JTextField txt_branch_name1;
    private javax.swing.JTextField txt_completion_date;
    private javax.swing.JTextField txt_completion_date1;
    private javax.swing.JTextField txt_dept_name;
    private javax.swing.JTextField txt_dept_name1;
    private javax.swing.JTextField txt_developer_id;
    private javax.swing.JTextField txt_developer_id1;
    private javax.swing.JTextField txt_projID;
    private javax.swing.JTextField txt_projID1;
    private javax.swing.JTextField txt_proj_name;
    private javax.swing.JTextField txt_proj_name1;
    private javax.swing.JTextField txt_recieved_date;
    private javax.swing.JTextField txt_recieved_date1;
    private javax.swing.JTextArea txt_remarks;
    private javax.swing.JTextArea txt_remarks1;
    private javax.swing.JTextField txt_starting_date;
    private javax.swing.JTextField txt_starting_date1;
    // End of variables declaration//GEN-END:variables
}
