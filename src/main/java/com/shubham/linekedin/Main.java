package com.shubham.linekedin;

interface MyInterface{
    default void defaultMethod(){
        System.out.println("Hello, I'm default Method");
    }

    void abstractMethod();
}
class Main implements MyInterface{
    @Override
    public void abstractMethod() {
        System.out.println("Hello, I'm implementation of abstract method.");
    }

    public static void main(String[] args) {
        MyInterface myInterface = new Main();
        myInterface.abstractMethod();
        myInterface.defaultMethod();

        MyInterface myInterface1 = () -> System.out.println("Hello, I'm implementation of abstract method using lambda Expression.");
        myInterface1.abstractMethod();
    }
}