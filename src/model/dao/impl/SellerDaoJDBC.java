package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	
	private Connection con;
	
	 public SellerDaoJDBC(Connection con) {
		this.con = con; 
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findyById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(
					"SELECT seller.*,departamento.Nome as DepName\r\n" + 
					"FROM seller INNER JOIN departamento\r\n" + 
					"ON seller.DepartmentId = departamento.Id\r\n" + 
					"WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("DepartmentId"));
				dep.setName(rs.getString("DepName"));
				Seller obj = new Seller();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getNString("Nome"));
				obj.setEmail(rs.getNString("Email"));
				obj.setBaseSalary(rs.getDouble("BaseSalary"));
				obj.setBirthyDate(rs.getDate("Data_de_nascimento"));	
				obj.setDepartment(dep);
				return	obj;
			}
			return null;
		}
		catch(SQLException s) {
			throw new DbException(s.getMessage());
		}
		finally {
			DB.closeStatment(st);
			DB.closeReultset(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
