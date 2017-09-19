# git 常用命令介绍


### 约定
git 命令后面的参数常见的形式 -v / --status  
\- 一个中划线后面通常接的是缩写参数，例如： `git status -v`  
\-- 两个中划线后面通常接的是全拼参数，例如： `git log --before='2017-08-12'`  
*另外 -- 后面通常还可以接文件路径名，用于显示地告诉 git 当前命令只作用于该文件，例如：`git checkout readme` 命令，如果当前仓库正好存在一个文件和分支名称都叫 readme，那么你会发现`git checkout readme`将检出 readme 分支，而如果你只是想恢复/检出 readme 文件，那么就需要使用 `git checkout -- readme` 来明确告诉 git readme 是一个文件名*  

### git status
- `git status -vv`
- `git status -s`

### git add
- `git add .`  ## . 表示添加所有的改变  
- `git add -A`  ## 等同于 --all，也等同于 . 的效果，也是添加所有的改动
