🏦 Simple Swing Bank System (Bank_GUI.java)
A basic, single-user Bank Management System implemented in Java using Swing for the Graphical User Interface (GUI). This application allows a user to perform fundamental banking operations in a desktop environment.

✨ Features
Open Account: Create a new bank account with user details (Name, DOB, Address, etc.) and generate a unique 10-digit account number.

Deposit: Add funds to the account by entering the account number and amount.

Withdraw: Withdraw funds from the account, requiring both the account number and a password for security. Enforces a minimum balance requirement ($500.00).

Bank Statement: Display current account details, including the holder's name, account number, contact information, and the current balance.

Fixed Deposit (FD) Calculation: Calculate the interest and final maturity value for a Fixed Deposit based on the investment amount and duration. The principal amount is deducted from the current balance upon booking the FD.

🛠️ Technology Stack
Language: Java

GUI Toolkit: Java Swing (javax.swing.*, java.awt.*)

Formatting: DecimalFormat for currency, SimpleDateFormat and java.util.Calendar for date operations.

Concurrency: Uses SwingUtilities.invokeLater() to ensure thread safety for GUI initialization.

🚀 Getting Started
Prerequisites
Java Development Kit (JDK) 8 or newer installed on your machine.

Compilation and Execution
Save the Code: Save the provided code snippet as Bank_GUI.java.

Compile: Open a terminal or command prompt and navigate to the directory where you saved the file. Execute the following command:

Bash

javac Bank_GUI.java
Run: Execute the compiled class file:

Bash

java Bank_GUI
A main application window titled "Simple Bank System" should appear, presenting the main menu buttons.

💡 How to Use
Open Account: Click "Open Account" first. Fill in all required details, including setting a password. The system will then generate and display your Account Number. This is essential for all subsequent operations.

Deposit/Withdraw/Fixed Deposit: Use your generated Account Number and the set Password (for secure actions like Withdraw and FD) to perform transactions.

Check Status: Click "Bank Statement" to view your current details and balance.

Exit: Click "Exit" to close the application.

🔑 Key Implementation Details
Single-User Focus: The system is designed to manage only one user's account at a time within the running application instance. All data (name, currentBalance, ACTNO, etc.) is stored in memory as class fields and is reset upon application restart.

Data Types: Monetary values (currentBalance, fdPrincipal, etc.) are correctly stored using the double data type for precision with decimal places.

GUI Design: Uses a simple GridLayout for the main menu and transaction windows, utilizing the Nimbus Look and Feel for a modern appearance.

Fixed Deposit (FD) Calculation: The FD interest is calculated using the Simple Interest formula based on a fixed, hard-coded rate schedule:

1 Year: 4.0%

2 Years: 5.0%

3 Years: 6.0%

4 Years: 7.0%

5+ Years: 8.0%

Action Handling: The main buttons implement the ActionListener interface, while the action listeners for secondary windows (like enterButton in openAccountGUI) use Lambda expressions for cleaner code.
