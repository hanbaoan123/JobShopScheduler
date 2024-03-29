![](https://github.com/hanbaoan123/image/raw/master/logo.png "")


## 说明
&emsp;&emsp;本调度平台旨在打造一个通用性更强的平台，在该平台上可以比较算法在标准调度案例上的性能。通用性体现在两个方面：算法实现和调度表达。算法通用性方面，平台支持各种不同的优化算法，包括启发式（简单规则）、元启发式（遗传算法，粒子群算法）和深度强化学习（这部分因为和课题相关，暂时也不发布），并且平台建立了完善的调度算法接口，可以实现各种想要实现的算法。问题通用性在于建立了通用的调度模型，可以表达单机调度问题，并行机调度问题，流水调度问题，作业车间调度问题和柔性作业车间调度问题，这些问题会在之后的版本发行中陆续给出。在当前v0.0.0版本只公开了使用启发式求解单机调度问题相关的代码。

&emsp;&emsp;JobShhopScheduler共包含两个Maven项目：instance-scheduler和web-scheduler。instance-scheduler是实现调度功能的核心项目，通过该项目可以使用不同的算法得到工序在何时由什么机床进行加工。web-scheduler是一个maven web项目，用于展示一些调度结果和算法学习过程。

&emsp;&emsp;本手册将给出完整的关于调度平台的使用说明。

&emsp;&emsp;**注：本调度平台所使用到的所有安装程序和文件均可在[这里](https://pan.baidu.com/s/1OJ1KAOXlNwhqeJWwJ2didA?pwd=nb31)找到，密码为nb31。**

## 安装

&emsp;&emsp;打开Eclipse，依次选择File->Import->Git->Projects from Git->Clone URI，Next之后填写Github远程仓库地址：[https://github.com/hanbaoan123/JobShopScheduler.git](https://github.com/hanbaoan123/JobShopScheduler.git)，然后选择保存的目录即可。
 
<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/1fromgit.jpg"/><br/>图1 从Git导入项目</div>

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/2cloneurl.jpg"/><br/>图2 克隆URI</div>

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/3gitrepos.jpg"/><br/>图3 填写URI</div>

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/4%E4%BF%9D%E5%AD%98%E4%BD%8D%E7%BD%AE.jpg"/><br/>图4 选择本地保存目录</div>

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/5%E5%AF%BC%E5%85%A5%E4%B8%BA%E4%B8%80%E8%88%AC%E9%A1%B9%E7%9B%AE.jpg"/><br/>图5 导入现有项目</div>


### Eclipse配置

&emsp;&emsp;代码导入后会出现报错，不要着急，这是因为java和maven等没有进行配置造成的。

* JDK配置

&emsp;&emsp;在Window->preferences中设置installed JRES，添加jdk（java8及以上版本），同时设置编译级别为1.8。关于JDK的安装与环境变量配置请自行百度。

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/6jdk.jpg"/><br/>图6 安装JRE</div>

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/7compiler1.8.jpg"/><br/>图7 设置编译级别为1.8</div>

* Maven配置

&emsp;&emsp;在Maven的User Settings中，选择maven的配置文件，关于如何安装和配置maven可以自行百度。

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/8maven.jpg"/><br/>图8 设置Maven</div>

&emsp;&emsp;这时Eclipse会根据配置的maven自动的更新项目所需要的jar包，等待完成。

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/9%E8%87%AA%E5%8A%A8%E6%9B%B4%E6%96%B0jar%E5%8C%85.jpg "/><br/>图9 自动更新jar包</div>

&emsp;&emsp;如果此时还有报错，则设置项目的编译路径，在项目上右键->Build Path->Configure Build Path，右侧Libraries中选择安装的jdk。

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/10%E9%A1%B9%E7%9B%AEjdk.jpg "/><br/>图10 项目build路径</div>

### 案例配置

&emsp;&emsp;在该调度平台中已经事先生成好了单机调度标准案例，将“instance”文件夹复制到用户目录下（注意放在别的路径是无效的），如C:\Users\hba下，需要根据自己的系统和用户目录确定。

## 程序运行

&emsp;&emsp;在instanceScheduler项目下的src/test/java中，给出了启发式调度运行的测试程序，直接右键Run As->Java Application即可。
### 结果可视化

&emsp;&emsp;结果可视化主要用于展示调度结果和算法学习过程，用于不同算法间性能的对比，目前只发布了甘特图的动态展示页面。

<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/15%E5%90%AF%E5%8A%A8%E6%9C%8D%E5%8A%A1%E5%99%A8.jpg  "/><br/>图11 启动Server</div>
<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/14%E5%90%AF%E5%8A%A8%E6%88%90%E5%8A%9F.jpg  "/><br/>图12 启动成功</div>
<div align=center><img src="https://github.com/hanbaoan123/image/blob/master/16%E7%BB%93%E6%9E%9C%E5%B1%95%E7%A4%BA.jpg  "/><br/>图13 结果展示</div>

