#!/bin/bash

echo "========================================="
echo "HR 管理系统开发环境启动脚本"
echo "========================================="

# 启动后端
echo "正在启动后端服务..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!

# 等待后端启动
sleep 10

# 启动前端
echo "正在启动前端服务..."
cd ../frontend
npm run dev &
FRONTEND_PID=$!

echo ""
echo "========================================="
echo "服务启动成功！"
echo "========================================="
echo ""
echo "前端地址: http://localhost:8000"
echo "后端 API: http://localhost:8080"
echo "Swagger: http://localhost:8080/swagger-ui/index.html"
echo ""
echo "默认账号："
echo "- admin / 123456 (管理员)"
echo "- hr001 / 123456 (人事专员)"
echo "- emp001 / 123456 (普通员工)"
echo ""
echo "按 Ctrl+C 停止所有服务"
echo ""

# 等待用户中断
wait $BACKEND_PID $FRONTEND_PID

