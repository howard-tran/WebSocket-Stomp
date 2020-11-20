package route

import (
	"GoBackend/controller"

	"github.com/gin-gonic/gin"
)

func SetRoute(app *gin.Engine) *gin.Engine {
	RouteAccount(app)
	RouteCategory(app)
	RouteTest(app)
	return app
}

func RouteAccount(app *gin.Engine) {
	app.POST("api/account/login", controller.LoginHandle)

	app.POST("api/account/signup", controller.SignupHandle)

	app.POST("api/account/sendsms", controller.ConfirmTelbySMS)

	app.POST("api/account/checkkeycode", controller.CheckTelbySMS)
}

func RouteCategory(app *gin.Engine) {
	app.GET("/category/", controller.LoadCategory)
	app.POST("/category/", controller.CreateCategory)
}

func RouteTest(app *gin.Engine) {
	app.GET(
		"/",
		func(ctx *gin.Context) {
			ctx.JSON(200, gin.H{"active": "true"})
		})
}
