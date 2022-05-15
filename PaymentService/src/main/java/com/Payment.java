package com;
import java.sql.*;
public class Payment
{
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");
 con =
 DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","1234");
 }
 catch (Exception e)
 {
 e.printStackTrace();
 }
 return con;
 }
public String readPayments()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for reading.";
 }
 
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>FullName</th><th>NIC</th><th>Amount</th><th>Date</th><th>Bank Name</th><th>Debit Card</th>" + "<th>OTP Number</th><th>Update</th><th>Remove</th></tr>";
 String query = "select * from payment";
 Statement stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);

 // iterate through the rows in the result set
 while (rs.next())
 {
 String payID = Integer.toString(rs.getInt("payID"));
 String fullName = rs.getString("fullName");
 String NIC = rs.getString("NIC");
 String amount = rs.getString("amount");
 String date = rs.getString("date");
 String bankName = rs.getString("bankName");
 String debitCard = rs.getString("debitCard");
 String otpNumber = rs.getString("otpNumber");
 

 // Add into the html table
output += "<tr><td><input id='hidPaymentIDUpdate'name='hidPaymentIDUpdate'type='hidden' value='" +payID+ "'>" + fullName + "</td>";
output += "<td>" + NIC + "</td>";
output += "<td>" + amount + "</td>";
output += "<td>" + date + "</td>";
output += "<td>" + bankName + "</td>";
output += "<td>" + debitCard + "</td>";
output += "<td>" + otpNumber + "</td>";
 
// buttons
output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"+"<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger'data-Paymentid='"+ payID + "'>" + "</td></tr>";
 }
 con.close();
 
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the payments.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String insertPayment(String fullName, String NIC,String amount, String date,String bankName,String debitCard,String otpNumber)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for inserting.";
 }
 
 // create a prepared statement
 String query = " insert into payment(`payID`,`fullName`,`NIC`,`amount`,`date`,`bankName`,`debitCard`,`otpNumber`)" + " values (?, ?, ?, ?, ?, ?, ?, ?)";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, 0);
 preparedStmt.setString(2, fullName);
 preparedStmt.setString(3, NIC);
 preparedStmt.setString(4, amount);
 preparedStmt.setString(5, date);
 preparedStmt.setString(6, bankName);
 preparedStmt.setString(7, debitCard);
 preparedStmt.setString(8, otpNumber);
 
 // execute the statement
 preparedStmt.execute();
 con.close();
 String newPayments = readPayments();
 output = "{\"status\":\"success\", \"data\": \"" +newPayments + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String updatePayment(String payID,String fullName, String NIC,String amount, String date,String bankName,String debitCard,String otpNumber)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for updating.";
 }
 
 // create a prepared statement
 String query = "UPDATE payment SET fullName=?,NIC=?,amount=?,date=?,bankName=?,debitCard=?,otpNumber=? WHERE payID=?";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 
 // binding values
 preparedStmt.setString(1, fullName);
 preparedStmt.setString(2, NIC);
 preparedStmt.setString(3, amount);
 preparedStmt.setString(4, date);
 preparedStmt.setString(5, bankName);
 preparedStmt.setString(6, debitCard);
 preparedStmt.setString(7, otpNumber);
 preparedStmt.setInt(8, Integer.parseInt(payID));

 //execute the statement
preparedStmt.execute();
con.close();
String newPayments = readPayments();
output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
}
catch (Exception e)
{
output = "{\"status\":\"error\", \"data\":\"Error while updating the Payment.\"}";
System.err.println(e.getMessage());
}
return output;
}
public String deletePayment(String payID)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for deleting.";
}

// create a prepared statement
String query = "delete from payment where payID=?";
PreparedStatement preparedStmt = con.prepareStatement(query);

// binding values
preparedStmt.setInt(1, Integer.parseInt(payID));

// execute the statement
preparedStmt.execute();
con.close();
String newPayments = readPayments();
output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
}
catch (Exception e)
{
output = "{\"status\":\"error\", \"data\":\"Error while deleting the Payment.\"}";
System.err.println(e.getMessage());
}
return output;
}
}