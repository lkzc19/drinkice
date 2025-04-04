# 构建阶段
FROM golang:1.23.2-alpine AS builder

ARG GOOS
ARG GOARCH

ENV GOPROXY=https://goproxy.cn,direct

WORKDIR /app

COPY . .
RUN go mod tidy

# 构建应用
# RUN CGO_ENABLED=0 GOOS=${GOOS} GOARCH=${GOARCH} go build -o ./buildx/app
RUN go build -o ./buildx/app

# 运行时阶段
FROM scratch

# 设置环境变量默认值
ARG ACTIVE=dev
ENV ACTIVE=${ACTIVE}

WORKDIR /app

# 复制编译后的二进制文件
COPY --from=builder /app/buildx/app ./app

# 暴露端口
EXPOSE 8080 9998

# 启动命令
CMD ["./app", "-e", "${ACTIVE}"]