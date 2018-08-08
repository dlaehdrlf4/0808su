import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLparsing {

	public static void main(String[] args) {
		//스레드 만들기
		Thread thread = new Thread() {
			public void run() {
				try {
					URL url = new URL("https://tv.naver.com/");
					//URL 연결 객체 생성
					HttpURLConnection con = (HttpURLConnection)url.openConnection();
					// 옵션 설정
					// 캐시 설정 여부 - 다운로드 받아놓고 다음에 다운로드 받은 데이터를 이용할 
					// 것인지 여부 설정
					con.setUseCaches(false);
					// 얼마동안 접속을 시도해 볼 것인지 설정
					con.setConnectTimeout(30000);
					
					//문자열을 읽을 수 있는 스트림 생성
					BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					
					//다운로드 받는 문자열을 중간 저장할 인스턴스를 생성
					//String에 바로 추가하면 메모리 낭비가 발생합니다.
					//String은 자기 자신에게 추가할 수 없기 때문에
					//기존 내용을 복사해서 추가하기 때문에
					
					StringBuilder sb = new StringBuilder();
					while(true) {
						//한줄을 읽기
						String line = br.readLine();
						//읽은 게 없으면 종료
						if(line == null) break;
						sb.append(line + "\n");
						
					}
					//다 읽은 데이터는 String으로 변환
					String html = sb.toString();
					//sb 메모리 정리 // 이름이 없어지면 메모리 정리가 된다.
					sb = null;
					//연결 객체 정리
					br.close();
					con.disconnect();
					//System.out.println(html);
					
					//HTML 루트를 찾아줍니다.
					Document doc = Jsoup.parse(html); // doc가 html 맨처음으로 간거다.
					//h2 태그 전체를 가져옵니다.
					Elements elements = doc.getElementsByTag("tooltip");
					
					//Elements elements =doc.getElementsByClass("screen_out");
					
					
					
					int i = 0;
					while(i<elements.size()) {
						// 데이터 1개 가져오기
						Element element = elements.get(i);
						// 태그 안의 내용출력
						System.out.println(element.text());
						i= i+1;
					}
					
					//Element element = doc.getElementById("mainServiceTitle");
					//System.out.println(element.text());
					
					
					
				}catch(Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
			}
		};
		//스레드 시작
		thread.start();
	}

}
