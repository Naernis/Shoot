public enum MonsterType {
    BASIC(3, 1, 1.0, "/resources/monsterGre.png"), 
    FAST(2, 1, 2.0, "/resources/monsterYel.png"), 
    TANK(5, 1, 0.8, "/resources/monsterBlu.png"),
    LOVE(4, 1, 1.5, "/resources/monsterPin.png"),
    PERFECT(5, 2, 2.0, "/resources/monsterPur.png"),
    STRANGE(2, 3, 1.0, "/resources/monsterCya.png"),
    BOSS(6, 4, 2.0, "/resources/monsterBla.png"),
    HIT(3, 2, 1.0, "/resources/monsterRed.png");

    public final int health;
    public final int damage;
    public final double speed;
    public final String spritePath;

    MonsterType(int health, int damage, double speed, String spritePath) {
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.spritePath = spritePath;
    }
}