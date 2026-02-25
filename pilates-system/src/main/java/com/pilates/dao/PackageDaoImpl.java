package com.pilates.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.pilates.model.Package;
import com.pilates.util.Tool;

public class PackageDaoImpl  implements PackageDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Package> findAll() throws Exception {
		// TODO Auto-generated method stub
		

	        List<Package> list = new ArrayList<>();

	        String sql = "SELECT * FROM packages";

	        try (Connection conn = Tool.getDb();
	             PreparedStatement ps = conn.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {


	            while (rs.next()) {

	                Package p = new Package(
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getInt("total_points"),
	                        rs.getInt("valid_days"),
	                        rs.getInt("price")
	                );

	                list.add(p);
	            }
	        }

	        return list;
	    }

}
