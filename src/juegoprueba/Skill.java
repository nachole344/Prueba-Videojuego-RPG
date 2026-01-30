
package juegoprueba;

public class Skill {
    
    // Atributos
    private String skillName;
    
    private Type skillType;
    
    private int skillPower;
    
    private int skillCost;
    
    // Constructor de habilidades
    public Skill(String name, Type type, int power, int cost){
        
        this.skillName = name;
        
        this.skillType = type;
        
        this.skillPower = power;
        
        this.skillCost = cost;
        
    }
    
    // Getters para usos posteriores
    public String getName() {
        return this.skillName;
    }
    
    public Type getType() {
        return this.skillType;
    }
    
    public int getPower() {
        return this.skillPower;
    }
    
    public int getCost() {
        return this.skillCost;
    }
}
