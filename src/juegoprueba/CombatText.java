
package juegoprueba;

public class CombatText {
    
    private String text;
    
    private float x;
    private float y;
    
    private int r;
    private int g;
    private int b;
    
    private float textSpeed = 3.0f;
    
    private int vanishTimer = 40;
    
    public CombatText(String text, float x, float y, int r, int g, int b){
        
        this.text = text;
        
        this.x = x;
        this.y = y;
        
        this.r = r;
        this.g = g;
        this.b = b;
        
    }
    
    // Getters
    
    public String getText() {
        return text;
    }
    
    public float getXpos(){
        return x;
    }
    
    public float getYpos(){
        return y;
    }
    
    public int getRvalue(){
        return r;
    }
    
    public int getGvalue(){
        return g;
    }
    
    public int getBvalue(){
        return b;
    }
    
    public boolean isTimerFinish(){
        return vanishTimer <= 0;
    }
    
    
    // Metodos
    public void update(){
        if (vanishTimer > 0) {
            vanishTimer--;
            this.y -= this.textSpeed; // El texto sube
            
            this.textSpeed *= 0.75;
            
        }
        
    }
    
}
