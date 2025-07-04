package models;
import java.time.LocalDate;


public class expirable extends product{
    private LocalDate expirydate;

    public expirable(String Name, double Price, int Quantity, LocalDate ExpiryDate) {
        super(Name, Price, Quantity);
        this.expirydate=ExpiryDate;
    }

    @Override
    public boolean isexpired(){
        return expirydate.isBefore(LocalDate.now());

    }
    public LocalDate getexpirydate() {
        return expirydate;
    }
}
