#!/usr/bin/env bash
# 一键：启动 Spring Boot + cpolar 内网穿透
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

cleanup() {
  if [[ -n "${BOOT_PID:-}" ]] && kill -0 "$BOOT_PID" 2>/dev/null; then
    echo "停止 Spring Boot (pid=$BOOT_PID)..."
    kill "$BOOT_PID" 2>/dev/null || true
  fi
}
trap cleanup EXIT INT TERM

echo "启动 Spring Boot..."
./gradlew bootRun --quiet &
BOOT_PID=$!

echo "等待服务就绪..."
for i in {1..60}; do
  if curl -sf http://127.0.0.1:8080/api/health >/dev/null 2>&1; then
    echo "本地服务已就绪：http://127.0.0.1:8080"
    echo "  - 健康检查：http://127.0.0.1:8080/api/health"
    echo "  - Swagger：  http://127.0.0.1:8080/swagger-ui.html"
    break
  fi
  sleep 2
  if [[ $i -eq 60 ]]; then
    echo "Spring Boot 启动超时"
    exit 1
  fi
done

"$ROOT/scripts/start-tunnel.sh"
