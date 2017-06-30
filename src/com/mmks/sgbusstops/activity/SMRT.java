package com.mmks.sgbusstops.activity;

public class SMRT {
	
public String getBUSNO() {
	return BUSNO;
}

public void setBUSNO(String bUSNO) {
	BUSNO = bUSNO;
}

public String getNAME() {
	return NAME;
}

public void setNAME(String nAME) {
	NAME = nAME;
}

public String getNAME2() {
	return NAME2;
}

public void setNAME2(String nAME2) {
	NAME2 = nAME2;
}

public String getFREQ1() {
	return FREQ1;
}

public void setFREQ1(String fREQ1) {
	FREQ1 = fREQ1;
}

public String getFREQ2() {
	return FREQ2;
}

public void setFREQ2(String fREQ2) {
	FREQ2 = fREQ2;
}

public String getFREQ3() {
	return FREQ3;
}

public void setFREQ3(String fREQ3) {
	FREQ3 = fREQ3;
}

public String getFREQ4() {
	return FREQ4;
}

public void setFREQ4(String fREQ4) {
	FREQ4 = fREQ4;
}

public String getF1() {
	return F1;
}

public void setF1(String f1) {
	F1 = f1;
}

public String getL1() {
	return L1;
}

public void setL1(String l1) {
	L1 = l1;
}

public String getF2() {
	return F2;
}

public void setF2(String f2) {
	F2 = f2;
}

public String getL2() {
	return L2;
}

public void setL2(String l2) {
	L2 = l2;
}

public String getF3() {
	return F3;
}

public void setF3(String f3) {
	F3 = f3;
}

public String getL3() {
	return L3;
}

public void setL3(String l3) {
	L3 = l3;
}


public String getTOBUSNO() {
	return TOBUSNO;
}

public void setTOBUSNO(String tOBUSNO) {
	TOBUSNO = tOBUSNO;
}

public String getTONAME() {
	return TONAME;
}

public void setTONAME(String tONAME) {
	TONAME = tONAME;
}

public String getTONAME2() {
	return TONAME2;
}

public void setTONAME2(String tONAME2) {
	TONAME2 = tONAME2;
}

public String getTOFREQ1() {
	return TOFREQ1;
}

public void setTOFREQ1(String tOFREQ1) {
	TOFREQ1 = tOFREQ1;
}

public String getTOFREQ2() {
	return TOFREQ2;
}

public void setTOFREQ2(String tOFREQ2) {
	TOFREQ2 = tOFREQ2;
}

public String getTOFREQ3() {
	return TOFREQ3;
}

public void setTOFREQ3(String tOFREQ3) {
	TOFREQ3 = tOFREQ3;
}

public String getTOFREQ4() {
	return TOFREQ4;
}

public void setTOFREQ4(String tOFREQ4) {
	TOFREQ4 = tOFREQ4;
}

public String getTOF1() {
	return TOF1;
}

public void setTOF1(String tOF1) {
	TOF1 = tOF1;
}

public String getTOL1() {
	return TOL1;
}

public void setTOL1(String tOL1) {
	TOL1 = tOL1;
}

public String getTOF2() {
	return TOF2;
}

public void setTOF2(String tOF2) {
	TOF2 = tOF2;
}

public String getTOL2() {
	return TOL2;
}

public void setTOL2(String tOL2) {
	TOL2 = tOL2;
}

public String getTOF3() {
	return TOF3;
}

public void setTOF3(String tOF3) {
	TOF3 = tOF3;
}

public String getTOL3() {
	return TOL3;
}

public void setTOL3(String tOL3) {
	TOL3 = tOL3;
}


private String BUSNO = "";
private String NAME  = "";
private String NAME2 = "";
private String FREQ1 = "";
private String FREQ2 = "";
private String FREQ3 = "";
private String FREQ4 = "";

private String F1 = "";
private String L1 = "";
private String F2 = "";
private String L2 = "";
private String F3 = "";
private String L3 = "";

private String TOBUSNO = "";
private String TONAME  = "";
private String TONAME2 = "";
private String TOFREQ1 = "";
private String TOFREQ2 = "";
private String TOFREQ3 = "";
private String TOFREQ4 = "";

private String TOF1 = "";
private String TOL1 = "";
private String TOF2 = "";
private String TOL2 = "";
private String TOF3 = "";
private String TOL3 = "";

	public void clearAll() {
		BUSNO = "";
		NAME = "";
		NAME2 = "";
		FREQ1 = "";
		FREQ2 = "";
		FREQ3 = "";
		FREQ4 = "";

		F1 = "";
		L1 = "";
		F2 = "";
		L2 = "";
		F3 = "";
		L3 = "";
		
		TOBUSNO = "";
		TONAME = "";
		TONAME2 = "";
		TOFREQ1 = "";
		TOFREQ2 = "";
		TOFREQ3 = "";
		TOFREQ4 = "";

		TOF1 = "";
		TOL1 = "";
		TOF2 = "";
		TOL2 = "";
		TOF3 = "";
		TOL3 = "";
	}
	
public StringBuffer page1 = new StringBuffer("<!DOCTYPE html><html><head><title>Your Bus Service Details:</title><style type=\"text/css\">.hdr{color:#FFFFFF;font-family: Arial,Helvetica,sans-serif;font-size: 12px;font-weight: bold;}.subheader {color: #5C0366;font-family: Arial,Helvetica,sans-serif;font-size: 12px;font-weight: bold;text-decoration: none;}.results {color: #FFFFFF;font-family: Arial,Helvetica,sans-serif;font-size: 11px;font-weight: bold;}.whtclr {color: #FFFFFF;}.purpleclr{color: #5C0366;}.font11 {font-family: Arial,Helvetica,sans-serif;font-size: 11px;}</style></head><body>")
.append("<div align=\"left\" style=\"padding:5px;\">")
.append("<span class=\"subheader\">Your Bus Service Details:</span>")
.append("</div><div id=\"BusDetail\" style=\"padding:5px;\">")
.append("<div class=\"tabs ui-tabs ui-widget ui-widget-content ui-corner-all\" id=\"tabs-bus\">")                 
.append("<div class=\"clearfix ui-tabs-panel ui-widget-content ui-corner-bottom\" id=\"tabs-bus-1\">")
.append("<div id=\"bus-days\" style=\"display: block;\">")
.append("<div>")
.append("<table cellspacing=\"1\" cellpadding=\"7\" border=\"0\" bordercolor=\"#FFFFFF\" bgcolor=\"#FFFFFF\">")
.append("<tbody>")
.append("<tr class=\"results\">")
.append("<th rowspan=\"2\" class=\"hdr\" bgcolor=\"#cc3300\">")
.append("<font size=\"2\">")
.append("<b><span class=\"whtclr\">");

//getBUSNO();

public StringBuffer page2 = new StringBuffer()
.append("</span></b></font>")
.append("</th>")
.append("<th class=\"hdr\" id=\"categoryDay1\" colspan=\"2\" bgcolor=\"#5C0366\">Weekdays</th>")
.append("<th class=\"hdr\" id=\"categoryDay2\" colspan=\"2\" bgcolor=\"#5C0366\">Saturdays</th>")
.append("<th class=\"hdr\" id=\"categoryDay3\" colspan=\"2\" bgcolor=\"#5C0366\">Sundays &amp; Public Holidays</th>")
.append("</tr>")
.append("<tr class=\"results\">")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">1st Bus</div></th>")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">Last Bus</div></th>")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">1st Bus</div></th>")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">Last Bus</div></th>")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">1st Bus</div></th>")
.append("<th bgcolor=\"#cc3300\"><div align=\"center\">Last Bus</div></th> ")                                   
.append("</tr>                                ")
.append("<tr id=\"bus-days-from\" color=\"#FFFFFF\">");

public StringBuffer page2_1 = new StringBuffer()
.append("<td class=\"results\" bgcolor=\"#cc3300\" width=\"100\">");

//getNAME();

public StringBuffer page3 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay1-first\" class=\"font11\">");

//getF1()

public StringBuffer page4 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay1-last\" class=\"font11\">");

//getL1()

public StringBuffer page5 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay2-first\" class=\"font11\">");


//getF2()

public StringBuffer page6 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay2-last\" class=\"font11\">");

//getL2()

public StringBuffer page7 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay3-first\" class=\"font11\">");


//getF3()

public StringBuffer page8 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-categoryDay3-last\" class=\"font11\">");


//getL3()
public StringBuffer page8_1 = new StringBuffer()
.append("</td></tr>");

public StringBuffer page9 = new StringBuffer()
.append("</tbody>")
.append("</table>")
.append("</div>")
.append("<div class=\"\"></div>")
.append("</div> ")
.append("<div id=\"bus-frequency\" style=\"margin-top:10px;\">")    
.append("<div>")
.append("<table cellspacing=\"1\" cellpadding=\"7\" border=\"0\" bordercolor=\"#FFFFFF\" bgcolor=\"#FFFFFF\">")
.append("<tbody><tr>")
.append("<th class=\"hdr\" bgcolor=\"#cc3300\">Frequency</th>")
.append("<th class=\"hdr\" bgcolor=\"#cc3300\">6:30am &ndash; 8:30am</th>")
.append("<th class=\"hdr\" bgcolor=\"#cc3300\">8:31am &ndash; 4:59pm</th>  ")                                  
.append("<th class=\"hdr\" bgcolor=\"#cc3300\">5:00pm &ndash; 7:00pm</th>")
.append("<th class=\"hdr\" bgcolor=\"#cc3300\">After 7:01pm</th>   ")
.append("</tr>");

public StringBuffer page9_1 = new StringBuffer()
.append("<tr id=\"bus-frequency-from\">")
.append("<td class=\"hdr\" bgcolor=\"#cc3300\" width=\"100\">");


//getNAME2()

public StringBuffer page10 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-frequency-0\" class=\"font11\">");

//getFREQ1()

public StringBuffer page11 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-frequency-1\" class=\"font11\">");

//getFREQ2()

public StringBuffer page12 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-frequency-2\" class=\"font11\">");


//getFREQ3()

public StringBuffer page13 = new StringBuffer()
.append("</td><td bgcolor=\"#F1F2F3\" id=\"from-frequency-3\" class=\"font11\">");


//getFREQ4()

public StringBuffer page14_1 = new StringBuffer()
.append("</td></tr>");

public StringBuffer page14 = new StringBuffer()
.append("</tbody></table>")
.append("</div>")
.append("</div>")
.append("</div>")               
.append("</div>")
.append("</div>")
.append("</body>")
.append("</html>");

}
