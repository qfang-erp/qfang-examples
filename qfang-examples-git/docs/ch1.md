# Git 基本概念介绍

## 大纲
![TOC]


## git 工作空间

** git 三大工作空间 **
- git 工作目录*（就是操作系统的目录）*
- 暂存区*（通过 `git add` 命令添加的文件/修改，在 .git 目录下）*
- git 本地仓库*（通过 `git commit` 命令将修改提交到本地仓库，也 .git 目录下）*

*另外还有一个 git 的远程仓库，远程仓库是为了分布式开发时，大家进行代码进行共享和同步*

`git add` 添加文件到暂存区示例
![git add 添加文件到暂存区](../images/ch1/01.png)

`git commit` 添加文件到本地仓库
![git commit 添加文件到本地仓库](../images/ch1/02.png)
