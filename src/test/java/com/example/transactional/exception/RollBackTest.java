package com.example.transactional.exception;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollBackTest {

    @Autowired RollBackService service;

    @Test
    void runtimeException(){
        Assertions.assertThatThrownBy(() -> service.runtimeException())
                        .isInstanceOf(RuntimeException.class);
    }
    @Test
    void checkedException(){
        Assertions.assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(MyException.class);
    }

    @Test
    void rollbackFor(){
        Assertions.assertThatThrownBy(() -> service.runtimeException())
                .isInstanceOf(MyException.class);
    }

    @TestConfiguration
    static class RollbackTestConfig {
        @Bean
        RollBackService rollBackService(){
            return new RollBackService();
        }
    }

    @Slf4j
    static class RollBackService {

        //런타임 예외 발생: 롤백
        @Transactional
        public void runtimeException(){
            log.info("call runtime Exception");
            throw new RuntimeException();
        }

        //체크 예외 발생 : 커밋
        @Transactional
        public void checkedException() throws MyException{
            log.info("call checkedException");
            throw new MyException();
        }

        //체크 예외 rollbackFor 지장 : 롤백
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException {
            throw new MyException();
        }
    }


    static class MyException extends Exception {
    }
}
