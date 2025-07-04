package models;

public class shippableprod extends product implements shippable{
    private double weight;
    
    public shippableprod(String Name, double Price, int Quantity, double Weight) {
        super(Name, Price, Quantity);
        this.weight=Weight;
    }
    @Override
    public double getweight() {
        return weight;
    }
    @Override
    public boolean isexpired() {
        return false;
    }
    
}
