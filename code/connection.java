public class connection {

        private String city1;
        private String city2;
        private double distance;

        public connection(String city1, String city2, double distance) {//I defined a class which contains city names and distance between them.
            this.city1 = city1;
            this.city2 = city2;
            this.distance = distance;
        }

        public String getCity1() {
            return city1;
        }

        public String getCity2() {
            return city2;
        }

        public double getDistance() {
            return distance;
        }

}
