package com.transferdata2.project;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CSVUTF2 {
	
	public static void main(String[] args) {
		String jdbcUrl="jdbc:mysql://localhost:3306/itms";
		String username="root";
		String password="Neeru@123";
		
		String filePath="C:\\Users\\neera\\OneDrive\\Desktop\\data2.csv";
        int batchSize=20;

        Connection connection=null;
        try{
            connection= DriverManager.getConnection(jdbcUrl,username,password);
            connection.setAutoCommit(false);

            String sql="insert into itemslist(id,name,quantity,cost)values(?,?,?,?)";

            PreparedStatement statement=connection.prepareStatement(sql);

            BufferedReader lineReader=new BufferedReader(new FileReader(filePath));

            String lineText=null;
            int count=0;

            lineReader.readLine();
            while ((lineText=lineReader.readLine())!=null){
                String[] data =lineText.split(",");

                String id=data[0];
                String name=data[1];
                String quantity=data[2];
                String cost=data[3];

                statement.setInt(1,parseInt(id));
                statement.setString(2,name);
                statement.setString(3,quantity);
                statement.setInt(4,parseInt(cost));
                statement.addBatch();

                if(count%batchSize==0){
                    statement.executeBatch();
                }
            }
            lineReader.close();
            statement.executeBatch();
            connection.commit();
            connection.close();

            System.out.println("Data has been inserted successfully");



        }catch(Exception exception){
            exception.printStackTrace();
        }
	}

}
