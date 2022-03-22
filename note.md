##beans.xml框架

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--开启注解支持-->
    <context:annotation-config/>

</beans>
```

##常用依赖

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.1.10.RELEASE</version>
</dependency>
<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

##注解说明

## Spring

- @Autowired：自动装配，通过类型，名字，结合@Qualifier(value = "xxx")，还可以设置(required = false)

- @Resource：自动装配，通过名字，类型，结合(name = "xxx")

- @Nullable：字段标记这个注解，说明这个字段可以为null

  

- @Component：组件，放在类上说明这个类被Spring管理，就是bean，@Component("user")

**@Component三个衍生注解**

- @Controller：web层

- @Service：service层

- @Repository：dao层

- @Value：值注入

  

- @Configuration：也被Spring托管，注册到容器，本身也是@Component，配置类，和beans.xml一样

- @Bean：注册一个bean

- @ComponentScan("cjw.pojo")

- @Import(MyConfig2.class)

- @Scope：作用域，@Scope("Singleton")



注解实现AOP

- @Aspect：标注这个类是一个切面

- @Before：@Before("execution(* cjw.service.UserServiceImpl.*(..))")

- @After：方法执行后

- @Around：环绕增强

  

## SpringMVC

- @Controller

- @RequestMapping("/HelloController")：url映射配置

- @PathVariable：在Spring MVC中可以使用  @PathVariable 注解，让方法参数的值对应绑定到一个URI模板变量上。



方法级别的注解

@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping

@RequestParam("username") : username提交的域的名称 ，约定从前端接收的

public String hello(@RequestParam("username") String name){



@ResponseBody：不会走视图解析器，会直接返回一个字符串

@RestController：在类上直接使用 **@RestController** ，这样子，里面所有的方法都只会返回 json 字符串了，不用再每一个都添加@ResponseBody ！我们在前后端分离开发中，一般都使用 @RestController ，十分便捷！

## lombok

```
@Data
@AllArgsConstructor
@NoArgsConstructor
```

## MyBatis

@Alias：起别名，

```java
@Alias("user")
public class User {
  ...
}
```

## 缩写

jpa：Java Persistence API

jwt：Json web token

*ORM*（Object Relational Mapping

ASP：active server page

*Redis*（Remote Dictionary Server )

*Nginx* (engine x) 是一个高性能的[HTTP](https://baike.baidu.com/item/HTTP)和[反向代理](https://baike.baidu.com/item/反向代理/7793488)web服务器

CDN缓存：Content Delivery Network，相当于服务器端缓存

*RPC*一般指远程过程调用。 *RPC*是远程过程调用（Remote Procedure Call

乐观锁的实现方式主要有两种：CAS机制和版本号机制，CAS（Compare And Swap）

## JavaWeb

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908161516406.png" alt="image-20210908161516406" style="zoom: 50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908162255062.png" alt="image-20210908162255062" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908164317213.png" alt="image-20210908164317213" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908165143512.png" alt="image-20210908165143512" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908165421575.png" alt="image-20210908165421575" style="zoom:50%;" />

2、Web服务器

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908170249468.png" alt="image-20210908170249468" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908170445905.png" alt="image-20210908170445905" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908170604122.png" alt="image-20210908170604122" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908170702140.png" alt="image-20210908170702140" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908185655212.png" alt="image-20210908185655212" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908185907969.png" alt="image-20210908185907969" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908192908340.png" alt="image-20210908192908340" style="zoom:50%;" />

<img src="C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20210908201518042.png" alt="image-20210908201518042" style="zoom:50%;" />

**Maven**

**约定大于配置**



**转发和重定向区别**

1、请求次数

重定向是浏览器向服务器发送一个请求并收到响应后再次向一个新地址发出请求，转发是服务器收到请求后为了完成响应跳转到一个新的地址；重定向至少请求两次，转发请求一次；

2、地址栏不同

重定向地址栏会发生变化，转发地址栏不会发生变化；

3、是否共享数据

重定向两次请求不共享数据，转发一次请求共享数据（在request级别使用信息共享，使用重定向必然出错）；

4、跳转限制

重定向可以跳转到任意URL，转发只能跳转本站点资源；

5、发生行为不同

**重定向是客户端行为，转发是服务器端行为**；



## Java多线程



三种方法：继承Thread类，实现Runnable接口，实现Callable接口；分别覆盖run()方法和call()方法

静态代理模式，Thread代理Runnable接口实例

1函数式接口，2实现类，3静态内部类，4局部内部类，5匿名内部类，6lambda表达式，

线程5状态，新建、就绪、运行、阻塞、死亡，getState()

线程启动：start()；休眠：sleep(100)，模拟延时，倒计时，放大问题发生性；礼让yiled()；停止，最好使用标志位让线程自己停止；线程强制执行，join()；优先级setPriority(int a) getPriority()；

守护线程，如gc，setDaemon(true)，其余都是用户线程，jvm不用等待守护线程执行完毕

解决线程安全，线程同步形成条件：队列+锁（锁释放开销降低性能，性能倒置）不安全买票、不安全取钱，List不安全 synchronized(obj){}  死锁

Lock显式锁，实现类Reenactlock可重入锁

sleep()不释放锁，线程通信：wait()释放锁，notify()唤醒等待线程，notifyAll()

生产者消费者问题：管程法（缓冲区），信号灯法

线程池，ExecutorService，Executors，提高响应速度，降低资源消耗，便于管理线程

并发

乐观锁悲观锁：[【BAT面试题系列】面试官：你了解乐观锁和悲观锁吗？ - 编程迷思 - 博客园 (cnblogs.com)](https://www.cnblogs.com/kismetv/p/10787228.html#t4)

共享锁（S锁）排他锁（X锁）：[共享锁（读锁）和排他锁（写锁） - 思考的胖头鱼 - 博客园 (cnblogs.com)](https://www.cnblogs.com/nickup/p/9804020.html#:~:text=排他锁（X锁）：用于数据修改操作，例如 INSERT、UPDATE 或 DELETE。 确保不会同时同一资源进行多重更新。 如果事务T对数据A加上排他锁后，则其他事务不能再对A加任任何类型的封锁。,获准排他锁的事务既能读数据，又能修改数据。 乐观锁不是数据库自带的，需要我们自己去实现。 乐观锁是指操作数据库时 (更新操作)，想法很乐观，认为这次的操作不会导致冲突，在操作数据时，并不进行任何其他的特殊处理（也就是不加锁），而在进行更新后，再去判断是否有冲突了。 通常实现是这样的：在表中的数据进行操作时 (更新)，先给数据表加一个版本 (version)字段，每操作一次，将那条记录的版本号加1。)

CAS



## 注解和反射

内置注解：@Override @Deprecated（过时不推荐使用）@SuppressWarnings（抑制警告）

元注解：meta-annotation，负责注解其他注解；

@Target；指定一个注解的使用范围，即被 @Target 修饰的注解可以用在什么地方，ElementType 常用的枚举常量 FIELD METHOD  TYPE

@Retention；用于描述注解的生命周期，也就是该注解被保留的时间长短，SOURCE < CLASS < RUNTIME

@Documented；用 @Documented 注解修饰的注解类会被 JavaDoc 工具提取成文档

@Inherited 用来指定该注解可以被继承

静态动态语言；o.getClass()；Class.forName("com.Student")；Student.class；Integer.TYPE；

反射应用场景：1jdbc时，通过反射获取数据库驱动；2注解/xml配置，解析出字符串，再反射获取对象；3AOP时，利用反射动态生成代理对象

## 数据库

事务acid，隔离级别，脏读、不可重复读、虚读；数据库引擎

数据库索引，主键索引、唯一索引、常规索引和全文索引，

聚簇索引：将数据存储与索引放到了一块，找到索引也就找到了数据，默认是主键

为我们要考虑磁盘IO的影响，它相对于内存来说是很慢的。数据库索引是存储在磁盘上的，当数据量大时，就不能把整个索引全部加载到内存了，只能逐一加载每一个磁盘页（对应索引树的节点）。所以我们要减少IO次数，对于树来说，IO次数就是树的高度，而“矮胖”就是b树的特征之一

B+树的特点：

1.中间元素不存数据，只是当索引用，所有数据都保存在叶子结点中。

2.所有的中间节点在子节点中要么是最大的元素要么是最小的元素 。

3.叶子结点包含所有的数据，和指向这些元素的指针，而且叶子结点的元素形成了自小向大这样子的链表。

B+树的优势：

1. 单个节点可以存储更多的数据，减少I/O的次数。
2. 查找性能更稳定，因为都是要查找到叶子结点。
3. 叶子结点形成了有序链表，便于查询。范围查询

索引失效：

Like这种就是%在前面的不走索引，在后面的走索引

用索引列进行计算的，不走索引

对索引列用函数了，不走索引

索引列用了!= 不走索引

多列索引查询，最左匹配原则，要使用第一个字段

or要包括所有索引

sql优化

子查询变成left join
limit 分布优化，先利用ID定位，再分页

or条件优化，多个or条件可以用union all对结果进行合并（union all结果可能重复）
不必要的排序
where代替having,having 检索完所有记录，才进行过滤
避免嵌套查询
对多个字段进行等值查询时，联合索引



## 场景

- 有1亿个数字，其中有2个是重复的，快速找到它，时间和空间要最优

原理：把数字值直接映射到数组下标（时间最优），这里重复的数字只有两次，为了空间最优，就用bit来表示（只有0和1），1byte=8bit，一个byte可以存储8个数字的计数。
所以建立数组 byte[] bucket=new byte[(最大值-最小值)/8+1];

位图bitmap



- 海量数据处理
  假如有10 亿个手机号，怎么样快速判断一个手机号是否在其中 （bit map）
  判断数字是否存在、判断数字是否重复的问题，位图法是一种非常高效的方法

由于 unsigned int 数字的范围是 [0, 1 << 32)，我们用 1<<32=4,294,967,296 个 bit 来表示每个数字。初始位均为 0，那么总共需要内存：4,294,967,296b≈512M
40 亿个整数，将对应的 bit 设置为 1。接着读取要查询的数，查看相应位是否为 1，如果为 1 表示存在，如果为 0 表示不存在

BitMap应用

- 在3亿个整数中找出不重复的整数，限制内存不足以容纳3亿个整数

对于这种场景我可以采用2-BitMap来解决，即为每个整数分配2bit，用不同的0、1组合来标识特殊意思
如00表示此整数没有出现过，01表示出现一次，11表示出现过多次，就可以找出重复的整数了
需要的内存空间是正常BitMap的2倍，为：3亿*2/8/1024/1024=71.5MB

- 某个文件内包含一些电话号码，每个号码为8位数字，统计不同号码的个数
  8位最多99 999 999，大概需要99m个bit，大概10几m字节的内存即可。 （可以理解为从0-99 999 999的数字，每个数字对应一个Bit位，所以只需要99M个Bit==1.2MBytes，这样，就用了小小的1.2M左右的内存表示了所有的8位数的电话）



- 如何从大量数据中找出高频词？(hashmap + heap)
  题目描述

有一个 1GB 大小的文件，文件里每一行是一个词，每个词的大小不超过 16B，内存大小限制是 1MB，要求返回频数最高的 100 个词(Top 100)

解答思路

由于内存限制，我们依然无法直接将大文件的所有词一次读到内存中。因此，同样可以采用分治策略，把一个大文件分解成多个小文件，保证每个文件的大小小于 1MB，进而直接将单个小文件读取到内存中进行处理

首先遍历大文件，对遍历到的每个词 x，执行 hash(x) % 5000 ，将结果为 i 的词存放到文件 ai 中。遍历结束后，我们可以得到 5000 个小文件。每个小文件的大小为 200KB 左右。如果有的小文件大小仍然超过 1MB，则采用同样的方式继续进行分解
接着统计每个小文件中出现频数最高的 100 个词。最简单的方式是使用 HashMap 来实现。其中 key 为词，value 为该词出现的频率。具体方法是：对于遍历到的词 x，如果在 map 中不存在，则执行 map.put(x, 1) ；若存在，则执行 map.put(x, map.get(x)+1) ，将该词频数加 1
统计了每个小文件单词出现的频数。可以通过维护一个小顶堆来找出所有词中出现频数最高的 100 个。具体方法是：依次遍历每个小文件，构建一个小顶堆，堆大小为 100。如果遍历到的词的出现次数大于堆顶词的出现次数，则用新词替换堆顶的词，然后重新调整为小顶堆，遍历结束后，小顶堆上的词就是出现频数最高的 100 个词
方法总结

分而治之，进行哈希取余
使用 HashMap 统计频数
求解最大的 TopN 个，用小顶堆；求解最小的 TopN 个，用大顶堆



迪杰斯特拉

![image-20220224104811581](C:\Users\54925\AppData\Roaming\Typora\typora-user-images\image-20220224104811581.png)





以前的项目，开题

java并发，线程池，synchronized

jvm，如何判断是不是垃圾，强软弱虚

物理内存和虚拟内存

数据库慢查询，有遇到什么问题

五层模型，数据链路层协议，网络层一个ip数据报如何到另一个ip，你在学校里，怎么访问北京的ip



aop基于什么实现，malloc和new，如何解决幻读，为什么会导致线程不安全（主内存和工作内存）

平时提高效率的软件，数据库连接池的作用，线程池参数，linux命令，三数之和，最左匹配，volatile，原子类

线程池参数：[Java线程池七个参数详解：核心线程数、最大线程数、空闲线程存活时间、时间单位、工作队列、线程工厂、拒绝策略_抓手的博客-CSDN博客_java线程池参数详解](https://blog.csdn.net/Anenan/article/details/115603481)

volatile:[volatile是如何保证可见性和有序性的_intimexy的博客-CSDN博客_volatile 可见性 有序性怎么保证的](https://blog.csdn.net/weixin_37990128/article/details/110955558)

数据库连接池：[数据库连接池的作用！_bj890的博客-CSDN博客](https://blog.csdn.net/lovexiaoxiao/article/details/3491845)

[JDBC、C3P0、DBCP、Druid 数据源连接池使用的对比总结.md - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1368903)

java类加载过程：[面试干货1——请你说说Java类的加载过程_LuckyWangxs的博客-CSDN博客](https://blog.csdn.net/qq_41563912/article/details/116642556)

类变量（也叫静态变量

## 常见面试题

计网：[计算机网络常见面试题 - 不懒人 - 博客园 (cnblogs.com)](https://www.cnblogs.com/wuwuyong/p/12198928.html)

​			[计算机网络——计算机网络常见面试题总结_Magaret的博客-CSDN博客_计算机网络面试题总结](https://blog.csdn.net/u010843421/article/details/82026427)

[(15条消息) 为面试做准备，整理一些计算机专业的基础知识_wang-jue的博客-CSDN博客](https://blog.csdn.net/qq_36561737/article/details/116996160)

数据库：[数据库常见面试题（附答案）_csdn问鼎-CSDN博客_数据库面试题](https://blog.csdn.net/qq_22222499/article/details/79060495)

操作系统：[操作系统常见面试题 - yuxiaoba - 博客园 (cnblogs.com)](https://www.cnblogs.com/yuxiaoba/p/8646139.html#:~:text=操作系统常见面试题 1 1、什么是进程（Process）和线程（Thread）？有何区别？ 2 2、操作系统的特征 3 3、三态、五态、七态 4,导致死锁的四个必要条件。 10 10.进程调度算法。 (周转时间%3D程序结束时间-开始服务时间，带权周转时间%3D周转时间%2F 要求服务时间) More items... )

设计模式：[java常用的设计模式有哪些-Java基础-PHP中文网](https://www.php.cn/java/base/476780.html)

动态代理：[Java动态代理的两种实现方法_和大黄的博客-CSDN博客_动态代理的两种方式](https://blog.csdn.net/heyutao007/article/details/49738887)



cas缺点 并发共享虚拟异步

深浅拷贝 获取private字段 设计模式：单例、工厂方法、抽象工厂、建造者、观察者、适配器



注解反射 银行家算法 数据库连接池 hashmap concurrenthashmap copyonwrite

事务回滚：[MySQL中是如何实现事务提交和回滚的？_果子爸聊技术-CSDN博客_mysql是如何实现事务回滚的](https://blog.csdn.net/pzjtian/article/details/107453356)

隔离级别实现：[MySQL事务隔离级别和实现原理（看这一篇文章就够了！） - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/117476959)

NIO的作用：[(15条消息) 为什么要有NIO_u010325193的博客-CSDN博客_nio有什么用](https://blog.csdn.net/u010325193/article/details/80310924)

```java
//获取当前时间戳方法 一
System.currentTimeMillis();
//方法 二
Calendar.getInstance().getTimeInMillis();
//方法 三
new Date().getTime();

SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
    System.out.println("Key = " + entry.getKey() + ", Value = " 	+ entry.getValue());
}

Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
while (entries.hasNext()) {
    Map.Entry<Integer, Integer> entry = entries.next();
    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
}
```



