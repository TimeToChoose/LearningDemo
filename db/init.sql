-- 本地执行：mysql -uroot -p12345678 -h127.0.0.1 < db/init.sql

CREATE DATABASE IF NOT EXISTS learning_demo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE learning_demo;

CREATE TABLE IF NOT EXISTS demo_item (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(100) NOT NULL COMMENT '名称',
    category    VARCHAR(50)  NOT NULL COMMENT '分类',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='示例物品表';

CREATE TABLE IF NOT EXISTS demo_message (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    message     VARCHAR(500) NOT NULL COMMENT '消息内容',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Echo 消息记录';

INSERT INTO demo_item (name, category) VALUES
    ('Kotlin', 'language'),
    ('Spring Boot', 'framework'),
    ('Android', 'client');

-- ========== 业务模块表 ==========

CREATE TABLE IF NOT EXISTS sys_user (
    id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username        VARCHAR(50)  NOT NULL COMMENT '登录名',
    password_hash   VARCHAR(100) NOT NULL COMMENT 'BCrypt 密码',
    nickname        VARCHAR(50)  NOT NULL COMMENT '昵称',
    avatar          VARCHAR(500) NOT NULL DEFAULT '' COMMENT '头像 URL',
    bio             VARCHAR(500) NOT NULL DEFAULT '' COMMENT '个人简介',
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

CREATE TABLE IF NOT EXISTS refresh_token (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id     BIGINT       NOT NULL COMMENT '用户 ID',
    token_hash  VARCHAR(64)  NOT NULL COMMENT 'refresh token SHA-256',
    expires_at  DATETIME     NOT NULL COMMENT '过期时间',
    revoked     TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '是否已撤销',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_token_hash (token_hash)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Refresh Token';

CREATE TABLE IF NOT EXISTS mall_product (
    id          BIGINT         NOT NULL AUTO_INCREMENT COMMENT '主键',
    name        VARCHAR(100)   NOT NULL COMMENT '商品名',
    price       DECIMAL(10, 2) NOT NULL COMMENT '价格',
    cover_url   VARCHAR(500)   NOT NULL COMMENT '封面图',
    category    VARCHAR(50)    NOT NULL COMMENT '分类',
    stock       INT            NOT NULL DEFAULT 0 COMMENT '库存',
    description VARCHAR(1000)  NOT NULL DEFAULT '' COMMENT '描述',
    created_at  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商城商品';

CREATE TABLE IF NOT EXISTS video_feed (
    id          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    tab         VARCHAR(20)  NOT NULL COMMENT 'recommend/follow/nearby',
    title       VARCHAR(200) NOT NULL COMMENT '标题',
    cover_url   VARCHAR(500) NOT NULL COMMENT '封面',
    video_url   VARCHAR(500) NOT NULL COMMENT '视频地址',
    author_id   BIGINT       NOT NULL COMMENT '作者用户 ID',
    like_count  INT          NOT NULL DEFAULT 0 COMMENT '点赞数',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_tab_id (tab, id DESC),
    KEY idx_author (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短视频流';

-- 密码均为 demo123（BCrypt）
INSERT INTO sys_user (username, password_hash, nickname, avatar, bio) VALUES
    ('demo', '$2a$10$mhgJAQDMEgabRMSAvr7h5OVz8QpPMdNIEf5wVd5TYWIulndy95b6i', '演示用户', 'https://picsum.photos/seed/demo/200', 'LearningTemplate 演示账号'),
    ('alice', '$2a$10$mhgJAQDMEgabRMSAvr7h5OVz8QpPMdNIEf5wVd5TYWIulndy95b6i', 'Alice', 'https://picsum.photos/seed/alice/200', 'Android 学习者');

INSERT INTO mall_product (name, price, cover_url, category, stock, description) VALUES
    ('Kotlin 编程实战', 59.90, 'https://picsum.photos/seed/book-kotlin/400/300', 'digital', 120, 'Kotlin 语言系统学习书籍'),
    ('Jetpack Compose 入门', 69.00, 'https://picsum.photos/seed/book-compose/400/300', 'digital', 80, 'Compose UI 开发指南'),
    ('Spring Boot 微服务', 79.00, 'https://picsum.photos/seed/book-spring/400/300', 'digital', 60, 'Spring Boot 后端实战'),
    ('无线蓝牙耳机', 199.00, 'https://picsum.photos/seed/earphone/400/300', 'digital', 200, '降噪蓝牙 5.3 耳机'),
    ('机械键盘', 349.00, 'https://picsum.photos/seed/keyboard/400/300', 'digital', 45, '青轴机械键盘'),
    ('保温杯 500ml', 89.00, 'https://picsum.photos/seed/cup/400/300', 'life', 300, '不锈钢真空保温杯'),
    ('懒人沙发', 299.00, 'https://picsum.photos/seed/sofa/400/300', 'life', 30, '单人折叠懒人沙发'),
    ('香薰加湿器', 159.00, 'https://picsum.photos/seed/humidifier/400/300', 'life', 75, '静音卧室加湿器'),
    ('运动水杯', 49.00, 'https://picsum.photos/seed/bottle/400/300', 'life', 150, 'Tritan 材质运动水杯'),
    ('休闲卫衣', 199.00, 'https://picsum.photos/seed/hoodie/400/300', 'fashion', 90, '纯棉连帽卫衣'),
    ('运动跑鞋', 399.00, 'https://picsum.photos/seed/shoes/400/300', 'fashion', 55, '轻便缓震跑鞋'),
    ('帆布双肩包', 129.00, 'https://picsum.photos/seed/backpack/400/300', 'fashion', 110, '大容量通勤背包');

INSERT INTO video_feed (tab, title, cover_url, video_url, author_id, like_count) VALUES
    ('recommend', 'Kotlin 协程入门', 'https://picsum.photos/seed/v-r1/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4', 1, 1280),
    ('recommend', 'Compose 状态管理', 'https://picsum.photos/seed/v-r2/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4', 1, 956),
    ('recommend', 'Retrofit 网络封装', 'https://picsum.photos/seed/v-r3/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4', 2, 2100),
    ('recommend', 'Spring Boot 快速上手', 'https://picsum.photos/seed/v-r4/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4', 2, 876),
    ('recommend', 'MyBatis 实战技巧', 'https://picsum.photos/seed/v-r5/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4', 1, 543),
    ('recommend', '滑动冲突解决方案', 'https://picsum.photos/seed/v-r6/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4', 2, 3200),
    ('recommend', 'MVVM 架构模式', 'https://picsum.photos/seed/v-r7/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4', 1, 1678),
    ('recommend', 'OkHttp 拦截器详解', 'https://picsum.photos/seed/v-r8/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4', 2, 990),
    ('recommend', 'JWT 鉴权原理', 'https://picsum.photos/seed/v-r9/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4', 1, 1456),
    ('recommend', 'Room 数据库入门', 'https://picsum.photos/seed/v-r10/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4', 2, 723),
    ('follow', 'Alice 的学习日记', 'https://picsum.photos/seed/v-f1/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4', 2, 456),
    ('follow', '今日 Android 技巧', 'https://picsum.photos/seed/v-f2/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4', 2, 789),
    ('follow', 'Compose 动画练习', 'https://picsum.photos/seed/v-f3/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4', 2, 1200),
    ('follow', '后端接口联调记录', 'https://picsum.photos/seed/v-f4/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4', 1, 634),
    ('follow', 'EventBus 使用心得', 'https://picsum.photos/seed/v-f5/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4', 2, 890),
    ('follow', '自定义 View 绘制', 'https://picsum.photos/seed/v-f6/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4', 1, 1567),
    ('follow', '内存泄漏排查', 'https://picsum.photos/seed/v-f7/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4', 2, 2340),
    ('follow', 'Navigation 导航组件', 'https://picsum.photos/seed/v-f8/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4', 1, 567),
    ('nearby', '同城 · 西湖晨跑', 'https://picsum.photos/seed/v-n1/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4', 1, 345),
    ('nearby', '同城 · 咖啡探店', 'https://picsum.photos/seed/v-n2/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4', 2, 678),
    ('nearby', '同城 · 周末骑行', 'https://picsum.photos/seed/v-n3/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4', 1, 901),
    ('nearby', '同城 · 夜市美食', 'https://picsum.photos/seed/v-n4/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4', 2, 1234),
    ('nearby', '同城 · 公园摄影', 'https://picsum.photos/seed/v-n5/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4', 1, 456),
    ('nearby', '同城 · 书店打卡', 'https://picsum.photos/seed/v-n6/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4', 2, 789),
    ('nearby', '同城 · 江边日落', 'https://picsum.photos/seed/v-n7/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4', 1, 2100),
    ('nearby', '同城 · 地铁通勤', 'https://picsum.photos/seed/v-n8/400/700', 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4', 2, 567);