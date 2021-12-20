/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BranchController;
import Controller.DeaprtmentController;
import Controller.RegionController;
import Controller.Report_Requests_Controller;
import DB.DBConnection;
import Model.BranchModel;
import Model.DepartmentModel;
import Model.RegionModel;
import Model.Report_Requests_Model;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
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
import static jdk.nashorn.internal.runtime.JSType.isNumber;
import utility.Organization;
//import static jdk.nashorn.internal.runtime.JSType.isNumber;

/**
 *
 * @author dpman
 */
public class DashBoard extends javax.swing.JFrame {

    /**
     * Creates new form RequestForm
     */
    CardLayout cardLayout;
    Connection con = DBConnection.getConnection();
    public DashBoard() {
        initComponents();
        MenuBar.setVisible(false);
        txt_developer_id2.disable();
        load_developers();
        Request_Form_Id_generate();
        this.setLocationRelativeTo(null);
        scaleImage();
        AutoCompletion.enable(cmb_Org_Name);
        //new DashBoard().setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/PBCircle.png")));
        //new DashBoard().setIconImage(new ImageIcon(getClass().getResource("inventory/images/image-background-5.jpg")).getImage());
        try {
            loadTable();
            loadBranchTable();
            loadRegionTable();
            loadDepartmentTable();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        setResizable(false);
        load_date();
        //jTable1.getColumnModel().getColumn(1).setPreferredWidth(5);
        lbl_create_request1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl_view_request.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lbl_maintenace.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cardLayout = (CardLayout)(pri_CardLayout.getLayout());
    }
    Single_Row raw_data = new Single_Row();
    Color DefaultColor,ClickedColor,HoveredColor;
    
        /*    This is used to load the curent dat in the label */
    
    public void load_date(){
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
           LocalDateTime now = LocalDateTime.now();  
           String format = dtf.format(now);  
           lbl_current_date2.setText(format);
    }
    
    /*
        Clearing all the details in request table.
    */

    public void clearTable(){
        
        tbl_requests.setModel(new DefaultTableModel(null,new String[]
        {"Project_Id","Project_Name" , "Recieve dDate" , "Created_Date",
         "Start Date","Completion Date", "Current Status", "Remarks","Task Details",
         "Department Name" ,"Branch Name" ,"Developer Name","Developer_Id"}));
    }
    
    
    public void clearBranchTable(){
        
        tblBranch.setModel(new DefaultTableModel(null,new String[]
        {"branch_Code","branch_Name"}));
    }
    
    public void clearDepartmentTable(){
        
        tblDept.setModel(new DefaultTableModel(null,new String[]
        {"Department Id","Department Name"}));
    }
    
    public void clearRegionTable(){
        
        tblRegion.setModel(new DefaultTableModel(null,new String[]
        {"Region IDe","Region Name"}));
    }
    
    
    
    /*    This is used to clear all the data in form*/
    
     public void clearAll() throws ClassNotFoundException, SQLException{
        raw_data.txt_proj_id.setText("");
        raw_data.txt_recieved_date.setText("");
        raw_data.txt_proj_name.setText("");
        //raw_data.txt_branch_name.setText("");
        raw_data.txt_completion_date.setText("");
        raw_data.txt_remarks.setText("");
        raw_data.txt_recieved_date.setText("");
        raw_data.txt_starting_date.setText("");
        raw_data.txt_developer_id.setText("");
        combo_proj_status2.getModel().setSelectedItem("");
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
        lbl_maintenace.setBackground(DefaultColor);
        //Order.setBackground(DefaultColor);
    }
    
    public String Request_Form_Id_generate(){
            String UID = "Proj" ;
            String uuid=UUID.randomUUID().toString();
            uuid=uuid.substring(0,3);
            UID=UID+uuid;
            txt_projID2.setText(UID);
            txt_projID2.disable();
            return UID;
            
        }
    
     /*    This is used to load devlopers in comboBox*/
    public void load_developers(){
        try {
                Report_Requests_Controller controller = new Report_Requests_Controller();
                ArrayList <String> userNames = controller.loadUserNames();
                for(String name:userNames){
                    
                    combo_developers2.addItem(name);
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
                i.getRemarks(),i.getTask_details(),i.getOrganization().getType(),
                i.getOrganization().getOrgName(),i.getDeveloper_name(),i.getDeveloper_id()};
                dtm.addRow(arr);
           }
    }
    

     public void loadDepartmentTable() throws ClassNotFoundException, SQLException{
        
           DeaprtmentController controller= new  DeaprtmentController();
           DefaultTableModel dtm= (DefaultTableModel)tblDept.getModel();
           ArrayList<DepartmentModel> DepartmentDetails = controller.getAllDepartmentDetails();
           
           for(DepartmentModel i: DepartmentDetails){
               
                Object arr[]={i.getDeparmentCode(),i.getDeparmentName()};
                dtm.addRow(arr);
           }
    }
     
    public void loadBranchTable() throws ClassNotFoundException, SQLException{
        
           BranchController controller= new BranchController();
           DefaultTableModel dtm= (DefaultTableModel) tblBranch.getModel();
           ArrayList<BranchModel> brancheDetails =controller.getAllBranchDetails() ;
           
           for(BranchModel b: brancheDetails){
               
                Object arr[]={b.getBranchCode(),b.getBranchName()};
                dtm.addRow(arr);
           }
    }
    
    public void loadRegionTable() throws ClassNotFoundException, SQLException{
        
           RegionController controller= new RegionController();
           DefaultTableModel dtm= (DefaultTableModel)tblRegion.getModel();
           ArrayList<RegionModel> regions = controller.getAllRegionDetails();
           
           for(RegionModel i: regions){
               
                Object arr[]={i.getRegionCode(),i.getRegionName()};
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
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel_main = new javax.swing.JPanel();
        menu_header = new javax.swing.JPanel();
        red_line = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        jSplitPane_menu = new javax.swing.JSplitPane();
        menu_bar = new javax.swing.JPanel();
        lbl_create_request1 = new javax.swing.JLabel();
        lbl_maintenace = new javax.swing.JLabel();
        lbl_view_request = new javax.swing.JLabel();
        MenuBar = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pri_CardLayout = new javax.swing.JPanel();
        pri_Card1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_requests = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        pri_Card3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lbl_proj_id4 = new javax.swing.JLabel();
        lbl_proj_id5 = new javax.swing.JLabel();
        lbl_proj_name2 = new javax.swing.JLabel();
        lbl_proj_completion_date8 = new javax.swing.JLabel();
        txt_proj_name2 = new javax.swing.JTextField();
        txt_projID2 = new javax.swing.JTextField();
        txt_completion_date2 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txt_remarks2 = new javax.swing.JTextArea();
        txt_recieved_date2 = new javax.swing.JTextField();
        lbl_proj_completion_date9 = new javax.swing.JLabel();
        lbl_proj_completion_date10 = new javax.swing.JLabel();
        txt_starting_date2 = new javax.swing.JTextField();
        lbl_proj_completion_date11 = new javax.swing.JLabel();
        combo_proj_status2 = new javax.swing.JComboBox<>();
        lbl_current_date2 = new javax.swing.JLabel();
        btn_submit3 = new javax.swing.JButton();
        btn_clear3 = new javax.swing.JButton();
        lbl_proj_dept_name9 = new javax.swing.JLabel();
        lbl_proj_dept_name10 = new javax.swing.JLabel();
        combo_developers2 = new javax.swing.JComboBox<>();
        lbl_proj_dept_name11 = new javax.swing.JLabel();
        txt_developer_id2 = new javax.swing.JTextField();
        cmbTypes = new javax.swing.JComboBox<>();
        lbl_proj_dept_name12 = new javax.swing.JLabel();
        cmb_Org_Name = new javax.swing.JComboBox<>();
        mng_dept_Card = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDept = new javax.swing.JTable();
        lbl_proj_id6 = new javax.swing.JLabel();
        lbl_proj_name3 = new javax.swing.JLabel();
        txtDeptCode = new javax.swing.JTextField();
        txtDeptName = new javax.swing.JTextField();
        btnAddDepartment = new javax.swing.JButton();
        btnUpdateDepartment = new javax.swing.JButton();
        btnDeleteDepartment = new javax.swing.JButton();
        mng_region_Card = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblRegion = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtRegionName = new javax.swing.JTextField();
        txtRegion = new javax.swing.JTextField();
        btnUpdateRegions = new javax.swing.JButton();
        btnAddRegion = new javax.swing.JButton();
        btnDeleteRegion = new javax.swing.JButton();
        mng_branches_Card = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblBranch = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtBranchName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtBranchCode = new javax.swing.JTextField();
        btnAddBranch = new javax.swing.JButton();
        btnUpdateBranch = new javax.swing.JButton();
        btnAddDelete = new javax.swing.JButton();

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

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImages(getIconImages());
        setResizable(false);

        jPanel_main.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu_header.setBackground(new java.awt.Color(255, 174, 1));

        red_line.setBackground(new java.awt.Color(206, 0, 0));

        javax.swing.GroupLayout red_lineLayout = new javax.swing.GroupLayout(red_line);
        red_line.setLayout(red_lineLayout);
        red_lineLayout.setHorizontalGroup(
            red_lineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        red_lineLayout.setVerticalGroup(
            red_lineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
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
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238)
                .addComponent(jLabel1)
                .addContainerGap(259, Short.MAX_VALUE))
            .addComponent(red_line, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menu_headerLayout.setVerticalGroup(
            menu_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menu_headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(red_line, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_main.add(menu_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1440, 100));

        jSplitPane_menu.setBorder(null);
        jSplitPane_menu.setDividerSize(3);

        menu_bar.setBackground(new java.awt.Color(255, 153, 0));
        menu_bar.setPreferredSize(new java.awt.Dimension(305, 184));

        lbl_create_request1.setBackground(new java.awt.Color(255, 255, 255));
        lbl_create_request1.setFont(new java.awt.Font("Dialog", 1, 34)); // NOI18N
        lbl_create_request1.setForeground(new java.awt.Color(0, 0, 0));
        lbl_create_request1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_create_request1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/request (2).png"))); // NOI18N
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

        lbl_maintenace.setBackground(new java.awt.Color(255, 255, 255));
        lbl_maintenace.setFont(new java.awt.Font("Dialog", 1, 34)); // NOI18N
        lbl_maintenace.setForeground(new java.awt.Color(0, 0, 0));
        lbl_maintenace.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_maintenace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings.png"))); // NOI18N
        lbl_maintenace.setText("Maintenance");
        lbl_maintenace.setOpaque(true);
        lbl_maintenace.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_maintenaceMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_maintenaceMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_maintenaceMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_maintenaceMousePressed(evt);
            }
        });
        lbl_maintenace.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lbl_maintenaceKeyPressed(evt);
            }
        });

        lbl_view_request.setBackground(new java.awt.Color(255, 255, 255));
        lbl_view_request.setFont(new java.awt.Font("Dialog", 1, 34)); // NOI18N
        lbl_view_request.setForeground(new java.awt.Color(0, 0, 0));
        lbl_view_request.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_view_request.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/interview (3).png"))); // NOI18N
        lbl_view_request.setText(" View Requests");
        lbl_view_request.setOpaque(true);
        lbl_view_request.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_view_requestMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_view_requestMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_view_requestMouseExited(evt);
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

        MenuBar.setBackground(new java.awt.Color(204, 204, 204));
        MenuBar.setForeground(new java.awt.Color(255, 255, 255));
        MenuBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuBarMouseExited(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 0));
        jLabel6.setText("             Manage Department Details");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.setOpaque(true);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("                 Manage Branch Details");
        jLabel7.setToolTipText("");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.setOpaque(true);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 0));
        jLabel9.setText("                 Manage Region Details");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.setOpaque(true);
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        javax.swing.GroupLayout MenuBarLayout = new javax.swing.GroupLayout(MenuBar);
        MenuBar.setLayout(MenuBarLayout);
        MenuBarLayout.setHorizontalGroup(
            MenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        MenuBarLayout.setVerticalGroup(
            MenuBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuBarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menu_barLayout = new javax.swing.GroupLayout(menu_bar);
        menu_bar.setLayout(menu_barLayout);
        menu_barLayout.setHorizontalGroup(
            menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_barLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MenuBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lbl_create_request1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                        .addComponent(lbl_maintenace, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_view_request, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        menu_barLayout.setVerticalGroup(
            menu_barLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_barLayout.createSequentialGroup()
                .addComponent(lbl_create_request1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_view_request, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_maintenace, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MenuBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(422, Short.MAX_VALUE))
        );

        jSplitPane_menu.setLeftComponent(menu_bar);

        pri_CardLayout.setBackground(new java.awt.Color(255, 255, 255));
        pri_CardLayout.setLayout(new java.awt.CardLayout());

        pri_Card1.setBackground(new java.awt.Color(255, 255, 255));
        pri_Card1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        tbl_requests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project_Id", "Project_Name", "Recieved Date", "Created_Date", "Start Date", "Completion Date", "Current Status", "Remarks", "Task Details", "Organization Type", "Organization Name", "Developer Name", "Developer_Id"
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

        javax.swing.GroupLayout pri_Card1Layout = new javax.swing.GroupLayout(pri_Card1);
        pri_Card1.setLayout(pri_Card1Layout);
        pri_Card1Layout.setHorizontalGroup(
            pri_Card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pri_Card1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pri_Card1Layout.setVerticalGroup(
            pri_Card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pri_Card1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pri_CardLayout.add(pri_Card1, "card2");

        pri_Card3.setBackground(new java.awt.Color(255, 255, 255));
        pri_Card3.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel5.setBackground(new java.awt.Color(255, 153, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("                                    Report Request Form");
        jLabel5.setOpaque(true);

        lbl_proj_id4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id4.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id4.setText("Remarks :");

        lbl_proj_id5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id5.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id5.setText("Project ID :");

        lbl_proj_name2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_name2.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_name2.setText("Project Name :");

        lbl_proj_completion_date8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date8.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date8.setText("Completion Date :");

        txt_proj_name2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_proj_name2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_proj_name2ActionPerformed(evt);
            }
        });

        txt_projID2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_projID2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_projID2ActionPerformed(evt);
            }
        });

        txt_completion_date2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_completion_date2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_completion_date2ActionPerformed(evt);
            }
        });

        txt_remarks2.setColumns(20);
        txt_remarks2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_remarks2.setRows(5);
        jScrollPane6.setViewportView(txt_remarks2);

        txt_recieved_date2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_recieved_date2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_recieved_date2ActionPerformed(evt);
            }
        });

        lbl_proj_completion_date9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date9.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date9.setText("Recieved Date :");

        lbl_proj_completion_date10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date10.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date10.setText("Starting  Date :");

        txt_starting_date2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_starting_date2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_starting_date2ActionPerformed(evt);
            }
        });

        lbl_proj_completion_date11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_completion_date11.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_completion_date11.setText(" Project Status:");

        combo_proj_status2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Ongoing", "Done", "Blocked" }));

        lbl_current_date2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbl_current_date2.setForeground(new java.awt.Color(0, 0, 0));

        btn_submit3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_submit3.setText("Submit");
        btn_submit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submit3ActionPerformed(evt);
            }
        });

        btn_clear3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_clear3.setText("Clear");
        btn_clear3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear3ActionPerformed(evt);
            }
        });

        lbl_proj_dept_name9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name9.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name9.setText("Developer Name:");

        lbl_proj_dept_name10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name10.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name10.setText("Branch Name:");

        combo_developers2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_developers2ItemStateChanged(evt);
            }
        });
        combo_developers2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_developers2MouseClicked(evt);
            }
        });
        combo_developers2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_developers2ActionPerformed(evt);
            }
        });

        lbl_proj_dept_name11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name11.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name11.setText("Developer Id:");

        txt_developer_id2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txt_developer_id2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_developer_id2ActionPerformed(evt);
            }
        });

        cmbTypes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Branch", "Department", "Region" }));
        cmbTypes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTypesItemStateChanged(evt);
            }
        });
        cmbTypes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypesActionPerformed(evt);
            }
        });

        lbl_proj_dept_name12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_dept_name12.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_dept_name12.setText("Type:");

        javax.swing.GroupLayout pri_Card3Layout = new javax.swing.GroupLayout(pri_Card3);
        pri_Card3.setLayout(pri_Card3Layout);
        pri_Card3Layout.setHorizontalGroup(
            pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pri_Card3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pri_Card3Layout.createSequentialGroup()
                                .addComponent(lbl_proj_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_proj_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pri_Card3Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(lbl_proj_id5, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_projID2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pri_Card3Layout.createSequentialGroup()
                                .addComponent(lbl_proj_completion_date9, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_recieved_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pri_Card3Layout.createSequentialGroup()
                                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_proj_completion_date10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(pri_Card3Layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addComponent(lbl_current_date2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_starting_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE))
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addComponent(lbl_proj_completion_date8, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txt_completion_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addComponent(lbl_proj_dept_name9, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(combo_developers2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addComponent(lbl_proj_completion_date11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(combo_proj_status2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pri_Card3Layout.createSequentialGroup()
                        .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pri_Card3Layout.createSequentialGroup()
                                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbl_proj_dept_name11, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_proj_dept_name10, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_proj_id4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pri_Card3Layout.createSequentialGroup()
                                        .addComponent(btn_submit3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_clear3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane6)
                                    .addGroup(pri_Card3Layout.createSequentialGroup()
                                        .addComponent(txt_developer_id2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmb_Org_Name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pri_Card3Layout.createSequentialGroup()
                                .addComponent(lbl_proj_dept_name12, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)))
                .addGap(43, 43, 43))
        );
        pri_Card3Layout.setVerticalGroup(
            pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pri_Card3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_current_date2)
                .addGap(37, 37, 37)
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_projID2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_id5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_proj_status2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_proj_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_dept_name9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_developers2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_proj_completion_date8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_completion_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_dept_name11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_developer_id2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_recieved_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_completion_date9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_proj_dept_name12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTypes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_proj_completion_date10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_starting_date2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_proj_dept_name10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb_Org_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25)
                .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addComponent(lbl_proj_id4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pri_Card3Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addGroup(pri_Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_clear3)
                            .addComponent(btn_submit3))
                        .addGap(51, 51, 51))))
        );

        pri_CardLayout.add(pri_Card3, "card3");

        jLabel10.setBackground(new java.awt.Color(255, 153, 0));
        jLabel10.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("                                 Manage Department details");
        jLabel10.setOpaque(true);

        tblDept.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Department Name", "Department ID"
            }
        ));
        jScrollPane2.setViewportView(tblDept);

        lbl_proj_id6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_id6.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_id6.setText("Department Name :");

        lbl_proj_name3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbl_proj_name3.setForeground(new java.awt.Color(0, 0, 0));
        lbl_proj_name3.setText("Department Code :");

        btnAddDepartment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddDepartment.setText("Add");
        btnAddDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDepartmentActionPerformed(evt);
            }
        });

        btnUpdateDepartment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUpdateDepartment.setText("Update");
        btnUpdateDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDepartmentActionPerformed(evt);
            }
        });

        btnDeleteDepartment.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDeleteDepartment.setText("Delete");
        btnDeleteDepartment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDepartmentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mng_dept_CardLayout = new javax.swing.GroupLayout(mng_dept_Card);
        mng_dept_Card.setLayout(mng_dept_CardLayout);
        mng_dept_CardLayout.setHorizontalGroup(
            mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(mng_dept_CardLayout.createSequentialGroup()
                .addGroup(mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mng_dept_CardLayout.createSequentialGroup()
                        .addGap(607, 607, 607)
                        .addComponent(btnAddDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateDepartment)
                        .addGap(18, 18, 18)
                        .addComponent(btnDeleteDepartment))
                    .addGroup(mng_dept_CardLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mng_dept_CardLayout.createSequentialGroup()
                                .addComponent(lbl_proj_id6, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDeptName, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mng_dept_CardLayout.createSequentialGroup()
                                .addComponent(lbl_proj_name3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDeptCode, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mng_dept_CardLayout.setVerticalGroup(
            mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mng_dept_CardLayout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addGroup(mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_proj_id6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeptName, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_proj_name3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDeptCode, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mng_dept_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddDepartment)
                    .addComponent(btnUpdateDepartment)
                    .addComponent(btnDeleteDepartment))
                .addGap(135, 135, 135))
        );

        pri_CardLayout.add(mng_dept_Card, "card5");

        jLabel11.setBackground(new java.awt.Color(255, 153, 0));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("                                     Manage Region details");
        jLabel11.setOpaque(true);

        tblRegion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Region Name", "Region Code"
            }
        ));
        tblRegion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRegionMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblRegion);

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("Region Name:");

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setText("Region Code:");

        btnUpdateRegions.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUpdateRegions.setText("Update");
        btnUpdateRegions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateRegionsActionPerformed(evt);
            }
        });

        btnAddRegion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddRegion.setText("Add");
        btnAddRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRegionActionPerformed(evt);
            }
        });

        btnDeleteRegion.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnDeleteRegion.setText("Delete");
        btnDeleteRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRegionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mng_region_CardLayout = new javax.swing.GroupLayout(mng_region_Card);
        mng_region_Card.setLayout(mng_region_CardLayout);
        mng_region_CardLayout.setHorizontalGroup(
            mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mng_region_CardLayout.createSequentialGroup()
                .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 1109, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addContainerGap())
            .addGroup(mng_region_CardLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mng_region_CardLayout.createSequentialGroup()
                        .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(32, 32, 32)
                        .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRegionName)
                            .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mng_region_CardLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddRegion)
                        .addGap(38, 38, 38)
                        .addComponent(btnUpdateRegions)
                        .addGap(44, 44, 44)
                        .addComponent(btnDeleteRegion)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mng_region_CardLayout.setVerticalGroup(
            mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mng_region_CardLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtRegionName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(mng_region_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateRegions)
                    .addComponent(btnAddRegion)
                    .addComponent(btnDeleteRegion))
                .addGap(118, 118, 118))
        );

        pri_CardLayout.add(mng_region_Card, "card6");

        jLabel14.setBackground(new java.awt.Color(255, 153, 0));
        jLabel14.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("                                     Manage Branch details");
        jLabel14.setOpaque(true);

        tblBranch.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Branch Code", "Branch Name"
            }
        ));
        jScrollPane7.setViewportView(tblBranch);

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setText("Branch Name:");

        txtBranchName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBranchNameKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setText("Branch Code:");

        txtBranchCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBranchCodeKeyTyped(evt);
            }
        });

        btnAddBranch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddBranch.setText("Add");
        btnAddBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBranchActionPerformed(evt);
            }
        });

        btnUpdateBranch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnUpdateBranch.setText("Update");
        btnUpdateBranch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBranchActionPerformed(evt);
            }
        });

        btnAddDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnAddDelete.setText("Delete");
        btnAddDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mng_branches_CardLayout = new javax.swing.GroupLayout(mng_branches_Card);
        mng_branches_Card.setLayout(mng_branches_CardLayout);
        mng_branches_CardLayout.setHorizontalGroup(
            mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
            .addGroup(mng_branches_CardLayout.createSequentialGroup()
                .addComponent(jScrollPane7)
                .addContainerGap())
            .addGroup(mng_branches_CardLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mng_branches_CardLayout.createSequentialGroup()
                        .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(32, 32, 32)
                        .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBranchName)
                            .addComponent(txtBranchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mng_branches_CardLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 594, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAddBranch)
                        .addGap(38, 38, 38)
                        .addComponent(btnUpdateBranch)
                        .addGap(44, 44, 44)
                        .addComponent(btnAddDelete)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mng_branches_CardLayout.setVerticalGroup(
            mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mng_branches_CardLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtBranchName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtBranchCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(mng_branches_CardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateBranch)
                    .addComponent(btnAddBranch)
                    .addComponent(btnAddDelete))
                .addGap(94, 94, 94))
        );

        pri_CardLayout.add(mng_branches_Card, "card7");

        jSplitPane_menu.setRightComponent(pri_CardLayout);

        jPanel_main.add(jSplitPane_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1440, 810));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_main, javax.swing.GroupLayout.PREFERRED_SIZE, 1435, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        lbl_maintenace.setBackground(DefaultColor);
        cardLayout.show(pri_CardLayout, "card3");
        MenuBar.setVisible(false);
    }//GEN-LAST:event_lbl_create_request1MouseClicked

    private void lbl_maintenaceMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maintenaceMousePressed
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(ClickedColor);
    }//GEN-LAST:event_lbl_maintenaceMousePressed

    private void lbl_create_request1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MouseEntered
        // TODO add your handling code here:
        lbl_create_request1.setBackground(HoveredColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_create_request1MouseEntered

    private void lbl_create_request1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_create_request1MouseExited
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_create_request1MouseExited

    private void lbl_maintenaceMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maintenaceMouseEntered
        // TODO add your handling code here:
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(HoveredColor);
    }//GEN-LAST:event_lbl_maintenaceMouseEntered

    private void lbl_maintenaceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maintenaceMouseClicked
        // TODO add your handling code here:
        cardLayout.show(pri_CardLayout, "card1");
        MenuBar.setVisible(true);
    }//GEN-LAST:event_lbl_maintenaceMouseClicked

    private void lbl_maintenaceKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_maintenaceKeyPressed
        // TODO add your handling code here:
        cardLayout.show(pri_CardLayout, "card1");
    }//GEN-LAST:event_lbl_maintenaceKeyPressed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
        String org_type = model.getValueAt(index,9).toString();
        String org_name = model.getValueAt(index,10).toString();
        String developer_name = model.getValueAt(index,11).toString();
        String developer_id = model.getValueAt(index,12).toString();
        raw_data.setVisible(true);
        raw_data.pack();
        raw_data.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        raw_data.txt_proj_id.setText(project_id);
        raw_data.txt_proj_name.setText(project_name);
        raw_data.txt_completion_date.setText(completion_date);
        raw_data.txt_remarks.setText(remarks);
        raw_data.txt_recieved_date.setText(recieved_date);
        raw_data.combo_dev_names1.setSelectedItem(developer_name);
        raw_data.txt_request_date.setText(created_date);
        raw_data.txt_starting_date.setText(start_date);
        raw_data.txt_developer_id.setText(developer_id);
        //raw_data.jTextField1.setText(org_type);
        raw_data.cmbOrgNames.setSelectedItem(org_type);
        raw_data.jComboBox2.setSelectedItem(org_name);
        System.out.println(org_name);
        combo_proj_status2.getModel().setSelectedItem(current_status);
    }//GEN-LAST:event_tbl_requestsMouseClicked
    
    private boolean validate_fields(String ProjId,String ProjName, String 
    recieved_date, String created_date, String starting_date, String proj_status, 
    String remarks, String department, String branch_name, 
    String developers, String dev_Id){
        
        if( (ProjId!=null)||(ProjName!=null)||(recieved_date!=null)||(created_date!=null)||(starting_date!=null)||
        (proj_status!=null)||(remarks!=null)||(department!=null)||(branch_name!=null)||
        (developers!=null)||(dev_Id!=null))
        {
            return true;
        }
        JOptionPane.showMessageDialog(this, "Error all the mandatory fields must be filled");
        return false;
    }
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
            
            String developer_Name = combo_developers2.getSelectedItem().toString();
            PreparedStatement pst = con.prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                
                txt_developer_id2.setText(String.valueOf(rst.getObject("developer_id")));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbl_requestsMousePressed

    private void tbl_requestsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_requestsKeyPressed
        // TODO add your handling code here:
        try {
            
            String developer_Name = combo_developers2.getSelectedItem().toString();
            PreparedStatement pst = con.prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,developer_Name);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                
                txt_developer_id2.setText(String.valueOf(rst.getObject("developer_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbl_requestsKeyPressed

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

    private void btn_clear3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear3ActionPerformed
        // TODO add your handling code here:
        txt_projID2.setText("");
        txt_proj_name2.setText("");
        txt_completion_date2.setText("");
        lbl_current_date2.setText("");
        txt_recieved_date2.setText("");
        txt_starting_date2.setText("");
    }//GEN-LAST:event_btn_clear3ActionPerformed
    /*
        Creating New Report request
    */
    private void btn_submit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submit3ActionPerformed
        // TODO add your handling code here:
        
         if(txt_projID2.getText().isEmpty()||txt_proj_name2.getText().isEmpty()||
               lbl_current_date2.getText().isEmpty()||
               txt_starting_date2.getText().isEmpty()||
               combo_proj_status2.getSelectedItem().toString().isEmpty()||cmbTypes.getSelectedItem().toString().isEmpty()||
               txt_remarks2.getText().isEmpty()||txt_developer_id2.getText().isEmpty())
            {
                   JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled"); 
            }
            
            String ProjId = txt_projID2.getText();
            String ProjName = txt_proj_name2.getText();
            String completion_date =  txt_completion_date2.getText();
            String created_date = lbl_current_date2.getText();
            String remarks = txt_remarks2.getText();
            String recieved_date = txt_recieved_date2.getText();
            String starting_date = txt_starting_date2.getText();
            String proj_status = (String) combo_proj_status2.getSelectedItem();
            String developers = (String) combo_developers2.getSelectedItem();
            String org_type = (String) cmbTypes.getSelectedItem();
            String org_name = cmb_Org_Name.getSelectedItem().toString();
            String task_details = "";
            String dev_Id = txt_developer_id2.getText();

            if( txt_completion_date2.getText().isEmpty()){
                
                Organization org = new Organization(org_type,org_name);
                Report_Requests_Model request = new Report_Requests_Model(ProjId,
                        ProjName,recieved_date,created_date,
                        starting_date,proj_status,remarks,
                        task_details,org,developers,dev_Id);
                
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
            
                Organization org = new Organization(org_type,org_name);
                Report_Requests_Model request = new  Report_Requests_Model(
                ProjId,ProjName,recieved_date,created_date,starting_date,
                completion_date,proj_status,remarks,task_details,org,developers,dev_Id);
                
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

    }//GEN-LAST:event_btn_submit3ActionPerformed

    private void txt_projID2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_projID2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_projID2ActionPerformed

    private void txt_proj_name2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_proj_name2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_proj_name2ActionPerformed

    private void txt_developer_id2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_developer_id2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_developer_id2ActionPerformed

    private void combo_developers2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_developers2ActionPerformed
        // TODO add your handling code here:
        try {
            
            String userName = combo_developers2.getSelectedItem().toString();
            PreparedStatement pst = con.prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,userName);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                
                txt_developer_id2.setText(String.valueOf(rst.getObject("developer_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_combo_developers2ActionPerformed

    private void combo_developers2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_developers2ItemStateChanged
        // TODO add your handling code here:
        try {
            
            String userName = combo_developers2.getSelectedItem().toString();
            PreparedStatement pst = con.prepareStatement("select * from projects_tbl where developer_name= ?");
            pst.setObject(1,userName);
            ResultSet rst= pst.executeQuery();
            if(rst.next()){
                txt_developer_id2.setText(String.valueOf(rst.getObject("developer_id")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_combo_developers2ItemStateChanged

    private void txt_completion_date2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_completion_date2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_completion_date2ActionPerformed

    private void txt_starting_date2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_starting_date2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_starting_date2ActionPerformed

    private void txt_recieved_date2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_recieved_date2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_recieved_date2ActionPerformed

    private void lbl_view_requestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMouseClicked
        // TODO add your handling code here:
        lbl_view_request.setBackground(ClickedColor);
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
        cardLayout.show(pri_CardLayout, "card2");
        MenuBar.setVisible(false);
        
    }//GEN-LAST:event_lbl_view_requestMouseClicked

    private void lbl_view_requestMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMouseEntered
        // TODO add your handling code here:
        lbl_view_request.setBackground(HoveredColor);
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_view_requestMouseEntered

    private void lbl_view_requestMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMousePressed
        // TODO add your handling code here:
        lbl_view_request.setBackground(ClickedColor);
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_view_requestMousePressed

    private void lbl_view_requestKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lbl_view_requestKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_view_requestKeyPressed

    private void lbl_view_requestMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_view_requestMouseExited
        // TODO add your handling code here:
        lbl_view_request.setBackground(DefaultColor);
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_view_requestMouseExited

    private void lbl_maintenaceMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_maintenaceMouseExited
        // TODO add your handling code here:
        lbl_view_request.setBackground(DefaultColor);
        lbl_create_request1.setBackground(DefaultColor);
        lbl_maintenace.setBackground(DefaultColor);
    }//GEN-LAST:event_lbl_maintenaceMouseExited

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        jLabel7.setBackground(ClickedColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(DefaultColor);
        cardLayout.show(pri_CardLayout, "card7");
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        // TODO add your handling code here:
        jLabel7.setBackground(HoveredColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(DefaultColor);
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(DefaultColor);
    }//GEN-LAST:event_jLabel7MouseExited

    private void MenuBarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuBarMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuBarMouseExited

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(ClickedColor);
        jLabel6.setBackground(DefaultColor);
        cardLayout.show(pri_CardLayout, "card6");
        
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(HoveredColor);
        jLabel6.setBackground(DefaultColor);
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(DefaultColor);
        
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(ClickedColor);
        cardLayout.show(pri_CardLayout, "card5");
        
        
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(HoveredColor);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        // TODO add your handling code here:
        jLabel7.setBackground(DefaultColor);
        jLabel9.setBackground(DefaultColor);
        jLabel6.setBackground(DefaultColor);
    }//GEN-LAST:event_jLabel6MouseExited
    /*
        Adding A new Branch
    */
    private void btnAddBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBranchActionPerformed
        // TODO add your handling code here:
        if(txtBranchName.getText().isEmpty()||txtBranchCode.getText().isEmpty())
            {
                   JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled"); 
            }

            String BranchId = txtBranchCode.getText();
            String BranchName = txtBranchName.getText();

            if (!isNumber(Integer.parseInt(BranchId))){
                JOptionPane.showMessageDialog(this, "Error BranchCode should be a number");
            } 
            else{
                
                BranchModel newBranch = new BranchModel(BranchId,BranchName);
                BranchController controller= new BranchController();

                try {
                    if(controller.addBranch(newBranch)){

                        JOptionPane.showMessageDialog(this, "The record inserted");
                        clearAll();
                        clearBranchTable();
                        loadBranchTable();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //JOptionPane.showMessageDialog(this, "The branch ID should be a number");*/

    }//GEN-LAST:event_btnAddBranchActionPerformed

    private void btnAddDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDeleteActionPerformed
        try {
            // TODO add your handling code here:
            String BranchId = txtBranchCode.getText();
            String BranchName = txtBranchName.getText();
            BranchController controller= new BranchController();
            
            if(txtBranchName.getText().isEmpty() && txtBranchCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Branch ID or Branch Name should be enterd");
            }
            else if(txtBranchName.getText().isEmpty() && !txtBranchCode.getText().isEmpty())
            { 
                 if(controller.deleteBranch(BranchId)){
                    JOptionPane.showMessageDialog(this, "Branch Deleted Successfully");
                    clearBranchTable();
                    loadBranchTable();
                 }
                 
            }
            else{
                if (controller.deleteBranch(BranchName)){
                    JOptionPane.showMessageDialog(this, "Branch Deleted Successfully");
                    clearBranchTable();
                    loadBranchTable();
                 }
            }
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddDeleteActionPerformed

    private void btnUpdateBranchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBranchActionPerformed
        // TODO add your handling code here:
        
        String BranchId = txtBranchCode.getText();
        String BranchName = txtBranchName.getText();
        BranchModel Model = new BranchModel(BranchId,BranchName); 
        BranchController controller= new BranchController();

        try {
            if(controller.updateaddBranch(Model)){
                
                JOptionPane.showMessageDialog(this, "The record updated");
                clearBranchTable();
                loadBranchTable();
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateBranchActionPerformed

    private void txtBranchNameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBranchNameKeyTyped
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtBranchNameKeyTyped

    private void txtBranchCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBranchCodeKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_txtBranchCodeKeyTyped

    private void btnAddRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRegionActionPerformed
        // TODO add your handling code here:
        String RegionId = txtRegion.getText();
        String RegionName = txtRegionName.getText();
         if(txtRegionName.getText().isEmpty()||txtRegion.getText().isEmpty())
            {
                   JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled"); 
            }

            if (!isNumber(Integer.parseInt(RegionId))){
                JOptionPane.showMessageDialog(this, "Error Region should be a number");
            } 
            else{
                
                RegionModel newRegion = new RegionModel(RegionId,RegionName);
                RegionController controller= new RegionController();

                try {
                    if(controller.addRegion(newRegion)){

                        JOptionPane.showMessageDialog(this, "The record inserted");
                        clearAll();
                        clearRegionTable();
                        loadRegionTable();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }//GEN-LAST:event_btnAddRegionActionPerformed
    /*
        This is the code to update the regions
    */
    private void btnUpdateRegionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateRegionsActionPerformed
        // TODO add your handling code here:
        String RegionId = txtRegion.getText();
        String RegionName = txtRegionName.getText();
        RegionModel Model = new RegionModel(RegionId,RegionName); 
        RegionController controller= new RegionController();

        try {
            if(controller.updateRegion(Model)){
                
                JOptionPane.showMessageDialog(this, "The record updated");
                clearRegionTable();
                loadRegionTable();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateRegionsActionPerformed

    private void btnDeleteRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRegionActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            String RegionId = txtRegion.getText();
            String RegionName = txtRegionName.getText();
            RegionController controller= new RegionController();
            
            if( txtRegionName.getText().isEmpty() && txtRegion.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Region ID or Region Name should be enterd");
            }
            else if(txtBranchName.getText().isEmpty() && !txtBranchCode.getText().isEmpty())
            { 
                 if(controller.deleteRegion(RegionId)){
                    JOptionPane.showMessageDialog(this, "Region Deleted Successfully");
                    clearRegionTable();
                    loadRegionTable();
                 }
                 
            }
            else{
                if (controller.deleteRegion(RegionName)){
                    JOptionPane.showMessageDialog(this, "Region Deleted Successfully");
                    clearRegionTable();
                    loadRegionTable();
                 }
            }
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteRegionActionPerformed

    private void btnAddDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDepartmentActionPerformed
        // TODO add your handling code here:
        String DepartmentId = txtDeptCode.getText();
        String DepartmentName = txtDeptName.getText();
         if(txtDeptName.getText().isEmpty()||txtDeptCode.getText().isEmpty())
            {
                   JOptionPane.showMessageDialog(this, "Error all the mandotory fields must be filled"); 
            }

            if (!isNumber(Integer.parseInt(DepartmentId))){
                JOptionPane.showMessageDialog(this, "Error DepartmentID should be a number");
            } 
            else{
                
                DepartmentModel newDepartment = new DepartmentModel(DepartmentId,DepartmentName);
                DeaprtmentController controller= new DeaprtmentController();

                try {
                    if(controller.addDepartment(newDepartment)){

                        JOptionPane.showMessageDialog(this, "The record inserted");
                        clearAll();
                        clearDepartmentTable();
                        loadDepartmentTable();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }//GEN-LAST:event_btnAddDepartmentActionPerformed

    private void btnUpdateDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDepartmentActionPerformed
        // TODO add your handling code here:
        
        String DepartmentId = txtDeptCode.getText();
        String DepartmentName = txtDeptName.getText();
        DepartmentModel Model = new DepartmentModel(DepartmentId,DepartmentName); 
        DeaprtmentController controller= new DeaprtmentController();

        try {
            if(controller.updateDepartment(Model)){
                
                JOptionPane.showMessageDialog(this, "The record updated");
                clearDepartmentTable();
                loadDepartmentTable();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateDepartmentActionPerformed
    /* 
        This is the code to delete a branch
    */
    private void btnDeleteDepartmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDepartmentActionPerformed
        try {
            // TODO add your handling code here:
            String DepartmentId = txtDeptCode.getText();
            String DepartmentName = txtDeptName.getText();
            DeaprtmentController controller= new DeaprtmentController();
            
            if( txtDeptName.getText().isEmpty() && txtDeptCode.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Department ID or Department Name should be enterd");
            }
            else if(txtBranchName.getText().isEmpty() && !txtDeptCode.getText().isEmpty())
            { 
                 if(controller.deleteDepartment(DepartmentId)){
                    JOptionPane.showMessageDialog(this, "Department Deleted Successfully");
                    clearDepartmentTable();
                    loadDepartmentTable();
                 }
                 
            }
            else{
                if (controller.deleteDepartment(DepartmentName)){
                    JOptionPane.showMessageDialog(this, "Department Deleted Successfully");
                    clearDepartmentTable();
                    loadDepartmentTable();
                 }
            }
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteDepartmentActionPerformed

    private void cmbTypesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTypesItemStateChanged
        // TODO add your handling code here:
        cmb_Org_Name.removeAllItems();
         try {
            
            String orgType = cmbTypes.getSelectedItem().toString();
            Report_Requests_Controller controller = new Report_Requests_Controller();
            ArrayList <String> orgNames =controller.loadOrgNames(orgType);
            
            for(String Org:orgNames){
                    cmb_Org_Name.addItem(Org);
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DashBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmbTypesItemStateChanged

    private void cmbTypesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypesActionPerformed
         // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbTypesActionPerformed

    private void tblRegionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRegionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRegionMouseClicked

    private void combo_developers2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_developers2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_developers2MouseClicked

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
                
                DashBoard db = new DashBoard();
                db.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/PBCircle.png")));
               
            }
        });

    }
    
    public static void run(){
      
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MenuBar;
    private javax.swing.JButton btnAddBranch;
    private javax.swing.JButton btnAddDelete;
    private javax.swing.JButton btnAddDepartment;
    private javax.swing.JButton btnAddRegion;
    private javax.swing.JButton btnDeleteDepartment;
    private javax.swing.JButton btnDeleteRegion;
    private javax.swing.JButton btnUpdateBranch;
    private javax.swing.JButton btnUpdateDepartment;
    private javax.swing.JButton btnUpdateRegions;
    private javax.swing.JButton btn_clear2;
    private javax.swing.JButton btn_clear3;
    private javax.swing.JButton btn_submit2;
    private javax.swing.JButton btn_submit3;
    private javax.swing.JButton btn_update1;
    private javax.swing.JPanel card3;
    private javax.swing.JPanel card4;
    private javax.swing.JComboBox<String> cmbTypes;
    public javax.swing.JComboBox<String> cmb_Org_Name;
    private javax.swing.JComboBox<String> combo_developers1;
    private javax.swing.JComboBox<String> combo_developers2;
    private javax.swing.JComboBox<String> combo_proj_status1;
    private javax.swing.JComboBox<String> combo_proj_status2;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel_main;
    private javax.swing.JPanel jPanel_main1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane_menu;
    private javax.swing.JSplitPane jSplitPane_menu1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel lbl_create_request1;
    private javax.swing.JLabel lbl_create_request2;
    private javax.swing.JLabel lbl_current_date1;
    private javax.swing.JLabel lbl_current_date2;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_logo1;
    private javax.swing.JLabel lbl_maintenace;
    private javax.swing.JLabel lbl_proj_completion_date10;
    private javax.swing.JLabel lbl_proj_completion_date11;
    private javax.swing.JLabel lbl_proj_completion_date4;
    private javax.swing.JLabel lbl_proj_completion_date5;
    private javax.swing.JLabel lbl_proj_completion_date6;
    private javax.swing.JLabel lbl_proj_completion_date7;
    private javax.swing.JLabel lbl_proj_completion_date8;
    private javax.swing.JLabel lbl_proj_completion_date9;
    private javax.swing.JLabel lbl_proj_dept_name10;
    private javax.swing.JLabel lbl_proj_dept_name11;
    private javax.swing.JLabel lbl_proj_dept_name12;
    private javax.swing.JLabel lbl_proj_dept_name4;
    private javax.swing.JLabel lbl_proj_dept_name5;
    private javax.swing.JLabel lbl_proj_dept_name6;
    private javax.swing.JLabel lbl_proj_dept_name7;
    private javax.swing.JLabel lbl_proj_dept_name9;
    private javax.swing.JLabel lbl_proj_id2;
    private javax.swing.JLabel lbl_proj_id3;
    private javax.swing.JLabel lbl_proj_id4;
    private javax.swing.JLabel lbl_proj_id5;
    private javax.swing.JLabel lbl_proj_id6;
    private javax.swing.JLabel lbl_proj_name1;
    private javax.swing.JLabel lbl_proj_name2;
    private javax.swing.JLabel lbl_proj_name3;
    private javax.swing.JLabel lbl_view_request;
    private javax.swing.JLabel lbl_view_request1;
    private javax.swing.JPanel menu_bar;
    private javax.swing.JPanel menu_bar1;
    private javax.swing.JPanel menu_header;
    private javax.swing.JPanel menu_header1;
    private javax.swing.JPanel mng_branches_Card;
    private javax.swing.JPanel mng_dept_Card;
    private javax.swing.JPanel mng_region_Card;
    private javax.swing.JPanel pri_Card1;
    private javax.swing.JPanel pri_Card3;
    private javax.swing.JPanel pri_CardLayout;
    private javax.swing.JPanel pri_CardLayout1;
    private javax.swing.JPanel red_line;
    private javax.swing.JPanel red_line1;
    private javax.swing.JButton refresh;
    private javax.swing.JTable tblBranch;
    private javax.swing.JTable tblDept;
    private javax.swing.JTable tblRegion;
    private javax.swing.JTable tbl_requests;
    private javax.swing.JTable tbl_requests1;
    private javax.swing.JTextField txtBranchCode;
    private javax.swing.JTextField txtBranchName;
    private javax.swing.JTextField txtDeptCode;
    private javax.swing.JTextField txtDeptName;
    private javax.swing.JTextField txtRegion;
    private javax.swing.JTextField txtRegionName;
    private javax.swing.JTextField txt_branch_name1;
    private javax.swing.JTextField txt_completion_date1;
    private javax.swing.JTextField txt_completion_date2;
    private javax.swing.JTextField txt_dept_name1;
    private javax.swing.JTextField txt_developer_id1;
    private javax.swing.JTextField txt_developer_id2;
    private javax.swing.JTextField txt_projID1;
    private javax.swing.JTextField txt_projID2;
    private javax.swing.JTextField txt_proj_name1;
    private javax.swing.JTextField txt_proj_name2;
    private javax.swing.JTextField txt_recieved_date1;
    private javax.swing.JTextField txt_recieved_date2;
    private javax.swing.JTextArea txt_remarks1;
    private javax.swing.JTextArea txt_remarks2;
    private javax.swing.JTextField txt_starting_date1;
    private javax.swing.JTextField txt_starting_date2;
    // End of variables declaration//GEN-END:variables
}
