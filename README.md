# LearningDemo

Spring Boot 后端学习工程（Kotlin + MyBatis），为 Android 前端提供真实业务接口示例：

- 登录鉴权（JWT + refresh token）
- 个人信息
- 商城列表/详情
- 短视频流（tab + 游标分页）

## 技术栈

- Spring Boot `4.0.6`
- Kotlin `2.2.21`
- MyBatis `4.0.1`
- MySQL
- SpringDoc OpenAPI（Swagger）

## 环境要求

- JDK 17
- MySQL 8+

## 本地启动

1. 初始化数据库：

```bash
mysql -uroot -p12345678 -h127.0.0.1 < db/init.sql
```

2. 启动服务：

```bash
./gradlew bootRun
```

默认端口：`8080`

## API 文档

- Swagger UI：`http://localhost:8080/swagger-ui.html`
- OpenAPI JSON：`http://localhost:8080/api-docs`

## 关键接口

### 鉴权

- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/logout`

### 个人信息

- `GET /api/user/profile`
- `PUT /api/user/profile`

### 商城

- `GET /api/mall/categories`
- `GET /api/mall/products`
- `GET /api/mall/products/{id}`

### 视频流

- `GET /api/videos/feed?tab=recommend&cursor=&limit=`

## 测试账号

- 用户名：`demo`
- 密码：`demo123`

## 与前端联调

前端工程位于 `../MockApiProject`，已配置多环境 flavor：

- `emulator`（10.0.2.2）
- `genymotion`（10.0.3.2）
- `realdevice`（192.168.2.197）
- `tunnel`（vicp.fun 穿透域名）

网络配置详情见：

- `../.cursor/skills/local-dev-network/SKILL.md`
