#!/bin/sh

GATEWAY=$(ip route | grep default | awk '{print $3}' | head -n1)
COUNT=4
LOG_FILE="/var/log/ping_gateway.log"

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" >> "$LOG_FILE"
}

if ping -c $COUNT -W 2 "$GATEWAY" > /dev/null 2>&1; then
    log "✅ 网关 $GATEWAY 正常"
    exit 0
else
    log "❌ 网关 $GATEWAY 不通"
    # 告警动作放这里，例如：
    # curl -X POST "https://webhook.example.com/alert" -d "网关异常"
    exit 1
fi
