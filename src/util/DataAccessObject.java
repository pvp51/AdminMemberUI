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
			ps=conn.prepareStatement("SELECT * From records WHERE userName=? AND password=?");
			ps.setString(1, this.records.get(0).getUserName());
			ps.setString(2, this.records.get(0).getPassword());

			rs = ps.executeQuery();

			if(rs.next()) {
				this.records.get(0).setId(rs.getInt("id"));
				this.records.get(0).setType(rs.getString("type"));
				//this.records.get(0).setMessage("success");
				this.records.get(0).setEmail(rs.getString("email"));
				this.records.get(0).setFullName(rs.getString("fullName"));
				this.records.get(0).setGender(rs.getString("gender"));
				this.records.get(0).setPassword(rs.getString("password"));
				this.records.get(0).setUserName(rs.getString("userName"));
				this.records.get(0).setPhoneNumber(rs.getString("phoneNumber"));
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
		case "insert" :
			ps=conn.prepareStatement("INSERT INTO records (userName, password, fullName, email, phoneNumber, gender, type) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, this.records.get(0).getUserName());
			ps.setString(2, this.records.get(0).getPassword());
			ps.setString(3, this.records.get(0).getFullName());
			ps.setString(4, this.records.get(0).getEmail());
			ps.setString(5, this.records.get(0).getPhoneNumber());
			ps.setString(6, this.records.get(0).getGender());
			ps.setString(7, this.records.get(0).getType());

			int rs2 = ps.executeUpdate();
			if(rs2!=0) {
				System.out.println("Number of rows inserted:"+ rs2);
			}else {
				System.out.println("Number of rows inserted:"+ rs2 +"Please try again");
			}
			ps.close();
			DBUtil.dbDisconnect();
			break; 
		case "update" :
			ps=conn.prepareStatement("UPDATE records SET userName =?, password =?, fullName =?, email =?, phoneNumber =?, gender =?, type =? WHERE id =?");
			ps.setString(1, this.records.get(0).getUserName());
			ps.setString(2, this.records.get(0).getPassword());
			ps.setString(3, this.records.get(0).getFullName());
			ps.setString(4, this.records.get(0).getEmail());
			ps.setString(5, this.records.get(0).getPhoneNumber());
			ps.setString(6, this.records.get(0).getGender());
			ps.setString(7, this.records.get(0).getType());
			ps.setInt(8, this.records.get(0).getId());

			int rs3 = ps.executeUpdate();
			if(rs3!=0) {
				System.out.println("Number of rows updated:"+ rs3);
			}else {
				System.out.println("Number of rows updated:"+ rs3 +"Please try again");
			}
			ps.close();
			DBUtil.dbDisconnect();
			break; 
		case "delete" :
			ps=conn.prepareStatement("Delete From records WHERE id=?");
			ps.setInt(1, this.records.get(0).getId());
		
			int code = ps.executeUpdate();

			if(code != 0) {
				System.out.println("No. of rows deleted: "+code);
				this.records = new ArrayList<>();
			}
			else {
				System.out.println("Not rows found in DB");
				//this.records.get(0).setMessage("failed");
				this.records = new ArrayList<>();
			}

			ps.close();
			DBUtil.dbDisconnect();
			break; 
		case "all" :
			this.records = new ArrayList<>();
			ps=conn.prepareStatement("SELECT * From records");

			rs = ps.executeQuery();
			Record record;
			while(rs.next()) {
				record = new Record();
				record.setId(rs.getInt("id"));
				record.setFullName(rs.getString("fullName"));
				record.setEmail(rs.getString("email"));
				record.setGender(rs.getString("gender"));
				record.setPassword(rs.getString("password"));
				record.setUserName(rs.getString("username"));
				record.setType(rs.getString("type"));
				record.setPhoneNumber(rs.getString("phoneNumber"));
				this.records.add(record);
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
