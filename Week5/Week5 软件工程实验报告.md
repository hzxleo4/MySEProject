# Week5 软件工程实验报告

| 组员名单 | 黄梓轩   | 李嘉毅  | 梁恒中   |
| -------- | -------- | ------- | -------- |
| 学号     | 19335085 | 1335098 | 19335119 |

## 实验内容

准备一个项目可行性报告的框架

- 项目题目
- 项目背景（技术的、经济的、社会的）和目标概述
- 项目产品的表现形态、主要功能和性能
- 项目技术路线和技术成熟度分析
- 项目资源（技术、人力、预算、时间等资源）分析
- 项目风险分析
- 时间进度分析

## 实验进展

本周主要完成报告框架的搭建、前三个内容的确定以及后面内容等讨论。下面内容为未完成的手稿

### 项目题目

龙芯基础指令集架构模拟器LARS(Loongarch Assembler and Runtime Simulator)

### 项目背景

#### 技术背景

龙芯指令集（Loongarch）是龙芯中科于2020年推出的一个全新的指令集架构。包括基础架构部分和向量指令、虚拟化、二进制翻译等扩展部分，近2000条指令，具有较好的自主性、先进性与兼容性。龙芯指令系统从整个架构的顶层规划，到各部分的功能定义，再到细节上每条指令的编码、名称、含义，在架构上进行自主重新设计，具有充分的自主性。

龙芯指令系统摒弃了传统指令系统中部分不适应当前软硬件设计技术发展趋势的陈旧内容，吸纳了近年来指令系统设计领域诸多先进的技术发展成果。同原有兼容指令系统相比，不仅在硬件方面更易于高性能低功耗设计，而且在软件方面更易于编译优化和操作系统、虚拟机的开发。其在设计时充分考虑兼容生态需求，融合了各国际主流指令系统的主要功能特性，同时依托龙芯团队在二进制翻译方面十余年的技术积累创新，能够实现多种国际主流指令系统的高效二进制翻译。龙芯中科从2020年起新研的CPU均支持LoongArch®。

基于其完善开源的指令集架构，我们的模拟器可以较为简单地实现功能。

#### 经济背景

芯片行业是一个资金密集的行业。研发一款旗舰SoC手机芯片的费用约为数亿美元。之前有消息称华为麒麟980研发投入大约为3亿美元。

芯片制造更是高投入重资产，以台积电为例，2020年台积电的资本投入上看至200亿美元。如此高的资金投入让很多企业投不起而掉下队来，芯片代工行业越来越集中，订单越来越多交到台积电手中，甚至英特尔都开始把部分订单交给台积电，从而造就了台积电今天的行业地位。

所以芯片技术密集和资金密集这两大特性使得玩家越来越少，最终是赢家通吃。

全球芯片巨头包括英特尔、AMD、高通、三星、台积电等，这些大玩家们无论技术实力还是资金实力都让业界其他企业难以望其项背。

#### 社会背景

目前芯片业依然是美国最重要的产业，有一大批实力雄厚的大公司在主导整个产业的发展。美国一共有苹果、英特尔、英伟达、IBM、高通、德州仪器、博通七家七亿美元级别的顶尖科技公司，这些公司都是芯片细分领域的龙头企业。

而由于中美关系日益紧张，我国芯片面临着严重的卡脖子问题。这需要我们摆脱对国外企业的依赖，自主研发产品，自己构建行业生态。我们的龙芯基础指令集架构模拟器正是在这样的背景下孕育而出，为国内的行业生态尽一份力。

### 目标概述

我们的项目大致有以下三个目标：

1. 为Loongarch指令集架构生态添砖加瓦。Loongarch作为一个新的指令集架构，其生态的建立方兴未艾。该项目的开发可以在一定程度上完善Loongarch指令集架构生态。
2. 基于目前基于Loongarch指令集架构的处理器较为稀少，价格较为高昂，该项目的开发可以降低普通开发人员接触Loongarch指令集架构的门槛，有利于推广Loongarch指令集架构。
3. 该项目的开发能够支持企业/高校围绕Loongarch指令集架构培训计算机相关技术人员，培养更多人才攻坚解决目前国家的卡脖子问题。

### 表现形态与主要功能
在其他操作系统上运行，具有图形化交互界面，支持指令输入，能够执行LoongArch指令。
### 性能
预期峰值运算速度超过1.6GFlops,支持并行运算。

### 项目技术路线和技术成熟度分析



### 项目资源分析

#### 技术

#### 人力

三名本科生

#### 预算

三台便携式电脑用作开发

三名人员的劳工费

用于专业学习的资料费

#### 时间

大致开发时间为3个月

### 项目风险分析

1.开发时间不足，预期功能未能实现
2.项目组成员压力过大放弃开发寻求更简单的项目

### 时间进度分析

