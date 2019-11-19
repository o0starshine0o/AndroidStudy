//package com.abelhu.androidstudy.rx;
//
//import com.xianlai.protostar.bean.KvBean;
//import com.xianlai.protostar.net.bean.Result;
//import com.xianlai.protostar.net.rx.TransformUtils;
//import com.xianlai.protostar.net.service.DomainService;
//
//import java.util.Map;
//
//import io.reactivex.Observable;
//
//public class Rx {
//
//    public static Observable<KvBean.DataBean> getKv(Map<String, String> params){
//        return RetrofitManager.getInstance().getDefautService().getKv(params).compose(TransformUtils.dataSchedulers());
//    }
//
//
//
//    /**
//     * 获取DefautService
//     */
//    public static DomainService service() {
//        RetrofitManager manager = RetrofitManager.getInstance();
//        return manager.getDefautService();
//    }
//
//    public static <R> Observable<R> compose(Observable<Result<R>> value){
//        return value.compose(TransformUtils.dataSchedulers());
//    }
//}



//    private SingleSource<Boolean> asyncNoRelySdk(){
//        return Single.just(true);
//    }
//
//    private void asyncRelySdk(){
//
//    }

//        asyncInitSdk();
//        Single.just(true).flatMap(e->asyncNoRelySdk()).subscribe(e-> asyncRelySdk());