package server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;

import model.DataObject;
import model.Record;
import util.DataAccessObject;

public class ThreadedDataObjectServer
{  public static void main(String[] args ) 
   {  
	
      try 
      {  @SuppressWarnings("resource")
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
	public ThreadedDataObjectHandler(Socket i) 
   { 
   		incoming = i;
   }
   
   public void run()
   {  try 
      { 	ObjectInputStream in =
				new ObjectInputStream(incoming.getInputStream());
			ObjectOutputStream out =
				new ObjectOutputStream(incoming.getOutputStream());

			records = (ArrayList<Record>) in.readObject();
			System.out.println("Message read: " + records.size());
           // myObject.setMessage("Got it!");
			
			DataAccessObject doa = new DataAccessObject(records);
			records = new ArrayList<>();
			records = doa.buildQuery();	

			System.out.println("Message written: " + records.size());
			out.writeObject(records);

			in.close();			
			out.close();
         	incoming.close();    
					    
      }
      catch (Exception e) 
      {  System.out.println("Error Found : "+e.getMessage());
      } 
   }
   
   DataObject myObject = null;
   private Socket incoming;
   
}

