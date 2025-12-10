package model;

public class Student {
    private String id;
    private String name;
    private Double moyenne;
    private Formation formation;

    public Student(String id, String name, Double moyenne, Formation formation) {
        this.id = id;
        this.name = name;
        this.moyenne = moyenne;
        this.formation = formation;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getMoyenne() {
        return moyenne;
    }
    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }
    public Formation getFormation() {
        return formation;
    }
    public void setFormation(Formation formation) {
        this.formation = formation;
    }
}
