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

## 定时任务
corn表达式:
```text
* 表示所有值. 例如:在分的字段上设置 "*",表示每一分钟都会触发。

? 表示不指定值。使用的场景为不需要关心当前设置这个字段的值。例如:要在每月的10号触发一个操作，但不关心是周几，所以需要周位置的那个字段设置为"?" 具体设置为 0 0 0 10 * ?

- 表示区间。例如 在小时上设置 "10-12",表示 10,11,12点都会触发。

, 表示指定多个值，例如在周字段上设置 "MON,WED,FRI" 表示周一，周三和周五触发

/ 用于递增触发。如在秒上面设置"5/15" 表示从5秒开始，每增15秒触发(5,20,35,50)。在月字段上设置'1/3'所示每月1号开始，每隔三天触发一次。

L 表示最后的意思。在日字段设置上，表示当月的最后一天(依据当前月份，如果是二月还会依据是否是润年[leap]), 在周字段上表示星期六，相当于"7"或"SAT"。如果在"L"前加上数字，则表示该数据的最后一个。例如在周字段上设置"6L"这样的格式,则表示“本月最后一个星期五"

W 表示离指定日期的最近那个工作日(周一至周五). 例如在日字段上设置"15W"，表示离每月15号最近的那个工作日触发。如果15号正好是周六，则找最近的周五(14号)触发, 如果15号是周未，则找最近的下周一(16号)触发.如果15号正好在工作日(周一至周五)，则就在该天触发。如果指定格式为 "1W",它则表示每月1号往后最近的工作日触发。如果1号正是周六，则将在3号下周一触发。(注，"W"前只能设置具体的数字,不允许区间"-").

'L'和 'W'可以一组合使用。如果在日字段上设置"LW",则表示在本月的最后一个工作日触发（一般指发工资）

# 序号(表示每月的第几个周几)，例如在周字段上设置"6#3"表示在每月的第三个周六.注意如果指定"#5",正好第五周没有周六，则不会触发该配置(用在母亲节和父亲节再合适不过了)

```
