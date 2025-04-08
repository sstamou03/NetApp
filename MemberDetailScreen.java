package netapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDetailScreen extends JFrame {
    private Connection conn;
    private String memberEmail;
    private JTextArea detailsArea;
    private JTextArea messagesArea;
    private JTextArea educationArea;
    private JTextArea experienceArea;

    public MemberDetailScreen(Connection conn, String memberEmail) {
        this.conn = conn;
        this.memberEmail = memberEmail;

        setTitle("Details for :"+memberEmail);
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        detailsArea = new JTextArea(5, 40);
        detailsArea.setEditable(false);
        messagesArea = new JTextArea(10, 40);
        messagesArea.setEditable(false);
        educationArea = new JTextArea(10, 40);
        educationArea.setEditable(false);
        experienceArea = new JTextArea(10, 40);
        experienceArea.setEditable(false);

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.setBorder(new EmptyBorder(20,20,20,20));
        
        panel.add(new JLabel("Personal Details"));
        panel.add(new JScrollPane(detailsArea));
        panel.add(new JLabel("Messages"));
        panel.add(new JScrollPane(messagesArea));
        panel.add(new JLabel("Education"));
        panel.add(new JScrollPane(educationArea));
        panel.add(new JLabel("Experience"));
        panel.add(new JScrollPane(experienceArea));

        add(panel);

        fetchMemberDetails();
        fetchMessages();
        fetchEducation();
        fetchExperience();
    }

    private void fetchMemberDetails() {
    	// Write code to show member's details to the appropriate JTextArea
    	String query = "SELECT \"firstName\",\"secondName\", \"dateOfBirth\",\"country\",gender"
    			+ " FROM member WHERE email = '" + memberEmail + "'" ;
		try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			if(res.next()) {
			
			String detailsText = "Name: " + res.getString(1) + " " +res.getString(2)+"\n"
								+ "Email: "+ memberEmail + "\n"
								+"Date Of Birth: "+ (res.getDate(3)).toString() + "\n"
								+ "Gender: "+ res.getString(5)+"\n"
								+ "Country: " + res.getString(4) ;
	    	
	    	detailsArea.setText(detailsText);}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }

    private void fetchMessages() {
    	// Write code to show messages from member to the appropriate JTextArea
    	String query="SELECT msg.\"theText\""
    			+ "FROM \"msg\""
    			+ "WHERE \"senderEmail\" = '" + memberEmail + "'";  //θελει και κατι ακομα για να συνδεεται με το userEmail
    	
    	try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			while(res.next()) {
				
				 String texts = res.getString("theText");
				 messagesArea.setText(texts);
	    	}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }

    private void fetchEducation() {
    	// Write code to show member's details about education the appropriate JTextArea
    	String query ="SELECT * FROM education WHERE education.email = '" + memberEmail + "'";
    	
    	try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			String eduText="";
			
			while(res.next()) {
			
			 eduText =eduText+ "Country: " +res.getString(2)+"\n"
								+ "School: "+res.getString(3) + "\n"
								+"Level: "+ res.getString(4) + "\n"
								+ "Category: "+ res.getInt(5)+"\n"
								+ "From: "+ (res.getDate(6)).toString()+"\n"
								+ "To: " + (res.getDate(7)).toString()+"\n\n\n" ;
	    	
			}
				educationArea.setText(eduText);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    private void fetchExperience() {
    	// Write code to show member's professional experience to the appropriate JTextArea
String query ="SELECT * FROM experience WHERE experience.email = '" + memberEmail + "'";
    	
    	try {
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(query);
			
			String expText="";
			
			while(res.next()) {
				
				Date toDate=res.getDate(7);
				String toDateString="";
				
				
				if(toDate==null) {
					toDateString= "-";
				} else  toDateString=(res.getDate(7)).toString();
			
			 expText =expText+ "Company: " +res.getString(2)+"\n"
								+ "Status: "+res.getString(3) + "\n"
								+"Title: "+ res.getString(4) + "\n"
								+ "Description: "+ res.getString(5)+"\n"
								+ "From: "+(res.getDate(6)).toString()+"\n"
								+ "To: " + toDateString+"\n\n\n" ;
	    	
			}
			experienceArea.setText(expText);
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}

