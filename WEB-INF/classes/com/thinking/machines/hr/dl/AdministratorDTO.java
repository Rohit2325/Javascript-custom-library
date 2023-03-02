package com.thinking.machines.hr.dl;
public class AdministratorDTO implements java.io.Serializable,Comparable<AdministratorDTO>
{
private String username;
private String password;
public AdministratorDTO()
{
this.password ="";
this.username="";
}
public void setUsername(String username)
{
this.username = username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password= password;
}
public String getPassword()
{
return this.password;
}
public boolean equals(Object object)
{
if(!(object instanceof AdministratorDTO)) return false;
AdministratorDTO other = (AdministratorDTO)object;
return other.getUsername().equals(this.username);
}
public int compareTo(AdministratorDTO other)
{
return this.username.compareToIgnoreCase(other.getUsername());
}
public int hashCode()
{
return this.username.hashCode();
}
}