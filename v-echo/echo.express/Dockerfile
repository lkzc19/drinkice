# 使用Node.js alpine基础镜像
FROM node:18-alpine

# 设置工作目录
WORKDIR /app

# 先复制package文件安装依赖
COPY package*.json ./
RUN npm install

# 复制项目文件
COPY . .

# 设置环境变量
ENV ACTIVE=prod

# 暴露端口
EXPOSE 8080 9999

# 启动命令
CMD ["sh", "-c", "npm run ${ACTIVE}"]