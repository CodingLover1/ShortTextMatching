### 基于lucene全文检索引擎实现的短文本匹配系统

#### 运行结果如下图所示
主界面
![](projectimages/1.png)

搜索结果界面
![](projectimages/2.png)

翻页
![](projectimages/4.png)

内容展示
![](projectimages/3.png)


#### lucene 全文检索引擎

简单的说Ajax请求通过XMLHttpRequest对象发送请求，该对象有四个状态（readyState）：
 
 0-未初始化、1-正在初始化、2-发送数据、3-正在发送数据、4-完成。
 
 当XMLHttpRequest.readyState为4时，表示ajax请求已经完成可以得到响应结果。
 
 ajax的success和error方法根据响应状态码来触发。当XMLHttpRequest.status为200的时候，表示响应成功，此时触发success().其他状态码则触发error()。
 
 除了根据响应状态码外，ajax还会在下列情况下走error方法：
 
 返回数据类型不是JSON
 
 网络中断
 
 后台响应中断

