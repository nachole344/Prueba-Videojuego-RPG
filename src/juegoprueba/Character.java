package juegoprueba;

import java.util.ArrayList;

public class Character {

    // Attributes
    private String name;

    private int maxHp;
    private int currentHp;

    private int maxSp;
    private int currentSp;

    private Type weakness;

    // Stats
    private int strength;
    private int magic;
    private int defense;
    private int magicDefense;

    // Skills & Items
    private ArrayList<Skill> skills;
    private ArrayList<Item> items;

    // Sistema de niveles
    private int level = 1;
    private int currentExp = 0;
    private int expToNextLevel = 100;

    // Status Effect
    private int poisonedTurnsLeft = 0;
    private int strengthUpgradeTurnsLeft = 0;
    private int defenseUpgradeTurnsLeft = 0;
    private int magicUpgradeTurnsLeft = 0;
    private int magicDefenseUpgradeTurnsLeft = 0;

    // Constructor (Nombre, Vida máxima, SP máximos, Fuerza, Magia, Defensa, Defensa Mágica y Debilidad)
    public Character(String name, int maxHp, int maxSp, int strength, int magic, int defense, int magicDefense, Type weakness) {
        this.name = name;

        this.maxHp = maxHp;
        this.currentHp = maxHp; // Empieza con toda la vida

        this.maxSp = maxSp;
        this.currentSp = maxSp; // Empieza con todos los SP

        this.weakness = weakness;

        this.strength = strength;
        this.magic = magic;
        this.defense = defense;
        this.magicDefense = magicDefense;

        this.skills = new ArrayList<>();
        this.items = new ArrayList<>();

    }

    // Getters para usos posteriores
    public String getName() {
        return this.name;
    }

    public int getCurrentHP() {
        return this.currentHp;
    }

    public int getCurrentSP() {
        return this.currentSp;
    }

    public int getMaxHP() {
        return this.maxHp;
    }

    public int getMaxSP() {
        return this.maxSp;
    }

    public Type getWeakness() {
        return this.weakness;
    }

    public int getStrength() {
        if (this.strengthUpgradeTurnsLeft > 0) {
            return (int) (this.strength * 1.5);
        }
        return this.strength;
    }

    public int getMagic() {
        if (this.magicUpgradeTurnsLeft > 0) {
            return (int) (this.magic * 1.5);
        }
        return this.magic;
    }

    public int getDefense() {
        if (this.defenseUpgradeTurnsLeft > 0) {
            return (int) (this.defense * 1.5);
        }
        return this.defense;
    }

    public int getMagicDefense() {
        if (this.magicDefenseUpgradeTurnsLeft > 0) {
            return (int) (this.magicDefense * 1.5);
        }
        return this.magicDefense;
    }

    public ArrayList<Skill> getSkills() {
        return this.skills;
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public int getLevel() {
        return this.level;
    }

    public boolean isPoisoned() {
        return this.poisonedTurnsLeft > 0;
    }

    public boolean hasStatusBuff() {

        if (this.getStrengthUpgrade() > 0) {
            return true;
        }

        if (this.getDefenseUpgrade() > 0) {
            return true;

        }

        if (this.getMagicUpgrade() > 0) {
            return true;

        }

        if (this.getMagicDefenseUpgrade() > 0) {
            return true;

        }

        return false;
    }

    public int getStrengthUpgrade() {
        return this.strengthUpgradeTurnsLeft;
    }

    public int getDefenseUpgrade() {
        return this.defenseUpgradeTurnsLeft;
    }

    public int getMagicUpgrade() {
        return this.magicUpgradeTurnsLeft;
    }

    public int getMagicDefenseUpgrade() {
        return this.magicDefenseUpgradeTurnsLeft;
    }

    // Methods
    public void restPoisonTurn(int amount) {
        this.poisonedTurnsLeft -= amount;

        if (this.poisonedTurnsLeft <= 0) {
            this.poisonedTurnsLeft = 0;

            System.out.println(this.name + " se recuperó del veneno.");

        }
    }

    public void updateStatusUpgradeTurns() {
        if (this.strengthUpgradeTurnsLeft > 0) {
            this.strengthUpgradeTurnsLeft--;
            if (this.strengthUpgradeTurnsLeft == 0) {
                System.out.println("El ataque de " + this.name + " volvió a la normalidad.");
            }

        }

        if (this.defenseUpgradeTurnsLeft > 0) {
            this.defenseUpgradeTurnsLeft--;
            if (this.defenseUpgradeTurnsLeft == 0) {
                System.out.println("La defensa de " + this.name + " volvió a la normalidad.");
            }

        }

        if (this.magicUpgradeTurnsLeft > 0) {
            this.magicUpgradeTurnsLeft--;
            if (this.magicUpgradeTurnsLeft == 0) {
                System.out.println("La magia de " + this.name + " volvió a la normalidad.");
            }

        }

        if (this.magicDefenseUpgradeTurnsLeft > 0) {
            this.magicDefenseUpgradeTurnsLeft--;
            if (this.magicDefenseUpgradeTurnsLeft == 0) {
                System.out.println("La defensa mágica de " + this.name + " volvió a la normalidad.");
            }

        }
    }

    public void addSkill(Skill newSkill) {
        // Verificar si el personaje ya sabe la habilidad
        for (Skill s : this.skills) {
            if (s.getName().equals(newSkill.getName())) {
                System.out.println(this.name + " ya sabe " + newSkill.getName() + "!");
                return;
            }
        }

        // Una vez se verifica que el personajo no sabe la habilidad, se la agregamos
        this.skills.add(newSkill);
        System.out.println(this.name + " aprendió " + newSkill.getName() + "!");

    }

    public void removeSkill(Skill skillToRemove) {
        // Verificar si el personaje ya sabe la habilidad
        for (Skill s : this.skills) {
            if (s.getName().equals(skillToRemove.getName())) {
                this.skills.remove(s);
                System.out.println(this.name + " ya no puede usar " + s.getName());
                return;
            }
        }

        // Si el personaje no sabe la habilidad, no se la podemos eliminar
        System.out.println(this.name + " no conoce la habilidad " + skillToRemove.getName() + ", no se puede borrar.");

    }

    public void addItem(Item itemTemplate, int amount) {

        for (Item i : this.items) {
            if (i.getName().equals(itemTemplate.getName())) {

                i.addAmount(amount);
                System.out.println(this.name + " guardó " + itemTemplate.getName() + " (Total: " + i.getAmount() + ")");
                return;
            }

        }

        Item newItem = new Item(itemTemplate.getName(), itemTemplate.getType(), itemTemplate.getPower(), amount, itemTemplate.getOffensive());

        this.items.add(newItem);
        System.out.println(this.name + " consiguió " + itemTemplate.getName() + " (x" + itemTemplate.getAmount() + ") !");
    }

    public boolean consumeItem(Item itemTemplate) {

        for (Item i : this.items) {
            if (i.getName().equals(itemTemplate.getName())) {

                // Restamos 1 unidad
                i.decreaseAmount(1);

                // Si se acaban, lo borramos de la lista
                if (i.getAmount() <= 0) {
                    this.items.remove(i);
                    System.out.println("¡Se acabaron los " + itemTemplate.getName() + "!");
                }
                return true;
            }
        }

        System.out.println("No tienes " + itemTemplate.getName());
        return false; // Fallo
    }

    public void useItem(Item item, Character target) {

        if (this.consumeItem(item)) {

            System.out.println(this.name + " usa " + item.getName());

            if (item.getType() == ItemType.HEAL) {

                target.heal(item.getPower());

            } else if (item.getType() == ItemType.ETHER) {

                target.restoreSP(item.getPower());

            } else if (item.getType() == ItemType.REVIVE) {

                if (!target.isAlive()) {

                    target.heal(target.getMaxHP() / 2);

                } else { // Si no está muerto, se le curará un cuarto de vida

                    target.heal(target.getMaxHP() / 4);

                }

            } else if (item.getType() == ItemType.MAX_REVIVE) {

                if (!target.isAlive()) {

                    target.heal(target.getMaxHP());

                } else { // Si no está muerto, se le mitad

                    target.heal(target.getMaxHP() / 2);

                }

            } else if (item.getType() == ItemType.POISON_HEAL) {

                target.restPoisonTurn(item.getPower());

            } else if (item.getType() == ItemType.STRENGHT_UPGRADE) {

                target.strengthUpgradeTurnsLeft = item.getPower();

            } else if (item.getType() == ItemType.DEFENSE_UPGRADE) {

                target.defenseUpgradeTurnsLeft = item.getPower();

            } else if (item.getType() == ItemType.MAGIC_UPGRADE) {

                target.magicUpgradeTurnsLeft = item.getPower();

            } else if (item.getType() == ItemType.MAGIC_DEFENSE_UPGRADE) {

                target.magicDefenseUpgradeTurnsLeft = item.getPower();

            } else { // Objeto de ataque

                this.itemDamage(item, target);

            }

        }

    }

    public void showStatus() {
        System.out.println("---------------------------");
        System.out.println("Name: " + this.name);
        System.out.println("HP: " + this.currentHp + "/" + this.maxHp);
        System.out.println("SP: " + this.currentSp + "/" + this.maxSp);
        System.out.println("---------------------------");
    }

    public void receiveDamage(int damage) {

        this.currentHp -= damage;

        // Seguro para que la vida no sea negativa
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }

        System.out.println(this.name + " recibió " + damage + " de daño!");
    }

    private int calculateCritical(int damage) {

        // Probabilidad de crítico 5%
        if (((int) (Math.random() * 100) < 5)) {
            System.out.println("¡CRÍTICO!");

            damage *= 2;

        }

        return damage;

    }

    public void heal(int heal) {

        this.currentHp += heal;

        // Seguro para que no supere su vida máxima
        if (this.currentHp > this.maxHp) {
            this.currentHp = this.maxHp;

        }

        if (this.currentHp == this.maxHp) {
            System.out.println(this.name + " recuperó toda su vida!");

        } else {

            System.out.println(this.name + " recuperó " + heal + " puntos de vida!");
        }

    }

    public void restoreSP(int restoredSP) {
        this.currentSp += restoredSP;

        // Seguro para que no se superen los SP máximos
        if (this.currentSp > this.maxSp) {
            this.currentSp = this.maxSp;

        }

        if (this.currentSp == this.maxSp) {

            System.out.println(this.name + " recuperó todos sus SP");

        } else {

            System.out.println(this.name + " recuperó " + restoredSP + " puntos de SP!");

        }

    }

    private void poisonChance() {

        if ((int) (Math.random() * 100) < 50) {

            System.out.println(this.name + " ha sido envenenado!");
            this.poisonedTurnsLeft = 3;

        }
    }

    public void attack(Character target) {
        System.out.println(this.name + " ataca a " + target.name + "!");

        int damageDealt = this.getStrength() - target.getDefense();

        // Seguro de daño mínimo
        if (damageDealt <= 0) {
            damageDealt = 1;
        }

        if (target.weakness == Type.PHYSICAL) {

            System.out.println("WEAKNESS STRUCK! >>> ONE MORE! <<<");

            damageDealt *= 2;

        }

        calculateCritical(damageDealt);

        target.receiveDamage(damageDealt);

    }

    public void castSkill(Skill skill, Character target) {

        // Si no se tienen los suficientes SP, no se puede hacer el ataque
        if (this.currentSp < skill.getCost()) {
            System.out.println(this.name + " no tiene suficiente SP!");
            return;

        }

        this.currentSp -= skill.getCost(); // Resta el costo de la habilidad a los SP actuales

        if (skill.getType() == Type.HEAL) { // Si skill es de curación, no deberia hacer daño

            int heal = skill.getPower();

            this.heal(heal); // Temporalmente solo se curará a si mismo, luego se añadirá la opción de curar a otro

        } else { // Si la skill no cura, hace daño

            // Calculo del daño (Magia del usuario + poder de la habilidad) - defensa magica del oponente
            int damage = (this.getMagic() + skill.getPower()) - target.getMagicDefense();

            // Seguro de daño mínimo
            if (damage <= 0) {
                damage = 1;

            }

            // Verificacion de las debilidades
            if (skill.getType() == target.weakness) {

                System.out.println("WEAKNESS STRUCK! >>> ONE MORE! <<<");

                damage *= 2;

            }

            calculateCritical(damage);

            target.receiveDamage(damage);

            if (skill.getType() == Type.POISON && !(target.poisonedTurnsLeft > 0)) { // Si está envenenado no puede volver a ser envenenado
                target.poisonChance();

            }

        }

    }

    public void itemDamage(Item item, Character target) {

        int damage = item.getPower();

        Type element;

        if (item.getType() == ItemType.ATTACK) {

            damage -= target.getDefense();

            // Seguro de daño mínimo
            if (damage <= 0) {
                damage = 1;

            }

        } else {

            damage -= target.getDefense();

            // Seguro de daño mínimo
            if (damage <= 0) {
                damage = 1;

            }
        }

        if (item.getType() == ItemType.ATTACK) {

            element = Type.PHYSICAL;

        } else if (item.getType() == ItemType.FIRE_ATTACK) {

            element = Type.FIRE;

        } else if (item.getType() == ItemType.ICE_ATTACK) {

            element = Type.ICE;

        } else if (item.getType() == ItemType.ELECTRIC_ATTACK) {

            element = Type.ELECTRIC;

        } else if (item.getType() == ItemType.POISON_ATTACK) {

            element = Type.POISON;

        } else if (item.getType() == ItemType.WIND_ATTACK) {

            element = Type.WIND;

        } else {

            // En caso de haberse colado un item que no haya sido de ataque se usa un seguro
            System.out.println("ERROR. Este no es un objeto de ataque");
            return;

        }

        // Verificacion de las debilidades
        if (element == target.weakness) {

            System.out.println("WEAKNESS STRUCK! >>> ONE MORE! <<<");

            damage *= 2;

        }

        calculateCritical(damage);

        target.receiveDamage(damage);

        if (element == Type.POISON && !(target.poisonedTurnsLeft > 0)) { // Si está envenenado no puede volver a ser envenenado
            target.poisonChance();

        }

    }

    public void levelUp() {

        this.expToNextLevel *= 1.5;

        this.level++;

        this.maxHp += 25;
        this.maxSp += 25;

        this.strength += 25;
        this.magic += 25;
        this.defense += 25;
        this.magicDefense += 25;

        this.heal(this.maxHp);
        this.restoreSP(this.maxSp);

        System.out.println(this.name + " subió a nivel " + this.level);

    }

    public int getExpReward() {

        return 50 + (this.level * 10);

    }

    public void gainExp(int amount) {

        this.currentExp += amount;

        // Sube de nivel
        while (this.currentExp >= this.expToNextLevel) {
            this.currentExp -= this.expToNextLevel;
            this.levelUp();

        }

    }

    public void addLevel(int amount) {

        for (int i = 0; i < amount; i++) {

            this.levelUp();

        }

    }

    public boolean isAlive() {

        return this.currentHp > 0;

    }

}
