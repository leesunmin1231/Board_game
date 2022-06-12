package main_program;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class Map {
    private Vector map_data = new Vector();

    public Map() throws IOException{
        ReadMap();
    }
    public void ReadMap() throws IOException{
        Scanner scanner = new Scanner(new File("data/another.map"));
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            str = String.join("",str.split(" "));
            map_data.add(str);
        }
    }
    public Vector getMapData(){
        return map_data;
    }
}
