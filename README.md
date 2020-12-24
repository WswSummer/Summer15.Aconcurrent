## Summer15.Aconcurrent 线程池

```java
public ThreadPoolExecutor(int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    ThreadFactory threadFactory,
    RejectedExecutionHandler handler)
```

参数 1：corePoolSize
核心线程数，线程池中始终存活的线程数。

参数 2：maximumPoolSize
最大线程数，线程池中允许的最大线程数，当线程池的任务队列满了之后可以创建的最大线程数。

参数 3：keepAliveTime
最大线程数可以存活的时间，当线程中没有任务执行时，最大线程就会销毁一部分，最终保持核心线程数量的线程。

参数 4：unit:
单位是和参数 3 存活时间配合使用的，合在一起用于设定线程的存活时间 ，参数 keepAliveTime 的时间单位有以下 7 种可选：

TimeUnit.DAYS：天
TimeUnit.HOURS：小时
TimeUnit.MINUTES：分
TimeUnit.SECONDS：秒
TimeUnit.MILLISECONDS：毫秒
TimeUnit.MICROSECONDS：微妙
TimeUnit.NANOSECONDS：纳秒

参数 5：workQueue
一个阻塞队列，用来存储线程池等待执行的任务，均为线程安全，它包含以下 7 种类型：

ArrayBlockingQueue：一个由数组结构组成的有界阻塞队列。
LinkedBlockingQueue：一个由链表结构组成的有界阻塞队列。
SynchronousQueue：一个不存储元素的阻塞队列，即直接提交给线程不保持它们。
PriorityBlockingQueue：一个支持优先级排序的无界阻塞队列。
DelayQueue：一个使用优先级队列实现的无界阻塞队列，只有在延迟期满时才能从中提取元素。
LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。与SynchronousQueue类似，还含有非阻塞方法。
LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。
较常用的是 LinkedBlockingQueue 和 Synchronous，线程池的排队策略与 BlockingQueue 有关。

参数 6：threadFactory
线程工厂，主要用来创建线程，默认为正常优先级、非守护线程。

参数 7：handler
拒绝策略，拒绝处理任务时的策略，系统提供了 4 种可选：

AbortPolicy：拒绝并抛出异常。
CallerRunsPolicy：使用当前调用的线程来执行此任务。
DiscardOldestPolicy：抛弃队列头部（最旧）的一个任务，并执行当前任务。
DiscardPolicy：忽略并抛弃当前任务。
默认策略为 AbortPolicy

ThreadPoolExecutor 关键节点的执行流程如下：
* 当线程数小于核心线程数时，创建线程。
* 当线程数大于等于核心线程数，且任务队列未满时，将任务放入任务队列。
* 当线程数大于等于核心线程数，且任务队列已满：若线程数小于最大线程数，创建线程；若线程数等于最大线程数，抛出异常，拒绝任务。

