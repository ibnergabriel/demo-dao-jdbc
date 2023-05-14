package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {

        Department dp = new Department(1,"Books");

        Seller seller = new Seller
                (21,
                        "Davi",
                        "davijudo22@gmail.com",
                        new Date(),
                        2100.00,
                        new Department(99,"GPU"));

        System.out.println(seller);
    }
}
