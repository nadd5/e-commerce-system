package services;
import java.util.ArrayList;
import java.util.Map;
import models.Cart;
import models.customer;
import models.product;
import models.shippable;
import models.shippableprod;
import java.util.List;

public class checkout {
    private static final double shippingfee=30.0;
    private shipping service= new shipping();

    public void process(customer cust,Cart cart){
        if(cart.isempty()){
            System.out.println("Cart is empty. Cannot proceed to checkout.");
            return;
        }
        
        double subtotal =0;
        List<shippableprod> shippables = new ArrayList<>();
        for(Map.Entry<product,Integer>entry:cart.getitems().entrySet()){
            product prod=entry.getKey();
            int quant=entry.getValue();
            if(!prod.isavailable(quant)){
                System.out.println("Product "+prod.getname()+" is out of stock or not available in requested quantity.");
                return;
            }
            if(prod.isexpired()){
                System.out.println("Product "+prod.getname()+" is expired and cannot be purchased.");
                return;
            }
            subtotal += prod.getprice()*quant;
            if(prod instanceof shippable){
                shippables.add((shippableprod) prod);
            }
        }
        double total = subtotal + shippingfee;
        if (!cust.pay(total)) {
            System.out.println("Payment failed. Insufficient balance. Please try again.");
            return;
        }
        if(!shippables.isEmpty()){
            service.ship(shippables);
        } else {
            System.out.println("No shippable items in the cart. Proceeding without shipping.");
        }
        System.out.println("\n**Checkout Receipt**");
        for(Map.Entry<product,Integer>entry:cart.getitems().entrySet()){
            System.out.println("Product: " + entry.getKey().getname() + 
                               ", Quantity: " + entry.getValue() + 
                               ", Price: " + entry.getKey().getprice() * entry.getValue());
        }
        System.out.println("-------------------------------");
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Shipping Fee: " + shippingfee);
        System.out.println("Total Amount: " + total);
        System.out.println("Balance after purchase: " + cust.getbalance());
        System.out.println("Thank you for your purchase, " + cust.getname() + "!");

}

}