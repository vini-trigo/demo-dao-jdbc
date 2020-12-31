package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class program {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		
		SimpleDateFormat sdf = new SimpleDateFormat(("dd/MM/yyyy HH:mm:ss"));
		Date y2 = sdf.parse("25/06/2001 16:47:40");
		
		SellerDao dao = DaoFactory.createSellerDao();
		
		System.out.println("=== TEST 1: seller findById ===");
		Seller seler = dao.findyById(2);
		System.out.println(seler);
		
		System.out.println("\n=== TEST 2: seller findByDepartment ===");
		Department department = new Department(2, null);
		List<Seller> list = dao.findByDepartment(department);
		
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TEST 3: seller findAll ===");
		List<Seller> lit = dao.findAll();
		
		for(Seller bj : lit) {
			System.out.println(bj);
		}
		
		System.out.println("\n=== TEST 4: seller insert ===");
		Seller seller = new Seller(null, "George", "george@gmail.com", y2, 4000.0, department);
		dao.insert(seller);
		System.out.println("Insret: " + seller.getId());
		
		System.out.println("\n=== TEST 5: seller update ===");
		seler = dao.findyById(1);
		seler.setName("Alou som");
		dao.update(seler);
		System.out.println("Updatezado");

	}

}
