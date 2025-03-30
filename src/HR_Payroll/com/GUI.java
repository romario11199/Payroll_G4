package HR_Payroll.com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class GUI extends JFrame {
    private JTextField nameField, salaryField, commissionField, salesField;
    private JComboBox<String> typeComboBox;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private ArrayList<Payable> employees;

    public GUI() {
        setTitle("Payroll Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        employees = new ArrayList<>();

        // Top Panel for Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Type:"));
        String[] employeeTypes = {"Salaried", "Commission", "Base + Commission"};
        typeComboBox = new JComboBox<>(employeeTypes);
        inputPanel.add(typeComboBox);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        inputPanel.add(new JLabel("Commission Rate:"));
        commissionField = new JTextField();
        inputPanel.add(commissionField);

        inputPanel.add(new JLabel("Sales:"));
        salesField = new JTextField();
        inputPanel.add(salesField);

        add(inputPanel, BorderLayout.NORTH);

        // Table for Employee Data
        tableModel = new DefaultTableModel(new String[]{"Name", "Type", "Salary", "Commission", "Sales"}, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Employee");
        JButton removeButton = new JButton("Remove Employee");
        JButton generateStubButton = new JButton("Generate Pay Stubs");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(generateStubButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeEmployee();
            }
        });

        generateStubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePayStubs();
            }
        });

        setVisible(true);
    }

    private void addEmployee() {
        String name = nameField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        double salary = parseDouble(salaryField.getText());
        double commissionRate = parseDouble(commissionField.getText());
        double sales = parseDouble(salesField.getText());

        if (name.isEmpty() || salary < 0 || (type.equals("Commission") && commissionRate < 0) || (type.equals("Base + Commission") && (commissionRate < 0 || sales < 0))) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Payable employee = null;
            if (type.equals("Salaried")) {
                employee = new SalariedEmployee(name, "123-45-6789", salary);
            } else if (type.equals("Commission")) {
                employee = new CommissionEmployee(name, "123-45-6789", sales, commissionRate);
            } else if (type.equals("Base + Commission")) {
                employee = new BasePlusCommissionEmployee(name, "123-45-6789", sales, commissionRate, salary);
            }

            if (employee != null) {
                employees.add(employee);
                tableModel.addRow(new Object[]{name, type, salary, commissionRate, sales});
            }
        } catch (InvalidEmployeeDataException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            employees.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to remove.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generatePayStubs() {
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            for (Payable employee : employees) {
                writer.write("Employee: " + ((Employee) employee).getName() + ", Payment: " + employee.getPaymentAmount() + "\n");
            }
            JOptionPane.showMessageDialog(this, "Pay stubs generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing pay stubs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseDouble(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
