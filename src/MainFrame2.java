import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainFrame2 extends JFrame{


    private JButton refreshButton;
    private JButton exitButton;
    private JButton detailsButton;
    private JButton deleteButton;
    private JButton addButton;
    private JTable table;
    private JPanel Main;
    private JScrollPane scrollPane;
    private JButton exportxmlButton;
    private JButton importxmlButton;
    private JButton exportXLSButton;
    private JButton importXLSButton;
    private JTextField searchField;
    private JButton updateButton;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;


    public MainFrame2() {
        super("Baza danych pojazdów");
        this.setSize(800, 600);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.Main);


        tableModel = new DefaultTableModel();
        tableModel.addColumn("Numer");
        tableModel.addColumn("Marka");
        tableModel.addColumn("Model");
        tableModel.addColumn("Rok Produkcji");
        tableModel.addColumn("Typ");

        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new MainFrame2.CustomRenderer());
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(23);

        sorter = new TableRowSorter<>(tableModel);
     table.setRowSorter(sorter);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 24));

        scrollPane.setViewportView(table);





        JPanel buttonPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.setBackground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        buttonPanel.add(searchField, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(refreshButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(addButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        buttonPanel.add(deleteButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        buttonPanel.add(detailsButton, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        buttonPanel.add(updateButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(importxmlButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        buttonPanel.add(exportxmlButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        buttonPanel.add(importXLSButton, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        buttonPanel.add(exportXLSButton, gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        buttonPanel.add(exitButton, gbc);



        //JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());
        //panel.add(scrollPane, BorderLayout.CENTER);
        //panel.add(buttonPanel, BorderLayout.SOUTH);

        Main.setLayout(new BorderLayout());
        Main.add(scrollPane, BorderLayout.CENTER);
        Main.add(buttonPanel, BorderLayout.SOUTH);


        //add(panel);
        loadPojazdy();



        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPojazdy();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPojazdDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePojazd();
            }
        });

        detailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPojazdDetails();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        importxmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importPojazdy();
            }
        });

        exportxmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPojazdy();
            }
        });

        exportXLSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportPojazdyXLS();
            }
        });

        importXLSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importPojazdyXLS();
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePojazd();
            }
        });

    }

    private void updatePojazd() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String currentMarka = (String) tableModel.getValueAt(selectedRow, 1);
            String currentModel = (String) tableModel.getValueAt(selectedRow, 2);
            int currentRokProdukcji = (int) tableModel.getValueAt(selectedRow, 3);
            String typ = (String) tableModel.getValueAt(selectedRow, 4);

            JTextField markaField = new JTextField(currentMarka);
            JTextField modelField = new JTextField(currentModel);
            JTextField rokField = new JTextField(Integer.toString(currentRokProdukcji));
            JTextField specyficznyField = new JTextField();

            String specyficznyLabel = typ.equalsIgnoreCase("Osobowy") ? "Liczba Drzwi:" : "Pojemność Silnika:";

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Marka:"));
            panel.add(markaField);
            panel.add(new JLabel("Model:"));
            panel.add(modelField);
            panel.add(new JLabel("Rok Produkcji:"));
            panel.add(rokField);
            panel.add(new JLabel(specyficznyLabel));
            panel.add(specyficznyField);

            while (true) {
                int result = JOptionPane.showOptionDialog(null, panel, "Aktualizuj Pojazd",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                        new String[]{"OK", "Anuluj"}, null);

                if (result == JOptionPane.OK_OPTION) {
                    String newMarka = markaField.getText();
                    String newModel = modelField.getText();
                    String newRokString = rokField.getText();
                    String newSpecyficznyString = specyficznyField.getText();

                    if (newMarka.isEmpty() || newModel.isEmpty() || newRokString.isEmpty() || newSpecyficznyString.isEmpty()) {
                        showMessageDialog(this, "Wszystkie pola muszą być uzupełnione.", "Błąd", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    try {
                        int newRokProdukcji = Integer.parseInt(newRokString);
                        int newSpecyficzny = Integer.parseInt(newSpecyficznyString);

                        Pojazd pojazd = null;
                        if (typ.equalsIgnoreCase("Osobowy")) {
                            pojazd = new Osobowy(newMarka, newModel, newRokProdukcji, newSpecyficzny);
                        } else if (typ.equalsIgnoreCase("Motor")) {
                            pojazd = new Motor(newMarka, newModel, newRokProdukcji, newSpecyficzny);
                        }

                        if (pojazd != null) {
                            PojazdDAO.aktualizujPojazd(pojazd, id);
                        }

                        loadPojazdy();
                        break;
                    } catch (NumberFormatException e) {
                        showMessageDialog(this, "Nieprawidłowy format roku produkcji lub specyfikacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                } else {
                    break;
                }

            }
        } else {
            showMessageDialog(this, "Wybierz pojazd do aktualizacji.",
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadPojazdy() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM Pojazdy";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("idPojazdu");
                String marka = rs.getString("marka");
                String model = rs.getString("model");
                int rokProdukcji = rs.getInt("rokProdukcji");
                String typ = rs.getString("typ");
                tableModel.addRow(new Object[]{id, marka, model, rokProdukcji, typ});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAddPojazdDialog() {
        JTextField markaField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField rokField = new JTextField();
        JTextField typField = new JTextField();
        JTextField specyficznyField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Marka:"));
        panel.add(markaField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Rok Produkcji:"));
        panel.add(rokField);
        panel.add(new JLabel("Typ (Osobowy/Motor):"));
        panel.add(typField);
        panel.add(new JLabel("Specyficzny (Drzwi/Pojemność):"));
        panel.add(specyficznyField);

        while (true) {
            int result = JOptionPane.showOptionDialog(null, panel, "Dodaj Pojazd",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                    new String[]{"OK", "Anuluj"}, null);

            if (result == JOptionPane.OK_OPTION) {
                String marka = markaField.getText();
                String model = modelField.getText();
                String rokString = rokField.getText();
                String typ = typField.getText();
                String specyficznyString = specyficznyField.getText();

                if (marka.isEmpty() || model.isEmpty() || rokString.isEmpty() || typ.isEmpty() || specyficznyString.isEmpty()) {
                    showMessageDialog(this, "Wszystkie pola muszą być uzupełnione.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    int rokProdukcji = Integer.parseInt(rokString);
                    int specyficzny = Integer.parseInt(specyficznyString);

                    if (typ.equalsIgnoreCase("Osobowy")) {
                        //int liczbaDrzwi = Integer.parseInt(specyficznyString);
                        //if (liczbaDrzwi != 3 && liczbaDrzwi != 5) {
                        //    JOptionPane.showMessageDialog(this, "Samochód osobowy może mieć tylko 3 lub 5 drzwi.", "Błąd", JOptionPane.WARNING_MESSAGE);
                         //   continue;
                        //}
                        Osobowy osobowy = new Osobowy(marka, model, rokProdukcji, specyficzny);
                        OsobowyDAO.dodajOsobowy(osobowy);
                    } else if (typ.equalsIgnoreCase("Motor")) {
                        Motor motor = new Motor(marka, model, rokProdukcji, specyficzny);
                        MotorDAO.dodajMotor(motor);
                    }

                    loadPojazdy();
                    break;
                } catch (NumberFormatException e) {
                    showMessageDialog(this, "Nieprawidłowy format roku produkcji lub specyfikacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
            } else {
                break;
            }
        }
    }

    private void deletePojazd() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String typ = (String) tableModel.getValueAt(selectedRow, 4);
            if (typ.equalsIgnoreCase("Osobowy")) {
                OsobowyDAO.usunOsobowy(id);
            } else if (typ.equalsIgnoreCase("Motor")) {
                MotorDAO.usunMotor(id);
            }
            loadPojazdy();
        } else {
            showMessageDialog(this, "Wybierz pojazd do usunięcia.",
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showPojazdDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String typ = (String) tableModel.getValueAt(selectedRow, 4);

            String details = "";
            String sql = "";

            if (typ.equalsIgnoreCase("Osobowy")) {
                sql = "SELECT liczbaDrzwi FROM Osobowe WHERE idPojazdu = ?";
            } else if (typ.equalsIgnoreCase("Motor")) {
                sql = "SELECT pojemnoscSilnika FROM Motory WHERE idPojazdu = ?";
            }

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    if (typ.equalsIgnoreCase("Osobowy")) {
                        int liczbaDrzwi = rs.getInt("liczbaDrzwi");
                        details = "Liczba drzwi: " + liczbaDrzwi;
                    } else if (typ.equalsIgnoreCase("Motor")) {
                        int pojemnoscSilnika = rs.getInt("pojemnoscSilnika");
                        details = "Pojemność silnika: " + pojemnoscSilnika + " cm3";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            showMessageDialog(this, details, "Szczegóły pojazdu",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            showMessageDialog(this, "Wybierz pojazd do wyświetlenia szczegółów.",
                    "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
/*
    private class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
*/



    class CustomRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.decode("#8FBC8F"));
                } else {
                    c.setBackground(new Color(240, 240, 240));
                }
            }
            return c;
        }
    }


    private void importPojazdy() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            XMLImporter.importFromXML(selectedFile);
            loadPojazdy();
        }
    }

    private void exportPojazdy() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            XMLExporter.exportToXML(selectedFile);
        }
    }

    private void importPojazdyXLS() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            XLSImporter.importFromXLS(selectedFile);
            loadPojazdy();
        }
    }

    private void exportPojazdyXLS() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            XLSExporter.exportToXLS(selectedFile);
        }
    }

/*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame2 frame2 = new MainFrame2();
            frame2.setVisible(true);
        });
    }


 */


}
