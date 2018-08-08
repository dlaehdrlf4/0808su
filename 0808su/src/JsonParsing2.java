import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParsing2 {
	public static void main(String[] args) {
		//다운로드 받을 문자열을 저장할 변수
				String json = "";
				Thread th = new Thread() {
					public void run(){
						//스레드로 수행할 내용
						try {
							//다운로드 받을 URL 생성
							URL url = new URL("https://apis.daum.net/local/v1/search/category.json?apikey=465b06fae32febacbc59502598dd7685&code=AT4&location=37.514322572335935,127.06283102249932&radius=20000\r\n" + 
									"");
							//URL 연결 객체 생성
							HttpURLConnection con = (HttpURLConnection)url.openConnection();
							// 옵션 설정
							// 캐시 설정 여부 - 다운로드 받아놓고 다음에 다운로드 받은 데이터를 이용할 
							// 것인지 여부 설정
							con.setUseCaches(false);
							// 얼마동안 접속을 시도해 볼 것인지 설정
							con.setConnectTimeout(30000);
							
							//문자열을 읽기 위한 스트림 생성
							BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
							
							//줄 단위로 데이터를 읽어서 sb에 추가
							StringBuilder sb = new StringBuilder();
							while(true) {
								String line = br.readLine();
								if(line == null) break;
								sb.append(line);
							}
							//sb의 내용을 json에 대입
							String json = sb.toString();
							System.out.println(json);
							
							//JSonobject 객체로 변환
							JSONObject main = new JSONObject(json);
							//System.out.println(main)
							
							//channel 키의 객체 가져오기
							JSONObject channel = main.getJSONObject("channel");
							System.out.println(channel);
							
							// item 배열을 가져오기
							JSONArray item = channel.getJSONArray("item");
							
							int i = 0;
							while(i < item.length()) {
								JSONObject a = item.getJSONObject(i);
								String address = a.getString("title") +  ":" + a.getString("address");
								System.out.println(address);
								i = i + 1;
								
							}
							
							
							
						}catch(Exception e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						} 
						
					}
				};
				th.start();
	}
}
