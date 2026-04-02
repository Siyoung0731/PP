package matter2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Apo {
	void input();
	void process();
	void output();
}
class AptVo {
	//입력data : 동호수,세대주명,전기사용량,수도사용량,기본관리비,평형코드
	private String unitnum;
	private String name;
	private int elused;
	private int wtused;
	private double mfee;
	private char phcode;
	
	//출력 : 동호수,세대주명,전기요금,수도요금,총관리비,평형명
	private double elfee;
	private double wtfee;
	private double tmf;
	private String phname;
	
	public String getUnitnum() {
		return unitnum;
	}
	public void setUnitnum(String unitnum) {
		this.unitnum = unitnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getElused() {
		return elused;
	}
	public void setElused(int elused) {
		this.elused = elused;
	}
	public int getWtused() {
		return wtused;
	}
	public void setWtused(int wtused) {
		this.wtused = wtused;
	}
	public double getMfee() {
		return mfee;
	}
	public void setMfee(double mfee) {
		this.mfee = mfee;
	}
	public char getPhcode() {
		return phcode;
	}
	public void setPhcode(char phcode) {
		this.phcode = phcode;
	}
	public double getElfee() {
		return elfee;
	}
	public void setElfee(double elfee) {
		this.elfee = elfee;
	}
	public double getWtfee() {
		return wtfee;
	}
	public void setWtfee(double wtfee) {
		this.wtfee = wtfee;
	}
	public double getTmf() {
		return tmf;
	}
	public void setTmf(double tmf) {
		this.tmf = tmf;
	}
	public String getPhname() {
		return phname;
	}
	public void setPhname(String phname) {
		this.phname = phname;
	}
	public AptVo(String unitnum, String name, int elused, int wtused, double mfee, char phcode) {
		this.unitnum = unitnum;
		this.name = name;
		this.elused = elused;
		this.wtused = wtused;
		this.mfee = mfee;
		this.phcode = phcode;
	}
	@Override
	public String toString() {
		return "ApartVo [unitnum=" + unitnum + ", name=" + name + ", elused=" + elused + ", wtused=" + wtused
				+ ", mfee=" + mfee + ", phcode=" + phcode + ", elfee=" + elfee + ", wtfee=" + wtfee + ", tmf=" + tmf
				+ ", phname=" + phname + "]";
	}	
}
class Apt implements Apo {
	List<AptVo> aptList = new ArrayList<>();
	
	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);	
		System.out.println("동호수,세대주명,전기사용량,수도사용량,기본관리비,평형코드");
		int i = 0;
		while (true) {
			String line = sc.nextLine();
			if (line.equals("quit")) {
				System.out.println();
				break;
			}
			String[] li = line.trim().split(",");
			String unitnum = li[0].trim();
			String name= li[1].trim();
			int elused = Integer.parseInt(li[2].trim());
			int wtused = Integer.parseInt(li[3].trim());
			double mfee = Double.parseDouble(li[4].trim());
			char phcode = li[5].trim().charAt(0);
			
			AptVo aVo = new AptVo(unitnum, name, elused, wtused, mfee, phcode);
			
			aptList.add(aVo);
		}
	}

	@Override
	public void process() {
		for (AptVo aptVo : aptList) {
			//전기요금 = 전기사용량 * 120
			double elused = aptVo.getElused();
			double elfee =elused * 120.0;
			aptVo.setElfee(elfee);	
			//수도요금 = 수도사용량 * 900
			double wtused = aptVo.getWtused();
			double wtfee = wtused * 900.0;
			aptVo.setWtfee(wtfee);		
			//총관리비 = 기본관리비 + 전기요금 + 수도요금
			double mfee = aptVo.getMfee();
			double tmf = mfee + elfee + wtfee;
			aptVo.setTmf(tmf);
			//평형명 = A:20평형, B:30평형, C:40평형, D:50평형
			char phcode = aptVo.getPhcode();
			String phname = "";
			switch (phcode) {
			case 'A': phname = "20평형"; break;
			case 'B': phname = "30평형"; break;
			case 'C': phname = "40평형"; break;
			case 'D': phname = "50평형"; break;
			}
			aptVo.setPhname(phname);
			// 금액은 소수이하 두자리로 반올림		
		}
	}

	@Override
	public void output() {
		String title = "동호수 세대주명 전기요금 수도요금 총관리비 평형명";
		System.out.println(title);
		for (AptVo aptVo : aptList) {
			String fmt = "%s %s %.2f %.2f %.2f %s";
			String unitnum = aptVo.getUnitnum();
			String name = aptVo.getName();
			double elfee = aptVo.getElfee();
			double wtfee = aptVo.getWtfee();
			double tmf = aptVo.getTmf();
			String phname = aptVo.getPhname();
			String msg = String.format(fmt, unitnum, name, elfee, wtfee, tmf, phname);
			System.out.println(msg);			
		}
	}
	
}
public class TestApartFee {
	public static void main(String[] args) {
		Apt apt = new Apt();
		apt.input();
		apt.process();
		apt.output();
	}

}
