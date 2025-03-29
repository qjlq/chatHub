from fastapi import FastAPI, WebSocket, WebSocketDisconnect
from websockets import connect
import json
import asyncio
import uuid

app = FastAPI()

# Spring Boot WebSocket 服务端地址
SPRING_BOOT_WS_URL = "ws://localhost:8080/ws-endpoint"

# 客户端连接池（管理多个连接）
active_connections = {}

async def springboot_ws_client(connection_id: str):
    """
    连接 Spring Boot WebSocket 服务端的客户端
    """
    try:
        async with connect(SPRING_BOOT_WS_URL) as ws:
            # 将连接存入连接池
            active_connections[connection_id] = ws
            
            # 持续监听服务端消息
            while True:
                message = await ws.recv()
                print(f"Received from Spring Boot: {message}")
                
                # 这里可以添加消息处理逻辑
                # 例如转发到前端或存入数据库
                
    except Exception as e:
        print(f"Connection error: {str(e)}")
    finally:
        # 清理连接
        if connection_id in active_connections:
            del active_connections[connection_id]

@app.websocket("/api/ws-client")
async def websocket_client_endpoint(websocket: WebSocket):
    """
    FastAPI 的 WebSocket 端点，作为中间层客户端
    """
    await websocket.accept()
    connection_id = str(uuid.uuid4())
    
    try:
        # 启动后台任务连接 Spring Boot
        asyncio.create_task(springboot_ws_client(connection_id))
        
        # 监听前端消息并转发到 Spring Boot
        while True:
            data = await websocket.receive_text()
            
            # 如果 Spring Boot 连接已建立
            if connection_id in active_connections:
                spring_ws = active_connections[connection_id]
                await spring_ws.send(json.dumps({
                    "from": "fastapi-client",
                    "message": data
                }))
            else:
                await websocket.send_text("尚未连接到 Spring Boot 服务端")
                
    except WebSocketDisconnect:
        print("客户端断开连接")
    finally:
        # 关闭 Spring Boot 连接
        if connection_id in active_connections:
            await active_connections[connection_id].close()
            del active_connections[connection_id]