require conf/machine/include/loongarch/tune-loongarch.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-loongson2"

IMAGE_FSTYPES += " tar.gz"

SERIAL_CONSOLES ?= "115200;ttyS0"

PREFERRED_PROVIDER_virtual/bootloader = "u-boot"
PREFERRED_VERSION_u-boot = "2024.04"
