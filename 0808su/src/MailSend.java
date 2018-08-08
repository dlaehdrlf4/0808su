import org.apache.commons.mail.SimpleEmail;

public class MailSend {

	public static void main(String[] args) {
		try {
			// 텍스트 메일을 보낼 수 있는 클래스의 객체 만들기
			SimpleEmail email = new SimpleEmail();
			
			//서버 설정
			email.setAuthentication("dlaehdrlf4", "비밀번호");
			email.setHostName("smtp.naver.com");
			email.setSmtpPort(587); //포트번호
			//메일 보안 설정 옵션 : 메일이 암호화 되서 전송됩니다.
			email.setSSL(true);
			email.setTLS(true);
			email.setFrom("dlaehdrlf4@naver.com","donggil"); // 보낸사람 이름<주소>
			email.setCharset("utf-8");
			
			//받는 설정
			email.addTo("dlaehdrlf4@hanmail.net");
			email.setSubject("메일 보내기 연습이다.");
			email.setMsg("메일 갔냐?");
			
			//보내기
			email.send();
			System.out.println("메일보내기 성공");
			
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
