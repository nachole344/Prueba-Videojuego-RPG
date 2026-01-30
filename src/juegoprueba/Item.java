
package juegoprueba;

public class Item {
    
    private String name;
    
    private ItemType type;
    
    private int power;
    
    private int amount;
    
    private boolean isOffensive;
    
    public Item(String name, ItemType type, int power, int amount, boolean isOffensive){
        
        this.name = name;
        
        this.type = type;
        
        this.power = power;
        
        this.amount = amount;
        
        this.isOffensive = isOffensive;
        
    }
    
    // Getters para usos posteriores en caso de ser necesarios
    
    public String getName(){
        return this.name;
    }
    public ItemType getType(){
        return this.type;
    }
    public int getPower(){
        return this.power;
    }
    public int getAmount(){
        return this.amount;
    }
    public boolean getOffensive(){
        return this.isOffensive;
    }
    
    // Metodos
    
    public void addAmount(int amount){
        this.amount += amount;
    }
    
    public void decreaseAmount(int amount){
        this.amount -= amount;
    }
    
}
