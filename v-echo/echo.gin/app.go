package main

import (
	"flag"
	"os"
	"strconv"

	"echo.gin/internal/config"
	"echo.gin/internal/router"
	"github.com/gin-gonic/gin"
)

func main() {
	// 配合docker传入变量选择配置文件
	env := os.Getenv("ACTIVE")
	if env == "" {
		flag.StringVar(&env, "e", "dev", "Environment configuration")
	}
	flag.Parse()

	config := config.LoadConfig(env)
	r := gin.Default()
	router.Route(r)
	r.Run(":" + strconv.Itoa(config.Port))
}
