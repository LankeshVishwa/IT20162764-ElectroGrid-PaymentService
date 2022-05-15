package com;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.Payment;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
private static final long serialVersionUID = 1L;

Payment PaymentObj = new Payment();


public PaymentsAPI() {
super();
// TODO Auto-generated constructor stub
}




protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
response.getWriter().append("Served at: ").append(request.getContextPath());
}




protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
// doGet(request, response);

{
String output = PaymentObj.insertPayment(request.getParameter("fullName"),
request.getParameter("NIC"),
request.getParameter("amount"),
request.getParameter("date"),
request.getParameter("bankName"),
request.getParameter("debitCard"),
request.getParameter("otpNumber"));
response.getWriter().write(output);
}



}



private static Map getParasMap(HttpServletRequest request)
{
Map<String, String> map = new HashMap<String, String>();
try
{
Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
String queryString = scanner.hasNext() ?
scanner.useDelimiter("\\A").next() : "";
scanner.close();
String[] params = queryString.split("&");
for (String param : params)
{

String[] p = param.split("=");
map.put(p[0], p[1]);
}
}
catch (Exception e)
{
}
return map;
}



protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub

{
Map paras = getParasMap(request);
String output = PaymentObj.updatePayment(paras.get("hidPaymentIDSave").toString(),
paras.get("fullName").toString(),
paras.get("NIC").toString(),
paras.get("amount").toString(),
paras.get("date").toString(),
paras.get("bankName").toString(),
paras.get("debitCard").toString(),
paras.get("otpNumber").toString());
response.getWriter().write(output);
}



}




protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub

{
Map paras = getParasMap(request);
String output = PaymentObj.deletePayment(paras.get("payID").toString());
response.getWriter().write(output);
}
}



}