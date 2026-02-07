package b_2;

import javax.swing.*;


import java.awt.*;
import java.util.LinkedList;

public class GUI extends JFrame {
    private LinkedList<Account> globalAccounts;
    private Transaction transferObject;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    
    private JPanel homePanel, depositPanel, withdrawPanel, transferPanel, showAllPanel;

    
    private JTextField accDeposit, depositInput;
    private JTextField accWithdraw, withdrawInput;
    private JTextField acc1Transfer, acc2Transfer, transferAmount;

    
    private JLabel depositResult, withdrawResult, transferResult;
    private JTextArea showAllText;

    public GUI(LinkedList<Account> accounts) {
        super("Banking System");
        getContentPane().setForeground(Color.BLACK);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        globalAccounts = accounts;
        transferObject = new Transaction();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(0, 0, 0));

        
        createHomePanel();
        createDepositPanel();
        createWithdrawPanel();
        createTransferPanel();
        createShowAllPanel();

        mainPanel.add(homePanel, "Home");
        mainPanel.add(depositPanel, "Deposit");
        mainPanel.add(withdrawPanel, "Withdraw");
        mainPanel.add(transferPanel, "Transfer");
        mainPanel.add(showAllPanel, "ShowAll");

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void createHomePanel() {
        homePanel = new JPanel(null);

        // Background image
        ImageIcon bgIcon = new ImageIcon("bank.png"); // replace with your image file
        Image scaledBg = bgIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledBg));
        background.setBounds(0, 0, 800, 600);

        // Add background first
        homePanel.add(background);

        // Dashboard Overview (placed on top of background)
        JLabel totalAccountsLabel = new JLabel("Total Accounts: " + globalAccounts.size());
        totalAccountsLabel.setBounds(50, 50, 300, 25);
        background.add(totalAccountsLabel);

        int totalBalance = globalAccounts.stream().mapToInt(Account::getBalance).sum();
        JLabel totalBalanceLabel = new JLabel("Total Balance: " + totalBalance);
        totalBalanceLabel.setBounds(50, 80, 300, 25);
        background.add(totalBalanceLabel);

        int highestBalance = globalAccounts.stream().mapToInt(Account::getBalance).max().orElse(0);
        JLabel highestBalanceLabel = new JLabel("Highest Balance: " + highestBalance);
        highestBalanceLabel.setBounds(50, 110, 300, 25);
        background.add(highestBalanceLabel);

        double avgBalance = globalAccounts.isEmpty() ? 0 :
                globalAccounts.stream().mapToInt(Account::getBalance).average().orElse(0);
        JLabel avgBalanceLabel = new JLabel("Average Balance: " + avgBalance);
        avgBalanceLabel.setBounds(50, 140, 300, 25);
        background.add(avgBalanceLabel);

        // Navigation Buttons
        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(330, 200, 120, 30);
        depositBtn.addActionListener(e -> cardLayout.show(mainPanel, "Deposit"));
        background.add(depositBtn);

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(330, 250, 120, 30);
        withdrawBtn.addActionListener(e -> cardLayout.show(mainPanel, "Withdraw"));
        background.add(withdrawBtn);

        JButton transferBtn = new JButton("Transfer");
        transferBtn.setBounds(330, 300, 120, 30);
        transferBtn.addActionListener(e -> cardLayout.show(mainPanel, "Transfer"));
        background.add(transferBtn);

        JButton showAllBtn = new JButton("Show All");
        showAllBtn.setBounds(330, 350, 120, 30);
        showAllBtn.addActionListener(e -> {
            updateShowAllText();
            cardLayout.show(mainPanel, "ShowAll");
        });
        background.add(showAllBtn);

        // Theme Toggle
        JButton themeBtn = new JButton("Dark Mood");
        themeBtn.setBounds(330, 400, 120, 30);
        themeBtn.addActionListener(e -> {
            rootPaneCheckingEnabled = !rootPaneCheckingEnabled;
            Color bg = rootPaneCheckingEnabled ? Color.DARK_GRAY : Color.WHITE;
            Color fg = rootPaneCheckingEnabled ? Color.WHITE : Color.BLACK;

            for (Component comp : mainPanel.getComponents()) {
                comp.setBackground(bg);
                comp.setForeground(fg);
            }
            homePanel.setBackground(bg);
            homePanel.setForeground(fg);
            SwingUtilities.updateComponentTreeUI(this);
        });
        background.add(themeBtn);
    }


    private void createDepositPanel() {
        depositPanel = new JPanel(null);

        JLabel label = new JLabel("Deposit to Account:");
        label.setBounds(254, 188, 200, 25);
        depositPanel.add(label);

        accDeposit = new JTextField();
        accDeposit.setBounds(374, 188, 150, 25);
        depositPanel.add(accDeposit);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(254, 241, 200, 25);
        depositPanel.add(amountLabel);

        depositInput = new JTextField();
        depositInput.setBounds(374, 241, 150, 25);
        depositPanel.add(depositInput);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setBounds(333, 301, 100, 25);
        confirmBtn.addActionListener(e -> handleDeposit());
        depositPanel.add(confirmBtn);

        depositResult = new JLabel("");
        depositResult.setBounds(284, 121, 500, 25);
        depositPanel.add(depositResult);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10, 10, 80, 25);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        depositPanel.add(backBtn);
    }

    private void createWithdrawPanel() {
        withdrawPanel = new JPanel(null);

        JLabel label = new JLabel("Withdraw from Account:");
        label.setBounds(229, 188, 200, 25);
        withdrawPanel.add(label);

        accWithdraw = new JTextField();
        accWithdraw.setBounds(372, 188, 150, 25);
        withdrawPanel.add(accWithdraw);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(229, 241, 200, 25);
        withdrawPanel.add(amountLabel);

        withdrawInput = new JTextField();
        withdrawInput.setBounds(372, 241, 150, 25);
        withdrawPanel.add(withdrawInput);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setBounds(333, 301, 100, 25);
        confirmBtn.addActionListener(e -> handleWithdraw());
        withdrawPanel.add(confirmBtn);

        withdrawResult = new JLabel("");
        withdrawResult.setBounds(284, 121, 500, 25);
        withdrawPanel.add(withdrawResult);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10, 10, 80, 25);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        withdrawPanel.add(backBtn);
    }

    private void createTransferPanel() {
        transferPanel = new JPanel(null);

        JLabel label1 = new JLabel("From Account:");
        label1.setBounds(228, 139, 150, 25);
        transferPanel.add(label1);

        acc1Transfer = new JTextField();
        acc1Transfer.setBounds(322, 139, 150, 25);
        transferPanel.add(acc1Transfer);

        JLabel label2 = new JLabel("To Account:");
        label2.setBounds(228, 188, 150, 25);
        transferPanel.add(label2);

        acc2Transfer = new JTextField();
        acc2Transfer.setBounds(322, 188, 150, 25);
        transferPanel.add(acc2Transfer);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(228, 241, 150, 25);
        transferPanel.add(amountLabel);

        transferAmount = new JTextField();
        transferAmount.setBounds(322, 241, 150, 25);
        transferPanel.add(transferAmount);

        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.setBounds(304, 301, 100, 25);
        confirmBtn.addActionListener(e -> handleTransfer());
        transferPanel.add(confirmBtn);

        transferResult = new JLabel("");
        transferResult.setBounds(201, 92, 500, 25);
        transferPanel.add(transferResult);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10, 10, 80, 25);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        transferPanel.add(backBtn);
    }

    private void createShowAllPanel() {
        showAllPanel = new JPanel(null);
        showAllText = new JTextArea();
        showAllText.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(showAllText);
        scrollPane.setBounds(50, 50, 650, 400);
        showAllPanel.add(scrollPane);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(10, 10, 80, 25);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        showAllPanel.add(backBtn);
    }

    private void handleDeposit() {
        try {
            int accNum = Integer.parseInt(accDeposit.getText().trim());
            int amount = Integer.parseInt(depositInput.getText().trim());

            Account acc = findAccount(accNum);
            if (acc != null) {
                acc.deposit(amount);
                depositResult.setText("Deposited. New Balance: " + acc.getBalance());
                showMessage("Deposit successful.");
            } else {
                showMessage("Account not found.");
            }
        } catch (NumberFormatException e) {
            depositResult.setText("Invalid input.");
        }
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

	private void handleWithdraw() {
        try {
            int accNum = Integer.parseInt(accWithdraw.getText().trim());
            int amount = Integer.parseInt(withdrawInput.getText().trim());

            Account acc = findAccount(accNum);
            if (acc != null) {
            	if (acc.getBalance()<=0) {
	                acc.withdraw(amount);
	                withdrawResult.setText("Withdraw. New Balance: " + acc.getBalance());
	                showMessage("Withdraw successful.");
            	} else {
            		showMessage("Insufficient funds.");
            		withdrawResult.setText("Withdrawal failed: Not enough balance.");
            	}
            } else {
                showMessage("Account not found.");
                withdrawResult.setText("Account not found.");
            }
        } catch (NumberFormatException e) {
        	withdrawResult.setText("Invalid input.");
        }
    }


	private void handleTransfer() {
	    try {
	        int fromAcc = Integer.parseInt(acc1Transfer.getText().trim());
	        int toAcc = Integer.parseInt(acc2Transfer.getText().trim());
	        int amount = Integer.parseInt(transferAmount.getText().trim());

	        Account acc1 = findAccount(fromAcc);
	        Account acc2 = findAccount(toAcc);

	        if (acc1 != null && acc2 != null) {
	            if (acc1.getBalance() >= amount) {
	                acc1.withdraw(amount);
	                acc2.deposit(amount);
	                transferResult.setText("New Balance: From Account:" + acc1.getAccountNum()+","+acc1.getBalance()+ ", To Account:" + acc2.getAccountNum()+","+acc2.getBalance());
	                showMessage("Transfer successful.");
	            } else {
	                transferResult.setText("Insufficient funds for transfer.");
	            }
	        } else {
	            showMessage("One or both accounts not found.");
	        }
	    } catch (NumberFormatException e) {
	        transferResult.setText("Invalid input.");
	    }
	}


    private Account findAccount(int accNum) {
        for (Account acc : globalAccounts) {
            if (acc.getAccountNum() == accNum) {
                return acc;
            }
        }
        return null;
    }

    private void updateShowAllText() {
        StringBuilder sb = new StringBuilder();
        for (Account acc : globalAccounts) {
            sb.append("Name: ").append(acc.getFirstName()).append(" ").append(acc.getLastName())
              .append(" | Account: ").append(acc.getAccountNum())
              .append(" | Balance: ").append(acc.getBalance()).append("\n");
        }
        showAllText.setText(sb.toString());
    }
}

