SUMMARY = "Basic initramfs to boot rootfs"
LICENSE = "MIT"

inherit core-image

# Don't allow the initramfs to contain a kernel, as kernel modules will depend
# on the kernel image.
PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_LINGUAS = ""

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_NAME_SUFFIX ?= ""

PACKAGE_INSTALL = " \
    base-files \
    base-passwd \
    netbase \
    ${VIRTUAL-RUNTIME_base-utils} \
    ${VIRTUAL-RUNTIME_login_manager} \
    ${VIRTUAL-RUNTIME_init_manager} \
    ${VIRTUAL-RUNTIME_dev_manager} \
    curl lrzsz \
    mmc-utils mtd-utils \
    e2fsprogs-e2fsck e2fsprogs-mke2fs \
    util-linux-lsblk util-linux-lscpu \
    libubootenv-bin \
    setup \
    "
