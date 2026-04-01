package matter1;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


//모든 기능은 class에 구현한다. //입력 DATA 1줄을 입력받고 결과 1줄로 출력합니다
interface Bpo {
	void input();
	void process();
	void output();
}
class BookVo {
	//입력data : 회원번호,이름,도서명,대출일,반납예정일,회원구분
	//출력 : 회원번호,이름,도서명,대출일수,대출등급,회원구분명
	private int num;
	private String name;
	private String bname;
	private String cdate;
	private int rcdate;
	private char member;
	
	private int cnum;
	private String cgrade;
	private String mname;
	
	public BookVo(int num, String name, String bname, String cdate, int rcdate, char member) {
		this.num = num;
		this.name = name;
		this.bname = bname;
		this.cdate = cdate;
		this.rcdate = rcdate;
		this.member = member;
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public int getRcdate() {
		return rcdate;
	}

	public void setRcdate(int rcdate) {
		this.rcdate = rcdate;
	}

	public char getMember() {
		return member;
	}

	public void setMember(char member) {
		this.member = member;
	}

	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}

	public String getCgrade() {
		return cgrade;
	}

	public void setCgrade(String cgrade) {
		this.cgrade = cgrade;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	@Override
	public String toString() {
		return "BookVo [num=" + num + ", name=" + name + ", bname=" + bname + ", cdate=" + cdate + ", rcdate=" + rcdate
				+ ", member=" + member + ", cnum=" + cnum + ", cgrade=" + cgrade + ", mname=" + mname + "]";
	}
}
class BookCheck implements Bpo {
	private BookVo bVo;
	@Override
	public void input() {
		Scanner sc = new Scanner(System.in);
		System.out.println("회원번호,이름,도서명,대출일,반납예정일,회원구분");
		String line = sc.nextLine();
		String[] li = line.trim().split(",");
		int num = Integer.parseInt(li[0].trim());
		String name = li[1].trim();
		String bname = li[2].trim();
		String cdate = li[3].trim();
		int rcdate = Integer.parseInt(li[4].trim());
		char member = li[5].trim().charAt(0);
		
		bVo = new BookVo(num, name, bname, cdate, rcdate, member);
	}

	@Override
	public void process() {
		//대출일수 = 현재날짜 기준으로 대출일로부터 지난 일수
		LocalDate today = LocalDate.now();
		String cdate = bVo.getCdate();
		
		int cyear = Integer.parseInt(cdate.substring(0, 4));
		int cmonth = Integer.parseInt(cdate.substring(4, 6));
		int cday = Integer.parseInt(cdate.substring(6, 8));
		
		LocalDate loandate = LocalDate.of(cyear, cmonth, cmonth);
		long daysBetween = ChronoUnit.DAYS.between(loandate, today);
		bVo.setCnum((int) daysBetween);
		//대출등급 = 7일 이하 정상, 8~14일 주의, 15일 이상 연체
		String cgrade = "";
		int cnum = bVo.getCnum();
		if(cnum <= 7) {
			cgrade = "정상";
		} else {
			if (cnum >= 8 && cnum <= 14) {
				cgrade = "주의";
			} else {
				if (cnum >= 15) {
					cgrade = "연체";
				}
			}
		}
		bVo.setCgrade(cgrade);
		//회원구분명 = A:일반회원, B:우수회원, C:특별회원
		char member = bVo.getMember();
		String mname = "";
		switch (member) {
		case 'A': mname = "일반회원"; break;
		case 'B': mname = "우수회원"; break;
		case 'C': mname = "특별회원"; break;
		default:
			break;
		}
		bVo.setMname(mname);
		//날짜 형식은 yyyyMMdd
	}

	@Override
	public void output() {
		String title = "회원번호 이름 도서명 대출일수 대출등급 회원구분명";
		String fmt = "%d %s %s %d %s %s";
		int num = bVo.getNum();
		String name = bVo.getName();
		String bname = bVo.getBname();
		int cnum = bVo.getCnum();
		String cgrade = bVo.getCgrade();
		String mname = bVo.getMname();
		System.out.printf(fmt, num, name, bname, cnum, cgrade, mname);
	}
	
}
public class TestBook {
	public static void main(String[] args) {
		BookCheck bc = new BookCheck();
		bc.input();
		bc.process();
		bc.output();
	}
}
