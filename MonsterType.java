public enum MonsterType { //tri par points rapporter
    BASIC(1, 1, 1.0, 5, "/resources/monsterGre.png"),
    HIT(1, 3, 1.0, 6, "/resources/monsterRed.png"),
    FAST(1, 1, 2.2, 6, "/resources/monsterYel.png"), 
    STRANGE(2, 2, 1.3, 6, "/resources/monsterCya.png"),
    TANK(4, 1, 0.4, 6, "/resources/monsterBlu.png"),
    LOVE(2, 2, 1.6, 7, "/resources/monsterPin.png"),
    PERFECT(3, 3, 1.9, 8, "/resources/monsterPur.png"),
    BOSS(5, 4, 2.3, 10, "/resources/monsterBla.png");
    

    public final int health;
    public final int damage;
    public final double speed;
    public final int points; 
    public final String spritePath;

    MonsterType(int health, int damage, double speed, int points, String spritePath) {
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.points = points;
        this.spritePath = spritePath;
    }
}