public class Move {
    private String name;
    private String type;
    private int power;
    private float accuracy;
    
    //constructor
    public Move(String name, String type, int power, float accuracy) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
    }

    //getters and setters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getPower() { return power; }
    public float getAccuracy() { return accuracy; }
}