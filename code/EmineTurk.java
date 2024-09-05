
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;
/**
 * @author EmineTurk student ıd:2022400228
 * @version 1.0
 * In this code I wrote an algorithm which finds shortest path between given cities according to their x and y coordinates.
 */

public class EmineTurk {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> allcities = new ArrayList<>();//I created a list that contains all cities in string format.


        int counter1=0;//I created a counter which finds number of cities.

        try (Scanner sc = new Scanner(new FileInputStream("city_coordinates.txt"))) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                counter1 += 1;

            }
        }int counter2=0;//I created a new counter which calculates number of connections between cities.
        try (Scanner sc = new Scanner(new FileInputStream("city_connections.txt"))) {
            while(sc.hasNextLine()){
                String line =sc.nextLine();
                counter2++;

            }
        }
        City[] cities = new City[counter1];//I opened an list which contains city objects.










        try (Scanner sc = new Scanner(new FileInputStream("city_coordinates.txt"))) {//I read txt file and added city name and it's x y coordinates to cities list as city objects.


            for (int i = 0; i < counter1; i++) {
                String line = sc.nextLine();
                String[] Splittedline = line.split(", ");
                cities[i] = new City((Splittedline[0]), Integer.parseInt(Splittedline[1]), Integer.parseInt(Splittedline[2]));
                int cityindex = indexcalculator(Splittedline[0]);
                allcities.add(cities[i].getName().toString());
            }
        }

        String startingcity=null;//I took input until user enter a valid city name.
        String destinationcity=null;
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter starting city:");
            startingcity = scanner.nextLine();
            startingcity = startingcity.substring(0, 1).toUpperCase() + startingcity.substring(1).toLowerCase();
            if (allcities.contains(startingcity)) {
                break;

            }else{
                System.out.println("City named "+startingcity+ " not found Please enter a valid city name.");
            }
        }

        while(true){//I made the same thing for destinatioşn city as well.
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter destination city: ");
            destinationcity = scanner.nextLine();
            destinationcity = destinationcity.substring(0, 1).toUpperCase() + destinationcity.substring(1).toLowerCase();
            if(allcities.contains(destinationcity)){
                break;
            }else{
                System.out.println("City named "+destinationcity+ " not found Please enter a valid city name.");
            }


        }




        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(1400, 700);
        StdDraw.setXscale(0, 2377);
        StdDraw.setYscale(0, 1055);
        StdDraw.picture(2377 / 2.0, 1055 / 2.0, "map.png", 2377, 1055);
        int num_cities = counter1;

        connection[] connections = new connection[counter2];


        String[][] listeall = new String[counter2][2];//I created a list which contains citi



        try (Scanner sc = new Scanner(new FileInputStream("city_coordinates.txt"))) {//I found connectioned cities from txt file then I found their x and y coordinates finally I wrote their names and draw points on x y coordinates.



            for (int i = 0; i < counter1; i++) {
                String line = sc.nextLine();
                String[] Splittedline = line.split(", ");
                cities[i] = new City((Splittedline[0]), Integer.parseInt(Splittedline[1]), Integer.parseInt(Splittedline[2]));





                int x =  cities[i].getX();
                int y =  cities[i].getY();
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.setPenRadius(0.01);
                StdDraw.point(x, y);
                StdDraw.setPenRadius(0.0001);
                String Textedcity = Splittedline[0];
                StdDraw.text(x - 9, y + 20, Textedcity);
                StdDraw.show();


            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }







        try (Scanner sc = new Scanner(new FileInputStream("city_connections.txt"))) {//I read txt file and found indexes of connectioned cities so I could draw lines represents roads between them.

            for (int b = 0; b < counter2; b++) {

                String line1 = sc.nextLine();
                String[] Splittedline1 = line1.split(",");

                String a = Splittedline1[0];
                String c = Splittedline1[1];
                listeall[b][0] = a;
                listeall[b][1] = c;


                int index1 = indexcalculator(a);
                int index2 = indexcalculator(c);
                int x1 = cities[index1].getX();
                int y1 = cities[index1].getY();
                int x2 = cities[index2].getX();
                int y2 = cities[index2].getY();
                connections[b]= new connection(Splittedline1[0],Splittedline1[1],cities[index1].distanceTo(cities[index2]));
                StdDraw.setPenColor(StdDraw.GRAY);
                StdDraw.setPenRadius(0.003);
                StdDraw.line(x1, y1, x2, y2);
                StdDraw.show();


            }
            double[] distances=new double[counter1];//I created a list which assigns a distance value to every city which represents shortest way to arrive that city.
            boolean[] comingbefore= new boolean[counter1];//I created a list which assign a value false for every city at the beginning then if city was visited I returned that value true;
            String[] lastplace= new String[counter1];//While going from shortest way this list assigns to every city a city which comes one before them.
            List<List<String>> allcitiesneighbors = new ArrayList<>();//This list contains every cities all neigbors according to indexes in cities list.

            for (int z=0;z<cities.length;z++){
                List<String> onecitiesneighbors = new ArrayList<>();//I created a list which takes one city's neighbor then I added it allcitiesneighborlist to access all of them.
                for(int k=0;k<counter2;k++){
                    if (connections[k].getCity1().equals(cities[z].getName())){
                        onecitiesneighbors.add(connections[k].getCity2());
                    }else if(connections[k].getCity2().equals(cities[z].getName())){
                        onecitiesneighbors.add(connections[k].getCity1());
                    }
                }
                allcitiesneighbors.add(onecitiesneighbors);



            }





            for(int i=0;i<cities.length;i++){
                distances[i]= Double.MAX_VALUE;// I initialized all city's distances to infinity.
                comingbefore[i]=false;//I initialized all city's to false
                lastplace[i]=null;//I have started all cities last place as null


            }int indexstartingcity=indexcalculator(startingcity);
            distances[indexstartingcity]=0;//I initialized starting city's index to zero.




            for(int i =0;i<cities.length;i++) {
                int minimumindex = -1;
                double minDistance = Double.MAX_VALUE;

                for (int j = 0; j < cities.length; j++) {//If a city was not being visited before and its distance less then mindistance , I equalized index to this city's index to return it's visited value to true.I also equalized min distance to its distance.
                    if (!comingbefore[j] && distances[j] < minDistance) {
                        minimumindex = j;
                        minDistance = distances[j];

                    }
                }
                if (minimumindex == -1) {//if there is no city anymore break the loop.

                    break;
                }
                comingbefore[minimumindex] = true;




                for (String connection : allcitiesneighbors.get( minimumindex)){//I found current city's neighbors and summed up current cities distance value and distance between current and neighbor.
                    int indexofneighbor=indexcalculator(connection);
                    double lastdistance = distances[ minimumindex] +  cities[minimumindex].distanceTo(cities[indexofneighbor]);
                    if(lastdistance<distances[indexofneighbor]){//if final distance less than distance I found before I equalized distance to new distance I found.
                        distances[indexofneighbor]=lastdistance;
                        lastplace[indexofneighbor]=cities[minimumindex].getName();//I assigned lastplace to that neighbor for this city.
                    }


                }


            }
            List<City> Sensiblepath = new ArrayList<>();//I created a list to write my path's cities in it.
            String citycurrent = destinationcity;
            while(citycurrent != null){//I started from destination city and went to starting city until city current is null and added all of them to sensiblepath.
                Sensiblepath.add(cities[indexcalculator(citycurrent)]);
                citycurrent = lastplace[indexcalculator(citycurrent)];

            }
            Collections.reverse(Sensiblepath);//To start from starting city I reversed list.




            int p = 0;//I drew blue lines between cities which are at shortest path.And I wrote their names with blue color.
            while (p < Sensiblepath.size() - 1) {
                City cityfirst = Sensiblepath.get(p);
                City citysecond = Sensiblepath.get(p + 1);
                StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                StdDraw.setPenRadius(0.01);
                StdDraw.line(cityfirst.getX(), cityfirst.getY(), citysecond.getX(), citysecond.getY());
                StdDraw.text(cityfirst.getX()-9, cityfirst.getY()+20, cityfirst.getName());
                p++;
            }



            StdDraw.text(Sensiblepath.getLast().getX()-9, Sensiblepath.getLast().getY()+20, Sensiblepath.getLast().getName());//I wrote destination city's name in blue color as well.
            StdDraw.show();
            int indexdestinationcity=indexcalculator(destinationcity);
            double wholepath=distances[indexdestinationcity];//I found whole path by looking at destination city's distance value.

            if(Sensiblepath.size()==1&& !startingcity.equals(destinationcity)){//If there is just one city in list I said no path could be found.
                System.out.print("No path could be found.");

            }
            else{
                System.out.printf("Total Distance: %.2f Path: ",wholepath);//If there are more than one city I said there is a path and I wrote city's path.

                for (int i = 0; i < Sensiblepath.size()-1 ; i++) {
                    System.out.print(Sensiblepath.get(i).getName()+"->");




                }
                int indexlastcity=Sensiblepath.size()-1;

                System.out.print(Sensiblepath.get(indexlastcity).getName());//I wrote last city's name to the console output.
            }





























        }







    }













    public static int indexcalculator(String city) throws FileNotFoundException {//I made an indexcalculator which calculates city indexes int cities list.
        int num_cities = 81;
        City[] cities = new City[num_cities];


        try (Scanner sc = new Scanner(new FileInputStream("city_coordinates.txt"))) {

            for (int i = 0; i < 81; i++) {
                String line = sc.nextLine();
                String[] Splittedline = line.split(", ");
                cities[i] = new City((Splittedline[0]), Integer.parseInt(Splittedline[1]), Integer.parseInt(Splittedline[2]));
            }
        }
        int index = 0;
        for (int d = 0; d < 81; d++) {
            String a = cities[d].getName();
            if (Objects.equals(a, city)) {
                index += d;


            }
        }


        return index;
    }







}