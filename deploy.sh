#!/bin/bash

echo "========================================="
echo "HR 管理系统部署脚本"
echo "========================================="

# 检查 MySQL 是否运行
echo "检查 MySQL 服务..."
if ! command -v mysql &> /dev/null; then
    echo "错误: MySQL 未安装或未添加到 PATH"
    exit 1
fi

# 构建后端
echo ""
echo "1. 构建后端项目..."
cd backend
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "错误: 后端构建失败"
    exit 1
fi
echo "✓ 后端构建成功"

# 构建前端
echo ""
echo "2. 构建前端项目..."
cd ../frontend
npm install
npm run build
if [ $? -ne 0 ]; then
    echo "错误: 前端构建失败"
    exit 1
fi
echo "✓ 前端构建成功"

echo ""
echo "========================================="
echo "部署完成！"
echo "========================================="
echo ""
echo "启动说明："
echo "1. 后端: cd backend && java -jar target/hr-backend-1.0.0.jar"
echo "2. 前端: cd frontend && npm run preview"
echo ""
echo "或使用开发模式："
echo "1. 后端: cd backend && mvn spring-boot:run"
echo "2. 前端: cd frontend && npm run dev"
echo ""
echo "访问地址："
echo "- 前端: http://localhost:8000"
echo "- 后端 API: http://localhost:8080"
echo "- Swagger: http://localhost:8080/swagger-ui/index.html"
echo ""

