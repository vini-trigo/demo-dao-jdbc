package app;

import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;

import model.entities.Department;
import model.entities.Seller;

public class program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Department obj = new Department(1, "books");
		
		Seller seler = new Seller(22, "Onildo", "alou", new Date(), 3000, obj);
		
		System.out.println(obj);
	}

}
