package com.timmy.baselib.http.config;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * HTTPS请求证书校验辅助类
 */
public class SslHelper {

    private static String client_key_path = "client.bks";//获取保存在assets资源文件下的客户端证书
    private static String truststore_key_path = "truststore.bks";
    private static String client_password = "123456";//client.bks 密码
    private static String trust_password= "123456";

    public static SSLSocketFactory getSslSocketFactory(Context context){
        SSLSocketFactory sslSocketFactory = null;

        try {
            //服务器需要验证的客户端证书,其实就是客户端的keystore
            KeyStore keyStore = KeyStore.getInstance("BKS");//客户端信任的服务器证书
            KeyStore trustStore = KeyStore.getInstance("BKS");

            //读取证书
            InputStream clientIs = context.getAssets().open(client_key_path);
            InputStream trustIs = context.getAssets().open(truststore_key_path);

            //加载证书
            keyStore.load(clientIs,client_password.toCharArray());
            keyStore.load(trustIs,trust_password.toCharArray());

            clientIs.close();
            trustIs.close();

            //初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            trustManagerFactory.init(trustStore);
            keyManagerFactory.init(keyStore,client_password.toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(),trustManagerFactory.getTrustManagers(),null);

            sslSocketFactory = sslContext.getSocketFactory();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslSocketFactory;
    }

}
