package com.android.kreators.tortoise.net;

import android.content.Context;

import com.android.kreators.tortoise.factory.PreferenceFactory;
import com.android.kreators.tortoise.manager.CacheManager;
import com.android.kreators.tortoise.model.auth.UserCacheItem;
import com.android.nobug.constant.HeaderProperty;
import com.android.nobug.util.log;

import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    // OkHttp Client
    private static OkHttpClient client, unsafeClient;

    //  =====================================================================================

    public static OkHttpClient getOkHttpClient(final Context context) {
        client = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor(context))
                .build();
        return client;
    }

    private static OkHttpClient getUnsafeOkHttpClient(final Context context) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            unsafeClient = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .addInterceptor(getInterceptor(context))
                    .build();
            return unsafeClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //  =========================================================================================

    public static BaseService with(final Context context, String domain) {
        BaseService baseService = new Retrofit.Builder()
                .client(getOkHttpClient(context))
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BaseService.class);
        return baseService;
    }

    public static BaseService withUnsafe(final Context context) {
        BaseService baseService = new Retrofit.Builder()
                .client(getUnsafeOkHttpClient(context))
                .baseUrl( CacheManager.getInstance().getVersionItem().host )
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BaseService.class);
        return baseService;
    }

    private static Interceptor getInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                UserCacheItem item = PreferenceFactory.getUserItem(context);

                String authToken = "";
                String facebookToken = "";

                if( item != null ) {
                    authToken = item.auth_token != null ? item.auth_token : "";
                    facebookToken = item.facebook_token != null ? item.facebook_token : "";
                }

                String agent = HeaderProperty.getUserAgent(context);

                log.show( "auth token : " + item.auth_token );

                Request original = chain.request();
                Request request = original.newBuilder()
                        .header(HeaderProperty.AUTH_TOKEN, authToken)
                        .header(HeaderProperty.FACEBOOK_AUTH_TOKEN, facebookToken)
                        .header(HeaderProperty.USER_AGENT, agent)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        };
    }



}




