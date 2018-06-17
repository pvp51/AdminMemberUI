package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Record;

public class DataAccessObject {
	
	private ArrayList<Record> records;
	public DataAccessObject(ArrayList<Record> records){
		this.records = records;
	}
	
	public ArrayList<Record> buildQuery() throws SQLException {

	Connection conn = DBUtil.dbConnect();
	PreparedStatement ps;
	ResultSet rs;
	switch(records.get(0).getMessage()) {
	case "login" :
		ps=conn.prepareStatement("SELECT id, type From records WHERE userName=? AND password=?");
		ps.setString(1, this.records.get(0).getUserName());
		ps.setString(2, this.records.get(0).getPassword());

		rs = ps.executeQuery();

		if(rs.next()) {
			this.records.get(0).setId(rs.getInt("id"));
			this.records.get(0).setType(rs.getString("type"));
			this.records.get(0).setMessage("success");
		}
		else {
			System.out.println("Not Exists in DB");
			//this.records.get(0).setMessage("failed");
			this.records = new ArrayList<>();
		}

		ps.close();
		rs.close();
		DBUtil.dbDisconnect();
		break; 
	default : 
		System.out.println("Action Code Does not match");
	}
	
	return this.records;
	}

}
