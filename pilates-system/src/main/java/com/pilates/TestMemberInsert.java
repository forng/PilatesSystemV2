package com.pilates;

import java.util.List;

import com.pilates.dao.MemberDao;
import com.pilates.dao.MemberDaoImpl;
import com.pilates.model.Member;

public class TestMemberInsert {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 // 建立 DAO
        //MemberDao dao = new MemberDaoImpl();

        // 建立會員物件
        //Member member = new Member("M001", "王小明", "0912345678", "a@gmail.com");

        // 呼叫新增
        //dao.insert(member);
		
		

		    MemberDao dao = new MemberDaoImpl();

		    // 呼叫 findAll()
		    List<Member> list = dao.findAll();

		    // 印出總筆數
		    System.out.println("總共有幾筆：" + list.size());

		    // 一筆一筆印出
		    for (Member m : list) {
		        System.out.println(
		                m.getId() + " - " +
		                m.getMemberNo() + " - " +
		                m.getName()
		        );
		    }
		    Member m = dao.findById(1);
		    m.setName("王大明");
		    dao.update(m);
	}

}
