package ru.potatocoder228.itmo.lab6.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Класс, описывающий дракона.
 * Объекты класса являются элементами коллекции
 */

public class Dragon implements Collectionable, Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int age; //Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private Boolean speaking; //Поле может быть null
    private DragonType type; //Поле может быть null
    private DragonCave cave; //Поле не может быть null

    /**
     * Конструктор, задающий параметры для создания дракона
     *
     * @param nm          имя
     * @param age         возраст
     * @param description описание
     * @param speaking    способность говорить
     * @param type        тип дракона
     * @param cave        глубина пещеры
     */

    public Dragon(String nm, Coordinates coordinates, int age, String description, Boolean speaking, DragonType type, DragonCave cave) {
        this.name = nm;
        this.coordinates = coordinates;
        this.age = age;
        this.description = description;
        this.speaking = speaking;
        this.type = type;
        this.cave = cave;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        creationDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId() {
        id = this.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public int getAge() {
        return age;
    }
    public String getDescription(){
        return this.description;
    }

    @Override
    public String toString() {
        String strCreationDate = creationDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String s = "";
        s+="Dragon:\n";
        s += "  \"id\" : " + id + ",\n";
        s += "  \"name\" : " + "\"" + name + "\"" + ",\n";
        s += "  \"coordinates\" : " + coordinates.toString() + ",\n";
        s += "  \"creationDate\" : " + "\"" + strCreationDate + "\"" + ",\n";
        s += "  \"age\" : " + age + ",\n";
        s += "  \"coordinates\" : " + "\"" + coordinates.toString() + "\"" + ",\n";
        s += "  \"description\" : " + "\"" + description + "\"" + ",\n";
        s += "  \"speaking\" : " + "\"" + speaking.toString() + "\"" + ",\n";
        s += "  \"type\" : " + type.toString() + "\n";
        s += "  \"cave\" : " + cave.getDepth() + "\n";
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Dragon another = (Dragon) obj;
        return this.getId() == another.getId();
    }

    public int compareTo(Collectionable dragon) {
        return Long.compare(this.age, dragon.getAge());
    }

    public boolean validate() {
        return (
                coordinates != null && coordinates.validate() &&
                        age > 0 &&
                        cave != null && (id > 0) &&
                        name != null && !name.equals("") &&
                        creationDate != null
        );
    }
    public float getCave(){
        return this.cave.getDepth();
    }
    public Boolean getSpeaking(){
        return this.speaking;
    }

    public static class SortingComparator implements Comparator<Dragon> {
        public int compare(Dragon first, Dragon second) {
            int result = Double.compare(first.getCoordinates().getX(), second.getCoordinates().getX());
            if (result == 0) {
                // both X are equal -> compare Y too
                result = Double.compare(first.getCoordinates().getY(), second.getCoordinates().getY());
            }
            return result;
        }
    }
    public String[] getAllFields() {
        return new String[]{name, id.toString(), String.valueOf(coordinates.getX()),
                coordinates.getY().toString(), creationDate.format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")), Integer.toString(age), description, speaking.toString(),
                type.toString(), Float.toString(cave.getDepth())};
    }
}