public class City {
    private String name;
    private int x;
    private int y;

    private double path;










    public City(String name, int x, int y ) {//I opened a class whis takes city names and their x and y coordinates.
        this.name = name;
        this.x = x;
        this.y = y;


    }




    public String getName() {//I accessed city names
        return name;
    }

    public int getX() {//I accessed x coordinate of city
        return x;
    }

    public int getY() {//I accessed y coordinate of city
        return y;
    }







    public double distanceTo(City other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}