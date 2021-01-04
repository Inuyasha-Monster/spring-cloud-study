package com.djl.springcloud.service.Impl;

import com.djl.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

/**
 * @author djl
 * @create 2021/1/4 14:40
 * 表示当前这个类是source负责生产消息并且发送channel
 */
@EnableBinding(Source.class)
public class MessageProviderImpl implements IMessageProvider {

    /**
     * 使用信道发送消息
     */
    private final MessageChannel output;

    public MessageProviderImpl(MessageChannel output) {
        this.output = output;
    }

    /**
     * 这里,就会调用send方法,将消息发送给channel,然后channel将消费发送给binder,然后发送到rabbitmq中
     *
     * @return
     */
    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("****************serial:" + serial);
        return serial;
    }
}
