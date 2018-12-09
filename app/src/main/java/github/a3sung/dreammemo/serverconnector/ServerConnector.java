package github.a3sung.dreammemo.serverconnector;

import android.os.AsyncTask;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 서버와 통신하는 클래스. 싱글톤 패턴으로 객체를 제공한다.
 * Created by JoMingyu on 2018-06-28.
 */

public class ServerConnector {

    // 싱글톤 패턴
    private ServerConnector() {};
    private static ServerConnector instatnce = null;
    public static ServerConnector getInstatnce(){
        if (instatnce == null) instatnce = new ServerConnector();
        return instatnce;
    }

    public static final int CONNECTION_TIME = 100000;
    public static final int READ_TIME = 100000;
    public static final String BASE_URL = "http://cau20162990.iptime.org:3000/";

    /**
     * Url 주소와 RequestCallback 인터페이스를 구현하여 전달하면,
     * 해당 주소에서 Response를 받아와 RequestCallback의 requestCallback 메소드의
     * result 로 전달한다.
     * @param url 요청할 페이지의 url 주소
     * @param callback Response 정보로 콜백을 준다.
     */
    public void requestGet(final String url, final RequestCallback callback){
        requestGet(url, callback, new ErrorCallback() {
            @Override
            public void errCallback(int resultCode) {}
        });
    }
    /**
     * Url 주소와 RequestCallback 인터페이스를 구현하여 전달하면,
     * 해당 주소에서 Response를 받아와 RequestCallback의 requestCallback 메소드의
     * result 로 전달한다.
     * @param url 요청할 페이지의 url 주소
     * @param requestCallback Response 정보로 콜백을 준다.
     * @param  errorCallback 에러가 나왔을 경우 콜백된다.
     */
    public void requestGet(final String url, final RequestCallback requestCallback, final ErrorCallback errorCallback){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL endpoint = null;
                HttpURLConnection conn = null;
                try {
                    endpoint = new URL(url);
                    conn = (HttpURLConnection) endpoint.openConnection();
                    conn.setConnectTimeout(CONNECTION_TIME);
                    conn.setReadTimeout(READ_TIME);
                    if (conn.getResponseCode() == 200){ // 네트워크 연결에 성공했을 경우

                        // 인풋 가져옴
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        StringBuilder sbr = new StringBuilder(); // 읽어온 스트링 저장하는 스트링빌더
                        String line = null; // 한줄씩 들어가는 임시 보관소
                        while ((line = br.readLine()) != null){
                            sbr.append(line);
                        }

                        br.close();
                        is.close();
                        // callback 함수 호출
                        requestCallback.requestCallback(sbr.toString());
                    }
                    else  {
                        errorCallback.errCallback(conn.getResponseCode());
                    }
                } catch(ConnectTimeoutException e){
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_CONECTION_TIMEOUT);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_MALFORM_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_ETC);
                } finally {
                    conn.disconnect();
                }

            }
        });
    }

    /**
     * Url 주소와 RequestCallback 인터페이스를 구현하여 전달하면,
     * 해당 주소에서 Response를 받아와 RequestCallback의 requestCallback 메소드의
     * result 로 전달한다.
     * @param url 요청할 페이지의 url 주소
     * @param data POST 방식으로 전달할 data
     * @param callback Response 정보로 콜백을 한다.
     */
    public void requestPost(final String url, final String data, final RequestCallback callback){
        requestPost(url, data, callback, new ErrorCallback() {
            @Override
            public void errCallback(int resultCode) {}
        });
    }

    /**
     * Url 주소와 RequestCallback 인터페이스를 구현하여 전달하면,
     * 해당 주소에서 Response를 받아와 RequestCallback의 requestCallback 메소드의
     * result 로 전달한다.
     * @param url 요청할 페이지의 url 주소
     * @param data POST 방식으로 전달할 data
     * @param requestCallback Response 정보로 콜백을 한다.
     * @param errorCallback 에러가 나왔을 경우 콜백을 해준다.
     */
    public void requestPost(final String url, final String data, final RequestCallback requestCallback, final ErrorCallback errorCallback){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL endpoint = null;
                HttpURLConnection conn = null;
                try {
                    endpoint = new URL(url);
                    conn = (HttpURLConnection) endpoint.openConnection();
                    conn.setConnectTimeout(CONNECTION_TIME);
                    conn.setReadTimeout(READ_TIME);
                    conn.setRequestProperty("Content-type", "application/json");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.getOutputStream().write(data.getBytes());
                    if (conn.getResponseCode() == 200){ // 네트워크 연결에 성공했을 경우

                        // 인풋 가져옴
                        InputStream is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        StringBuilder sbr = new StringBuilder(); // 읽어온 스트링 저장하는 스트링빌더
                        String line = null; // 한줄씩 들어가는 임시 보관소
                        while ((line = br.readLine()) != null){
                            sbr.append(line);
                        }

                        br.close();
                        is.close();

                        // callback 함수 호출
                        requestCallback.requestCallback(sbr.toString());
                    } else {
                        errorCallback.errCallback(conn.getResponseCode());
                    }
                } catch(ConnectTimeoutException e){
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_CONECTION_TIMEOUT);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_MALFORM_URL);
                } catch (IOException e) {
                    e.printStackTrace();
                    errorCallback.errCallback(ErrorCallback.ERR_ETC);
                } finally {
                    conn.disconnect();
                }

            }
        });

    }


}