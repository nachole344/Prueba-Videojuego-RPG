package juegoprueba;

import java.util.ArrayList;
import java.util.Scanner;

public class Battle {

    private Character hero;
    private Character enemy;
    private Scanner sc;
    private int turn;

    public Battle(Character hero, Character enemy) {
        this.hero = hero;
        this.enemy = enemy;
        this.sc = new Scanner(System.in);
        this.turn = 1;
    }

    public void start() {

        turn = 1;

        System.out.println("======= Battle Start =======");

        while (hero.isAlive() && enemy.isAlive()) {

            hero.showStatus();
            enemy.showStatus();

            System.out.println("\n======= ROUND " + turn + " =======");

            System.out.println("Elige una acción:");
            System.out.println("1. Ataque");
            System.out.println("2. Menú de magia");

            System.out.print("> ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine(); // --> Para limpiar el Scanner

                if (choice == 1) {

                    hero.attack(enemy);

                } else if (choice == 2) {

                    ArrayList<Skill> heroSkills = hero.getSkills();

                    // Si no tiene habilidades, avisamos
                    if (heroSkills.isEmpty()) {
                        System.out.println("No tienes habilidades aprendidas!");
                        continue;
                    }

                    System.out.println("=======  SKILLS =======");

                    // Mostrar las habilidades disponible
                    for (int i = 0; i < heroSkills.size(); i++) {
                        Skill s = heroSkills.get(i);
                        System.out.println((i + 1) + ". " + s.getName() + " (" + s.getCost() + " SP)");
                    }

                    System.out.println("0. Cancelar");
                    System.out.print("> Elige una habilidad: ");

                    //  Leer elección de habilidad
                    if (sc.hasNextInt()) {
                        int skillIndex = sc.nextInt();
                        sc.nextLine();

                        if (skillIndex == 0) {
                            continue;
                        }
                        
                        int realIndex = skillIndex - 1;

                        if (realIndex >= 0 && realIndex < heroSkills.size()) {

                            Skill selectedSkill = heroSkills.get(realIndex);
                            hero.castSkill(selectedSkill, enemy);

                        } else {
                            System.out.println("Habilidad no válida.");
                        }
                    }

                } else {

                    System.out.println("Comando desconocido. Pierdes el turno.");
                }

            } else {
                String badInput = sc.nextLine(); // Leemos la basura para limpiar el scanner
                System.out.println("Error: '" + badInput + "' no es un número válido.");
            }

            if (!enemy.isAlive()) {

                System.out.println(enemy.getName() + " ha sido derrotado!");
                break;

            }

            System.out.println("\nTurno del Enemigo:");
            enemy.attack(hero);

            if (!hero.isAlive()) {
                System.out.println(hero.getName() + " ha sido derrotado... Game Over");
                break;
            }

            turn++;

        }

        System.out.println("======= Battle Ended =======");
    }

}
