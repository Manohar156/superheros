package models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter@Setter
public class SuperHeros {
 private String name;
 private int id;
    private String superName;
    private String profession;
    private long age;
    private Boolean canFly;
    public SuperHeros(){

    }

    public SuperHeros(String name, String superName, String profession, long age, Boolean canFly) {
        this.name = name;
        this.superName = superName;
        this.profession = profession;
        this.age = age;
        this.canFly = canFly;
    }
}
