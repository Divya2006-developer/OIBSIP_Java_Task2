import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ATMInterface extends JFrame {
    private double balance = 5000.0; 
    private String userId = "user123";
    private String userPin = "1234";
    private ArrayList<String> transactionHistory = new ArrayList<>();
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextArea historyArea;
    
    public ATMInterface() {
        setTitle("ATM Interface");
        setSize(650, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createLoginScreen(), "LOGIN");
        mainPanel.add(createMainMenuScreen(), "MENU");
        mainPanel.add(createTransactionHistoryScreen(), "HISTORY");
        mainPanel.add(createWithdrawScreen(), "WITHDRAW");
        mainPanel.add(createDepositScreen(), "DEPOSIT");
        mainPanel.add(createTransferScreen(), "TRANSFER");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }
    
    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(44, 62, 80));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel titleLabel = new JLabel("ATM INTERFACE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(241, 196, 15));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel("Secure Banking System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        panel.add(subtitleLabel, gbc);
        
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JTextField userIdField = new JTextField();
        userIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JPasswordField pinField = new JPasswordField();
        pinField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(231, 76, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        loginPanel.add(userIdLabel);
        loginPanel.add(userIdField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(loginButton);
        loginPanel.add(clearButton);
        
        gbc.gridy = 2; gbc.insets = new Insets(30, 10, 10, 10);
        panel.add(loginPanel, gbc);
        
        JLabel infoLabel = new JLabel("Demo: user123 / PIN: 1234");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        gbc.gridy = 3; gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(infoLabel, gbc);
        
        loginButton.addActionListener(e -> {
            String id = userIdField.getText();
            String pin = new String(pinField.getPassword());
            if (id.equals(userId) && pin.equals(userPin)) {
                cardLayout.show(mainPanel, "MENU");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or PIN!", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        clearButton.addActionListener(e -> {
            userIdField.setText("");
            pinField.setText("");
        });
        
        return panel;
    }
    
    private JPanel createMainMenuScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel headerLabel = new JLabel("ATM MAIN MENU");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(new Color(241, 196, 15));
        headerPanel.add(headerLabel);
        
        JPanel centerPanel = new JPanel(new GridLayout(6, 1, 15, 15));
        centerPanel.setBackground(new Color(52, 73, 94));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));
        
        String[] menuItems = {
            "1. Transaction History",
            "2. Withdraw",
            "3. Deposit",
            "4. Transfer",
            "5. Quit"
        };
        
        for (int i = 0; i < menuItems.length; i++) {
            JButton button = new JButton(menuItems[i]);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(new Color(41, 128, 185));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            
            int index = i;
            button.addActionListener(e -> handleMenuSelection(index));
            centerPanel.add(button);
        }
        
        JLabel balanceLabel = new JLabel("Current Balance: $" + String.format("%.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(46, 204, 113));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(balanceLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void handleMenuSelection(int index) {
        switch (index) {
            case 0: 
                updateHistoryDisplay();
                cardLayout.show(mainPanel, "HISTORY");
                break;
            case 1: 
                cardLayout.show(mainPanel, "WITHDRAW");
                break;
            case 2: 
                cardLayout.show(mainPanel, "DEPOSIT");
                break;
            case 3: 
                cardLayout.show(mainPanel, "TRANSFER");
                break;
            case 4: 
                cardLayout.show(mainPanel, "LOGIN");
                break;
        }
    }
    
    private JPanel createTransactionHistoryScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel headerLabel = new JLabel("TRANSACTION HISTORY");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        historyArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createWithdrawScreen() {
        return createTransactionScreen("WITHDRAW", "Withdraw Amount:", 
            amount -> {
                if (amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                balance -= amount;
                addTransaction("WITHDRAW", amount);
                JOptionPane.showMessageDialog(this, 
                    "Withdrawal successful!\nNew Balance: $" + String.format("%.2f", balance));
                return true;
            });
    }
    
    private JPanel createDepositScreen() {
        return createTransactionScreen("DEPOSIT", "Deposit Amount:", 
            amount -> {
                balance += amount;
                addTransaction("DEPOSIT", amount);
                JOptionPane.showMessageDialog(this, 
                    "Deposit successful!\nNew Balance: $" + String.format("%.2f", balance));
                return true;
            });
    }
    
    private JPanel createTransferScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("TRANSFER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        JLabel accountLabel = new JLabel("Recipient Account:");
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(accountLabel, gbc);
        
        JTextField accountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(accountField, gbc);
        
        JLabel amountLabel = new JLabel("Transfer Amount:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(amountLabel, gbc);
        
        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        
        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(46, 204, 113));
        transferButton.setForeground(Color.WHITE);
        transferButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(transferButton, gbc);
        
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(backButton, gbc);
        
        transferButton.addActionListener(e -> {
            try {
                String account = accountField.getText();
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive!");
                    return;
                }
                if (amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                    return;
                }
                balance -= amount;
                addTransaction("TRANSFER to " + account, amount);
                JOptionPane.showMessageDialog(this, 
                    "Transfer successful!\nNew Balance: $" + String.format("%.2f", balance));
                accountField.setText("");
                amountField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });
        
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        return panel;
    }
    
    private JPanel createTransactionScreen(String title, String label, 
                                          java.util.function.Function<Double, Boolean> action) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        JLabel amountLabel = new JLabel(label);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(amountLabel, gbc);
        
        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(amountField, gbc);
        
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(confirmButton, gbc);
        
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(52, 152, 219));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(backButton, gbc);
        
        confirmButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive!");
                    return;
                }
                if (action.apply(amount)) {
                    amountField.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });
        
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        
        return panel;
    }
    
    private void addTransaction(String type, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String transaction = String.format("[%s] %s: $%.2f | Balance: $%.2f", 
            sdf.format(new Date()), type, amount, balance);
        transactionHistory.add(transaction);
    }
    
    private void updateHistoryDisplay() {
        historyArea.setText("");
        historyArea.append("========== TRANSACTION HISTORY ==========\n\n");
        if (transactionHistory.isEmpty()) {
            historyArea.append("No transactions yet.\n");
        } else {
            for (String transaction : transactionHistory) {
                historyArea.append(transaction + "\n\n");
            }
        }
        historyArea.append("\nCurrent Balance: $" + String.format("%.2f", balance) + "\n");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface());
    }
}
