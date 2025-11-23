# meta-loongarch

`meta-loongarch` 是用于loongarch架构的yocto bsp层，用于支持基于2k0300的99pai构建工程  

## 构建流程

### 1.准备环境和源码

基于Ubuntu24.04虚拟机构建，参考[yocto教程](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html)安装需要的包，**使用非root用户编译**
``` shell
sudo apt-get install build-essential chrpath cpio debianutils diffstat file gawk gcc git iputils-ping libacl1 liblz4-tool locales python3 python3-git python3-jinja2 python3-pexpect python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd
```

#### a.克隆loongarch层
``` shell
git clone https://github.com/otomam/meta-loongarch.git -b walnascar
```

#### b.克隆poky

``` shell
git clone https://github.com/yoctoproject/poky.git -b walnascar
```

### 2.构建
#### a.配置构建环境
``` shell
. meta-loongarch/setup
```
#### b.构建镜像
``` shell
bitbake core-image-minimal
```

#### c.获取生成文件  
``` shell
# 通过搭建tftp和nfs服务对内核和rootfs进行了测试,    
# nfs目录需要权限 [ /srv/nfs *(rw,sync,no_root_squash) ]   
# rootfs直接解压yocto生成的rootfs [ tar xfp tmp/deploy/images/loongson-2k0300-99pai/core-image-minimal-loongson-2k0300-99pai.rootfs.tar.gz ]   

SOC#setenv ipaddr 192.168.2.111
SOC#setenv serverip 192.168.2.110
SOC#setenv gatewayip 192.168.2.1
SOC#setenv netmask 255.255.255.0
SOC#setenv bootfile fitImage
SOC#setenv bootargs console=ttyS0,115200n8 root=/dev/nfs rw nfsroot=${serverip}:/srv/nfs,v3,tcp ip=${ipaddr}::${gatewayip}:${netmask}::eth0:off
SOC#tftpboot
Speed: 1000, full duplex
Using ethernet@0x16020000 device
TFTP from server 192.168.2.100; our IP address is 192.168.2.111
Filename 'fitImage'.
Load address: 0x9000000003000000
Loading: ###
         2.6 MiB/s
done
Bytes transferred = 13264396 (ca660c hex)
Automatic boot of image at addr 0x9000000003000000 ...
## Loading kernel from FIT Image at 9000000003000000 ...
   Using 'conf-loongson_2k0300_99_pai_wifi.dtb' configuration
   Trying 'kernel-1' kernel subimage
     Description:  Linux kernel
     Created:      2025-11-22   5:40:31 UTC
     Type:         Kernel Image
     Compression:  gzip compressed
     Data Start:   0x9000000003000108
     Data Size:    13224567 Bytes = 12.6 MiB
     Architecture: LoongArch
     OS:           Linux
     Load Address: 0x00200000
     Entry Point:  0x01541000
     Hash algo:    sha1
     Hash value:   45b1a4ac9eb2f95311c26c24a54c0b230b511e9c
   Verifying Hash Integrity ... sha1+ OK
## Loading fdt from FIT Image at 9000000003000000 ...
   Using 'conf-loongson_2k0300_99_pai_wifi.dtb' configuration
   Trying 'fdt-loongson_2k0300_99_pai_wifi.dtb' fdt subimage
     Description:  Flattened Device Tree blob
     Created:      2025-11-22   5:40:31 UTC
     Type:         Flat Device Tree
     Compression:  uncompressed
     Data Start:   0x9000000003c9cc98
     Data Size:    18742 Bytes = 18.3 KiB
     Architecture: LoongArch
     Load Address: 0x0a000000
     Hash algo:    sha1
     Hash value:   2746965a1d6ad9449404652a3d71ca406c1239a6
   Verifying Hash Integrity ... sha1+ OK
   Loading fdt from 0x9000000003c9cc98 to 0x0a000000
   Booting using the fdt blob at 0xa000000
Working FDT set to 900000000a000000
   Uncompressing Kernel Image to 200000
   Loading Device Tree to 900000000cbef000, end 900000000cbf6935 ... OK
Working FDT set to 900000000cbef000
[    0.000000] Linux version 6.9.0-rc7 (oe-user@oe-host) (loongarch64-loongky-linux-gcc (GCC) 14.3.0, GNU ld (GNU Binutils) 2.44.0.20250715) #1 SMP PREEMPT_DYNAMIC Sat Nov 22 05:40:31 UTC 2025
[    0.000000] 64-bit Loongson Processor probed (LA264 Core)
# ...
[   10.847855] VFS: Mounted root (nfs filesystem) on device 0:18.
[   10.855011] devtmpfs: mounted
[   10.859889] Freeing unused kernel image (initmem) memory: 576K
[   10.865932] This architecture does not have kernel memory protection.
[   10.872432] Run /sbin/init as init process
INIT: version 3.14 booting
Starting udev
[   11.754085] udevd[114]: starting version 3.2.14
[   11.926684] udevd[115]: starting eudev-3.2.14
[   15.048972] random: crng init done
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