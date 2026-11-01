#!/bin/sh

is_wifi() {
    [ -e "/sys/class/net/$1/wireless" ]
}

ARPING="$(command -v arping 2>/dev/null || true)"
[ -n "$ARPING" ] || exit 0

IFACE=""
IP=""
for d in /sys/class/net/*/wireless; do
    [ -e "$d" ] || continue
    iface="$(basename "$(dirname "$d")")"

    [ "$(cat "/sys/class/net/$iface/operstate" 2>/dev/null)" = "up" ] || continue

    ip4="$(ip -4 -o addr show dev "$iface" 2>/dev/null | \
           awk '{print $4}' | cut -d/ -f1 | head -n 1)"
    [ -n "$ip4" ] || continue

    IFACE="$iface"
    IP="$ip4"
    break
done

[ -n "$IFACE" ] || exit 0

"$ARPING" -U -c 1 -w 1 -q -I "$IFACE" -s "$IP" "$IP" >/dev/null 2>&1 || true
exit 0