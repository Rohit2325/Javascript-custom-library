package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
public class AdministratorDAO
{
public AdministratorDTO getByUsername(String username) throws DAOException
{
AdministratorDTO administrator=null;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select * from administrator where uname=?");
preparedStatement.setString(1,username);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username/password");
}
administrator = new AdministratorDTO();
administrator.setUsername(username);
administrator.setPassword(resultSet.getString("password"));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
return administrator;
}
}