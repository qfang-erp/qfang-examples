# git 使用中一些小技巧

**统计 HEAD 中有多少文件数量**  
``` shell
$ git ls-tree -r -l HEAD | wc -l
439

# 另外可以扩展统计有多少个 java 文件
$ git ls-tree -r -l HEAD |grep .java | wc -l
154
```
使用命令 `git ls-tree -r` -r 参数表示递归整个目录树，该命令会列出整个 HEAD 仓库中所有的文件列表，然后结合 linux 的相关统计命令就可以统计出总的文件数量了。  


**找到某个文件名所在的全路径**  
某些时候我们可以知道某个文件的文件名，但是不知道该文件在项目中完整的路径，当我们要使用 `git diff` 等命令来查看差异的时候，最好能知道文件的全路径名（虽然也可以使用通配符的形式，例如 `git diff commit1 commit2 *xxxService.java` 的形式，但如果存在比较多的重名文件就比较难办了）
``` shell
$ git ls-tree -r HEAD | grep EnableConfigServer.java
100644 blob b092791d75764206a87be682e804155a97871cc6    spring-cloud-config-server/src/main/java/org/springframework/cloud/config/server/EnableConfigServer.java
```
同样也是利用 `git ls-tree -r` 命令
