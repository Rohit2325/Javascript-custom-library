package com.thinking.machines.hr.dl;
import java.util.*;
import java.math.*;
public class EmployeeDTO implements java.io.Serializable,Comparable<EmployeeDTO>
{
private String employeeId;
private String name;
private int designationCode;
private String title;
private java.util.Date dateOfBirth;
private String gender;
private boolean isIndian;
private BigDecimal basicSalary;
private String panNumber;
private String aadharCardNumber;
public EmployeeDTO()
{
this.employeeId="";
this.name="";
this.designationCode=0;
this.title="";
this.dateOfBirth=null;
this.gender="";
this.isIndian=true;
this.basicSalary=null;
this.panNumber="";
this.aadharCardNumber="";
}
public void setEmployeeId(String employeeId)
{
this.employeeId = employeeId;
}
public String getEmployeeId()
{
return this.employeeId;
}
public void setName(String name)
{
this.name= name;
}
public String getName()
{
return this.name;
}
public void setDesignationCode(int designationCode)
{
this.designationCode=designationCode;
}
public int getDesignationCode()
{
return this.designationCode;
}
public void setDesignation(String title)
{
this.title = title;
}
public String getDesignation()
{
return this.title;
}
public void setIsIndian(boolean isIndian)
{
this.isIndian = isIndian;
}
public boolean getIsIndian()
{
return this.isIndian;
}
public void setDateOfBirth(java.util.Date dateOfBirth)
{
this.dateOfBirth = dateOfBirth;
}
public java.util.Date getDateOfBirth()
{
return this.dateOfBirth;
}
public void setGender(String gender)
{
this.gender= gender;
}
public String getGender()
{
return this.gender;
}
public void setBasicSalary(BigDecimal basicSalary)
{
this.basicSalary = basicSalary;
}
public BigDecimal getBasicSalary()
{
return this.basicSalary;
}
public void setPANNumber(String panNumber)
{
this.panNumber = panNumber;
}
public String getPANNumber()
{
return this.panNumber;
}
public void setAadharCardNumber(String aadharCardNumber)
{
this.aadharCardNumber = aadharCardNumber;
}
public String getAadharCardNumber()
{
return this.aadharCardNumber;
}
public boolean equals(Object object)
{
if(!(object instanceof EmployeeDTO)) return false;
EmployeeDTO employee = (EmployeeDTO)object;
return employee.getEmployeeId().equalsIgnoreCase(this.employeeId);
}
public int compareTo(EmployeeDTO other)
{
return this.employeeId.compareToIgnoreCase(other.employeeId);
}
public int hashCode()
{
return this.employeeId.hashCode();
}
}