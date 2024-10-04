import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PanelEncuesta {
    private JPanel Formulario;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox hombreCheckBox;
    private JCheckBox mujerCheckBox;
    private JCheckBox otroCheckBox;
    private JComboBox<String> comboBox1;
    private JButton guardarButton;
    private JButton cancelarButton;
    private ButtonGroup generoGroup;

    public PanelEncuesta() {
        generoGroup = new ButtonGroup();
        generoGroup.add(hombreCheckBox);
        generoGroup.add(mujerCheckBox);
        generoGroup.add(otroCheckBox);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String NOMBRE = textField1.getText();
                String EDAD = textField2.getText();
                String GENERO = "";
                if (hombreCheckBox.isSelected()) GENERO = "Hombre";
                if (mujerCheckBox.isSelected()) GENERO = "Mujer";
                if (otroCheckBox.isSelected()) GENERO = "Otro";
                String UNIVERSIDAD = (String) comboBox1.getSelectedItem();

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "INSERT INTO encuesta (NOMBRE, EDAD, GENERO, UNIVERSIDAD) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(query)) {
                        statement.setString(1, NOMBRE);
                        statement.setString(2, EDAD);
                        statement.setString(3, GENERO);
                        statement.setString(4, UNIVERSIDAD);
                        statement.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(Formulario,
                            "Datos guardados correctamente",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(Formulario,
                            "Error al guardar los datos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                JOptionPane.showMessageDialog(Formulario,
                                        "Nombre: " + NOMBRE + "\n" +
                                "Apellido: " + EDAD + "\n" +
                                "Género: " + GENERO + "\n" +
                                "Opción seleccionada: " + UNIVERSIDAD,
                        "Resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Operación cancelada");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PanelEncuesta");
        frame.setContentPane(new PanelEncuesta().Formulario);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
