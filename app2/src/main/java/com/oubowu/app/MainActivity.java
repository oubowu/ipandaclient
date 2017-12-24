package com.oubowu.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //        /**/
        //
        //        Observable.create((ObservableOnSubscribe<Integer>) e -> {
        //            Log.e(TAG, "Observable emit 1" + "\n");
        //            e.onNext(1);
        //            Log.e(TAG, "Observable emit 2" + "\n");
        //            e.onNext(2);
        //            Log.e(TAG, "Observable emit 3" + "\n");
        //            e.onNext(3);
        //            e.onComplete();
        //            Log.e(TAG, "Observable emit 4" + "\n");
        //            e.onNext(4);
        //        }).subscribe(new Observer<Integer>() {
        //
        //            private int i;
        //            //  2.x 中有一个 Disposable 概念，这个东西可以直接调用切断，可以看到，当它的 isDisposed() 返回为 false 的时候，接收器能正常接收事件，
        //            // 但当其为 true 的时候，接收器停止了接收。所以可以通过此参数动态控制接收事件了
        //            private Disposable mDisposable;
        //
        //            @Override
        //            public void onSubscribe(@NonNull Disposable d) {
        //                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
        //                mDisposable = d;
        //            }
        //
        //            @Override
        //            public void onNext(@NonNull Integer integer) {
        //                Log.e(TAG, "onNext : value : " + integer + "\n");
        //                i++;
        //                if (i == 2) {
        //                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
        //                    mDisposable.dispose();
        //                    Log.e(TAG, "onNext : isDisposable : " + mDisposable.isDisposed() + "\n");
        //                }
        //            }
        //
        //            @Override
        //            public void onError(@NonNull Throwable e) {
        //                Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                Log.e(TAG, "onComplete" + "\n");
        //            }
        //        });
        //
        //
        //        /**/
        //        Observable.create((ObservableOnSubscribe<Integer>) e -> {
        //            e.onNext(1);
        //            e.onNext(2);
        //            e.onNext(3);
        //        }).map(integer -> "This is result " + integer).subscribe(s -> {
        //            Log.e(TAG, "accept : " + s + "\n");
        //        });

        /**/
        //        Observable.zip(getStringObservable(), getIntegerObservable(), (s, integer) -> s + integer).subscribe(s -> {
        //            Log.e(TAG, "zip : accept : " + s + "\n");
        //        });

        /**/
        //        Observable.concat(Observable.just(1,2,3),Observable.just(4,5,6)).subscribe(integer -> {
        //            Log.e(TAG, "concat : "+ integer + "\n" );
        //        });

        //        Observable.create((ObservableOnSubscribe<Integer>) e -> {
        //            e.onNext(1);
        //            e.onNext(2);
        //            e.onNext(3);
        //        }).flatMap(integer -> {
        //            List<String> list = new ArrayList<>();
        //            for (int i = 0; i < 3; i++) {
        //                list.add("I am value " + integer);
        //            }
        //            int delayTime = (int) (1 + Math.random() * 10);
        //            return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
        //        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
        //            Log.e(TAG, "flatMap : accept : " + s + "\n");
        //        });

        //        Observable.create((ObservableOnSubscribe<Integer>) e -> {
        //            e.onNext(1);
        //            e.onNext(2);
        //            e.onNext(3);
        //        }).concatMap(integer -> {
        //            List<String> list = new ArrayList<>();
        //            for (int i = 0; i < 3; i++) {
        //                list.add("I am value " + integer);
        //            }
        //            int delayTime = (int) (1 + Math.random() * 10);
        //            return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
        //        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(s -> {
        //            Log.e(TAG, "flatMap : accept : " + s + "\n");
        //        });

        /**/
        //        Observable.just(1, 1, 1, 2, 2, 3, 4, 5).distinct().subscribe(integer -> Log.e(TAG, "distinct : " + integer + "\n"));

        /**/
        //        Observable.just(1, 20, 65, -5, 7, 19).filter(integer -> integer >= 10).subscribe(integer -> Log.e(TAG, "filter : " + integer + "\n"));

        /**/
        // buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个  Observable
        //        Observable.just(1,2,3,4,5).buffer(3,2).subscribe(integers -> {
        //            Log.e(TAG, "buffer size : " + integers.size() + "\n");
        //            Log.e(TAG, "buffer value : " );
        //            for (Integer i : integers) {
        //                Log.e(TAG, i + "");
        //            }
        //            Log.e(TAG, "\n");
        //        });

        /**/
        //        Log.e(TAG, "timer start : " + getNowStrTime() + "\n");
        //        Observable.timer(2, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
        //            Log.e(TAG, "timer :" + aLong + " at " + getNowStrTime() + "\n");
        //        });

        /**/
        //        Log.e(TAG, "interval start : " + getNowStrTime() + "\n");
        //        // 接受三个参数，分别是第一次发送延迟，间隔时间，时间单位
        //        mDisposable = Observable.interval(3, 2, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
        //                .subscribe(aLong -> {
        //                    Log.e(TAG, "interval :" + aLong + " at " + getNowStrTime() + "\n");
        //                });

        /**/
        //        Observable.just(1,2,3,4).doOnNext(integer -> {
        //            Log.e(TAG, "doOnNext 保存 " + integer + "成功" + "\n");
        //        }).subscribe(integer -> {
        //            Log.e(TAG, "doOnNext :" + integer + "\n");
        //        });

        /**/
        //        // skip 很有意思，其实作用就和字面意思一样，接受一个 long 型参数 count ，代表跳过 count 个数目开始接收。
        //        Observable.just(1,2,3,4,5).skip(2).subscribe(integer -> {
        //            Log.e(TAG, "skip : "+integer + "\n");
        //        });

        /**/
        //        // take，接受一个 long 型参数 count ，代表至多接收 count 个数据。
        //        Flowable.fromArray(1,2,3,4,5).take(2).subscribe(integer -> {
        //            Log.e(TAG, "accept: take : "+integer + "\n" );
        //        });

        /**/
        //        // 顾名思义，Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()。
        //        Single.just(new Random().nextInt()).subscribe(new SingleObserver<Integer>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            @Override
        //            public void onSuccess(Integer integer) {
        //                Log.e(TAG, "single : onSuccess : "+integer+"\n" );
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                Log.e(TAG, "single : onError : "+e.getMessage()+"\n");
        //            }
        //        });

        /**/
        //        // 去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
        //        Observable.create((ObservableOnSubscribe<Integer>) e -> {
        //            e.onNext(1); // skip
        //            Thread.sleep(400);
        //            e.onNext(2); // deliver
        //            Thread.sleep(505);
        //            e.onNext(3); // skip
        //            Thread.sleep(100);
        //            e.onNext(4); // deliver
        //            Thread.sleep(605);
        //            e.onNext(5); // deliver
        //            Thread.sleep(510);
        //            e.onComplete();
        //            // 去除发送间隔时间小于 500 毫秒的发射事件，所以 1 和 3 被去掉了
        //        }).debounce(500, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {
        //            Log.e(TAG, "debounce :" + integer + "\n");
        //        });

        /**/
        //        // 简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable
        //        Observable<Integer> observable = Observable.defer(() -> Observable.just(1, 2, 3));
        //
        //        observable.subscribe(new Observer<Integer>() {
        //            @Override
        //            public void onSubscribe(Disposable d) {
        //
        //            }
        //
        //            @Override
        //            public void onNext(Integer integer) {
        //                Log.e(TAG, "defer : " + integer + "\n");
        //            }
        //
        //            @Override
        //            public void onError(Throwable e) {
        //                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
        //            }
        //
        //            @Override
        //            public void onComplete() {
        //                Log.e(TAG, "defer : onComplete\n");
        //            }
        //        });

        /**/
        //        // last 操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
        //        Observable.just(1,2,3).last(4).subscribe(integer -> {
        //            Log.e(TAG, "last : " + integer + "\n");
        //        });

        /**/
        //        // merge 顾名思义，熟悉版本控制工具的你一定不会不知道 merge 命令，而在 Rx 操作符中，merge 的作用是把多个 Observable 结合起来，接受可变参数，
        //        // 也支持迭代器集合。注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
        //        Observable.merge(Observable.just(1,2),Observable.just(3,4,5 )).subscribe(integer -> {
        //            Log.e(TAG, "accept: merge :" + integer + "\n" );
        //        });

        /**/
        //        // reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值
        //        Observable.just(1, 2, 3, 4).reduce(new BiFunction<Integer, Integer, Integer>() {
        //            @Override
        //            public Integer apply(Integer integer, Integer integer2) throws Exception {
        //                return integer + integer2;
        //            }
        //        }).subscribe(integer -> {
        //            // 中间采用 reduce ，支持一个 function 为两数值相加
        //            Log.e(TAG, "accept: reduce : " + integer + "\n");
        //        });

        /**/
        //        // scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出
        //        Observable.just(1, 2, 3, 4).scan((integer, integer2) -> integer + integer2).subscribe(integer -> {
        //            Log.e(TAG, "accept: scan " + integer + "\n");
        //        });

        /**/
        //        // 按照实际划分窗口，将数据发送给不同的 Observable
        //        Log.e(TAG, "window\n");
        //        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
        //                .take(15) // 最多接收15个
        //                .window(3, TimeUnit.SECONDS) //
        //                .subscribeOn(Schedulers.io()) //
        //                .observeOn(AndroidSchedulers.mainThread()) //
        //                .subscribe(longObservable -> {
        //                    Log.e(TAG, "Sub Divide begin...\n");
        //                    longObservable.subscribeOn(Schedulers.io()) //
        //                            .observeOn(AndroidSchedulers.mainThread()) //
        //                            .subscribe(aLong -> {
        //                                Log.e(TAG, "Next:" + aLong + "\n");
        //                            });
        //                });

        // 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
        // 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
        // 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
            }
        }).observeOn(Schedulers.io()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private String getNowStrTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private Observable<String> getStringObservable() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                e.onNext("A");
                Log.e(TAG, "String emit : A \n");
                e.onNext("B");
                Log.e(TAG, "String emit : B \n");
                e.onNext("C");
                Log.e(TAG, "String emit : C \n");
            }
        });
    }

    private Observable<Integer> getIntegerObservable() {
        return Observable.create(e -> {
            if (!e.isDisposed()) {
                e.onNext(1);
                Log.e(TAG, "Integer emit : 1 \n");
                e.onNext(2);
                Log.e(TAG, "Integer emit : 2 \n");
                e.onNext(3);
                Log.e(TAG, "Integer emit : 3 \n");
                e.onNext(4);
                Log.e(TAG, "Integer emit : 4 \n");
                e.onNext(5);
                Log.e(TAG, "Integer emit : 5 \n");
            }
        });
    }

}
