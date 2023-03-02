package com.thinking.machines.hr.dl;
import java.sql.*;
import java.util.*;
import java.math.*;
public class EmployeeDAO
{
public List<EmployeeDTO> getAll() throws DAOException
{
List<EmployeeDTO> employees; 
employees = new LinkedList<>();
try
{
Connection connection = DAOConnection.getConnection();
Statement statement = connection.createStatement();
ResultSet resultSet;
EmployeeDTO employeeDTO;
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
resultSet = statement.executeQuery("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code");
while(resultSet.next())
{
id = resultSet.getInt("id");
name = resultSet.getString("name").trim();
designationCode = resultSet.getInt("designation_Code");
title = resultSet.getString("title").trim();
dateOfBirth = resultSet.getDate("date_of_birth");
gender = resultSet.getString("gender");
isIndian = resultSet.getBoolean("is_indian");
basicSalary = resultSet.getBigDecimal("basic_salary");
panNumber = resultSet.getString("pan_number");
aadharCardNumber = resultSet.getString("aadhar_card_number");
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
}catch(Exception daoException)
{
throw new DAOException(daoException.getMessage());
}
return employees;
}

public void addEmployee(EmployeeDTO employee) throws DAOException
{
try
{
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement = connection.prepareStatement("select Id from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number already exists "+panNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select id from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet =preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("aadhar card number already exists "+aadharCardNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,gender,is_indian,basic_salary,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getDesignationCode());
java.util.Date utilDate = employee.getDateOfBirth();
java.sql.Date sqlDate = new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,employee.getPANNumber());
preparedStatement.setString(8,employee.getAadharCardNumber());
preparedStatement.executeUpdate();
resultSet = preparedStatement.getGeneratedKeys();
resultSet.next();
int id = resultSet.getInt(1);
employee.setEmployeeId("A"+id);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}

public boolean panNumberExists(String panNumber) throws DAOException
{
boolean exists = false;
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where pan_number =?");
preparedStatement.setString(1,panNumber);
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
				
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
boolean exists = false;
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number =?");
preparedStatement.setString(1,aadharCardNumber);
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


public void deleteByEmployeeId(String employeeId) throws DAOException
{
int actualEmployeeId = 0;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("invalid Employee Id "+employeeId);
}
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from employee where id =?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid Employee id" +employeeId);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("delete from employee where id =?");
preparedStatement.setInt(1,actualEmployeeId);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}




public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
int actualEmployeeId = 0;
EmployeeDTO employeeDTO=null;
try
{
actualEmployeeId = Integer.parseInt(employeeId.substring(1));
}catch(Exception exception)
{
throw new DAOException("invalid Employee Id "+employeeId);
}
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select * from employee where id =?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid Employee id" +employeeId);
}
resultSet.close();
preparedStatement.close();
//EmployeeDTO employeeDTO;
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
String aadharCardNumber;
preparedStatement = connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
while(resultSet.next())
{
id = resultSet.getInt("id");
name = resultSet.getString("name").trim();
designationCode = resultSet.getInt("designation_Code");
title = resultSet.getString("title").trim();
dateOfBirth = resultSet.getDate("date_of_birth");
gender = resultSet.getString("gender");
isIndian = resultSet.getBoolean("is_indian");
basicSalary = resultSet.getBigDecimal("basic_salary");
panNumber = resultSet.getString("pan_number");
aadharCardNumber = resultSet.getString("aadhar_card_number");
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}



public void updateEmployee(EmployeeDTO employee) throws DAOException
{
String employeeId = "";
int actualEmployeeId =0;
try
{
try
{
employeeId = employee.getEmployeeId();
actualEmployeeId = Integer.parseInt(employeeId.substring(1));
}catch(NumberFormatException nfe)
{
throw new DAOException("invalid employeeId "+employeeId);
}
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement = connection.prepareStatement("select gender from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
ResultSet resultSet;
resultSet = preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("invalid employeeId "+employeeId);
}
resultSet.close();
preparedStatement.close();
String panNumber = employee.getPANNumber();
String aadharCardNumber = employee.getAadharCardNumber();
preparedStatement = connection.prepareStatement("select gender from employee where pan_number=? and id<>?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Pan Number already exists "+panNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("select gender from employee where aadhar_card_number=? and id<>?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,actualEmployeeId);
resultSet =preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("aadhar card number already exists "+aadharCardNumber);
}
resultSet.close();
preparedStatement.close();
preparedStatement = connection.prepareStatement("update employee set name=?,designation_code=?,date_of_birth=?,gender=?,is_indian=?,basic_salary=?,pan_number=?,aadhar_card_number=? where id=?");
preparedStatement.setString(1,employee.getName());
preparedStatement.setInt(2,employee.getDesignationCode());
java.util.Date utilDate = employee.getDateOfBirth();
java.sql.Date sqlDate = new java.sql.Date(utilDate.getYear(),utilDate.getMonth(),utilDate.getDate());
preparedStatement.setDate(3,sqlDate);
preparedStatement.setString(4,employee.getGender());
preparedStatement.setBoolean(5,employee.getIsIndian());
preparedStatement.setBigDecimal(6,employee.getBasicSalary());
preparedStatement.setString(7,employee.getPANNumber());
preparedStatement.setString(8,employee.getAadharCardNumber());
preparedStatement.setInt(9,actualEmployeeId);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}


public boolean employeeIdExists(String employeeId) throws DAOException
{
int actualEmployeeId=0;
try
{
System.out.println(employeeId);
actualEmployeeId = Integer.parseInt(employeeId.substring(1));
System.out.println(actualEmployeeId);
}catch(NumberFormatException nfe)
{
throw new DAOException("Invalid employeeId "+employeeId);
}
boolean exists=false;
try
{
Connection connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
preparedStatement = connection.prepareStatement("select gender from employee where id=?");
preparedStatement.setInt(1,actualEmployeeId);
resultSet = preparedStatement.executeQuery();
exists = resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close(); 
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
return exists;
}


public EmployeeDTO getByPanNumber(String panNumber) throws DAOException
{
EmployeeDTO employeeDTO = null;
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String aadharCardNumber;
preparedStatement = connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet = preparedStatement.executeQuery();
while(resultSet.next())
{
id = resultSet.getInt("id");
name = resultSet.getString("name").trim();
designationCode = resultSet.getInt("designation_Code");
title = resultSet.getString("title").trim();
dateOfBirth = resultSet.getDate("date_of_birth");
gender = resultSet.getString("gender");
isIndian = resultSet.getBoolean("is_indian");
basicSalary = resultSet.getBigDecimal("basic_salary");
aadharCardNumber = resultSet.getString("aadhar_card_number");
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}



public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
EmployeeDTO employeeDTO = null;
try
{
Connection connection;
connection = DAOConnection.getConnection();
PreparedStatement preparedStatement;
ResultSet resultSet;
int id;
String name;
int designationCode;
String title;
java.sql.Date dateOfBirth;
String gender;
boolean isIndian;
BigDecimal basicSalary;
String panNumber;
preparedStatement = connection.prepareStatement("select employee.id,employee.name,employee.designation_code,designation.title,employee.date_of_birth,employee.gender,employee.is_indian,employee.basic_salary,employee.pan_number,employee.aadhar_card_number from employee inner join designation on employee.designation_code=designation.code where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet = preparedStatement.executeQuery();
while(resultSet.next())
{
id = resultSet.getInt("id");
name = resultSet.getString("name").trim();
designationCode = resultSet.getInt("designation_Code");
title = resultSet.getString("title").trim();
dateOfBirth = resultSet.getDate("date_of_birth");
gender = resultSet.getString("gender");
isIndian = resultSet.getBoolean("is_indian");
basicSalary = resultSet.getBigDecimal("basic_salary");
panNumber = resultSet.getString("pan_number");
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId("A"+id);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDesignation(title);
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return employeeDTO;
}




}//class ends