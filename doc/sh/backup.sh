#!/bin/bash
#配置参数：

#需要压缩的路径
fileUrl[0]="/dockerconf"

#压缩后的名称（和上面对应）
fileName[0]="dockerconf"

zipFile(){
  echo "正在运行函数：压缩文件"
	i=0
	dateStr=`date +%Y%m%d%H%M%S`
	fileSize=${#fileUrl[*]}
	while(( $i<fileSize ))
	do
	  zip -r ${fileName[i]}${dateStr}.zip ${fileUrl[i]}
		let "i++"
	done
}

myMain(){
cd ~
zipFile
}

myMain