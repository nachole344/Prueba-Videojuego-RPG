package juegoprueba;

import processing.core.PApplet;

public class JuegoRPG extends PApplet {

    static final int SCREEN_WIDTH = 1275;
    static final int SCREEN_HEIGHT = 675;

    static final int ITEMS_PER_PAGE = 10;

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

    int cursorIndexX = 0; // Para indicar el index del cursor en el eje X
    int cursorIndexY = 0; // Para indicar el index del cursor en el eje Y

    int page = 0;
    int maxPages;

    // Declaración de Personajes
    Character hero;
    Character enemy;

    // Declaración de Skills
    Skill agi;
    Skill bufu;
    Skill dia;
    Skill toxic;
    Skill tornado;
    Skill spark;

    // Declaracion de los items
    Item templatePotion;
    Item templateSuperPotion;
    Item templateEther;
    Item templateAntidote;
    Item templateItemAgi;
    Item templateRevive;
    Item templateMaxRevive;
    Item templateProtein;
    Item templateIron;
    Item templateCalcium;
    Item templateZinc;

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
        tornado = new Skill("Tornado", Type.WIND, 10, 5);
        spark = new Skill("Spark", Type.ELECTRIC, 10, 5);

        // Crear templates de Items
        templatePotion = new Item("Poción", ItemType.HEAL, 50, 1, false);
        templateSuperPotion = new Item("Super Poción", ItemType.HEAL, 100, 1, false);
        templateEther = new Item("Éter", ItemType.ETHER, 10, 1, false);
        templateAntidote = new Item("Antidoto", ItemType.POISON_HEAL, 3, 1, false);
        templateItemAgi = new Item("Item Agi", ItemType.FIRE_ATTACK, 10, 1, true);
        templateRevive = new Item("Revivir", ItemType.REVIVE, 1, 1, false);
        templateMaxRevive = new Item("Revivir Máximo", ItemType.MAX_REVIVE, 1, 1, false);
        templateProtein = new Item("Proteina", ItemType.STRENGHT_UPGRADE, 4, 1, false);
        templateIron = new Item("Hierro", ItemType.DEFENSE_UPGRADE, 4, 1, false);
        templateCalcium = new Item("Calcium", ItemType.MAGIC_UPGRADE, 4, 1, false);
        templateZinc = new Item("Zinc", ItemType.MAGIC_DEFENSE_UPGRADE, 4, 1, false);

        // Crear heroes, asignar sus skills y darle sus objetos iniciales.
        hero = new Character("Joker", 100, 50, 20, 15, 10, 10, Type.ICE);
        hero.addSkill(agi);
        hero.addSkill(bufu);
        hero.addSkill(dia);

        hero.addItem(templatePotion, 3);
        hero.addItem(templateSuperPotion, 2);
        hero.addItem(templateEther, 2);
        hero.addItem(templateAntidote, 2);
        hero.addItem(templateItemAgi, 1);
        hero.addItem(templateRevive, 2);
        hero.addItem(templateMaxRevive, 2);
        hero.addItem(templateProtein, 1);
        hero.addItem(templateIron, 1);
        hero.addItem(templateCalcium, 1);
        hero.addItem(templateZinc, 1);

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

                if (cursorIndexX < 0 || cursorIndexX > 4) {

                    text("> ", 180 + 150 * cursorIndexX, 550 + 50 * cursorIndexY);

                } else {

                    text("> ", 80 + 200 * cursorIndexX, 550 + 50 * cursorIndexY);

                }
                if (status == GameStatus.BATTLE) {

                    text("Ataque", 100, 550);
                    text("Skills", 300, 550);
                    text("Objetos", 500, 550);

                } else if (status == GameStatus.SKILL_MENU) {

                    int xAddition = 0;

                    int y = 550;

                    for (int i = 0; i < hero.getSkills().size(); i++) {

                        if (i % 5 == 0 && i != 0) {

                            y += 50;
                            xAddition = 0;

                        }

                        text(hero.getSkills().get(i).getName() + " (" + hero.getSkills().get(i).getCost() + " SP)", 100 + 200 * xAddition, y);

                        xAddition++;

                    }

                    text("Cancelar", 50, 650);

                } else if (status == GameStatus.ITEM_MENU) {

                    int xAddition = 0;

                    int y = 550;

                    maxPages = hero.getItems().size() / ITEMS_PER_PAGE;

                    for (int i = 0 + (10 * page); i < hero.getItems().size() && i < 10 + (10 * page); i++) {

                        if (i % 5 == 0 && i != 0 + (10 * page)) {

                            y += 50;
                            xAddition = 0;

                        }

                        text(hero.getItems().get(i).getName() + " ( x" + hero.getItems().get(i).getAmount() + ")", 100 + 200 * xAddition, y);

                        xAddition++;

                    }

                    text("Cancelar", 50, 650);
                    text("Pág. Anterior", 950, 650);
                    text("Pág. Siguiente", 1100, 650);

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

                    if (hero.hasStatusBuff()) { // Revisa si hay algún buffeo y luego revisa cuál buffeo tiene

                        hero.updateStatusUpgradeTurns();

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

        if (c.hasStatusBuff()) { // Revisa si hay algún buffeo y luego revisa cuál buffeo tiene

            textSize(15);
            textAlign(CENTER);

            if (c.getStrengthUpgrade() > 0) {
                fill(255, 50, 50);
                text("S", x - 25, y + 115);
            }

            if (c.getDefenseUpgrade() > 0) {
                fill(50, 50, 255);
                text("D", x - 10, y + 115);
            }

            if (c.getMagicUpgrade() > 0) {
                fill(50, 255, 50);
                text("M", x + 5, y + 115);

            }

            if (c.getMagicDefenseUpgrade() > 0) {
                fill(50, 200, 200);
                text("MD", x + 25, y + 115);

            }

        }

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

            if (key == ' ' || key == ENTER) {

                if (cursorIndexX == 0) {
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

                    cursorIndexX = 0;
                    cursorIndexY = 0;

                } else if (cursorIndexX == 1) {
                    println("Presionaste 2: Magia");

                    if (!hero.getSkills().isEmpty()) {

                        status = GameStatus.SKILL_MENU;

                    } else {

                        System.out.println("No tienes habilidades");

                    }

                    cursorIndexX = 0;
                    cursorIndexY = 0;

                } else if (cursorIndexX == 2) {
                    println("Presionaste 3: Objetos");

                    if (!hero.getItems().isEmpty()) {

                        status = GameStatus.ITEM_MENU;

                    } else {

                        System.out.println("No tienes objetos");

                    }

                    cursorIndexX = 0;
                    cursorIndexY = 0;
                }

            } else if (key == LEFT || key == 'a') {

                cursorIndexX -= 1;

                if (cursorIndexX < 0) {

                    cursorIndexX = 0;

                }

            } else if (key == RIGHT || key == 'd') {

                cursorIndexX += 1;

                if (cursorIndexX > 2) {

                    cursorIndexX = 2;

                }

            }

        } else if (status == GameStatus.SKILL_MENU) { // El jugador está en el menu de mágia

            if (key == ' ' || key == ENTER) {

                for (int i = 0; i < hero.getSkills().size(); i++) {

                    if (cursorIndexX + 5 * cursorIndexY == i) {

                        hero.castSkill(hero.getSkills().get(i), enemy);

                        if (!enemy.isAlive()) {

                            int xpGained = enemy.getExpReward();
                            System.out.println("¡Has ganado " + xpGained + " XP!");

                            hero.gainExp(xpGained);

                            status = GameStatus.VICTORY;

                            cursorIndexX = 0;
                            cursorIndexY = 0;

                        } else {

                            playerTurn = false;
                            temp = 60;

                            status = GameStatus.BATTLE;

                            cursorIndexX = 0;
                            cursorIndexY = 0;

                        }

                    } else if (cursorIndexY == 2 && cursorIndexX == -1) {

                        status = GameStatus.BATTLE;

                        cursorIndexX = 0;
                        cursorIndexY = 0;

                    }

                }

            } else if (key == UP || key == 'w') {

                cursorIndexY -= 1;

                if (cursorIndexY < 0) {

                    cursorIndexY = 0;

                } else if (cursorIndexX <= -1) {

                    cursorIndexX = 0;

                }

            } else if (key == DOWN || key == 's') {

                cursorIndexY += 1;

                if (cursorIndexY >= 2) {

                    if (cursorIndexY == 2) {

                        cursorIndexX = -1;

                    }

                    cursorIndexY = 2;

                }

            } else if (key == LEFT || key == 'a') {

                cursorIndexX -= 1;

                if (cursorIndexX <= -1) {

                    if (cursorIndexX == -1) {

                        cursorIndexY = 2;

                    } else {

                        cursorIndexX = -1;

                    }

                } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                    cursorIndexY = 1;

                }

            } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                cursorIndexY = 1;

            } else if (key == RIGHT || key == 'd') {

                cursorIndexX += 1;

                if (cursorIndexX > 4) {

                    cursorIndexX = 4;

                } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                    cursorIndexY = 1;

                }

            }

        } else if (status == GameStatus.ITEM_MENU) { // El jugador está en el menu de mágia

            if (key == ' ' || key == ENTER) {

                for (int i = 0; i < hero.getItems().size(); i++) {

                    if (cursorIndexY == 2 && cursorIndexX == 5) { // Pagina anterior

                        page -= 1;

                        if (page < 0) {

                            page = 0;

                        }

                    } else if (cursorIndexY == 2 && cursorIndexX == 6) { // Pagina siguiente

                        page += 1;

                        if (page > maxPages) {

                            page = maxPages;

                        }

                    } else if (cursorIndexY == 2 && cursorIndexX == -1) { // Cancelar

                        status = GameStatus.BATTLE;

                        cursorIndexX = 0;
                        cursorIndexY = 0;
                        page = 0;

                    } else if (cursorIndexX + (5 * cursorIndexY) + (10 * page) == i) { // Objeto seleccionado

                        if (!hero.getItems().get(i).getOffensive()) {

                            hero.useItem(hero.getItems().get(i), hero);

                        } else { // Objetos ofensivos

                            hero.useItem(hero.getItems().get(i), enemy);
                        }

                        if (!enemy.isAlive()) {

                            int xpGained = enemy.getExpReward();
                            System.out.println("¡Has ganado " + xpGained + " XP!");

                            hero.gainExp(xpGained);

                            status = GameStatus.VICTORY;

                            cursorIndexX = 0;
                            cursorIndexY = 0;
                            page = 0;

                        } else {

                            playerTurn = false;
                            temp = 60;

                            status = GameStatus.BATTLE;

                            cursorIndexX = 0;
                            cursorIndexY = 0;
                            page = 0;

                        }

                    }
                }

            } else if (key == UP || key == 'w') {

                cursorIndexY -= 1;

                if (cursorIndexY < 0) {

                    cursorIndexY = 0;

                } else if (cursorIndexX <= -1) {

                    cursorIndexX = 0;

                } else if (cursorIndexX > 4) {

                    cursorIndexX = 4;

                }

            } else if (key == DOWN || key == 's') {

                cursorIndexY += 1;

                if (cursorIndexY >= 2) {

                    if (cursorIndexY == 2 && cursorIndexX >= 0 && cursorIndexX <= 4) {

                        if (cursorIndexX < 2) {

                            cursorIndexX = -1;

                        } else {

                            cursorIndexX = 5;

                        }

                    }

                    cursorIndexY = 2;

                }

            } else if (key == LEFT || key == 'a') {

                cursorIndexX -= 1;

                if (cursorIndexX <= -1) {

                    if (cursorIndexX == -1) {

                        cursorIndexY = 2;

                    } else {

                        cursorIndexX = -1;

                    }

                } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                    cursorIndexY = 1;

                }

            } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                cursorIndexY = 1;

            } else if (key == RIGHT || key == 'd') {

                cursorIndexX += 1;

                if (cursorIndexX >= 5) {

                    if (cursorIndexX > 6) {

                        cursorIndexX = 6;

                    } else {

                        cursorIndexY = 2;

                    }

                } else if (cursorIndexX >= 0 && cursorIndexX <= 4 && cursorIndexY >= 2) {

                    cursorIndexY = 1;

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
