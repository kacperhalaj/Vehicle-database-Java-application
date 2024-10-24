import javax.swing.*;

public class WelcomeForm extends JFrame {
    private JPanel PanelWelcome;
    private JProgressBar progressBar1;
    private JLabel lblProszeCzekac;
    private int width = 460, height = 340;

    public WelcomeForm(){
        super("Logowanie");
        this.setContentPane(this.PanelWelcome);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        progression();
    }

    private void progression(){
        int counter = 0;
        while (counter <= 100){
            lblProszeCzekac.setText("Proszę czekać ...");
            progressBar1.setValue(counter);
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            counter+=5;
        }
        dispose();

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }

}
