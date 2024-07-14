


### 线上问题排查

- CPU过高
  - 线程过多
    - `top` 查看CPU高的进程
    - `top -Hp pid` ：查看问题进程中的线程情况
    - `printf '%x\n' nid` 获得线程ID的十六进制表示，`jstack pid | grep nid -C10` ：查看对应的线程前后 10 行的状态信息
    - `jstack pid` 概览进程下各线程的状态
    - CAS锁等待导致的CPU高问题，可以换成 synchronized, 重量级锁情况下不占用CPU资源
  - 频繁GC导致的
    - `top -Hp pid` ：查看问题进程中的线程情况，会发现GC线程占满了CPU
      - 分析堆内存 dump 文件
        - 在线分析
          - `jstat -gc pid` 查看 pid 对应的 Java 进程当前情况下的 GC 情况：
          - `jmap -heap pid` ：显示 Java 堆详细信息
          - Arthas 工具
        - 事后分析
          - 隔离机器、`jmap -dump` 保存现场、重启上线、dump文件保存到本地
          - jhat 分析
          - Java VisualVM 工具
            - 还能进行实时的可视化监控分析，测试环境才使用，以免影响性能



