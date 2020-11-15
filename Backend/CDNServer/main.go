package main

import (
	"CDNServer/api"
	"CDNServer/middlewares"
	"fmt"
	"io"
	"os"
	"time"
	"github.com/gin-gonic/gin"
	cors "github.com/rs/cors/wrapper/gin"
)

func main() {
	app := gin.New()
	
	gin.SetMode(gin.ReleaseMode)
	//mutilogger
	SetupWriteLogFile()
	app.Use(cors.Default())
	app.Use(gin.Recovery())
	app.Use(middlewares.Logger())

	//max size request transition
	app.MaxMultipartMemory = 8 << 20 // 8 MiB
	api.SetRoute(app)

	app.Run()
}

func SetupWriteLogFile() {
	if _, err := os.Stat("log"); os.IsNotExist(err) {
		if err := os.MkdirAll("log", os.ModePerm); err != nil {
			fmt.Printf("[CreateFile] Create dir failed: %s\n", err.Error())
		}
	}
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
