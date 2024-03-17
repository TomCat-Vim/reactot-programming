package com.atguigu.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.time.Duration;

public class FluxDemo {

    public static void main(String[] args) {

        Flux.just(1, 2, 3, 4, 0)
                .map(ele -> 10 / ele)
                .doOnError(error -> {
                    System.out.println("流出错：" + error.getMessage());
                })
                .subscribe(ele -> {
                    System.out.println("数据流元素： " + ele);
                });


    }

    /**
     * 测试flux多个元素的处理流
     * @throws InterruptedException
     */
    public static void fluxTest() throws InterruptedException {

        Flux<Integer> just = Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .doOnComplete(() -> {
                    System.out.println("Flux流被订阅者处理完");
                })
                .doOnCancel(() -> {
                    System.out.println("Flux流的订阅被取消");
                })
                .doOnError(error -> {
//                    对流本身进行处理的时候，才会触发这个方法
                    System.out.println("流出错了 " + error.getMessage());
                })
                .doOnNext(ele -> {
                    System.out.println("doOnNext: " + ele);
                });

        just.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected Subscription upstream() {
                return super.upstream();
            }

            @Override
            public boolean isDisposed() {
                return super.isDisposed();
            }

            @Override
            public void dispose() {
                super.dispose();
            }

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                super.hookOnSubscribe(subscription);
            }

            @Override
            protected void hookOnNext(Integer value) {
                super.hookOnNext(value);
                System.out.println("订阅下一个元素: " + value);

                if (value < 5) {
                    System.out.println("订阅的元素是： " + value);
                    if (value == 3) {
                        int i = value / 0;
                    }
                }

            }

            @Override
            protected void hookOnComplete() {
                super.hookOnComplete();

                System.out.println("订阅者完成订阅");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
                System.out.println("hookOnError 订阅者订阅出错");
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
                System.out.println("hookOnCancel 订阅者订阅取消");
            }

            @Override
            protected void hookFinally(SignalType type) {
                super.hookFinally(type);

            }
        });

        Thread.sleep(5000);

    }

}
