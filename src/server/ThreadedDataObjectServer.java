package server;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Record;
import util.DataAccessObject;

public class ThreadedDataObjectServer{  
	public static void main(String[] args ) 
	{  

		try 
		{  
			@SuppressWarnings("resource")
			ServerSocket s = new ServerSocket(3000);

			for (;;)
			{  Socket incoming = s.accept( );
			new ThreadedDataObjectHandler(incoming).start();

			}   
		}	
		catch (Exception e) 
		{  System.out.println("Error Found : "+e.getMessage());
		} 
	} 
}

class ThreadedDataObjectHandler extends Thread
{  
	ArrayList<Record> records = null;
	private Socket incoming;
	public ThreadedDataObjectHandler(Socket i) 
	{ 
		incoming = i;
	}

	@SuppressWarnings("unchecked")
	public void run(){  
		try 
		{ 	
			ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());

			records = (ArrayList<Record>) in.readObject();
			System.out.println("Server, Message In: " + records.size());

			DataAccessObject doa = new DataAccessObject(records);
			records = new ArrayList<>();
			records = doa.buildQuery();	

			System.out.println("Server, Message Out: " + records.size());
			out.writeObject(records);

			in.close();			
			out.close();
			incoming.close();    

		}
		catch (Exception e) {  
			System.out.println("Error Found : "+e.getMessage());
		} 
	}
}

