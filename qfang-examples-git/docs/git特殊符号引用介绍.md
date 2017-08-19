# git 特殊符号引用介绍

git 自动维护了几个用于特定目的的特殊符号引用。这些引用可以在使用提交的任何地方使用。

- <font color='red'><strong>HEAD</strong></font> 始终指向当前分支的最终提交。当切换分支时 HEAD 会更新为指向新分支的最新提交。   
- <font color='red'><strong>ORIG_HEAD</strong></font> 某些操作，例如 merage / reset 会把 merge 之前的 HEAD 保存到 ORIG_HEAD 中，以便在 merge 之后可以使用 ORIG_HEAD 来回滚到合并之前的状态（在分支合并的时候，产生了冲突，如果已经修改了冲突，并产生了新的提交，但是冲突解决的有问题，想要还原之前的状态重新合并，这时可以使用 `git reset --hard ORIG_HEAD` 来还原到合并之前的状态）。  
- <font color='red'><strong>FETCH_HEAD</strong></font> 当使用命令 `git fetch` 抓取远程仓库更新时，FETCH_HEAD 保存着最近抓取的分支的 HEAD。  
- <font color='red'><strong>MERGE_HEAD</strong></font> 当一个合并正在进行时，其他分支的头暂时记录在 MERGE_HEAD 中，换言之， MERGE_HEAD 是正在合并进 HEAD 的提交。  
