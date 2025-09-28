# meta-loongarch

`meta-loongarch` 是用于loongarch架构的yocto bsp层，用于支持基于2k0300的99pai构建工程  

## 构建流程

### 1.准备环境和源码

基于Ubuntu24.04虚拟机构建，参考[yocto教程](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html)安装需要的包，**使用非root用户编译**
``` shell
sudo apt-get install build-essential chrpath cpio debianutils diffstat file gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd
```

#### a.克隆loongsarch层
``` shell
git clone https://github.com/otomam/meta-loongarch.git -b walnascar
```

#### b.克隆poky

``` shell
git clone https://github.com/yoctoproject/poky.git -b walnascar
```

### 2.构建
#### a.使用自定义的配置文件配置构建环境
``` shell
export TEMPLATECONF=$PWD/meta-loongarch/conf/templates/default
. poky/oe-init-build-env
```

#### b.构建镜像
``` shell
bitbake core-image-minimal
```

#### c.获取生成文件  
``` shell
#在 build/tmp/deploy/images/loongson-2k0300-99pai 目录下可以找到生成的uImage, rootfs和u-boot-spl-gz.bin (u-boot是在PMON上升级的, 一时没弄明白怎么在u-boot上更新)  
#通过搭建tftp和nfs服务对内核和rootfs进行了测试：  

SOC#setenv ipaddr 192.168.0.111
SOC#setenv serverip 192.168.0.110
SOC#setenv gatewayip 192.168.0.1
SOC#setenv netmask 255.255.255.0
SOC#setenv bootfile uImage
SOC#setenv bootargs console=ttyS0,115200n8 root=/dev/nfs rw nfsroot=${serverip}:/srv/nfs,v3,tcp ip=${ipaddr}::${gatewayip}:${netmask}::eth0:off
SOC#tftpboot
Speed: 1000, full duplex
Using ethernet@0x16020000 device
TFTP from server 192.168.0.110; our IP address is 192.168.0.111
Filename 'uImage'.
Load address: 0x9000000003000000
# ...
Warning: invalid device tree. Used linux default dtb
[    0.000000] Linux version 6.9.0-rc7 (oe-user@oe-host) (loongarch64-loongky-linux-gcc (GCC) 14.3.0, GNU ld (GNU Binutils) 2.44.0.20250715) #1 SMP PREEMPT_DYNAMIC Sat Sep 27 03:38:21 UTC 2025
# ...
[    9.575633] devtmpfs: mounted
[    9.580720] Freeing unused kernel image (initmem) memory: 640K
[    9.586803] This architecture does not have kernel memory protection.
[    9.593259] Run /sbin/init as init process
INIT: version 3.14 booting
Starting udev
[   11.180699] udevd[113]: starting version 3.2.14
[   11.818686] udevd[114]: starting eudev-3.2.14
[   14.516860] random: crng init done
INIT: Entering runlevel: 5
Configuring network interfaces... ip: RTNETLINK answers: File exists
ifup skipped for nfsroot interface eth0
run-parts: /etc/network/if-pre-up.d/nfsroot: exit status 1
ip: SIOCGIFFLAGS: No such device
Starting syslogd/klogd: done

Loongky (Yocto Project Reference Distro) 0.1.1 loongson-2k0300-99pai /dev/ttyS0

loongson-2k0300-99pai login: root
root@loongson-2k0300-99pai:~#
```