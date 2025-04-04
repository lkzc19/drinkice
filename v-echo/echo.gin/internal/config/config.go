package config

import (
	"os"
	"strconv"

	"github.com/joho/godotenv"
)

type Config struct {
	Env  string
	Port int
}

func LoadConfig(env string) *Config {
	_ = godotenv.Load("application-" + env + ".env")

	portStr := os.Getenv("PORT")
	port, _ := strconv.Atoi(portStr)

	return &Config{
		Env:  env,
		Port: port,
	}
}
