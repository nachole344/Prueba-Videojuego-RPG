
package juegoprueba.Legacy;

import juegoprueba.Character;
import juegoprueba.Skill;
import juegoprueba.Type;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("--- Battle Start ---");
        
        // Crear habilidades
        Skill agi = new Skill("Agi", Type.FIRE, 10, 5);
        Skill bufu = new Skill("Bufu", Type.ICE, 10, 5);

        // Crear heroes
        Character hero = new Character("Joker", 100, 50, 20, 15, 10, 10, Type.ICE);
        hero.addSkill(agi);
        hero.addSkill(bufu);
        
        // Crear enemigos
        Character enemy = new Character("Shadow", 80, 20, 15, 10, 5, 5, Type.FIRE);
        
        
        // Preparar batalla
        Battle battle = new Battle(hero, enemy);
        
        
        // Empezar batalla
        battle.start();
        
    }
}