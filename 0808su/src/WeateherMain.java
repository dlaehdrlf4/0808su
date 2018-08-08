import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeateherMain {

	public static void main(String[] args) {
		//다운로드 받을 문자열을 저장할 변수
				String json = "";
				Thread th = new Thread() {
					public void run(){
						//스레드로 수행할 내용
						try {
							//다운로드 받을 URL 생성
							URL url = new URL("http://www.hani.co.kr/rss/sports/");
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
							//sb의 내용을 xml에 대입
							String xml = sb.toString();
							//System.out.println(xml);
							
							//xml 문자열을 파싱할 수 있는 객체를 생성
							DocumentBuilderFactory factory = 
									DocumentBuilderFactory.newInstance();
							DocumentBuilder builder = factory.newDocumentBuilder();
							
							// 파싱할 문자열을 스트림으로 변환
							InputStream is = new ByteArrayInputStream(xml.getBytes());
							//문자열을 파싱 수행 - org.w3c.dom
							Document doc = builder.parse(is); 
							//루트 찾기
							Element root = doc.getDocumentElement();
							//System.out.println(root);
							
							//tmx 태그 전부 찾기
							NodeList list = root.getElementsByTagName("title");
							NodeList list1 = root.getElementsByTagName("description");
							//System.out.println(list);
							
							int i = 0;
							while(i<list.getLength()){
								//Thread.sleep(4000); 
								Node item = list.item(i);
								Node item1 = list1.item(i);
								//첫번째 자식 찾기
								Node child = item.getFirstChild();
								Node child1 = item1.getFirstChild();
								//데이터 찾기
								String tmx = child.getNodeValue();
								String tmx1 = child1.getNodeValue();
								//replaceAll로 없애기
								tmx1 = tmx1.replaceAll("</tr>", "");
								System.out.println(tmx + ":" + tmx1);
								
								i = i+1;
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
