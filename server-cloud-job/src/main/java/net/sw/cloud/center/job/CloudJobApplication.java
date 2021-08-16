package net.sw.cloud.center.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 分布式任务调度
 * @author tom
 */
@SpringBootApplication
public class CloudJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudJobApplication.class,args);

        System.out.println();
    }



}
