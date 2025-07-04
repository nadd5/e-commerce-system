import models.*;
import services.*;
import java.time.LocalDate;
public class App {
    public static void main(String[] args){
        customer cust=new customer("Nadia",1000);
        product cookie=new expirable("Cookies", 30, 50, LocalDate.of(2026, 10, 1));
        product milk=new expirable("Milk",50,50,LocalDate.of(2025,7,20));
        product bag=new shippableprod("Bag",600, 10, 0.5);
        product computer=new shippableprod("Computer",2000, 10, 2.5);
        product note=new product("Note", 100, 20){
            @Override
            public boolean isexpired() {
                return false;
            }
        };

        Cart cart= new Cart();
        cart.add(cookie, 2);
        cart.add(milk, 2);
        cart.add(bag, 1);
        cart.add(computer, 1);
        cart.add(note, 1);
        checkout checkout = new checkout();
        checkout.process(cust, cart);

    }
}
