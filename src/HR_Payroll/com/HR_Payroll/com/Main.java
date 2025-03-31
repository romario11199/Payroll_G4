/*
Griffiths Marlon - 2301010569
Romario McLymont - 2301010275
Delvian Brown - 230101767
Shanelle Farqurson -
Jhenelle Walker -
*/

package HR_Payroll.com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//GUI Swing Design
public class Main extends JFrame {
    private JTextField nameField, salaryField, commissionField, salesField, partNumField, partDescField, quantityField, priceField;
    private JComboBox<String> typeComboBox;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private ArrayList<Payable> employees;

    public Main() {
        setTitle("Payroll System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        employees = new ArrayList<>();

        // Top Panel for Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee/Invoice Details"));

        inputPanel.add(new JLabel("Name/Part Number:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Type:"));
        String[] employeeTypes = {"Salaried", "Commission", "Base + Commission", "Invoice"};
        typeComboBox = new JComboBox<>(employeeTypes);
        inputPanel.add(typeComboBox);

        inputPanel.add(new JLabel("Salary/Base Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        inputPanel.add(new JLabel("Commission Rate:"));
        commissionField = new JTextField();
        inputPanel.add(commissionField);

        inputPanel.add(new JLabel("Sales:"));
        salesField = new JTextField();
        inputPanel.add(salesField);

        // Invoice-specific fields
        inputPanel.add(new JLabel("Part Description:"));
        partDescField = new JTextField();
        inputPanel.add(partDescField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price Per Item:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        add(inputPanel, BorderLayout.NORTH);

        // Table for Employee & Invoice Data
        tableModel = new DefaultTableModel(new String[]{"Name/Part No.", "Type", "Salary/Base", "Commission", "Sales", "Quantity", "Price Per Item"}, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton generateStubButton = new JButton("Generate Pay Stubs");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(generateStubButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addEmployee());
        removeButton.addActionListener(e -> removeEmployee());
        generateStubButton.addActionListener(e -> generatePayStubs());

        // Ensure input fields update dynamically based on selection
        typeComboBox.addActionListener(e -> toggleFields());

        setVisible(true);
    }

    private void toggleFields() {
        String type = (String) typeComboBox.getSelectedItem();

        boolean isInvoice = type.equals("Invoice");

        salaryField.setEnabled(!isInvoice);
        commissionField.setEnabled(!isInvoice);
        salesField.setEnabled(!isInvoice);

        partDescField.setEnabled(isInvoice);
        quantityField.setEnabled(isInvoice);
        priceField.setEnabled(isInvoice);
    }

    private void addEmployee() {
        String type = (String) typeComboBox.getSelectedItem();
        try {
            Payable entry = null;

            if (type.equals("Salaried")) {
                String name = nameField.getText().trim();
                double salary = parseDouble(salaryField.getText());

                if (name.isEmpty() || salary < 0) throw new InvalidEmployeeDataException("Invalid input!");
                entry = new SalariedEmployee(name, "123-45-6789", salary);

            } else if (type.equals("Commission")) {
                String name = nameField.getText().trim();
                double sales = parseDouble(salesField.getText());
                double commissionRate = parseDouble(commissionField.getText());

                if (name.isEmpty() || sales < 0 || commissionRate < 0) throw new InvalidEmployeeDataException("Invalid input!");
                entry = new CommissionEmployee(name, "123-45-6789", sales, commissionRate);

            } else if (type.equals("Base + Commission")) {
                String name = nameField.getText().trim();
                double sales = parseDouble(salesField.getText());
                double commissionRate = parseDouble(commissionField.getText());
                double baseSalary = parseDouble(salaryField.getText());

                if (name.isEmpty() || sales < 0 || commissionRate < 0 || baseSalary < 0) throw new InvalidEmployeeDataException("Invalid input!");
                entry = new BasePlusCommissionEmployee(name, "123-45-6789", sales, commissionRate, baseSalary);

            } else if (type.equals("Invoice")) {
                String partNum = nameField.getText().trim();
                String partDesc = partDescField.getText().trim();
                int quantity = parseInt(quantityField.getText());
                double pricePerItem = parseDouble(priceField.getText());

                if (partNum.isEmpty() || partDesc.isEmpty() || quantity < 0 || pricePerItem < 0) throw new InvalidEmployeeDataException("Invalid input!");
                entry = new Invoice(partNum, partDesc, quantity, pricePerItem);
            }

            if (entry != null) {
                employees.add(entry);
                tableModel.addRow(new Object[]{
                        nameField.getText(),
                        type,
                        salaryField.getText(),
                        commissionField.getText(),
                        salesField.getText(),
                        quantityField.getText(),
                        priceField.getText()
                });
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
            JOptionPane.showMessageDialog(this, "Please select an entry to remove.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generatePayStubs() {
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            for (Payable payable : employees) {
                String entityName = (payable instanceof Employee) ? ((Employee) payable).getName() : "Invoice";

                writer.write("Processing payment for: " + entityName + "\n");
                writer.write("Payment Amount: $" + payable.getPaymentAmount() + "\n");
                writer.write("-------------------------------\n");

                payable.writeToFile();
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

    private int parseInt(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}

