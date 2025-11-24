import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.text.DecimalFormat; // Import for formatting money

public class Bank_GUI implements ActionListener {

    // --- GUI Components ---
    JButton Open_Account, Deposit, Withdraw, Statement, Fixed_Deposit, exit;
    JTextField User_Name, DOB, Adhaar, Phn, Addrs;
    JFrame J_Main;

    // --- Bank Data Fields (Corrected Data Types) ---
    // Use String for temporary password storage
    String name = null, password = "", address = null, DateOfBirth = null, adhar = null;
    String PhnNo = null;

    // Use double for monetary values to handle decimal places
    double currentBalance = 0.0; // Changed 'amt' (long) to 'currentBalance' (double)
    
    // For Fixed Deposit (FD) calculations
    double fdPrincipal = 0.0;
    double fdFinalValue = 0.0;
    double fdInterest = 0.0;

    // Account Number - Keep as long, but be mindful of string conversion
    long ACTNO = 0; // Initialize to 0, will be set when opening account
    
    // --- Formatting and Fonts ---
    Font f4 = new Font(Font.DIALOG, Font.BOLD, 25);
    Font f5 = new Font("Comic Sans MS", Font.PLAIN, 15);
    // Decimal formatter for currency
    private final DecimalFormat currencyFormat = new DecimalFormat("0.00");

    // Constructor
    public Bank_GUI() {
        // Set Look and Feel (Good practice)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Fallback L&F if Nimbus fails
        }

        // Defining and styling buttons
        exit = new JButton("Exit");
        Open_Account = new JButton("Open Account");
        Deposit = new JButton("Deposit");
        Withdraw = new JButton("Withdraw");
        Statement = new JButton("Bank Statement");
        Fixed_Deposit = new JButton("Fixed Deposit");

        // Setting fonts
        Open_Account.setFont(f4);
        Deposit.setFont(f4);
        Withdraw.setFont(f4);
        Statement.setFont(f4);
        Fixed_Deposit.setFont(f4);
        exit.setFont(f4);

        // Main JFrame setup
        J_Main = new JFrame("Simple Bank System");
        J_Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        J_Main.setSize(800, 600);
        J_Main.setLocationRelativeTo(null);
        J_Main.setResizable(false);

        // Main panel with buttons
        JPanel JP = new JPanel(new GridLayout(3, 2, 20, 20)); // Adjusted grid layout for better spacing
        JP.add(Open_Account);
        JP.add(Deposit);
        JP.add(Withdraw);
        JP.add(Statement);
        JP.add(Fixed_Deposit);
        JP.add(exit);
        J_Main.add(JP);

        // Add Action Listeners
        Open_Account.addActionListener(this);
        Deposit.addActionListener(this);
        Withdraw.addActionListener(this);
        Statement.addActionListener(this);
        Fixed_Deposit.addActionListener(this);
        exit.addActionListener(this);

        J_Main.setVisible(true);
    }

    public static void main(String[] args) {
        // Launch the GUI on the Event Dispatch Thread (Swing best practice)
        SwingUtilities.invokeLater(Bank_GUI::new);
    }

    // --- Centralized Action Listener ---
    public void actionPerformed(ActionEvent e) {
        // NOTE: Removed duplicate Look and Feel setting here. It only needs to be in the constructor.

        if (e.getSource() == Open_Account) {
            openAccountGUI();
        } else if (e.getSource() == Deposit) {
            depositGUI();
        } else if (e.getSource() == Withdraw) {
            withdrawGUI();
        } else if (e.getSource() == Statement) {
            statementGUI();
        } else if (e.getSource() == Fixed_Deposit) {
            fixedDepositGUI();
        } else if (e.getSource() == exit) {
            J_Main.dispose();
            System.exit(0);
        }
    }
    
    // --------------------------------------------------------------------------------
    // --- Dedicated GUI Methods for better organization ---
    // --------------------------------------------------------------------------------

    private void openAccountGUI() {
        if (ACTNO != 0) {
            JOptionPane.showMessageDialog(J_Main, "Account already opened! Account No: " + ACTNO, "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame J_Account = new JFrame("Open Account");
        JPanel JA = new JPanel(new GridLayout(7, 2, 10, 20)); // Adjusted layout
        J_Account.setSize(600, 550);
        J_Account.setLocationRelativeTo(null);
        JA.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel l1 = new JLabel("Enter your name:");
        JLabel l2 = new JLabel("Enter your Date Of Birth (DD/MM/YYYY):");
        JLabel l3 = new JLabel("Enter your Aadhar number:");
        JLabel l4 = new JLabel("Enter your phone number:");
        JLabel l5 = new JLabel("Enter your address:");
        JLabel l6 = new JLabel("Set a password:");

        // Use class fields for text fields
        User_Name = new JTextField(20);
        DOB = new JTextField(20);
        Adhaar = new JTextField(20);
        Phn = new JTextField(20);
        Addrs = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        
        // Add components to the panel
        JA.add(l1); JA.add(User_Name);
        JA.add(l2); JA.add(DOB);
        JA.add(l3); JA.add(Adhaar);
        JA.add(l4); JA.add(Phn);
        JA.add(l5); JA.add(Addrs);
        JA.add(l6); JA.add(passwordField);

        JButton enterButton = new JButton("CREATE ACCOUNT");
        enterButton.setFont(f5);
        
        JA.add(new JLabel("")); // Filler for layout
        JA.add(enterButton);

        J_Account.add(JA);
        J_Account.setVisible(true);

        // Use Lambda/anonymous class for button action
        enterButton.addActionListener(e -> {
            // Input Validation
            if (User_Name.getText().isEmpty() || DOB.getText().isEmpty() || Adhaar.getText().isEmpty() ||
                Phn.getText().isEmpty() || Addrs.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(J_Account, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate a 10-digit account number (9 billion range)
            ACTNO = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;

            // Store details
            password = new String(passwordField.getPassword()); // Safely convert password
            name = User_Name.getText();
            DateOfBirth = DOB.getText();
            adhar = Adhaar.getText();
            PhnNo = Phn.getText();
            address = Addrs.getText();
            currentBalance = 0.0; // Start with zero balance

            // Display success message
            JOptionPane.showMessageDialog(J_Account, 
                "Account Created Successfully!\nYour Account Number: " + ACTNO, 
                "Success", JOptionPane.INFORMATION_MESSAGE);

            J_Account.dispose();
        });
    }
    
    private void depositGUI() {
        if (ACTNO == 0) {
            JOptionPane.showMessageDialog(J_Main, "Please open an account first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame J_deposit = new JFrame("Deposit");
        J_deposit.setSize(600, 350);
        J_deposit.setLocationRelativeTo(null);
        J_deposit.setResizable(false);
        
        JPanel JW = new JPanel(new GridLayout(3, 2, 10, 20)); // Adjusted layout
        JW.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel l_acct = new JLabel("Enter Account Number:");
        JTextField tf_acct = new JTextField(10);
        JLabel l_amt = new JLabel("Enter Amount to Deposit:");
        JTextField tf_amt = new JTextField(10);
        JButton enterButton = new JButton("DEPOSIT");
        
        JW.add(l_acct); JW.add(tf_acct);
        JW.add(l_amt); JW.add(tf_amt);
        JW.add(new JLabel("")); JW.add(enterButton);

        J_deposit.add(JW, BorderLayout.CENTER);
        J_deposit.setVisible(true);
        
        enterButton.addActionListener(e -> {
            if (tf_amt.getText().isEmpty() || tf_acct.getText().isEmpty()) {
                JOptionPane.showMessageDialog(J_deposit, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                long inputAcctNo = Long.parseLong(tf_acct.getText().trim());
                double depositAmount = Double.parseDouble(tf_amt.getText().trim());

                if (inputAcctNo != ACTNO) {
                    JOptionPane.showMessageDialog(J_deposit, "Invalid Account Number.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (depositAmount <= 0) {
                    JOptionPane.showMessageDialog(J_deposit, "Deposit amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    currentBalance += depositAmount;
                    JOptionPane.showMessageDialog(J_deposit, "Amount " + currencyFormat.format(depositAmount) + " Deposited Successfully.\nNew Balance: " + currencyFormat.format(currentBalance), "Success", JOptionPane.INFORMATION_MESSAGE);
                    J_deposit.dispose();
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(J_deposit, "Please enter valid numeric values for Account Number and Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void withdrawGUI() {
        if (ACTNO == 0) {
            JOptionPane.showMessageDialog(J_Main, "Please open an account first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame J_withdraw = new JFrame("Withdraw");
        J_withdraw.setSize(600, 400);
        J_withdraw.setLocationRelativeTo(null);
        J_withdraw.setResizable(false);
        
        JPanel JWTH = new JPanel(new GridLayout(4, 2, 10, 20));
        JWTH.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel l_acc = new JLabel("Enter Account Number:");
        JTextField tf_acc = new JTextField(10);
        JLabel l_pwd = new JLabel("Enter Password:");
        JPasswordField tf_pwd = new JPasswordField(10);
        JLabel l_amt = new JLabel("Enter Amount to Withdraw:");
        JTextField tf_amt = new JTextField(10);
        JButton enterButton = new JButton("WITHDRAW");

        JWTH.add(l_acc); JWTH.add(tf_acc);
        JWTH.add(l_pwd); JWTH.add(tf_pwd);
        JWTH.add(l_amt); JWTH.add(tf_amt);
        JWTH.add(new JLabel("")); JWTH.add(enterButton);

        J_withdraw.add(JWTH, BorderLayout.CENTER);
        J_withdraw.setVisible(true);

        enterButton.addActionListener(e -> {
            if (tf_amt.getText().isEmpty() || tf_acc.getText().isEmpty() || tf_pwd.getPassword().length == 0) {
                JOptionPane.showMessageDialog(J_withdraw, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                long inputAcctNo = Long.parseLong(tf_acc.getText().trim());
                String inputPassword = new String(tf_pwd.getPassword());
                double withdrawAmount = Double.parseDouble(tf_amt.getText().trim());
                
                if (inputAcctNo != ACTNO || !inputPassword.equals(password)) {
                    JOptionPane.showMessageDialog(J_withdraw, "Invalid Account Number or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Minimum balance check (e.g., maintain a minimum of 500.0)
                if (withdrawAmount <= 0) {
                    JOptionPane.showMessageDialog(J_withdraw, "Withdrawal amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (currentBalance - withdrawAmount < 500.0) {
                    JOptionPane.showMessageDialog(J_withdraw, "Low Balance. Cannot withdraw, minimum balance of " + currencyFormat.format(500.0) + " required.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    currentBalance -= withdrawAmount;
                    JOptionPane.showMessageDialog(J_withdraw, "Amount " + currencyFormat.format(withdrawAmount) + " Withdrawn Successfully.\nNew Balance: " + currencyFormat.format(currentBalance), "Success", JOptionPane.INFORMATION_MESSAGE);
                    J_withdraw.dispose();
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(J_withdraw, "Please enter valid numeric values for Account Number and Amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private void statementGUI() {
        if (ACTNO == 0 || name == null) {
            JOptionPane.showMessageDialog(J_Main, "Please open an account first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame J_statement = new JFrame("Bank Statement");
        J_statement.setSize(600, 500);
        J_statement.setLocationRelativeTo(null);
        J_statement.setResizable(false);

        JPanel head = new JPanel();
        JLabel heading = new JLabel("BANK STATEMENT");
        heading.setFont(f4);
        head.add(heading);
        J_statement.add(head, BorderLayout.NORTH);

        JPanel secnd = new JPanel(new GridLayout(5, 2, 10, 30)); // Adjusted layout
        secnd.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Display current information
        secnd.add(new JLabel("NAME:"));
        secnd.add(new JTextField(name));
        secnd.add(new JLabel("ACCOUNT NUMBER:"));
        secnd.add(new JTextField(String.valueOf(ACTNO)));
        secnd.add(new JLabel("ADDRESS:"));
        secnd.add(new JTextField(address));
        secnd.add(new JLabel("PHONE NO:"));
        secnd.add(new JTextField(PhnNo));
        secnd.add(new JLabel("BALANCE AMOUNT:"));
        // Use the DecimalFormat for the balance
        secnd.add(new JTextField(currencyFormat.format(currentBalance))); 

        // Set all displayed text fields to non-editable
        for (Component comp : secnd.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setEditable(false);
                ((JTextField) comp).setFont(f5);
            }
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(f5);
            }
        }

        J_statement.add(secnd, BorderLayout.CENTER);

        // Close button
        JPanel pn = new JPanel();
        JButton closeButton = new JButton("CLOSE");
        closeButton.addActionListener(e -> J_statement.dispose());
        pn.add(closeButton);
        J_statement.add(pn, BorderLayout.SOUTH);

        J_statement.setVisible(true);
    }
    
    private void fixedDepositGUI() {
        if (ACTNO == 0) {
            JOptionPane.showMessageDialog(J_Main, "Please open an account first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame J_fd = new JFrame("Fixed Deposit");
        J_fd.setSize(600, 450);
        J_fd.setLocationRelativeTo(null);
        J_fd.setResizable(false);
        
        JPanel fix = new JPanel(new GridLayout(5, 2, 10, 20));
        fix.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel lAcc = new JLabel("Account Number:");
        JLabel lpwd = new JLabel("Password:");
        JLabel FdAMT = new JLabel("Amount:");
        JLabel lTime = new JLabel("Duration (in years):");
        
        JTextField TAcc = new JTextField(10);
        JPasswordField TPwd = new JPasswordField(10);
        JTextField Tamt = new JTextField(10);
        JTextField Ttime = new JTextField(10);
        JButton enterButton = new JButton("CALCULATE FD");
        
        fix.add(lAcc); fix.add(TAcc);
        fix.add(lpwd); fix.add(TPwd);
        fix.add(FdAMT); fix.add(Tamt);
        fix.add(lTime); fix.add(Ttime);
        fix.add(new JLabel("")); fix.add(enterButton);
        
        J_fd.add(fix, BorderLayout.CENTER);
        J_fd.setVisible(true);

        enterButton.addActionListener(e -> {
            if (TAcc.getText().isEmpty() || TPwd.getPassword().length == 0 || Tamt.getText().isEmpty() || Ttime.getText().isEmpty()) {
                JOptionPane.showMessageDialog(J_fd, "One or more fields are empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                long inputAcctNo = Long.parseLong(TAcc.getText().trim());
                String inputPassword = new String(TPwd.getPassword());
                fdPrincipal = Double.parseDouble(Tamt.getText().trim()); // Use double
                int timeYears = Integer.parseInt(Ttime.getText().trim());

                if (inputAcctNo != ACTNO || !inputPassword.equals(password)) {
                    JOptionPane.showMessageDialog(J_fd, "Invalid Account Number or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validation checks
                if (timeYears <= 0 || fdPrincipal <= 0) {
                    JOptionPane.showMessageDialog(J_fd, "Duration and Amount must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (currentBalance - fdPrincipal < 500.0) { // Check minimum balance requirement
                    JOptionPane.showMessageDialog(J_fd, "Insufficient Funds. Minimum balance of " + currencyFormat.format(500.0) + " required after FD investment.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 1. Determine Rate based on Duration (Time)
                double rate = 0.0;
                if (timeYears == 1) rate = 4.0;
                else if (timeYears == 2) rate = 5.0;
                else if (timeYears == 3) rate = 6.0;
                else if (timeYears == 4) rate = 7.0;
                else if (timeYears >= 5) rate = 8.0;

                // 2. Calculate Simple Interest (use double for calculation)
                // Formula: Interest = (Principal * Rate * Time) / 100
                fdInterest = (fdPrincipal * rate * timeYears) / 100.0;
                fdFinalValue = fdPrincipal + fdInterest;
                
                // 3. Calculate Maturity Date
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, timeYears);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                String m_date = dateFormat.format(calendar.getTime());
                
                // 4. Deduct principal from current balance
                currentBalance -= fdPrincipal;
                
                // 5. Display Result
                displayFDResult(m_date, timeYears, rate);
                J_fd.dispose();

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(J_fd, "Please enter valid numeric values for Account Number, Amount, and Duration.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void displayFDResult(String m_date, int timeYears, double rate) {
        JFrame disp = new JFrame("Fixed Deposit Confirmation");
        disp.setSize(600, 500);
        disp.setLocationRelativeTo(null);
        disp.setResizable(false);
        disp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Make it close cleanly

        JPanel head = new JPanel();
        JLabel heading = new JLabel("FIXED DEPOSIT SUCCESS");
        heading.setFont(f4);
        head.add(heading);
        disp.add(head, BorderLayout.NORTH);

        JPanel findisp = new JPanel(new GridLayout(5, 2, 10, 20)); // Adjusted layout
        findisp.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        findisp.add(new JLabel("Principal Amount:"));
        findisp.add(new JTextField(currencyFormat.format(fdPrincipal)));
        findisp.add(new JLabel("Duration (Years):"));
        findisp.add(new JTextField(String.valueOf(timeYears)));
        findisp.add(new JLabel("Annual Interest Rate:"));
        findisp.add(new JTextField(rate + "%"));
        findisp.add(new JLabel("Total Interest Earned:"));
        findisp.add(new JTextField(currencyFormat.format(fdInterest)));
        findisp.add(new JLabel("MATURITY AMOUNT:"));
        findisp.add(new JTextField(currencyFormat.format(fdFinalValue)));
        
        // Set all displayed text fields to non-editable
        for (Component comp : findisp.getComponents()) {
            if (comp instanceof JTextField) {
                ((JTextField) comp).setEditable(false);
                ((JTextField) comp).setFont(f5);
            }
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(f5);
            }
        }
        
        disp.add(findisp, BorderLayout.CENTER);
        
        JPanel dn = new JPanel();
        JButton closeButton = new JButton("CLOSE");
        closeButton.addActionListener(e -> {
            disp.dispose();
            // Show updated balance in main frame if possible, or just inform user
            JOptionPane.showMessageDialog(J_Main, "FD booked. New Current Account Balance: " + currencyFormat.format(currentBalance), "Balance Update", JOptionPane.INFORMATION_MESSAGE);
        });
        dn.add(closeButton);
        disp.add(dn, BorderLayout.SOUTH);
        
        disp.setVisible(true);
    }
}
