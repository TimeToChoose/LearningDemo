#!/usr/bin/env bash
# 内网穿透：将本机 8080 暴露为公网固定子域名（需先在 cpolar 官网预留子域名）
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
CONFIG="$ROOT/tunnel/cpolar.yml"
PORT="${SERVER_PORT:-8080}"

install_cpolar_mac() {
  if command -v brew >/dev/null 2>&1; then
    echo "正在通过 Homebrew 安装 cpolar..."
    brew install cpolar/cpolar/cpolar
  else
    echo "请先安装 cpolar：https://www.cpolar.com/docs"
    exit 1
  fi
}

if ! command -v cpolar >/dev/null 2>&1; then
  install_cpolar_mac
fi

if [[ ! -f "$CONFIG" ]]; then
  echo "未找到 $CONFIG"
  echo "请执行：cp tunnel/cpolar.yml.example tunnel/cpolar.yml"
  echo "并填入 authtoken；若需固定域名，在 cpolar 控制台预留子域名后编辑 tunnels 配置。"
  exit 1
fi

if grep -q 'YOUR_CPOLAR_AUTHTOKEN' "$CONFIG"; then
  echo "请先在 tunnel/cpolar.yml 中配置真实的 authtoken。"
  exit 1
fi

echo "检查本机 $PORT 端口是否已有服务..."
if ! curl -sf "http://127.0.0.1:$PORT/api/health" >/dev/null 2>&1; then
  echo "警告：http://127.0.0.1:$PORT/api/health 未响应。"
  echo "请先在 IDE 或另一终端运行：./gradlew bootRun"
  echo "5 秒后继续启动隧道..."
  sleep 5
fi

echo "启动 cpolar 隧道（配置文件：$CONFIG）..."
echo "公网地址请在 cpolar 控制台「状态」页查看，或使用：cpolar status"
exec cpolar start-all -config="$CONFIG"
