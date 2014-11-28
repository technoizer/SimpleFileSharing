
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Muhammad Izzuddin
 */
public class ListString implements Serializable{
    private ArrayList <String> names = new ArrayList<>();

    /**
     * @return the names
     */
    public ArrayList <String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(ArrayList <String> names) {
        this.names = names;
    }       
}
