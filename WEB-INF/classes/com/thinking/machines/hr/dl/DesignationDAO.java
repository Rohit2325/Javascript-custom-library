package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
public class DesignationDAO
{
public void addDesignation(DesignationDTO designation) throws DAOException
{
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select * from designation where title =?");
preparedStatement.setString(1,designation.getTitle());
resultSet = preparedStatement.executeQuery();
if(resultSet.next()== true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation "+designation.getTitle()+" already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("insert into designation (title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,designation.getTitle());
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
designation.setCode(resultSet.getInt(1));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
System.out.println(sqlException.getMessage());
}
}
public List<DesignationDTO> getAll() throws DAOException
{
List<DesignationDTO> designations; 
designations = new LinkedList<>();
try
{
Connection connection = DAOConnection.getConnection();
Statement statement = connection.createStatement();
ResultSet resultSet;
DesignationDTO designationDTO;
int code;
String title;
resultSet = statement.executeQuery("select * from designation order by title");
while(resultSet.next())
{
code = resultSet.getInt("code");
title = resultSet.getString("title");
designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception daoException)
{
throw new DAOException(daoException.getMessage());
}
return designations;
}
public DesignationDTO getByCode(int code) throws DAOException
{
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid code "+ code);
}
DesignationDTO designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
} 
public void updateDesignation(DesignationDTO designation) throws DAOException
{
try
{
String title = designation.getTitle();
int code = designation.getCode();
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid code" +code);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select * from designation where title=? and code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(title+ "exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void deleteByCode(int code) throws DAOException
{
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid code" +code);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where designation_code=?");
preparedStatement.setInt(1,code);
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation cannot deteted as it has alloted to a employee");
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("delete from designation where code =?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlexception)
{
System.out.println(sqlexception.getMessage());
}
}

public boolean designationCodeExists(int code) throws DAOException
{
boolean exists = false;
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select code from designation where code =?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
exists = resultSet.next();
resultSet.close();
preparedStatement.close();
}catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return exists;
}
}//class ends