package com.pilates.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pilates.model.Member;
import com.pilates.util.Tool;

public class MemberDaoImpl implements MemberDao{

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	    
	}

	@Override
	public void insert(Member member) throws Exception {
		// TODO Auto-generated method stub
		
		 try (Connection conn = Tool.getDb()) {

		        // =========================
		        // ① 產生 member_no
		        // =========================
		        String generateSql =
		            "SELECT CONCAT('M', LPAD(IFNULL(MAX(id),0)+1, 4, '0')) FROM members";

		        String memberNo = null;

		        try (PreparedStatement psGen = conn.prepareStatement(generateSql);
		             ResultSet rsGen = psGen.executeQuery()) {

		            if (rsGen.next()) {
		                memberNo = rsGen.getString(1);
		            }
		        }

		        member.setMemberNo(memberNo);
		 
		
		
		
		
		
		        // =========================
		        // ② INSERT
		        // =========================
		        String sql = "INSERT INTO members " +
		                "(member_no, name, phone, email, password, status, point_balance, created_at) " +
		                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		        try (PreparedStatement ps = conn.prepareStatement(sql)) {

		            ps.setString(1, member.getMemberNo());
		            ps.setString(2, member.getName());
		            ps.setString(3, member.getPhone());
		            ps.setString(4, member.getEmail());
		            ps.setString(5, member.getPassword());
		            ps.setString(6, member.getStatus());
		            ps.setInt(7, member.getPointBalance());
		            ps.setTimestamp(8, java.sql.Timestamp.valueOf(member.getCreatedAt()));

		            ps.executeUpdate();

		            System.out.println("✅ 會員新增成功");
		        }
		    }
		
	}

	@Override
	public Member findById(int id) throws Exception {
		// TODO Auto-generated method stub
		 String sql = "SELECT * FROM members WHERE id = ?";

		    try (Connection conn = Tool.getDb();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

		        // 把 id 塞進 SQL
		        ps.setInt(1, id);

		        // 執行查詢
		        ResultSet rs = ps.executeQuery();

		        // 如果有資料
		        if (rs.next()) {

		            Member member = new Member();

		            member.setId(rs.getInt("id"));
		            member.setMemberNo(rs.getString("member_no"));
		            member.setName(rs.getString("name"));
		            member.setPhone(rs.getString("phone"));
		            member.setEmail(rs.getString("email"));
		            member.setStatus(rs.getString("status"));
		            member.setPointBalance(rs.getInt("point_balance"));

		            return member;
		        }
		    }

		    return null; // 查不到
	}

	@Override
	public List<Member> findAll() throws Exception {
		// TODO Auto-generated method stub
		  String sql = "SELECT * FROM members";

		    List<Member> list = new ArrayList<>();

		    try (Connection conn = Tool.getDb();
		         PreparedStatement ps = conn.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {

		        while (rs.next()) {

		            Member member = new Member();

		            member.setId(rs.getInt("id"));
		            member.setMemberNo(rs.getString("member_no"));
		            member.setName(rs.getString("name"));
		            member.setPhone(rs.getString("phone"));
		            member.setEmail(rs.getString("email"));
		            member.setStatus(rs.getString("status"));
		            member.setPointBalance(rs.getInt("point_balance"));

		            list.add(member);
		        }
		    }

		    return list;
	}

	@Override
	public void update(Member member) throws Exception {
		// TODO Auto-generated method stub
		 String sql = "UPDATE members SET name=?, phone=?, email=?, status=? WHERE id=?";

		    try (Connection conn = Tool.getDb();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

		        ps.setString(1, member.getName());
		        ps.setString(2, member.getPhone());
		        ps.setString(3, member.getEmail());
		        ps.setString(4, member.getStatus());
		        ps.setInt(5, member.getId());

		        int rows = ps.executeUpdate();

		        if (rows > 0) {
		            System.out.println("✅ 更新成功");
		        } else {
		            System.out.println("❌ 找不到資料");
		        }
		    }
	}

	@Override
	public Member login(String phone, String password) throws Exception {
		// TODO Auto-generated method stub
		String sql =
		        "SELECT * FROM members WHERE phone = ? AND password = ?";

		    try (Connection conn = Tool.getDb();
		         PreparedStatement ps = conn.prepareStatement(sql)) {

		        ps.setString(1, phone);
		        ps.setString(2, password);

		        try (ResultSet rs = ps.executeQuery()) {

		            if (rs.next()) {

		            	Member m = new Member();
		                m.setId(rs.getInt("id"));
		                m.setMemberNo(rs.getString("member_no"));
		                m.setName(rs.getString("name"));
		                m.setPhone(rs.getString("phone"));
		                m.setPassword(rs.getString("password"));
		                m.setStatus(rs.getString("status"));
		                m.setPointBalance(rs.getInt("point_balance"));

		                return m;
		            }
		        }
		    }

		    return null;
	}

	@Override
	public boolean existsByPhone(String phone) throws Exception {
		String sql = "SELECT COUNT(*) FROM members WHERE phone = ?";

	    try (Connection conn = Tool.getDb();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, phone);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    }

	    return false;
	}
}
