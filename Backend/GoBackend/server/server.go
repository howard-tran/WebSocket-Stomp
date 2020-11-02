package server

import (
	"GoBackend/middlewares"
	"GoBackend/route"
	"fmt"
	"io"
	"os"
	"time"

	"github.com/gin-gonic/gin"
)

func NewServer() *gin.Engine {
	app := gin.New()
	SetupWriteLogFile()
	app.Use(gin.Recovery())
	app.Use(middlewares.Logger())
	app = route.SetRoute(app)
	return app
}

func SetupWriteLogFile() {
	f, err := os.OpenFile("log/server.log", os.O_APPEND|os.O_CREATE|os.O_RDWR, 0777)
	if err != nil {
		fmt.Printf("[SetupWriteLogger] %s\n", err.Error())
	}
	unitLogger := "\n--------- GoServer Start at " + time.Now().Format("2006/01/02 15:04:05") + " ---------\n"
	f.WriteString(unitLogger)
	gin.DefaultWriter = io.MultiWriter(os.Stdout, f)
	WriteLogErrorFile()
}

//WriteLogError return nil
func WriteLogErrorFile() {
	f, err := os.OpenFile("log/error.log", os.O_APPEND|os.O_CREATE|os.O_RDWR, 0777)
	if err != nil {
		fmt.Printf("[SetupWriteLogger] %s\n", err.Error())
	}

	gin.DefaultErrorWriter = io.MultiWriter(os.Stdout, f)
}
