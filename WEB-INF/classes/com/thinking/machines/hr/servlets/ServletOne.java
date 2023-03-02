package com.thinking.machines.hr.servlets;
import com.thinking.machines.hr.dl.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import com.google.gson.*;
public class ServletOne extends HttpServlet
{
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
}catch(Exception e)
{
//do nothing
}
}
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
response.setContentType("application/json");
response.setCharacterEncoding("utf-8");
PrintWriter pw = response.getWriter();
List<DesignationDTO> designations;
designations = new DesignationDAO().getAll();
Gson gson = new Gson();
String jsonString = gson.toJson(designations);
pw.print(jsonString);
pw.flush();
}catch(Exception exception)
{
System.out.println("in try");
try
{
System.out.println("in try");
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
}catch(Exception e)
{
//do nothing
}
}
}

}//class ends	