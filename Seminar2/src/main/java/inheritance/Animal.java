package inheritance;

public class Animal {
    String name;
    int age;

    void sound(){
        System.out.println("Animal makes a sound");
    }
    public Animal(){};
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Dog extends Animal implements Ibehaviour{

    public Dog(){
        super();
    }
    public Dog(String name, int age){
        super(name,age);
    }
    void sound(){
        System.out.println("Dog barks");
        super.sound();
    }

    @Override
    public void breath() {
        System.out.println();
    }
}

class Cat extends Animal{
    void sound(){
        System.out.println("Cat makes meow");
    }
}

