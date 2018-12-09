package github.a3sung.dreammemo.serverconnector;

/**
 * 에러가 발생했을 경우 콜백되는 함수입니다.
 * Created by JoMingyu on 2018-06-30.
 */

public interface ErrorCallback {

    public static final int
            ERR_CONECTION_TIMEOUT = 1000,
            ERR_MALFORM_URL = 1001,
            ERR_ETC = 1002;

    public void errCallback(int resultCode);

}