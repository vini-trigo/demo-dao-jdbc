package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(
					"INSERT INTO seller"
					+"(Nome, Email, Data_de_nascimento, BaseSalary, DepartmentId)"
					+"VALUES"
					+"(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setNString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthyDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int ra = st.executeUpdate();
			
			if(ra > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeReultset(rs);
			}
			else {
				throw new DbException("Erro inesperado, nenhuma linha afetado");
			}
		}
		catch(SQLException s) {
			throw new DbException(s.getMessage());
		}
		finally {
			DB.closeStatment(st);
		}
		
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(
					"UPDATE seller\r\n" + 
					"SET Nome = ?, Email = ?, Data_de_nascimento = ?, BaseSalary = ?, DepartmentId = ?\r\n" + 
					"WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setNString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthyDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();
			
		}
		catch(SQLException s) {
			throw new DbException(s.getMessage());
		}
		finally {
			DB.closeStatment(st);
		}
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
				Department dep = instantiated(rs);
				Seller obj = instantiateS(rs, dep);
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

	private Seller instantiateS(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getNString("Nome"));
		obj.setEmail(rs.getNString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthyDate(rs.getDate("Data_de_nascimento"));	
		obj.setDepartment(dep);
		return obj;
	}

	private Department instantiated(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(
					"SELECT seller.*,departamento.Nome as DepName\r\n" + 
					"FROM seller INNER JOIN departamento\r\n" + 
					"ON seller.DepartmentId = departamento.Id\r\n" + 
					"ORDER BY Nome");
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiated(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
			
				Seller obj = instantiateS(rs, dep);
				list.add(obj);
			}
			return list;
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = con.prepareStatement(
					"SELECT seller.*,departamento.Nome as DepName\r\n" + 
					"FROM seller INNER JOIN departamento\r\n" + 
					"ON seller.DepartmentId = departamento.Id\r\n" + 
					"WHERE DepartmentId = ? " + 
					"ORDER BY Nome");
			
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantiated(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
			
				Seller obj = instantiateS(rs, dep);
				list.add(obj);
			}
			return list;
		}
		catch(SQLException s) {
			throw new DbException(s.getMessage());
		}
		finally {
			DB.closeStatment(st);
			DB.closeReultset(rs);
		}
		
	}

}
