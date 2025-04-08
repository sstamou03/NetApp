package netapp;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainScreen extends JFrame implements ActionListener{
    private Connection conn;

    private String userEmail;
    
    private JPanel personalDetailsPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField countryField;
    private JButton updateButton;
    
    private JList<Member> networkList;
    private DefaultListModel<Member> networkListModel;
    
    

    public MainScreen(Connection conn, String userEmail) {
        this.conn = conn;
        this.userEmail = userEmail;

        setTitle("Home");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        personalDetailsPanel = new JPanel(new GridLayout(5, 2,10,10));
        personalDetailsPanel.setMaximumSize(new Dimension(600,200));
        personalDetailsPanel.setBorder(new EmptyBorder(10,10,10,10));
        personalDetailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        personalDetailsPanel.add(new JPanel(new FlowLayout(FlowLayout.TRAILING)).add(new JLabel("First Name:")));
        firstNameField = new JTextField();
        personalDetailsPanel.add(firstNameField);

        personalDetailsPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        personalDetailsPanel.add(lastNameField);

        personalDetailsPanel.add(new JLabel("Date of Birth:"));
        dobField = new JTextField();
        personalDetailsPanel.add(dobField);
        
        personalDetailsPanel.add(new JLabel("Country:"));
        countryField = new JTextField();
        personalDetailsPanel.add(countryField);

        personalDetailsPanel.add(new JLabel());
        updateButton = new JButton("Update");
        updateButton.addActionListener(this);
        personalDetailsPanel.add(updateButton);

        networkListModel = new DefaultListModel<>();
        networkList = new JList<>(networkListModel);
        networkList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        networkList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    new MemberDetailScreen(conn,networkList.getSelectedValue().email).setVisible(true);
                }
            }
        });
        
        
        add(personalDetailsPanel);
        JLabel nusers = new JLabel("My Network");
        nusers.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(nusers);
        JScrollPane jpane = new JScrollPane(networkList);
        jpane.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpane.setBorder(new EmptyBorder(10,10,10,10));
        add(jpane);

        fetchPersonalDetails();
        fetchNetwork();
    }
    
    public void showMessage(String msg) {
    	JOptionPane.showMessageDialog(null, msg);
    }
    
    private void fetchPersonalDetails() {
    	// Write code to show the appropriate values in the JTextFields
    	String query = "SELECT \"firstName\",\"secondName\", \"dateOfBirth\",\"country\""
    			+ " FROM member WHERE email = '" + userEmail + "'" ;
    	
    	
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			if(res.next()) {
			firstNameField.setText(res.getString(1));
			lastNameField.setText(res.getString(2));
			dobField.setText((res.getDate(3)).toString());
			countryField.setText(res.getString(4));
			}
			
			res.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
      
    	
    }
    
    private void updatePersonalDetails() {
    	// Write code to update the database with new values in the JTextFields
    	
    	 String query = "UPDATE member SET \"firstName\" ='"+firstNameField.getText()+"', "
    	 		+ "\"secondName\" ='"+lastNameField.getText()+"', "
    	 				+ "\"dateOfBirth\" ='"+(dobField.getText())+"',"
    	 		+ " country ='"+countryField.getText()+"' WHERE email ='"+userEmail+"'";
    	 
    	    
			try {
				Statement stmt = conn.createStatement();
				stmt.executeUpdate(query);
				
		       System.out.println("Update Successful!"); 
				
				
				stmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    }

    private void fetchNetwork() {
    	// Write code to fill the list with connected members
    	String query ="Select m.\"firstName\", m.\"secondName\",c.\"email\""
    			+ "From \"connects\" c JOIN \"member\" m on (c.\"email\"=m.\"email\")"
    			+ "Where c.\"connectedWithEmail\" = '"+userEmail+"' ";
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			while(res.next()) {
				
				Member m =new Member(res.getString(1),res.getString(2),res.getString(3));
				networkListModel.addElement(m);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//Perform necessary actions when the Update button is pressed
		
		this.updatePersonalDetails();
		
	}
}
