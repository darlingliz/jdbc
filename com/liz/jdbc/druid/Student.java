package org.example.jdbc.com.liz.jdbc.druid;

public class Student {
    private Integer id;
    private String name;
    public Student(){

    }
    public Student(Integer id,String name){
        this.id = id;
        this.name= name;
    }
    public void setId(Integer id){
        this.id = id ;
    }
    public Integer getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return id + " " + name;
    }

}
