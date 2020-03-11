package com.yh.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.yh.business.entity.Person;
import com.yh.business.mapper.PersonMapper;
import com.yh.business.schedule.TestTask;
import com.yh.business.service.XxService;
import com.yh.business.service.YhService;
import com.yh.business.thread.AsyncTask;
import com.yh.business.utils.AddressUtils;
import com.yh.business.utils.CommonUtils;
import com.yh.business.utils.HttpUtils;
import com.yh.business.utils.RedisUtils;
import com.yh.business.vo.ConditionInVo;
import com.yh.business.vo.ResultOutVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by idea China
 * Author: YH007
 * Time: 19:47 2020/1/22
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class YhServiceTest {

    @Test
    public void test1() {

        ConditionInVo conditionInVo = new ConditionInVo();
        conditionInVo.setName("yehan");
        conditionInVo.setPwd("007");

        String url = "http://localhost:8080/api/getDetail";
        Map<String, Object> body = CommonUtils.object2Map(conditionInVo);
        try {
            String s = HttpUtils.doHttpPost(url, null, null, body);
            JSONObject jsonObject = JSON.parseObject(s);
            String resultCode = (String) jsonObject.get("resultCode");
            ResultOutVo data = (ResultOutVo) jsonObject.get("data");
            String phone = data.getPhone();
            if ("200".equals(resultCode)) {
                System.out.println(phone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Autowired
    public PersonMapper personMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void test2() throws Exception {

        /*List<PersonDetail> allPerson = personMapper.getAllPerson();
        System.out.println(allPerson);*/
        //readFile02("C:\\Users\\YH\\OneDrive\\桌面\\新建文本文档 (2).txt");
        boolean b = redisUtils.lSet("key", "value");
        if (b) {
            System.out.println("成功");
        }
    }


    /**
     * 读取一个文本 一行一行读取
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> readFile02(String path) throws IOException {
        // 使用一个字符串集合来存储文本中的路径 ，也可用String []数组
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        // 防止路径乱码   如果utf-8 乱码  改GBK     eclipse里创建的txt  用UTF-8，在电脑上自己创建的txt  用GBK
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        while ((line = br.readLine()) != null) {
            // 如果 t x t文件里的路径 不包含---字符串       这里是对里面的内容进行一个筛选
            if (!line.matches("^\\+?[1-9][0-9]*$")) {
                list.add(line);
            }
        }
        br.close();
        isr.close();
        fis.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\YH\\OneDrive\\桌面\\b.txt"));
        for (String s : list) {
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.close();
        return list;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test4() {
        redisTemplate.opsForValue();
        RedisProperties.Jedis jedis = new RedisProperties.Jedis();


    }


    //private static Logger logger= LogManager.getLogger("RollingFileInfo");
    //private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);


    Logger LOGGER = LoggerFactory.getLogger(YhServiceTest.class);
    //Logger logger = Logger.getLogger(YhServiceTest.class.getName());


    @Test
    public void contextLoads() {
        //System.out.println();
        //日志的级别；
        //由低到高 trace<debug<info<warn<error
        //可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
        LOGGER.trace("这是trace日志...");
        LOGGER.debug("这是debug日志...");
        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root 级别
        LOGGER.info("这是info日志...");
        LOGGER.warn("这是warn日志...");
        LOGGER.error("这是error日志...");
    }

    @Autowired
    private Person person;

    @Test
    public void test11() {
        LOGGER.info("person:" + person);
    }

    @Test
    public void test12() throws UnsupportedEncodingException {
        String address = AddressUtils.getAddresses("192.168.1.103");
        LOGGER.info("获取到的ip的地区为：" + address);
    }


   /* public static void main(String[] args) throws InterruptedException {
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(5);
        MyThreadPool pool = new MyThreadPool(2, workQueue);
        for(int i = 0 ; i < 10 ; i ++){
            int num = i ;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + num + "执行");
                }
            });
        }
    }*/

    @Autowired
    private YhService yhService;

    @Test
    public void test13() {
        yhService.get();
        String json = "{\"name\": \"yh\", \"id\": \"007\"}";
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testAMQP() {

        //rabbitTemplate.send(exchange, routeKey, message);
        String exchange = "exchange.direct";
        String routeKey = "yh.news";
        Map<String, Object> message = new HashMap<>();
        message.put("msg", "这是第一个消息");
        message.put("data", Arrays.asList("helloworld", "first message"));
        rabbitTemplate.convertAndSend(exchange, routeKey, message);
    }


    @Test
    public void testAMQPReceive() {
        Object o = rabbitTemplate.receiveAndConvert("yh.news");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void testAMQPAdmin() {
        amqpAdmin.declareExchange(new DirectExchange("admin.exchange"));
    }

    @Test
    public void testThreadPool() {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 10, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory());
        try {
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                poolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前是线程" + finalI + "：" + Thread.currentThread());
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("系统异常：", e);
        }
    }


    @Autowired
    AsyncTask asyncTask;

    @Autowired
    XxService xxService;

    @Test
    public void test90() {
        for (int i = 0; i < 1000; i++) {
            xxService.testPool();
            //asyncTask.doTest();

        }
    }

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private Cache cache;

    @Test
    public void test888() throws Exception {


        JobDetail jobDetail = JobBuilder.newJob(TestTask.class).
                withIdentity("yh").usingJobData("jobMessage", "test").build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("yh").
                usingJobData("triggerMessage", "test").
                withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?")).build();

        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }


    @Test
    public void test000() {
        String value = (String) cache.getIfPresent("yh");
        System.out.println("value：" + value);
    }


    @Test
    public void test111() {

        redisUtils.set("yh", "007");

    }


}