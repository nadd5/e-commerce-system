import models.*;
import services.checkout;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class storegui extends JFrame{
    private JTextField nameField, balanceField, quantityField;
    private JComboBox<String> productDropdown;
    private JTextArea cartArea, outputArea;

    private Cart cart;
    private customer customer;
    private List<product> products = new ArrayList<>();
    private checkout checkoutService = new checkout();
    private JPanel customerPanel, shoppingPanel;
    public storegui() {
        setTitle("E-Commerce System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        customerPanel = new JPanel(new GridLayout(3, 2));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Enter Customer Details"));

        customerPanel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        customerPanel.add(nameField);

        customerPanel.add(new JLabel("Initial Balance:"));
        balanceField = new JTextField();
        customerPanel.add(balanceField);

        JButton startButton = new JButton("Start Shopping");
        customerPanel.add(startButton);

        add(customerPanel, BorderLayout.NORTH);
        startButton.addActionListener(_ -> setupCustomer());
        shoppingPanel = null;
    }
    private void setupCustomer() {
        String name = nameField.getText().trim();
        String balanceText = balanceField.getText().trim();

        if (name.isEmpty() || balanceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and balance.");
            return;
        }
        try {
            double balance = Double.parseDouble(balanceText);
            customer = new customer(name, balance);
            cart = new Cart();
            initProducts();
            showShoppingUI();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for balance.");
        }
    }

    private void initProducts() {
        products.clear();
        products.add(new expirable("Cookies", 30, 2, LocalDate.now().plusDays(1)));
        products.add(new expirable("Milk", 50, 3, LocalDate.now().plusDays(2)));
        products.add(new shippableprod("Bag", 600, 1, 0.5));
        products.add(new product("Notebook", 50, 3) {
            @Override public boolean isexpired() { return false; }
        });
    }

    private void showShoppingUI() {
        if (shoppingPanel != null) remove(shoppingPanel); 
        shoppingPanel = new JPanel(new BorderLayout());
        JPanel top = new JPanel(new GridLayout(3, 2));
        top.setBorder(BorderFactory.createTitledBorder("Add to Cart"));

        top.add(new JLabel("Select Product:"));
        productDropdown = new JComboBox<>();
        for (product p : products) productDropdown.addItem(p.getname());
        top.add(productDropdown);

        top.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        top.add(quantityField);

        JButton addButton = new JButton("Add to Cart");
        JButton checkoutButton = new JButton("Checkout");
        top.add(addButton);
        top.add(checkoutButton);

        shoppingPanel.add(top, BorderLayout.NORTH);
        cartArea = new JTextArea(10, 40);
        cartArea.setEditable(false);
        cartArea.setBorder(BorderFactory.createTitledBorder("Cart"));
        shoppingPanel.add(new JScrollPane(cartArea), BorderLayout.CENTER);
        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));
        shoppingPanel.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        add(shoppingPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        addButton.addActionListener(_ -> addToCart());
        checkoutButton.addActionListener(_ -> checkout());
    }

    private void addToCart() {
        try {
            int index = productDropdown.getSelectedIndex();
            int qty = Integer.parseInt(quantityField.getText());
            product selected = products.get(index);
            cart.add(selected, qty);
            cartArea.append(qty + "x " + selected.getname() + "\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void checkout() {
        outputArea.setText(""); // clear
        JTextAreaPrintStream ps = new JTextAreaPrintStream(outputArea);
        System.setOut(ps.getPrintStream());

        checkoutService.process(customer, cart);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new storegui().setVisible(true));
    }
    
}
