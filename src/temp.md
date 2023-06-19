
## volatile
volatile保证共享变量之间的【可见性】，共享变量位于JVM内存模型的堆区，同内存进行交互
计算机内存模型
    从内存中取指令和数据，到CPU中执行
    CPU和内存有速度差异，为提升速度，设计了CPU高速缓存：L1、L2、L3 Cache
    数据缓存在多CPU中，产生缓存一致性问题（单CPU多线程执行非原子操作也会产生线程安全问题）
Java内存模型
    产生线程安全的地方：线程共享的堆区和方法区
    解决办法：
        1.总线锁：锁住总线，使得只有一个CPU能够访问变量所在的内存；效率低下；JDK 1.6以前的synchronized
        2.MESI协议：缓存一致性协议，是一种写失效协议；通过总线嗅探机制，把读写请求通过总线广播给所有CPU，让各CPU去“嗅探”这些请求，再根据本地的情况进行响应
            不同状态（MESI）的处理器进行不同操作（PrRd、PrWr）带来状态转化，不同总线操作（BusRd、BusRdX、BusUpgr）给不同状态处理器带来状态转化
并发编程的3个主要问题
    1.可见性：各CPU中的缓存彼此不可见（修改后不能读取到最新值）
    2.原子性：非原子操作（读取、操作、写回)
    3.有序性：
        编译器重排序：执行语句
        CPU重排序：指令集
        内存重排序：读写
volatile语义
    1.变量可见性
        实现原则：1.缓存锁定（修改缓存、写回内存）；2.MESI协议（嗅探其他处理器的缓存和修改情况，当内存值修改后置为无效，有需要会重新从内存读取至缓存行）
    2.禁止重排序
        通过禁用编译器重排序和处理器重排序实现，禁止场景：X-v写、v读-X、v写-v读/写
        编译时在生成的字节码中插入内存屏障（一组硬件指令），保证屏障两边指令是正确的程序执行顺序、内存数据可见性
        4种内存屏障：LoadLoad、LoadStore、StoreLoad、StoreStore
volatile想实现原子性可以结合AtomicXxx原子类以及加锁实现
使用原则：只有在状态真正独立于程序内其他内容时才能使用volatile



## AQS(AbstractQueuedSynchronizer)
volatile int state + CLH队列存放阻塞等待的线程，Node()
lock()：1.CAS(state, 0)；2.不成功则acquire()，包括2.1.tryAcquire()（state为0且没有排队前辈线程则尝试一次CAS、判断自己是否独占者线程是则重入）、2.2.创建Node（可能初始化head）、2.3.入队（判断前节点是否为head，是且tryAcquire()成功则设置head=null，否则加到队尾、设置前节点waitStatus=-1用于释放时判断唤醒后置节点、UNSAFE.park()）
unlock()：1.tryRelease()（state--，=0则设独占者线程为null）；2.头结点存在且waitStatus != 0则唤醒后置节点，所以公平锁下唤醒了也可能拿不到锁
默认非公平锁：性能更高，可以减少CPU唤醒线程的开销，整体的吞吐效率会高点；可能导致线程饥饿。公平锁只少了lock()中第一步。

## Condition
替代传统的Object的wait()、notify()实现线程间的协作，使用await()、signal()更加安全和高效
await()：1.将当前线程加入到Condition队列；2.release()释放锁、唤醒CLH队列线程、park()；3.被唤醒、尝试获取锁
signal()：唤醒

锁有2种：自旋锁和阻塞锁
锁有两种实现：信号量（1同步队列+0等待队列）和管程，是等价的，可以互相实现
Java中的锁都是管程锁
    synchronized即Monitor锁：一个同步队列+一个等待队列（Object中的wait()、notify()方法）；只能唤醒一个或全部，唤醒再自挂起会造成死锁
    Lock+Condition：可以对多个条件进行控制，不会造成死锁



## 缓存（cache）
计算机资源和接收请求有限，在各环节中可以从缓存中直接获取目标数据并返回，从而减少计算量，有效提升响应速度，让有限的资源服务更多的用户。
最初来源于内存和CPU，提升访问速度
分类：
    一次请求过程中：前端缓存（浏览器）、本地缓存（协商缓存、强缓存等）、网关缓存(CDN缓存)、服务端缓存
    服务端缓存包括进程缓存（本地缓存）、分布式缓存、数据库缓存
设计不当，轻则请求变慢、性能降低，重则会数据不一致、系统可用性降低，甚至会导致缓存雪崩，整个系统无法对外提供服务
大量缓存同时失效：设置过期时间=失效时间 + 随机时间
查询不存在数据：
    1.网关层简单过滤，后端将key短期存储在缓存中
    2.针对黑客大量恶意攻击，添加布隆过滤器，在访问缓存前先进行初步过滤
缓存雪崩：缓存的部分节点不可用导致整个缓存体系甚至整个服务系统不可用，直接访问数据库
    集群部署（1.简单主从、2.一致性哈希分片集群）也会雪崩，不能解决
    解决方案：1.缓存系统实时监控，慢速时报警、替换；2.Hystrix限流&降级，避免MySQL被打死；3.业务DB的访问增加读写开关，慢速时关闭读开关、fail-fast返回
数据不一致：
    解决步骤：先删除缓存、发送变更key的MQ、执行DB更新；另一个线程发现无缓存，去MQ里查看是否有key，有则阻塞等待



### 线程池
Executor：execute，只接收Runnable任务
ExecutorService：submit、invokeAll执行完任何一个即返回并阻塞其他任务、shutdown（不接收新任务、执行完当前包括队列中的任务后关闭）、shutdownNow（尝试中断所有正在执行任务的线程、返回队列任务list）、awaitTermination超时等待结束，接收Runnable和Callable任务
ScheduledExecutorService：schedule、scheduleAtFixedRate、scheduleWithFixedDelay
Executors：线程池工厂，线程池的底层实现都是由 ThreadPoolExecutor 提供
    newFixedThreadPool：n, n, 0
    newSingleThreadExecutor：1, 1, 0
    newCachedThreadPool：0, MAX, 60s, SynchronousQueue
    newWorkStealingPool：Work-Stealing算法，ForkJoinPool
ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
    prestart（All）CoreThread()：在corePoolSize内创建一个（全部）线程，饿汉式
    workQueue：
        SynchronousQueue：同步队列，不存储元素，提供TransferQueue（公平模式）和TransferStack（非公平模式）存储put、阻塞等待take，保证“对于提交的任务，如果有空闲线程，则使用空闲线程来处理；否则新建一个线程来处理任务”
        LinkedBlockingQueue：无界缓存等待队列
        ArrayBlockingQueue：有界缓存等待队列，可指定缓存队列的大小
    RejectedExecutionHandler：丢弃并抛异常、直接丢弃、丢弃队列最前面的任务、由调用线程执行
线程池状态：AtomicInteger ctl，高3位存储状态runState、低29位存储有效线程数量workerCount
execute流程
Worker：继承了AQS类并且实现了Runnable接口，初始state=-1，相当于互斥锁
使用注意：
    避免任务堆积、线程泄露、死锁、不要使用TheadLocal（线程生命周期比任务长）
根据任务类型来配置线程池大小
    CPU密集型任务：避免线程太多导致上下文切换浪费资源，n+1
    I/O密集型任务：2n 或者 n * (1 + 平均等待时间/平均工作时间)



## 浅拷贝&深拷贝
=，对于基本数据类型是赋值，对于引用是传递引用地址
浅拷贝：在拷贝的时候，只对基本数据类型进行拷贝，对引用数据类型只是进行了引用的传递，没有真正的创建一个新的对象
    实现方式：1.实现clone接口，重新clone方法；2.Arrays.copyOf()；3.BeanUtils.copyProperties()
深拷贝：对引用对象也新建了
    实现方式：1.Serializable接口，序列化、反序列化
有子对象一定不能用浅拷贝吗？不一定，看是否需要改变





# I/O
## I/O设备
程序员眼中的I/O就是硬件提供给软件的接口
分类：
    1.块设备：固定大小块、物理地址，如硬盘、光盘、U盘
        缺点：寻址更慢，必须找到块开头进行读写
    2.字符设备：基于字符读写，如键盘、鼠标、打印机、以太网
I/O设备=机械组件+电子组件（设备控制器/适配器）
内存映射I/O：CPU与设备共享同一位置的内存
    优点：寻址和操作方便
    缺点：缓存设备寄存器带来复杂性、推测响应目标、
直接内存访问、DMA控制器

## I/O软件原理
目标：
程序控制
中断驱动
DMA






## kafka
支持多分区、多副本，基于 Zookeeper 的分布式消息流平台、发布-订阅式消息队列

点对点模式（一个消费者群组消费一个主题中的消息，消息队列）、发布订阅模式（一个主题中的消息被多个消费者群组全量消费）

为何如此之快：消息压缩、分批发送、零拷贝、顺序读写

压缩：CPU 时间去换磁盘空间或者 I/O 传输量






背景：使用的JMQ不支持较好地重试策略，可能很久才能重试，对实时性要求较高的业务不友好、不能把控重试行为，提需求回复这是业务方需要考虑的问题，因此拟开发一个组件/工具统一处理各个应用、topic的MQ重试消费
设计：
    spring-boot-starter配置
    新建注解，在处理topic的地方，切面捕获异常和消息信息
    存储？
    配置topic，自动获取需要执行的代码，线程上下文中传递重试次数、间隔时间等



JavaAgent+ASM：效率更高，直接使用指令来控制字节码

ASM：CGLIB、ByteBuddy等依赖于此
javassist：
CGLIB：包括Enhancer类
ByteBuddy：





MySQL中的锁
全局锁：flush tables with read lock，全局只读
    MyISAM 这种不支持事务的引擎，在备份数据库时使用
    mysqldump 备份时加上 –single-transaction 参数，开启可重复读的Read View，不需要加全局锁
表级锁：
    表锁：lock tables t_student read/write; 表级别的共享锁（读锁）/独占锁（写锁）
    元数据锁（MDL）：包括MDL读锁/写锁，防止CRUD时对表结构做变更，随事务保持，写优先
    意向锁：意向共享/独占锁，增、删、改、共享/独占读时加，普通select不加，目的是为了快速判断表里是否有记录被加锁
    AUTO-INC锁：插入语句执行后即释放，轻量级锁优化——字段赋值后就释放锁，innodb_autoinc_lock_mode参数设置
行级锁：
    Record Lock，记录锁
    Gap Lock，间隙锁
    Next-Key Lock：Record Lock + Gap Lock


