//package com.acmenxd.retrofit;
//
//
//import com.acmenxd.retrofit.converter.CustomConverterFactory;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//
///**
// * Created by alan on 2017/9/30.
// */
//
//public class ServiceGenerator {
//
////    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
//
//    private static final String BASE_URL = "https://account.ftoutiao.cn/";
//
////    private static Cache cache = new Cache(BaseApplication.getInstance().getCacheDir(), MAX_CACHE_SIZE);
//
//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                   // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(CustomConverterFactory.create());
//
//    private static HttpLoggingInterceptor loggingInterceptor =
//            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
//
//    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
//            .addInterceptor(new RequestInterceptor());
//
//    private static Retrofit retrofit = builder.build();
//
//    public static <S> S createService(Class<S> serviceClass) {
//        if (BookConfig.isDebug() && !httpClientBuilder.interceptors().contains(loggingInterceptor)) {
//            httpClientBuilder.addInterceptor(loggingInterceptor);
//            builder.client(httpClientBuilder.build());
//            retrofit = builder.build();
//        }
//
//        return retrofit.create(serviceClass);
//    }
//
//    public static Retrofit retrofit() {
//        return retrofit;
//    }
//
//}
