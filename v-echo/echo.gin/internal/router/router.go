package router

import (
	"echo.gin/internal/controller"
	"github.com/gin-gonic/gin"
)

// InitRouter 初始化路由配置
func Route(r *gin.Engine) {
	api := r.Group("/")
	{
		api.Any("/*path", controller.Echo)
	}
}
