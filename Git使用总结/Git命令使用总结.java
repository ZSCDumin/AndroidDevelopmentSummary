1、Git init   //初始化项目
2、Git status  //查看项目状态
3、Git add  //添加文件到 git 追踪列表里
4、Git commit  //提交到本地仓库
5、Git log  //提交记录
6、Git clone  //复制项目到本地
7、Git branch  //查看或者创建分支；
    创建net分支：git branch net；
    删除分支：git branch -d net 
8、Git checkout  //签出一个分支或者一个路径，在开发中常用与分支切换和恢复文件。
    切换net分支：git checkout net；
    恢复文件：git checkout -- SayHello.java
9、Git merge  //合并分支；
    合并net分支：git merge net
10、Git tag  //列出项目的标签和创建项目的标签
    git tag -a v1.0 -m "这里写相关信息" //为版本打一个标签
    git show 标签名 //来查看该分支的具体信息
11、Git help  //帮助
12、Git push  //推送
   （1）格式如下：git push 仓库名 分支名
   （2）git push origin master:master 或git push origin master //本地master分支提交到origin的master分支，分支相同的可简写
   （3）git push origin :net //删除origin仓库的net分支
13、Git pull  //更新最新的代码
14、Git ignore //忽略文件
15、fork  //远程复制别人的仓库到自己的仓库下