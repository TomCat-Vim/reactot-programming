package com.atguigu.flow;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class FlowDemo {

    public static void main(String[] args) throws InterruptedException {

        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<String>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(Thread.currentThread() + "subscript start：" + subscription);
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override  //
            public void onNext(String item) {
                System.out.println(Thread.currentThread() + "subscription get data : " + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(Thread.currentThread() + "subscription get error : " + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println(Thread.currentThread() + "subscription finish");
            }
        };

        //       绑定发布者和订阅者
        publisher.subscribe(subscriber);

        for (int i = 0; i <= 10; i++) {
            publisher.submit("p-" + i);
        }


        Thread.sleep(1000);

    }


}
