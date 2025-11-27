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

#### c.启动 
``` shell
# tftp启动fitimage（build/tmp/deploy/images/loongson-2k0300-99pai/fitImage-core-image-minimal-initramfs-loongson-2k0300-99pai-loongson-2k0300-99pai）   

SOC#setenv ipaddr 192.168.2.111
SOC#setenv serverip 192.168.2.110
SOC#setenv gatewayip 192.168.2.1
SOC#setenv netmask 255.255.255.0
SOC#setenv bootfile fitImage
SOC#tftpboot
Speed: 1000, full duplex
Using ethernet@0x16020000 device
TFTP from server 192.168.2.100; our IP address is 192.168.2.111
Filename 'fitImage'.
Load address: 0x9000000003000000
Loading: ###
         2.6 MiB/s
done
Bytes transferred = 25629220 (1871224 hex)
Automatic boot of image at addr 0x9000000003000000 ...
## Loading kernel from FIT Image at 9000000003000000 ...
   Using 'conf-loongson_2k0300_99_pai_wifi.dtb' configuration
   Trying 'kernel-1' kernel subimage
     Description:  Linux kernel
     Created:      2025-09-27   3:38:21 UTC
     Type:         Kernel Image
     Compression:  gzip compressed
     Data Start:   0x9000000003000108
     Data Size:    13224263 Bytes = 12.6 MiB
     Architecture: LoongArch
     OS:           Linux
     Load Address: 0x00200000
     Entry Point:  0x01541000
     Hash algo:    sha1
     Hash value:   072fe962b494ba68e6a8430b6773185b7147f072
   Verifying Hash Integrity ... sha1+ OK
## Loading ramdisk from FIT Image at 9000000003000000 ...
   Using 'conf-loongson_2k0300_99_pai_wifi.dtb' configuration
   Trying 'ramdisk-1' ramdisk subimage
     Description:  core-image-minimal-initramfs
     Created:      2025-09-27   3:38:21 UTC
     Type:         RAMDisk Image
     Compression:  uncompressed
     Data Start:   0x9000000003ca5f54
     Data Size:    12364827 Bytes = 11.8 MiB
     Architecture: LoongArch
     OS:           Linux
     Load Address: 0x06000000
     Entry Point:  0x06000000
     Hash algo:    sha1
     Hash value:   66be004f6acbd625349ba1f9523f2ec2c35219c3
   Verifying Hash Integrity ... sha1+ OK
   Loading ramdisk from 0x9000000003ca5f54 to 0x06000000
## Loading fdt from FIT Image at 9000000003000000 ...
   Using 'conf-loongson_2k0300_99_pai_wifi.dtb' configuration
   Trying 'fdt-loongson_2k0300_99_pai_wifi.dtb' fdt subimage
     Description:  Flattened Device Tree blob
     Created:      2025-09-27   3:38:21 UTC
     Type:         Flat Device Tree
     Compression:  uncompressed
     Data Start:   0x9000000003c9cb68
     Data Size:    18742 Bytes = 18.3 KiB
     Architecture: LoongArch
     Load Address: 0x0a000000
     Hash algo:    sha1
     Hash value:   2746965a1d6ad9449404652a3d71ca406c1239a6
   Verifying Hash Integrity ... sha1+ OK
   Loading fdt from 0x9000000003c9cb68 to 0x0a000000
   Booting using the fdt blob at 0xa000000
Working FDT set to 900000000a000000
   Uncompressing Kernel Image to 200000
   Loading Device Tree to 900000000cbef000, end 900000000cbf6935 ... OK
Working FDT set to 900000000cbef000
[    0.000000] Linux version 6.9.0-rc7 (oe-user@oe-host) (loongarch64-loongky-linux-gcc (GCC) 14.3.0, GNU ld (GNU Binutils) 2.44.0.20250715) #1 SMP PREEMPT_DYNAMIC Sat Sep 27 03:38:21 UTC 2025
[    0.000000] 64-bit Loongson Processor probed (LA264 Core)
# ...
# ...
# ...
[    6.159064] Freeing unused kernel image (initmem) memory: 576K
[    6.172819] This architecture does not have kernel memory protection.
[    6.188846] Run /init as init process
INIT: version 3.14 booting
Starting udev
[    6.767737] udevd[114]: starting version 3.2.14
[    6.805848] udevd[115]: starting eudev-3.2.14
[    8.055007] mmc0: new HS200 MMC card at address 0001
[    8.067367] mmcblk0: mmc0:0001 08A391 7.28 GiB
[    8.083172]  mmcblk0: p1
[    8.093361] mmcblk0boot0: mmc0:0001 08A391 4.00 MiB
[    8.113635] mmcblk0boot1: mmc0:0001 08A391 4.00 MiB
[    9.612852] random: crng init done
INIT: Entering runlevel: 5
Configuring network interfaces... [    9.743532] stmmaceth 16020000.ethernet eth0: Register MEM_TYPE_PAGE_POOL RxQ-0
[    9.753605] stmmaceth 16020000.ethernet eth0: PHY [stmmac-0:01] driver [Generic PHY] (irq=POLL)
[    9.763002] stmmaceth 16020000.ethernet eth0: No Safety Features support found
[    9.770277] stmmaceth 16020000.ethernet eth0: IEEE 1588-2008 Advanced Timestamp supported
[    9.780396] stmmaceth 16020000.ethernet eth0: registered PTP clock
[    9.793310] stmmaceth 16020000.ethernet eth0: configuring for phy/rgmii link mode
udhcpc: started, v1.37.0
udhcpc: broadcasting discover
udhcpc: sendto: No buffer space available
udhcpc: broadcasting discover
udhcpc: sendto: No buffer space available
[   13.878548] stmmaceth 16020000.ethernet eth0: Link is Up - 1Gbps/Full - flow control rx/tx
udhcpc: broadcasting discover
udhcpc: no lease, forking to background
ip: SIOCGIFFLAGS: No such device
Starting syslogd/klogd: done

Loongky (Yocto Project Reference Distro) 0.1.1 loongson-2k0300-99pai /dev/ttyS0

loongson-2k0300-99pai login:
Loongky (Yocto Project Reference Distro) 0.1.1 loongson-2k0300-99pai /dev/ttyS0

loongson-2k0300-99pai login: root
root@loongson-2k0300-99pai:~#
```