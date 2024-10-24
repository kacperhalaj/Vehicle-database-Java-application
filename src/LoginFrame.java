import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;
    private JPanel JPanel1;

/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            }
        });
    }
*/



    public LoginFrame() {
        super("Logowanie");
        this.setSize(460, 340);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.JPanel1);
        this.setLocationRelativeTo(null);
        loginField.setPreferredSize(new Dimension(200,25));
        passwordField.setPreferredSize(new Dimension(200,25));
        //BackgroundPanel backgroundPanel = new BackgroundPanel("1.jpg");



        loginField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


    }

    private void login() {
        String login = loginField.getText();
        String password = new String(passwordField.getPassword());
        try {
            if (authenticate(login, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Zalogowano pomyślnie!",
                        "Zautoryzowano",JOptionPane.INFORMATION_MESSAGE);
                MainFrame2 mainFrame2 = new MainFrame2();
                mainFrame2.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this, "Błędny login lub hasło.",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(LoginFrame.this, "Błąd podczas logowania.");
        }
    }

    private boolean authenticate(String login, String password) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM Users WHERE login = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        }

    }





}
