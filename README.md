# meta-loongarch

`meta-loongarch` 是用于loongarch架构的yocto bsp层，用于支持基于2k0300的99pai构建工程  

## 构建流程

### 1. 准备环境和源码

基于Ubuntu24.04虚拟机构建，参考[yocto教程](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html)安装需要的包，**使用非root用户编译**
``` shell
sudo apt-get install build-essential chrpath cpio debianutils diffstat file gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd
```

### 2. 参考yocto教程，修改机型并生成loongarch构建工程

### 3. 选择合适目录克隆本仓库

``` shell
git clone https://github.com/otomam/meta-loongarch.git -b wrynose
```

### 4. 克隆 meta-openembedded 并将两layer添加到配置文件

```
BBLAYERS ?= " \
  /home/oto/yocto/bitbake-builds/loongson/layers/openembedded-core/meta \
  /home/oto/yocto/bitbake-builds/loongson/layers/meta-yocto/meta-yocto-bsp \
  /home/oto/yocto/bitbake-builds/loongson/layers/meta-yocto/meta-poky \
  /home/oto/yocto/meta-openembedded/meta-oe \
  /home/oto/yocto/meta-openembedded/meta-python \
  /home/oto/yocto/meta-openembedded/meta-multimedia \
  /home/oto/yocto/meta-openembedded/meta-networking \
  /home/oto/yocto/meta-loongarch \
  "
```

### 5. 构建

构建rootfs.tar.gz
``` shell
bitbake core-image-minimal
```

修改 loongson-2k0300.inc 文件可构建包含initramfs的fitImage
``` shell
bitbake core-image-minimal-initramfs
```
可在 setup.bb 中配置初始ip地址

### 6. 分区并启动
在u-boot中参考执行下面命令，使用tftp下载fitImage并启动，通过fdisk分区
``` shell
setenv ipaddr 192.168.2.111
setenv serverip 192.168.2.100 
setenv gatewayip 192.168.2.1
setenv netmask 255.255.255.0
setenv bootfile fitImage
saveenv

tftpboot
```

当前设定为划分至少三个分区
``` shell
# lsblk
NAME         MAJ:MIN RM  SIZE RO TYPE MOUNTPOINTS
mmcblk0      179:0    0  7.3G  0 disk
|-mmcblk0p1  179:1    0    1G  0 part
|-mmcblk0p2  179:2    0    1G  0 part
`-mmcblk0p3  179:3    0  5.3G  0 part
mmcblk0boot0 179:8    0    4M  1 disk
mmcblk0boot1 179:16   0    4M  1 disk

```

p1和p2用作root，p3用作data，通过tftp下载rootfs并解压到默认root分区p1，重启后从mmcblk0p1启动
``` shell
# tftp -g -l rootfs.tar.gz 192.168.2.100

# mount /dev/mmcblk0p1 /mnt/
# rm -rf /mnt/*
# tar xpf rootfs.tar.gz -C /mnt/

# sync
# reboot
```

``` shell
# lsblk
NAME         MAJ:MIN RM  SIZE RO TYPE MOUNTPOINTS
mmcblk0      179:0    0  7.3G  0 disk
|-mmcblk0p1  179:1    0    1G  0 part /
|-mmcblk0p2  179:2    0    1G  0 part
`-mmcblk0p3  179:3    0  5.3G  0 part /data
mmcblk0boot0 179:8    0    4M  1 disk
mmcblk0boot1 179:16   0    4M  1 disk

```

想要更新，可用scp或tftp将rootfs下载到本地并解压到mmcblk0p2，使用脚本修改u-boot变量切换启动分区
``` shell
# scp xxx@192.168.2.100:/srv/tftp/rootfs.tar.gz .
# mount /dev/mmcblk0p2 /mnt/
# rm -rf /mnt/*
# tar -xpf rootfs.tar.gz -C /mnt/
# sync
```

``` shell
# /switch_root.sh 2
Switching root filesystem to: /dev/mmcblk0p2
Updating bootargs to: earlycon console=ttyS0,115200n8 rw rootwait root=/dev/mmcblk0p2
Updating bootcmd to: ext4load mmc 0:2 ${loadaddr} /boot/fitImage; bootm ${loadaddr}
----------------------------------------
Verification: New environment variables:
bootargs=earlycon console=ttyS0,115200n8 rw rootwait root=/dev/mmcblk0p2
bootcmd=ext4load mmc 0:2 ${loadaddr} /boot/fitImage; bootm ${loadaddr}
----------------------------------------
Environment variables updated. Please run 'reboot' to apply changes.
```
