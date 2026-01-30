package juegoprueba;

import processing.core.PApplet;

public class JuegoRPG extends PApplet {

    static final int SCREEN_WIDTH = 1200;
    static final int SCREEN_HEIGHT = 675;

    // Estados de juego
    enum GameStatus {
        BATTLE,
        SKILL_MENU,
        ITEM_MENU,
        VICTORY,
        DEFEAT
    }

    GameStatus status = GameStatus.BATTLE;

    boolean playerTurn = true;

    int temp = 0; // Para contar el tiempo entre turnos

    // Declaración de Personajes
    Character hero;
    Character enemy;

    // Declaración de Skills
    Skill agi;
    Skill bufu;
    Skill dia;
    Skill toxic;

    // Declaracion de los items
    Item templatePotion;
    Item templateEther;
    Item templateAntidote;
    Item templateItemAgi;

    public static void main(String[] args) {
        PApplet.main("juegoprueba.JuegoRPG");

    }

    public void spawnNewEnemy() {

        enemy = new Character("Shadow", 80, 20, 15, 10, 5, 5, Type.FIRE);

        enemy.addLevel(hero.getLevel() - 1);
        enemy.addSkill(toxic);
        enemy.addSkill(bufu);

    }

    @Override
    public void settings() {
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    @Override
    public void setup() {

        // Crear habilidades
        agi = new Skill("Agi", Type.FIRE, 10, 5);
        bufu = new Skill("Bufu", Type.ICE, 10, 5);
        dia = new Skill("Dia", Type.HEAL, 10, 5);
        toxic = new Skill("Toxic", Type.POISON, 10, 5);

        // Crear templates de Items
        templatePotion = new Item("Poción", ItemType.HEAL, 50, 1, false);
        templateEther = new Item("Éter", ItemType.ETHER, 10, 1, false);
        templateAntidote = new Item("Antidoto", ItemType.POISON_HEAL, 3, 1, false);
        templateItemAgi = new Item("Item Agi", ItemType.FIRE_ATTACK, 10, 1, true);

        // Crear heroes, asignar sus skills y darle sus objetos iniciales.
        hero = new Character("Joker", 100, 50, 20, 15, 10, 10, Type.ICE);
        hero.addSkill(agi);
        hero.addSkill(bufu);
        hero.addSkill(dia);
        
        hero.addItem(templatePotion, 3);
        hero.addItem(templateEther, 2);
        hero.addItem(templateAntidote, 2);
        hero.addItem(templateItemAgi, 1);

        // Crear  y asignar sus skills
        enemy = new Character("Shadow", 80, 20, 15, 10, 5, 5, Type.FIRE);
        enemy.addSkill(toxic);

    }

    @Override
    public void draw() {
        background(0); // fondo negro

        if (status == GameStatus.BATTLE || status == GameStatus.SKILL_MENU || status == GameStatus.ITEM_MENU) {

            drawCharacter(hero, SCREEN_WIDTH / 4, 300);
            drawCharacter(enemy, SCREEN_WIDTH * 3 / 4, 300);

            fill(255);
            textSize(20);
            textAlign(CENTER);

            if (playerTurn) {

                text("TU TURNO", SCREEN_WIDTH / 2, 30);

                textAlign(LEFT);

                if (status == GameStatus.BATTLE) {

                    text("1. Ataque", 100, 600);
                    text("2. Skills", 250, 600);
                    text("3. Objetos", 400, 600);

                } else if (status == GameStatus.SKILL_MENU) {

                    int finalIndex = 0;

                    for (int i = 0; i < hero.getSkills().size(); i++) {

                        text((i + 1) + ". " + hero.getSkills().get(i).getName() + " (" + hero.getSkills().get(i).getCost() + " SP)", 100 + 150 * i, 600);

                        finalIndex++;

                    }

                    text("0. Cancelar", 100 + 150 * finalIndex, 600);

                } else if (status == GameStatus.ITEM_MENU) {

                    int finalIndex = 0;

                    for (int i = 0; i < hero.getItems().size(); i++) {

                        text((i + 1) + ". " + hero.getItems().get(i).getName() + " ( x" + hero.getItems().get(i).getAmount() + ")", 100 + 150 * i, 600);

                        finalIndex++;

                    }

                    text("0. Cancelar", 100 + 150 * finalIndex, 600);

                }

            } else {

                text("TURNO DEL ENEMIGO", SCREEN_WIDTH / 2, 30);

            }

            if (!playerTurn) { // Si es turno del enemigo...

                if (temp > 0) {

                    temp--;

                } else {

                    println("¡Turno del enemigo!");

                    int magicChance = (int) (Math.random() * 100);

                    if (magicChance < 30 && enemy.getCurrentSP() > 5 && !enemy.getSkills().isEmpty()) {

                        int skillListLength = enemy.getSkills().size();

                        int randomIndex = (int) (Math.random() * skillListLength);

                        System.out.println("El enemigo usó magia");

                        enemy.castSkill(enemy.getSkills().get(randomIndex), hero);

                    } else {

                        System.out.println("El enemigo atacó físicamente");

                        enemy.attack(hero);

                    }

                    if (hero.isPoisoned()) {

                        System.out.println("El heroe sufre por el veneno.");
                        hero.receiveDamage(5);
                        hero.restPoisonTurn(1);

                    }

                    if (!hero.isAlive()) {

                        status = GameStatus.DEFEAT;

                    } else {

                        playerTurn = true;

                    }
                }
            }

        } else if (status == GameStatus.VICTORY) {

            fill(0, 255, 0); // Verde
            textSize(50);
            textAlign(CENTER);
            text("¡VICTORIA!", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
            textSize(20);
            text("Pulsa 'C' para continuar", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + 50);

        } else if (status == GameStatus.DEFEAT) {

            fill(255, 0, 0); // Rojo
            textSize(50);
            textAlign(CENTER);
            text("GAME OVER", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
            text("Pulsa 'R' para reiniciar", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2 + 50);

        }
    }

    // Dibujo de personaje
    public void drawCharacter(Character c, float x, float y) {

        if (c.isPoisoned()) { // si está envenenado se prioriza

            fill(100, 255, 100);

        } else if (x < SCREEN_WIDTH / 2) { // si está en el lado izquierdo es un héroe

            fill(100, 100, 255);

        } else {                    // si está en el lado derecho es un villano

            fill(255, 100, 100);

        }

        rect(x - 25, y, 50, 100); // El personaje (de momento un rectangulo)

        fill(255);
        textSize(20);
        textAlign(CENTER);

        text(c.getName() + " Lv. " + c.getLevel(), x, y - 30);

        if (c.getCurrentHP() < c.getMaxHP() / 4) {

            fill(255, 75, 75);

        }

        text("HP: " + c.getCurrentHP() + " / " + c.getMaxHP(), x, y + 130);

        fill(255);
        text("SP: " + c.getCurrentSP() + " / " + c.getMaxSP(), x, y + 160);
    }

    @Override
    public void keyPressed() {

        if (status == GameStatus.BATTLE) { // La partida esta en marcha

            if (!playerTurn) {
                return;

            }

            if (key == '1') {
                println("Presionaste 1: Ataque Físico");

                hero.attack(enemy);

                if (!enemy.isAlive()) {

                    int xpGained = enemy.getExpReward();
                    System.out.println("¡Has ganado " + xpGained + " XP!");

                    hero.gainExp(xpGained);

                    status = GameStatus.VICTORY;

                } else {

                    playerTurn = false;
                    temp = 60;

                }

            } else if (key == '2') {
                println("Presionaste 2: Magia");

                if (!hero.getSkills().isEmpty()) {

                    status = GameStatus.SKILL_MENU;

                } else {

                    System.out.println("No tienes habilidades");

                }

            } else if (key == '3'){
                println("Presionaste 3: Objetos");

                if (!hero.getItems().isEmpty()) {

                    status = GameStatus.ITEM_MENU;

                } else {

                    System.out.println("No tienes objetos");

                }
                
            }

        } else if (status == GameStatus.SKILL_MENU) { // El jugador está en el menu de mágia

            for (int i = 0; i < hero.getSkills().size(); i++) {

                if (key == String.valueOf(i + 1).charAt(0)) {

                    hero.castSkill(hero.getSkills().get(i), enemy);

                    if (!enemy.isAlive()) {

                        int xpGained = enemy.getExpReward();
                        System.out.println("¡Has ganado " + xpGained + " XP!");

                        hero.gainExp(xpGained);

                        status = GameStatus.VICTORY;

                    } else {

                        playerTurn = false;
                        temp = 60;

                        status = GameStatus.BATTLE;

                    }

                } else if (key == '0') {

                    status = GameStatus.BATTLE;

                }

            }

        } else if (status == GameStatus.ITEM_MENU) { // El jugador está en el menu de mágia

            for (int i = 0; i < hero.getItems().size(); i++) {

                if (key == String.valueOf(i + 1).charAt(0)) {

                    if (!hero.getItems().get(i).getOffensive()){
                        
                        hero.useItem(hero.getItems().get(i), hero);

                    } else { // Objetos ofensivos
                        
                        hero.useItem(hero.getItems().get(i), enemy);
                    }

                    if (!enemy.isAlive()) {

                        int xpGained = enemy.getExpReward();
                        System.out.println("¡Has ganado " + xpGained + " XP!");

                        hero.gainExp(xpGained);

                        status = GameStatus.VICTORY;

                    } else {

                        playerTurn = false;
                        temp = 60;

                        status = GameStatus.BATTLE;

                    }

                } else if (key == '0') {

                    status = GameStatus.BATTLE;

                }

            }

        } else if (status == GameStatus.VICTORY) { // El jugador gana

            if (key == 'c') {

                System.out.println("Aparece un nuevo enemigo");

                spawnNewEnemy();

                playerTurn = true;
                temp = 0;

                status = GameStatus.BATTLE;

            }

        } else { // El jugador pierde

            if (key == 'r') {

                System.out.println("Reiniciando...");

                // Reiniciando las stats
                hero.heal(hero.getMaxHP());
                enemy.heal(enemy.getMaxHP());

                hero.restoreSP(hero.getMaxSP());
                enemy.restoreSP(enemy.getMaxSP());

                playerTurn = true;
                temp = 0;

                status = GameStatus.BATTLE;

            }

        }
    }

}
