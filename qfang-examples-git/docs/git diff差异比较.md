# git diff 差异比较


### 命令介绍

`git diff` 可以用来比较对象库中任意两个树对象之间的差异，也可以用来比较工作目录，索引，和对象树之间的差异。通常，`git diff` 命令进行树对象之间的比较时，你可以指定 commit / branch / tag 等。  

**`git diff` 命令的几种形式区别**
- `git diff` 直接不带参数的 `git diff`，比较工作目录和索引之间的差异。  
- `git diff commit` 比较工作目录和给定 commit 直接的差异，例如：`git diff HEAD`，比较工作目录和最后一次 commit 之间的差异。  
- `git diff --cached commit` 比较索引和给定 commit 之间的差异，**如果省略 commit 则默认比较索引和 HEAD 之间的差异**（--cached 也可以使用 --staged 代替，意义是一样的）。  
- `git diff commit1 commit2` 比较任意两个 commit 之间的差异，实则是比较对象库中任意两个树对象之间的差异。  


### 相关参数
`git diff --ignore-all-space` 忽略空白字符，等同于 `git diff -w`  
`git diff --ignore-space-at-eol` 忽略行尾的空格  
`git diff --ignore-space-change` 忽略空格差异，例如有些人间距使用 tab 键，有些使用4个空格，使用 --ignore-space-change 参数来忽略这种差异  
`git diff --ignore-blank-lines` 忽略空行的差异  
`git diff --stat` 按文件来显示统计信息，显示每个文件的增加删除，修改的行数
`git diff --shortstat` 只显示统计信息，统计所有增加删除，修改的文件数  
`git diff --diff-filter=D` 只显示给定状态的改动，D 表示只显示删除的文件（可选的参数值包含：ACDMR，分别表示：ADD Copied Delete Modified）  
`git diff –G <regex>` 只显示满足条件的改动  


### 示例
``` bash
$ mkdir diff_demo
$ cd diff_demo/
$ git init
$ echo "init a.txt" > a.txt
$ echo "init b.txt" > b.txt
$ git add .
$ git commit -m "init project"

# 分别修改 a.txt 和 b.txt，并且将 a.txt 的修改进行暂存
$ echo "add new line" >> a.txt
$ echo "add new line for b.txt" >> b.txt
$ git add a.txt
```

`git diff` 比较工作目录和暂存区的差异，可以看到只有 b.txt 文件，因为 b.txt 改动还未暂存。  
``` bash
$ git diff --stat
 b.txt | 1 +
 1 file changed, 1 insertion(+)
```

`git diff --cached` 这条命令等同于 `git diff --cached HEAD`，比较索引和 HEAD 之间的差异，此时索引和 HEAD 直接的改动就是 a.txt  
``` bash
$ git diff --cached --stat
 a.txt | 1 +
 1 file changed, 1 insertion(+)
```

`git diff HEAD` 比较工作目录和 HEAD 之间的差异，可以看到同时显示了 a.txt 和 b.txt  
``` bash
$ git diff HEAD
diff --git a/a.txt b/a.txt
index 1e79c5e..c247691 100644
--- a/a.txt
+++ b/a.txt
@@ -1 +1,2 @@
 init a.txt
+add new line
diff --git a/b.txt b/b.txt
index db52b66..456d41a 100644
--- a/b.txt
+++ b/b.txt
@@ -1 +1,2 @@
 init b.txt
```

### 示例II
为了便于演示，我们`git clone https://github.com/spring-cloud/spring-cloud-config`  [spring-cloud-config](https://github.com/spring-cloud/spring-cloud-config) 库  

1、v1.2.0.RELEASE 与 v1.3.0.RELEASE 之间一共修改了多少个文件（增加修改删除修改的合计）  
``` bash
$ git diff --shortstat v1.2.0.RELEASE v1.3.0.RELEASE
 105 files changed, 4590 insertions(+), 1110 deletions(-)
```

2、v1.2.0.RELEASE 与 v1.3.0.RELEASE 之间一共增加了多少个文件  
``` bash
## 使用 --diff-filter=A 只显示新增的修改
$ git diff --diff-filter=A --shortstat v1.2.0.RELEASE v1.3.0.RELEASE
 27 files changed, 1899 insertions(+)

## 这个命令也是类似的效果，文档上的说明 --summary 显示的是 新增，重命名的改动
$ git diff --summary v1.2.0.RELEASE v1.3.0.RELEASE | wc -l
27
```

3、显示 EnableConfigServer.java 在 v1.2.0.RELEASE 与 v1.3.0.RELEASE 之间的改动  
``` bash
## 找到该文件的全路径名
$ git ls-tree -r HEAD | grep EnableConfigServer.java
100644 blob b092791d75764206a87be682e804155a97871cc6    spring-cloud-config-server/src/main/java/org/springframework/cloud/config/server/EnableConfigServer.java
## 这里不使用全路面名也可以使用通配符的方式 *EnableConfigServer。java
$ git diff v1.2.0.RELEASE v1.3.0.RELEASE -- spring-cloud-config-server/src/main/java/org/springframework/cloud/config/server/EnableConfigServer.java
```
