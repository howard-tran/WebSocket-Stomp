package api

import (
	"github.com/gin-gonic/gin"
)

func SetRoute(app *gin.Engine) *gin.Engine {
	RouteUpload(app)
	RouteDownload(app)
	RouteTest(app)
	return app
}

func RouteUpload(app *gin.Engine) {
	app.POST("/upload", UploadFiles)
	//app.POST("/uploads", UploadFiles)
}

func RouteDownload(app *gin.Engine) {
	app.GET("cdn/:filename", DownloadFile)
}

func RouteTest(app *gin.Engine) {
	//
	app.Static("/control", "./public")
	// app.GET(
	// 	"/",
	// 	func(ctx *gin.Context) {
	// 		ctx.HTML(200, "./public/index.html", []string{"a", "b", "c"})
	// 	})
}
