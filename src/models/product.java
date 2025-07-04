package models;

public abstract class product{
    protected String name;
    protected double price;
    protected int quantity;
    public product(String Name, double Price, int Quantity){
        this.name=Name;
        this.price=Price;
        this.quantity=Quantity;
    }
    
    public String getname(){return name;}
    public double getprice(){return price;}
    public int getquantity(){return quantity;}
    
    public void reducequantity(int amount){
        if(amount<=quantity){
            quantity-=amount;}
    }
    public boolean isavailable(int requested){
        return requested<=quantity;
    }
    
    public abstract boolean isexpired();

}