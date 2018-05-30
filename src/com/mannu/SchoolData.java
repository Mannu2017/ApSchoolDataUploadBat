package com.mannu;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SchoolData {
	private static Connection conn;
	private static String distnam,townam,sclnam,welnam,mattyp;
	
	public static void main(String[] args) {
		conn=DbConn.getConnection();
		try {
			
			if(conn.isClosed()) {
				conn=DbConn.getConnection();
			}
			PreparedStatement ps=conn.prepareStatement("select WelfareType,MatricType,URL from WelfateMaster");
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				System.out.println("main: "+rs.getString(1)+"^"+rs.getString(2)+"^"+rs.getString(3));
				Document doc=(Document) Jsoup.connect(rs.getString(3)).get(); 
				Elements trs=doc.select("TR");
				System.out.println("Tes: "+trs.size());
				for (int i = 0; i < trs.size(); i++) {
					Elements tds=trs.get(i).select("TD");
//					System.out.println("DDDD: "+tds.size());
					if(tds.size()==11) {
						if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
							if(Integer.parseInt(tds.get(2).text())>0) {
							
								Element link = tds.get(1).select("a").first();
								String absHref = link.attr("abs:href");
								System.out.println("Next Lavel: "+tds.get(1).text()+"^"+absHref);
								nextlvldata(rs.getString(1),rs.getString(2),tds.get(1).text(),absHref);
								
							}
						}
					} else if(tds.size()==9) {
						if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
							if(Integer.parseInt(tds.get(2).text())>0) {
								Element link = tds.get(1).select("a").first();
								String absHref = link.attr("abs:href");
								System.out.println("Next Lavel: "+tds.get(1).text()+"^"+absHref);
								nextlvldata(rs.getString(1),rs.getString(2),tds.get(1).text(),absHref);
								
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void nextlvldata(String string, String string2, String text, String absHref) throws IOException, SQLException {
		Document doc=(Document) Jsoup.connect(absHref).get(); 
		Elements trs=doc.select("TR");
		for (int i = 0; i < trs.size(); i++) {
			Elements tds=trs.get(i).select("TD");
			if(tds.size()==11) {
				if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
					if(Integer.parseInt(tds.get(2).text())>0) {
						Element link = tds.get(1).select("a").first();
						String bbsHref = link.attr("abs:href");
						System.out.println("3rd label: "+string+"^"+string2+"^"+text+"^"+tds.get(1).text()+"^"+bbsHref);
						forththlabeldata(string,string2,text,tds.get(1).text(),bbsHref);
					}
				}
			} else if(tds.size()==9) {
				if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
					if(Integer.parseInt(tds.get(2).text())>0) {
						Element link = tds.get(1).select("a").first();
						String bbsHref = link.attr("abs:href");
						System.out.println("3rd label: "+string+"^"+string2+"^"+text+"^"+tds.get(1).text()+"^"+bbsHref);
						trablewelfate(string,string2,text,tds.get(1).text(),bbsHref);
					}
				}
			}
		}
	}

	private static void trablewelfate(String string, String string2, String text, String text2, String bbsHref) throws IOException, SQLException {
		
		Document doc=(Document) Jsoup.connect(bbsHref).get(); 
		Elements trs=doc.select("TR");
		for (int i = 0; i < trs.size(); i++) {
			Elements tds=trs.get(i).select("TD");
			if(tds.size()==10) {
				if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
					if(conn.isClosed()) {
						conn=DbConn.getConnection();
					}
					System.out.println("4th Label: "+string+" ^ "+string2+" ^ "+text+" ^ "+text2+" ^ "+tds.get(1).text()+" ^ "+tds.get(2).text()+" ^ "+tds.get(3).text()+" ^ "+tds.get(4).text()+" ^ "+tds.get(5).text()+" ^ "+tds.get(8).text());
					
					CallableStatement ps=conn.prepareCall("insert into PreMatricHostelsReport (district,WelfareType,MatricType,Jurisdiction,MandaName,HostelName,HostelType,SanctionedStrength,[Date],TotalBoarders,Total) values "
							+ "('"+text+"','"+string+"','"+string2+"','"+text2+"','"+tds.get(1).text()+"','"+tds.get(2).text()+"','"+tds.get(3).text()+"','"+tds.get(4).text()+"',GETDATE(),'"+tds.get(5).text()+"','"+tds.get(8).text()+"')");
					ps.execute();
					ps.close();
					
				}
			}
		}
		
	}

	private static void forththlabeldata(String string, String string2, String text, String text2, String bbsHref) throws IOException, SQLException {
		Document doc=(Document) Jsoup.connect(bbsHref).get(); 
		Elements trs=doc.select("TR");
		for (int i = 0; i < trs.size(); i++) {
			Elements tds=trs.get(i).select("TD");
			if(tds.size()==12) {
				if(!tds.get(1).text().equals("  1  ") && tds.get(1).text().length()>1) {
					if(conn.isClosed()) {
						conn=DbConn.getConnection();
					}
					System.out.println("4th Label: "+string+" ^ "+string2+" ^ "+text+" ^ "+text2+" ^ "+tds.get(1).text()+" ^ "+tds.get(2).text()+" ^ "+tds.get(3).text()+" ^ "+tds.get(4).text()+" ^ "+tds.get(5).text()+" ^ "+tds.get(10).text());
					
					CallableStatement ps=conn.prepareCall("insert into PreMatricHostelsReport (district,WelfareType,MatricType,Jurisdiction,MandaName,HostelName,HostelType,SanctionedStrength,[Date],TotalBoarders,Total) values "
							+ "('"+text+"','"+string+"','"+string2+"','"+text2+"','"+tds.get(1).text()+"','"+tds.get(2).text()+"','"+tds.get(3).text()+"','"+tds.get(4).text()+"',GETDATE(),'"+tds.get(5).text()+"','"+tds.get(10).text()+"')");
					ps.execute();
					ps.close();
					
				}
			}
		}
	}
}
