#### Complier模块:

###### 模块流程图:![编译器模块流程.drawio](C:\Users\86183\Downloads\编译器模块流程.drawio.png)

###### 模块接口:

Boolean Complie(String)
	输入接口,参数为要编译的代码(String类型),输出为布尔类型,编译成功返回true,编译失败返回false.

String Ins_Out()

​	输出接口,返回编译后的二进制码(String类型).

###### 附注

​	Complie()仅能对代码类型和格式不符\超过数的上限做出返回,其它错误代码会导致运行时错误.

### vCPU接口

```java
\\初始化需要指令
vCPU(String [] bincode, MainFrame mf);
\\重置寄存器与PC与内存的置
void reset();
\\重新输入指令，内涵重置函数
void reload(String [] bincode);
\\运行全部指令
void runAll();
\\单步运行指令
void runStep();
```

#### 附注

vCPU支持单步运行与全部运行