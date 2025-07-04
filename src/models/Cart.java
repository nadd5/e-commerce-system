package models;
import java.util.HashMap;
import java.util.Map;
public class Cart {
    private Map<product, Integer>items=new HashMap<>();
    public void add(product prod, int quant){
        if(!prod.isavailable(quant)){
            throw new IllegalArgumentException(
                "requested quantity exceeds stock"
            );
        }
        items.put(prod,items.getOrDefault(prod,0)+quant);
    }

    public Map<product,Integer>getitems(){
        return items;
    }

    public boolean isempty(){
        return items.isEmpty();
    }

    public void clear(){
        items.clear();
    }
}