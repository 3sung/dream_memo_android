package github.a3sung.dreammemo.serverconnector;

/**
 * 서버에 요청을 보내고 결과가 올 경우 반환되는 콜백 인터페이스
 * Created by JoMingyu on 2018-06-29.
 */

public interface RequestCallback {

    public void requestCallback(String result);

}