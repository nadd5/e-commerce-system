package models;

public class customer {
    private String name;
    private double balance;

    public customer(String Name,double Balance){
        this.name=Name;
        this.balance=Balance;
    }

    public boolean pay(double amount){
        if(amount>balance){
            return false;
        }
        else{
            balance-=amount;
            return true;
        }
    }

    public double getbalance(){
        return balance;
    }

    public String getname(){
        return name;
    }

}