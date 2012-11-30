package model;

import java.io.Serializable;

public class Ort implements Serializable {

    private String plz;
    private String name;

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
