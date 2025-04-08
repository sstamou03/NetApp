package netapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginScreen extends JFrame implements ActionListener {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/MyDataBase_PLH303";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "160203";
    
    private JTextField emailField;
    private JTextField passField;
    private JButton loginButton;
    
    private Connection conn;

    public LoginScreen() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        emailField = new JTextField(20);
        
        passField = new JPasswordField();
        
        loginButton = new JButton("Login");

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(this);

        checkJDBCdriver();
        dbConnect();
    }
    
    public void showMessage(String msg) {
    	JOptionPane.showMessageDialog(null, msg);
    }
    
    private void checkJDBCdriver() {
    	//Check if a valid driver is available
    	try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver Found!");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found!");
		}
    	
    }
    
    private void dbConnect() {
    	//Connect to professional network database
    	try {
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			System.out.println("Connection established : "+conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private boolean authenticateUser(String email,String password) {
    	//Check the user and password
        try {
            String query = "SELECT email, \"thePassword\" FROM member WHERE email = '" + email + "' AND \"thePassword\" = '" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);                                  //create the query from table member for the authentication

            //an email and a password for test
            //00200d5e2d@example.com
            //f2ed72d6559d322

            if (res.next()) {
                System.out.println("User with email " + email + " exists in the DataBase");                 //valid
                return true;
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();                                                    //invalid
            System.out.println("Error: No user with this email " + email);
        }
        return false;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		//Perform necessary actions when the login button is pressed
		 String email = emailField.getText();
	        String password = new String(passField.getText());

	        System.out.println("Email: " + email);
	        System.out.println("Password: " + password);

	        if (authenticateUser(email, password)) {
	            showMessage("Login Successful!");
	            new MainScreen(conn,email).setVisible(true);
	        } else {
	            showMessage("Login Failed.Wrong Email or Password");
	        }
	        
	        emailField.setText("");
	        passField.setText("");

	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }


}
