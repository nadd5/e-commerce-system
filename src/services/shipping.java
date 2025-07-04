package services;
import models.shippable;
import models.shippableprod;
import java.util.List;

public class shipping {
    public void ship(List<shippableprod> shippables){
        double totalwight = 0;
        System.out.println("**Shipment notice**");
        for (shippable item : shippables) {
            System.out.println("Shipping item: " + item.getname() + " with weight: " + item.getweight()+" kg");
            totalwight += item.getweight();
        }
        System.out.println("Total weight of shipment: " + totalwight*1000 + " grams");
        System.out.println("Shipment completed successfully.");
    }
    
}
