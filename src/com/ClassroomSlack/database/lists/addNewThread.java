package com.ClassroomSlack.database.lists;

import com.ClassroomSlack.database.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class addNewThread {

    public static String add(String companyName, String threadName, String threadType){

        Connection con = null;
        PreparedStatement stmt = null;

        String query = DBUtils.prepareInsertQuery("classroomslack.threads", "companyName, threadName, threadType", "?,?,?");

        String status = "Ongoing";
        try{
            con = DBUtils.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1, companyName);
            stmt.setString(2, threadName);
            stmt.setString(3, threadType);
            stmt.executeUpdate();
            status ="success";
        }
        catch(Exception e){
            status = e.getMessage();
        }
        finally{
            DBUtils.closeStatement(stmt);
            DBUtils.closeConnection(con);
            return status;
        }
    }


}
