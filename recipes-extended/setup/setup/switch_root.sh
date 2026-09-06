#!/bin/sh

# Check if an argument is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <partition_number>"
    echo "Example: $0 1    # Switch to mmcblk0p1"
    echo "Example: $0 2    # Switch to mmcblk0p2"
    exit 1
fi

PARTITION_NUM=$1
NEW_ROOT="/dev/mmcblk0p${PARTITION_NUM}"

# 1. Update the 'rootpart' environment variable
echo "Switching root filesystem to: ${NEW_ROOT}"
fw_setenv rootpart "${NEW_ROOT}"

# 2. Re-assemble the complete 'bootargs'
# Note: BOOTARGS_BASE must match the base arguments set in your U-Boot environment
BOOTARGS_BASE="earlycon console=ttyS0,115200n8 rw rootwait"
NEW_BOOTARGS="${BOOTARGS_BASE} root=${NEW_ROOT}"

echo "Updating bootargs to: ${NEW_BOOTARGS}"
fw_setenv bootargs "${NEW_BOOTARGS}"

# 3. Re-assemble the 'bootcmd' to load kernel from the correct partition
# Note: BOOTCMD_BASE must match your U-Boot's kernel loading command
BOOTCMD_BASE="ext4load mmc 0:${PARTITION_NUM} \${loadaddr} /boot/fitImage; bootm \${loadaddr}"

echo "Updating bootcmd to: ${BOOTCMD_BASE}"
fw_setenv bootcmd "${BOOTCMD_BASE}"

# 4. Read back and print the new variables to verify
echo "----------------------------------------"
echo "Verification: New environment variables:"
fw_printenv bootargs
fw_printenv bootcmd
echo "----------------------------------------"

# 5. Prompt for reboot
echo "Environment variables updated. Please run 'reboot' to apply changes."