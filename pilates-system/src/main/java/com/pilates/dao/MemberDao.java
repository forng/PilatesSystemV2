package com.pilates.dao;

import java.util.List;

import com.pilates.model.Member;

public interface MemberDao {
	  /*
     * 新增會員
     * 
     * 參數：
     *   member → 要新增的會員物件
     * 
     * throws Exception：
     *   表示可能會丟出錯誤（例如資料庫錯誤）
     */
    void insert(Member member) throws Exception;
    boolean existsByPhone(String phone) throws Exception; //手機重複

    /*
     * 用 id 查詢會員
     * 
     * 參數：
     *   id → 會員的主鍵
     * 
     * 回傳：
     *   Member 物件
     */
    Member findById(int id) throws Exception;
    Member login(String phone, String password) throws Exception;

    /*
     * 查詢全部會員
     * 
     * 回傳：
     *   一個 List（會員清單）
     */
    List<Member> findAll() throws Exception;
    void update(Member member) throws Exception;
    
}
