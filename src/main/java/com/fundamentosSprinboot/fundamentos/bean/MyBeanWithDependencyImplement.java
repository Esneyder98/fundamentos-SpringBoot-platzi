package com.fundamentosSprinboot.fundamentos.bean;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{
    private MyOperation myOperation;

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        int numero =3;
        System.out.println(myOperation.sum(numero));
        System.out.println("Hola....Implementacion de un bean con dependencia");
    }
}
