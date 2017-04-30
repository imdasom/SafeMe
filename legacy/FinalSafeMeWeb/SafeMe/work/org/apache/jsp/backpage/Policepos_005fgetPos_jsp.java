/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.28
 * Generated at: 2015-12-14 06:36:36 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.backpage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public final class Policepos_005fgetPos_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("java.sql");
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("org.json.simple.JSONObject");
    _jspx_imports_classes.add("org.json.simple.JSONArray");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

	request.setCharacterEncoding("UTF-8");
   String DB_URL = "jdbc:mysql://localhost:3306/safemetest?";
   String DB_USER = "root";
   String DB_PASSWORD = "test123";
   Connection conn = null;
   PreparedStatement stmt = null;
   ResultSet rs = null;
   
   
   String query;
   String[] id = null;
   String[] loc_str = null;
   String[] loc_lat = null;
   String[] loc_lon = null;
   int num = 0;

   try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      stmt = conn.prepareStatement("select count(*) from policepos");
      rs = stmt.executeQuery();

      if(rs.next()){
			num = rs.getInt(1);
		}

      id = new String[num];
      loc_str = new String[num];
      loc_lat = new String[num];
      loc_lon = new String[num];

      stmt = conn.prepareStatement("select * from policepos");
	  rs = stmt.executeQuery();
		
      String s;
      int idx = 0;
      while (rs.next()) {
         id[idx] = String.valueOf(rs.getInt(1));
         loc_str[idx] = rs.getString(2);
         loc_lat[idx] = rs.getString(3);
         loc_lon[idx] = rs.getString(4);
         //System.out.println("id : "+id[idx]);
         idx++;
      }
      
      rs.close();
      stmt.close();
      conn.close();
   } catch (Exception e) {
      out.print("Exception Error...");
      out.print(e.toString());
   } finally {

   }

   JSONObject jsonMain = new JSONObject();
   JSONArray jArray = new JSONArray();
   JSONObject jObject = new JSONObject();
   
   int idx2 = 0;
   while(idx2 < id.length){
      jObject = new JSONObject();
      jObject.put("id", id[idx2]);
      jObject.put("loc_str", loc_str[idx2]);
      jObject.put("loc_lat", loc_lat[idx2]);
      jObject.put("loc_lon", loc_lon[idx2]);

      //System.out.println("------JSON 파싱 전-----");
      //System.out.println("normal "+idx2+" : "+name[idx2]+", "+lattitude[idx2]+", "+longitude[idx2]);
      
      jArray.add(jObject);
      idx2++;
   }
      
   //System.out.println("here");

   jsonMain.put("policepos", jArray);

   out.println(jsonMain);
   System.out.println("------Policepos 전송 성공-----");
   out.flush();

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}