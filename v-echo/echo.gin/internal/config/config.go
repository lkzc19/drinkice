package config

import (
	"flag"
	"os"
	"strconv"

	"github.com/joho/godotenv"
)

type Config struct {
	Env  string
	Port int
}

func LoadConfig() *Config {
	var env string
	flag.StringVar(&env, "e", "dev", "Environment configuration")
	flag.Parse()
	if env == "" {
		env = "dev"
	}
	_ = godotenv.Load("application-" + env + ".env")

	portStr := os.Getenv("PORT")
	port, _ := strconv.Atoi(portStr)

	return &Config{
		Env:  env,
		Port: port,
	}
}
