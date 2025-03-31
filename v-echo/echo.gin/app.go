package main

import (
	"strconv"

	"echo.gin/internal/config"
	"echo.gin/internal/router"
	"github.com/gin-gonic/gin"
)

func main() {
	config := config.LoadConfig()
	r := gin.Default()
	router.Route(r)
	r.Run(":" + strconv.Itoa(config.Port))
}
