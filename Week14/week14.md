# Week14 软件工程实验报告

| 组员名单 | 黄梓轩   | 李嘉毅  | 梁恒中   |
| -------- | -------- | ------- | -------- |
| 学号     | 19335085 | 1335098 | 19335119 |

# 详细设计说明书

## 1 引言

### 1.1 编写目的

本阶段已在系统的需求分析的基础上，对龙芯基础指令集架构模拟器作概要设计。主要解决了实现该系统需求的程序模块设计问题。包括如何把该系统划分成若干个模块，决定各个模块之间的接口，模块之间传递的信息，以及数据结构，模块结构的设计等。在以下的概要设计报告中将对在本阶段中对系统所做的所有概要设计进行详细的说明。

在下一阶段的详细设计中，程序设计员可参考此概要设计报告，在概要设计对龙芯基础指令集架构模拟器所做的模块结构设计的基础上，对模拟器进行详细设计。在以后的软件测试以及软件维护阶段也可以参考此说明书，以便了解在概要设计过程中所完成的各模块设计结构，或在修改时找出在本阶段设计的不足或者失误。

### 1.2 背景

本系统全名为'龙芯基础指令集模拟器LARS'，是由中山大学本科生三人黄梓轩、李嘉毅、梁恒中提出并开发，为能够熟练编写LA64/LA32汇编指令的编程人员，或正在学习LA64/LA32汇编指令的人员提供模拟器服务。

### 1.3 定义

专业术语：

- LARS：龙芯基础指令集模拟器

- LA64：64位龙芯指令集

- LA32：32位龙芯指令集

### 1.4参考资料

《龙芯架构参考手册 卷一：基础架构》--龙芯中科

## 2 程序系统的结构

![2图](/Users/zixuanhuang/Documents/Github_Program/MySEProject/Week14/2图.png)

## 3 交互界面（UI）设计说明

### 3.1 程序描述
本模块负责接收用户输入和向用户展示用户程序运行结果。本模块将用户输入的源程序代码和用户提交的命令交由compiler模块和VCPU模块处理，并从compiler模块和VCPU模块获得处理结果返回给用户。
### 3.2 功能
* 接收用户输入
* 向compiler模块和VCPU模块传递用户输入
* 从compiler模块和VCPU模块获得结果并返回给用户
### 3.3 性能
接收到用户输入到响应所需的时间小于1s。
### 3.4 输入项
输入项分为两类：
1. 源程序代码，用户通过编辑界面输入的文本
2. 用户命令，用户通过各种可视组件，如按钮，向程序发出的指令

### 3.5 输出项
输出项包括：
1. 用户源程序编译后得到的二进制码
2. 用户运行用户程序得到的结果，以寄存器和内存数据变化的形式展现

### 3.6 算法
无
### 3.7 流程逻辑
![week13_ui](D:/sysu/se/week13_ui.png)
### 3.8 接口
#### 依赖的接口
1. compile()函数，由compiler模块提供，用于传递用户输入的源程序，同时获得编译结果
2. run()函数，由VCPU模块提供，用于运行用户程序并获得运行结果
#### 提供的接口
无
### 3.9 存储分配
需要存储空间用于保存用户输入的源代码文件，具体需求根据用户输入的源代码规模而变。
### 3.10 注释设计
#### 单行注释
用于对变量、单行代码进行简要的说明
```
//注释内容
```
#### 多行注释
用于对多行代码，如代码块，进行简要的说明
```
/*
注释内容
*/
```
#### 文档注释
用于对整个文件、类等进行详细说明
```
/**
注释内容
*/
```
### 3.11 限制条件
必须与compiler模块、VCPU模块同时运行。
### 3.12 测试计划
1. 测试模块各个界面的组件是否正确排列，能否正确响应
2. 测试模块各个功能是否正常
3. 测试与compiler模块、VCPU模块协同工作是否正常
### 3.13 尚未解决的问题
窗口缩放时组件的缩放问题
## 4 编译器（Compiler）设计说明

### 4.1 程序描述

 编译模块对指令进行编译处理,负责实现模拟器的编译功能,本程序顺序编译指令.

### 4.2 功能

编译模块负责将输入的将LA64/LA32汇编指令编译为二进制码,将二进制码交给VCPU模块.

### 4.3 性能

要求编译精度完全准确,只需对指定的少量指令进行编译但可扩展指令,并能以每秒编译超过一百条

指令.

### 4.4 输入项

##### 4.1 LA64/LA32汇编指令

​	汇编指令的输入格式为一条长度有限的字符串,符合LA64/LA32汇编指令的标准.编译器将从交互界面的输出接口读取用户输入的指令字符串.

##### 4.2 无效指令

​	非字符串数据或不符合LA64/LA32汇编指令的标准的错误指令.

### 4.5 输出项

##### 5.1 二进制码

​	由标准汇编码编译而来,向VCPU模块的输入接口输出一串有限长字符串.

##### 5.2 报错码

​	检测到输入数据格式异常时,向VCPU和交互界面输出的一串字符串.

### 4.6 算法

本模块采用决策树的方式按字符段快速寻找汇编指令对应的机器码,其中决策树的建立由人工完成.

### 4.7 流程逻辑

### 4.8 接口

##### 8.1 存储器接口

​	Ins_Input(char*)

​	输入接口,用于从存储器读取指令.

##### 8.2 交互界面接口

​	Ins_Rep(char*)

​	输出接口,用于向交互界面发出错误报告.

##### 8.3 VCPU接口

​	Ins_Output(char*)

​	输出接口,用于向VCPU输出二进制码.

### 4.9 限制条件

​	输入指令应遵循LA64/LA32汇编指令格式,且应在给定的指令表中选择.

### 4.10测试计划

准备一组指令,包含标准指令和非标准指令进行输入测试,观察输出结果是否符合预期.

## 5 虚拟CPU（vCPU）设计说明

### 5.1 程序描述

这个程序是用作模拟CPU的功能，完成指令的运行，主要完成三大类指令：跳转指令，算术指令，访存指令。

### 5.2 功能

本程序输入Compiler输出的二进制码，然后根据指令运行，最后将结果返回给UI。

### 5.3 性能

能够完全正确执行指令，支持每秒超过运行100条指令。

### 5.4 输入项

##### 4.1 二进制码

​	由Cmpiler程序输出一串的二进制码。

### 5.5 输出项

##### 4.1 结果输出

​	根据运行后的结果向交互界面返回被修改的寄存器或内存的地址与值。

##### 4.2 报错码

​	检测到输入数据格式异常时,向交互界面输出的一串字符串。

### 5.6 算法

无

### 5.7 流程逻辑

三大指令流程如下，在完成后统一输出结果返回交互界面。

跳转指令：

![跳转](/Users/zixuanhuang/Documents/Github_Program/MySEProject/Week8/跳转.png)



算术指令：

![算术](/Users/zixuanhuang/Documents/Github_Program/MySEProject/Week8/算术.png)

访存指令：

![访存](/Users/zixuanhuang/Documents/Github_Program/MySEProject/Week8/访存.png)

### 5.8 接口

##### 8.1 错误发送接口

Err_report(char*)

​	输出接口，用于向交互界面发出错误

##### 8.2 交互界面接口

​	Res_report(int,int)

​	输出接口,用于向交互界面发出运行后寄存器修改的

##### 8.3 VCPU接口

​	Bin_Input(char*)

​	输入接口,用于接收二进制码。

### 5.9 存储分配

采用char数组存储二进制编码的指令。

采用int数组模拟寄存器与内存。

### 5.11 限制条件

本程序只能运行指令表中提供的指令

### 5.12 测试计划

1. 测试程序运行的正确性
2. 测试程序运行的速度

具体做法如下：

利用随机生成若干测试的二进制码和Compiler程序正确输出的二进制码来测试程序的正确性与速度。