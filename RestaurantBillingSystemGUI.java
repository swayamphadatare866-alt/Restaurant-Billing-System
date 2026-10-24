
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

public class RestaurantBillingSystemGUI extends JFrame {

    private final String[] menuItems = {
        "Veg Burger", "Paneer Pizza", "French Fries",
        "Veg Sandwich", "Cold Coffee", "Lemon Soda"
    };

    private final double[] prices = {
        80.0, 180.0, 70.0, 90.0, 100.0, 60.0
    };

    private final JComboBox<String> itemBox;
    private final JSpinner quantitySpinner;
    private final DefaultTableModel tableModel;
    private final JTable orderTable;

    private final JLabel subtotalLabel;
    private final JLabel taxLabel;
    private final JLabel totalLabel;

    private final DecimalFormat money = new DecimalFormat("0.00");
    private final double TAX_RATE = 0.05;

    public RestaurantBillingSystemGUI() {

        setTitle("Restaurant Billing System");
        setSize(900, 600);
        setMinimumSize(new Dimension(750, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(12, 12));

        Color background = new Color(245, 247, 250);
        Color darkBlue = new Color(35, 48, 68);
        Color green = new Color(35, 145, 95);

        getContentPane().setBackground(background);

        // Heading
        JLabel heading = new JLabel(
            "RESTAURANT BILLING SYSTEM",
            SwingConstants.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(Color.WHITE);
        heading.setOpaque(true);
        heading.setBackground(darkBlue);
        heading.setBorder(
            BorderFactory.createEmptyBorder(20, 10, 20, 10)
        );

        add(heading, BorderLayout.NORTH);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(background);
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        );

        add(mainPanel, BorderLayout.CENTER);

        // Food selection controls
        JPanel controls = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 10)
        );

        controls.setBackground(Color.WHITE);
        controls.setBorder(
            BorderFactory.createTitledBorder("Order Controls")
        );

        itemBox = new JComboBox<>(menuItems);
        itemBox.setPreferredSize(new Dimension(150, 30));

        quantitySpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 100, 1)
        );

        JButton addButton = new JButton("Add Item");
        JButton viewMenuButton = new JButton("View Full Menu");

        addButton.setBackground(green);
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        viewMenuButton.setFocusPainted(false);

        controls.add(new JLabel("Select Item:"));
        controls.add(itemBox);
        controls.add(new JLabel("Quantity:"));
        controls.add(quantitySpinner);
        controls.add(addButton);
        controls.add(viewMenuButton);

        // Customer order table
        String[] columns = {
            "Item Name", "Price (Rs.)", "Quantity", "Amount (Rs.)"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orderTable = new JTable(tableModel);
        orderTable.setRowHeight(28);

        orderTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 13)
        );

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(
            BorderFactory.createTitledBorder("Customer Order")
        );

        // Center section
        JPanel centerPanel = new JPanel(
            new BorderLayout(10, 10)
        );

        centerPanel.setBackground(background);
        centerPanel.add(controls, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bill summary
        JPanel bottomPanel = new JPanel(
            new BorderLayout(10, 10)
        );

        bottomPanel.setBackground(background);

        JPanel totalsPanel = new JPanel(
            new GridLayout(3, 2, 10, 8)
        );

        totalsPanel.setBackground(Color.WHITE);
        totalsPanel.setBorder(
            BorderFactory.createTitledBorder("Bill Summary")
        );

        subtotalLabel = new JLabel("Rs. 0.00");
        taxLabel = new JLabel("Rs. 0.00");
        totalLabel = new JLabel("Rs. 0.00");

        totalsPanel.add(new JLabel("Subtotal:"));
        totalsPanel.add(subtotalLabel);

        totalsPanel.add(new JLabel("Tax (5%):"));
        totalsPanel.add(taxLabel);

        totalsPanel.add(new JLabel("Grand Total:"));

        totalLabel.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        totalLabel.setForeground(green);
        totalsPanel.add(totalLabel);

        // Action buttons
        JPanel buttonsPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10)
        );

        buttonsPanel.setBackground(background);

        JButton removeButton = new JButton("Remove Selected");
        JButton clearButton = new JButton("Clear Order");
        JButton billButton = new JButton("Generate Bill");

        billButton.setBackground(green);
        billButton.setForeground(Color.WHITE);
        billButton.setFocusPainted(false);

        buttonsPanel.add(removeButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(billButton);

        bottomPanel.add(totalsPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Button event handling
        addButton.addActionListener(e -> addItem());

        viewMenuButton.addActionListener(e -> showFullMenu());

        removeButton.addActionListener(e -> removeItem());

        clearButton.addActionListener(e -> clearOrder());

        billButton.addActionListener(e -> generateBill());
    }

    // Display the complete menu in a separate window
    private void showFullMenu() {

        String[] columns = {"No.", "Food / Beverage", "Price (Rs.)"};

        DefaultTableModel menuModel =
            new DefaultTableModel(columns, 0) {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

        for (int i = 0; i < menuItems.length; i++) {

            menuModel.addRow(new Object[] {
                i + 1,
                menuItems[i],
                money.format(prices[i])
            });
        }

        JTable menuTable = new JTable(menuModel);
        menuTable.setRowHeight(30);

        menuTable.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 14)
        );

        menuTable.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane menuScrollPane = new JScrollPane(menuTable);
        menuScrollPane.setPreferredSize(new Dimension(400, 230));

        JPanel menuPanel = new JPanel(new BorderLayout(10, 10));

        JLabel menuHeading = new JLabel(
            "FOOD AND BEVERAGE MENU",
            SwingConstants.CENTER
        );

        menuHeading.setFont(
            new Font("Arial", Font.BOLD, 18)
        );

        menuPanel.add(menuHeading, BorderLayout.NORTH);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
            this,
            menuPanel,
            "Complete Restaurant Menu",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Add selected item to the order
    private void addItem() {

        int index = itemBox.getSelectedIndex();
        int quantity = (Integer) quantitySpinner.getValue();

        String name = menuItems[index];
        double price = prices[index];

        // Update quantity if item already exists
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            if (tableModel.getValueAt(i, 0).equals(name)) {

                int oldQuantity = Integer.parseInt(
                    tableModel.getValueAt(i, 2).toString()
                );

                int newQuantity = oldQuantity + quantity;

                tableModel.setValueAt(newQuantity, i, 2);

                tableModel.setValueAt(
                    money.format(price * newQuantity), i, 3
                );

                updateTotals();
                return;
            }
        }

        double amount = price * quantity;

        tableModel.addRow(new Object[] {
            name,
            money.format(price),
            quantity,
            money.format(amount)
        });

        updateTotals();
    }

    // Remove selected order item
    private void removeItem() {

        int selectedRow = orderTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Please select an item to remove."
            );

            return;
        }

        tableModel.removeRow(selectedRow);
        updateTotals();
    }

    // Clear the complete order
    private void clearOrder() {

        tableModel.setRowCount(0);
        updateTotals();
    }

    // Calculate subtotal
    private double calculateSubtotal() {

        double subtotal = 0;

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            double price = Double.parseDouble(
                tableModel.getValueAt(i, 1).toString()
            );

            int quantity = Integer.parseInt(
                tableModel.getValueAt(i, 2).toString()
            );

            subtotal += price * quantity;
        }

        return subtotal;
    }

    // Update subtotal, tax, and total labels
    private void updateTotals() {

        double subtotal = calculateSubtotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        subtotalLabel.setText(
            "Rs. " + money.format(subtotal)
        );

        taxLabel.setText(
            "Rs. " + money.format(tax)
        );

        totalLabel.setText(
            "Rs. " + money.format(total)
        );
    }

    // Generate customer receipt
    private void generateBill() {

        if (tableModel.getRowCount() == 0) {

            JOptionPane.showMessageDialog(
                this,
                "Please add items before generating a bill."
            );

            return;
        }

        double subtotal = calculateSubtotal();
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;

        StringBuilder receipt = new StringBuilder();

        receipt.append("       RESTAURANT BILL\n");
        receipt.append("----------------------------------\n");

        receipt.append(String.format(
            "%-17s %4s %9s%n",
            "Item", "Qty", "Amount"
        ));

        receipt.append("----------------------------------\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String name =
                tableModel.getValueAt(i, 0).toString();

            int quantity = Integer.parseInt(
                tableModel.getValueAt(i, 2).toString()
            );

            String amount =
                tableModel.getValueAt(i, 3).toString();

            receipt.append(String.format(
                "%-17s %4d %9s%n",
                name, quantity, amount
            ));
        }

        receipt.append("----------------------------------\n");

        receipt.append("Subtotal: Rs. ")
            .append(money.format(subtotal))
            .append("\n");

        receipt.append("Tax (5%): Rs. ")
            .append(money.format(tax))
            .append("\n");

        receipt.append("Grand Total: Rs. ")
            .append(money.format(total))
            .append("\n");

        receipt.append("----------------------------------\n");
        receipt.append("       Thank you! Visit again.\n");

        JTextArea receiptArea =
            new JTextArea(receipt.toString());

        receiptArea.setEditable(false);

        receiptArea.setFont(
            new Font("Monospaced", Font.PLAIN, 14)
        );

        receiptArea.setMargin(
            new Insets(12, 12, 12, 12)
        );

        JScrollPane receiptScroll =
            new JScrollPane(receiptArea);

        receiptScroll.setPreferredSize(
            new Dimension(430, 350)
        );

        JOptionPane.showMessageDialog(
            this,
            receiptScroll,
            "Your Bill Receipt",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Main method
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            RestaurantBillingSystemGUI gui =
                new RestaurantBillingSystemGUI();

            gui.setVisible(true);
        });
    }
}
