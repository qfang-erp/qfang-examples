# git 常见问题总结-配置相关

### git 配置文件说明

- /etc/gitconfig 文件：包含了适用于系统所有用户和所有库的值，可以使用命令 `git config --system ` 进行配置。
- ~/.gitconfig 文件 ：包含指定用户的配置，可以使用命令 `git config --global` 进行配置。
- 位于项目 .git 目录的 config 文件 (即 .git/config 文件) ：当前项目指定的配置，可以使用命令 `git config` 进行配置，该配置参数具有最高的优先级。  

当同一个参数在三个配置文件中都有设值时，项目文件下的配置参数值具有最高的优先级，其次是用户配置参数，最后是系统配置参数值。  

** 配置相关命令 **  
`git config --list`  查看所有的配置信息  
`git config --global --edit`  编辑 global 配置文件，当然在 window 环境下可以直接使用文本工具进行编辑（其实编辑的就是 ~/.gitconfig 文件）  

--------------------
### git bash 对于中文路径显示为数字问题解决办法  
默认情况下 git bash 对于中文的路径会进行编码，这样导致在使用`git status, git diff`等命令时无法直接看到改动的文件名，如下：  
![git bash 中文路径显示乱码](../images/1001.png)  
**解决办法：**  
`$ git config --global core.quotepath false`  
之后再使用`git status`查看可以正常显示中文文件名了
![git bash 正常显示中文路径](../images/1002.png)  

--------------------
### git diff 基于第三方比较工具配置
通常情况下我们要比较某个文件不同版本之间的差异时，我们可以使用 `git diff` 命令来进行比较，但是如果文件比较大，差异行数比较多时，基于命令窗口的 `git diff` 比较就显得有些无力了，幸好 git 支持我们自定义配置第三方专业的差异比较工具来进行比较。  
下面演示了通过配置 BCompare 工具来进行差异比较，我们使用直接编辑配置文件的办法来配置，在配置文件中加入如下配置，保存文件，重启 gitbash  
*（如果是 linux 环境可以使用 `git config --global --edit` 来打开 config vi 编辑界面）*  
``` bash
# difftool 配置
[diff]
	tool = bc4
[difftool "bc4"]
	cmd = "\"C:/Program Files/Beyond Compare 4/bcomp.exe\" \"$LOCAL\" \"$REMOTE\""
[difftool]
	prompt = false

# mergeftool 配置
[merge]
	tool = bc4
[mergetool "bc4"]
	cmd = "\"C:/Program Files/Beyond Compare 4/bcomp.exe\" \"$LOCAL\" \"$REMOTE\" \"$BASE\" \"$MERGED\""
	trustExitCode = true
```
如果配置成功的话，使用命令 `git difftool` 可以弹出 BCompare 的比较窗口  
