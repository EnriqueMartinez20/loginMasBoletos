import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;


/*El programa es un Login sencillo, que almacena usuarios y contraseñas de manera local mientras el programa esta en ejecución,
bajo esta logica, primero deberas registrar un usuario con su respectiva contraseña y validarla antes de poder iniciar sesión
(El codigo es sensible a Mayusculas y minusculas)*/

public class LoginRegisterApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}

class UserDatabase {
    private static final HashMap<String, String> users = new HashMap<>();

    public static boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // Usuario ya existe
        }
        users.put(username, password);
        return true;
    }

    public static boolean validateUser(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}

class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Inicio de Sesión");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#001F3E"));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel logoLabel = new JLabel("+BOLETOS");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(Color.decode("#A3BD31"));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(logoLabel, gbc);

        JLabel titleLabel = new JLabel("Iniciar Sesión");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        JTextField userText = new JTextField(20);
        JPasswordField passText = new JPasswordField(20);
        userText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        passText.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        panel.add(userText, gbc);
        gbc.gridy = 3;
        panel.add(passText, gbc);

        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(Color.decode("#A3BD31"));
        loginButton.setForeground(Color.BLACK);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Registrarme");
        registerButton.setForeground(Color.WHITE);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            loginButton.setText("Cargando...");
            loginButton.setEnabled(false);
            Timer timer = new Timer(100, event -> {
                loginButton.setText("Ingresar");
                loginButton.setEnabled(true);
                if (UserDatabase.validateUser(username, password)) {
                    JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
                }
            });
            timer.setRepeats(false);
            timer.start();
        });

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterFrame();
        });

        setVisible(true);
    }
}

class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Registro");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#001F3E"));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        JLabel titleLabel = new JLabel("Registrarse");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(titleLabel, gbc);

        JTextField userText = new JTextField(20);
        JPasswordField passText = new JPasswordField(20);
        JPasswordField confirmPassText = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        panel.add(userText, gbc);
        gbc.gridy = 3;
        panel.add(passText, gbc);
        gbc.gridy = 4;
        panel.add(confirmPassText, gbc);

        JButton registerButton = new JButton("Registrarme");
        registerButton.setBackground(Color.decode("#A3BD31"));
        registerButton.setForeground(Color.BLACK);
        gbc.gridy = 5;
        panel.add(registerButton, gbc);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setForeground(Color.WHITE);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        gbc.gridy = 6;
        panel.add(loginButton, gbc);

        add(panel, BorderLayout.CENTER);

        registerButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());
            String confirmPassword = new String(confirmPassText.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden");
                return;
            }
            if (UserDatabase.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registro exitoso");
                dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(this, "El usuario ya existe");
            }
        });

        loginButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}

