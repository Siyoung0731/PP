package matter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface Mpo {
	void input();
	void process();
	void output();
}
class MviVo {
	//Fields
	private String rsnum;
	private String name;
	private String mvcode;
	private int num;
	private double fee;
	private char tcode;
	
	private String mvname;
	private double totfee;
	private double scharge;
	private double kum;
	private String tname;
	//Constructor
	public MviVo(String rsnum, String name, String mvcode, int num, double fee, char tcode) {
		this.rsnum = rsnum;
		this.name = name;
		this.mvcode = mvcode;
		this.num = num;
		this.fee = fee;
		this.tcode = tcode;
	}
	//Getter/Setter
	public String getRsnum() {
		return rsnum;
	}
	public void setRsnum(String rsnum) {
		this.rsnum = rsnum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMvcode() {
		return mvcode;
	}
	public void setMvcode(String mvcode) {
		this.mvcode = mvcode;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public char getTcode() {
		return tcode;
	}
	public void setTcode(char tcode) {
		this.tcode = tcode;
	}
	public String getMvname() {
		return mvname;
	}
	public void setMvname(String mvname) {
		this.mvname = mvname;
	}
	public double getTotfee() {
		return totfee;
	}
	public void setTotfee(double totfee) {
		this.totfee = totfee;
	}
	public double getScharge() {
		return scharge;
	}
	public void setScharge(double scharge) {
		this.scharge = scharge;
	}
	public double getKum() {
		return kum;
	}
	public void setKum(double kum) {
		this.kum = kum;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	//toString
	@Override
	public String toString() {
		return "MviVo [rsnum=" + rsnum + ", name=" + name + ", mvcode=" + mvcode + ", num=" + num + ", fee=" + fee
				+ ", tcode=" + tcode + ", mvname=" + mvname + ", totfee=" + totfee + ", scharge=" + scharge + ", kum="
				+ kum + ", tname=" + tname + "]";
	}
}
class Mv implements Mpo {
	List<MviVo> mvList = new ArrayList<>();
	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);
		System.out.println("예매번호,이름,영화코드,관람인원,기본요금,시간대코드");
		int i = 0;
		while (true) {
			String line = sc.nextLine();
			if (line.equals("quit")) {
				System.out.println();
				break;
			}
			String[] li = line.trim().split(",");
			String rsnum = li[0].trim();
			String name = li[1].trim();
			String mvcode = li[2].trim();
			int num = Integer.parseInt(li[3].trim());
			double fee = Double.parseDouble(li[4].trim());
			char tcode = li[5].trim().charAt(0);	
			
			MviVo mvo = new MviVo(rsnum, name, mvcode, num, fee, tcode);
			
			mvList.add(mvo);
		}
	}

	@Override
	public void process() {
		for (MviVo mviVo : mvList) {
			 // 총요금 = 기본요금 * 관람인원
			double fee = mviVo.getFee();
			int num = mviVo.getNum();
			double totfee = fee * num;
			mviVo.setTotfee(totfee);
			 // 할증액 = 시간대코드에 따라 총요금의 비율 적용			
			 // M:조조 0%, D:일반 5%, N:심야 10%
			char tcode = mviVo.getTcode();
			double scharge = 0.0;
			switch (tcode) {
			case 'M': scharge = 0.0; break;
			case 'D': scharge = totfee * 0.05; break;
			case 'N': scharge = totfee * 0.1; break;
			}		
			mviVo.setScharge(scharge);
			 // 최종결제금액 = 총요금 + 할증액
			double kum = totfee + scharge;
			mviVo.setKum(kum);
			 // 영화명 = A1:액션대작, R1:로맨스극장, C1:코미디쇼, H1:공포특집
			Map<String, String> map1 = new HashMap<>();
			map1.put("A1", "액션대작");
			map1.put("R1", "로맨스극장");
			map1.put("C1", "코미디쇼");
			map1.put("H1", "공포특집");
			String mvcode = mviVo.getMvcode();
			String mvname = map1.get(mvcode);
			mviVo.setMvname(mvname);
			 // 시간대명 = M:조조, D:일반, N:심야
			Map<Character, String> map2 = new HashMap<>();
			map2.put('M', "조조");
			map2.put('D', "일반");
			map2.put('N', "심야");
			String tname = map2.get(tcode);
			mviVo.setTname(tname);
		}
	}

	@Override
	public void output() {
		Comparator<MviVo> compDesc = new Comparator<MviVo>() {
			
			public int compare(MviVo o1, MviVo o2) {
				return (int) (o2.getKum() - o1.getKum());
			}
		};
		
		Collections.sort(mvList, compDesc);
		
		String title = "예매번호 이름 영화명 총요금 할증액 최종결제금액 시간대명";
		System.out.println(title);
		for (MviVo mviVo : mvList) {
			String fmt = "%s %s %s %.2f %.2f %.2f %s";
			String rsnum = mviVo.getRsnum();
			String name = mviVo.getName();
			String mvname = mviVo.getMvname();
			double totfee = mviVo.getTotfee();
			double scharge = mviVo.getScharge();
			double kum = mviVo.getKum();
			String tname = mviVo.getTname();
			
			String msg = String.format(fmt, rsnum, name, mvname, totfee, scharge, kum, tname);
			System.out.println(msg);
		}
	}
	
}
public class TestMovie {
	public static void main(String[] args) {
		Mv mv = new Mv();
		mv.input();
		mv.process();
		mv.output();
	}

}
