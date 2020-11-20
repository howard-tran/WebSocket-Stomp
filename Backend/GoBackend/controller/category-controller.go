package controller

import (
	"GoBackend/entity"
	"GoBackend/service"
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func LoadCategory(ctx *gin.Context) {
	k, err := categoryservice.LoadCategory()

	if err != nil {
		fmt.Printf("[LoadCategory] Not loadding: %s\n", err.Error())
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusExpectationFailed, "Load error: "+err.Error()))
		return
	}
	ctx.JSON(http.StatusOK, service.CreateMsgSuccessJsonResponse(k))
}
func CreateCategory(ctx *gin.Context) {
	var enti entity.CategoryEntity

	err := ctx.BindJSON(&enti)

	if err != nil {
		fmt.Printf("[CreateCategory] Map data failre: %s\n", err.Error())
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusBadRequest, "Map data failre"))
		return
	}
	//fmt.Println(enti)

	id, err := categoryservice.CreateCategory(enti)

	if err != nil {
		fmt.Printf("[CreateCategory] Not loadding: %s\n", err.Error())
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusBadRequest, "Create error: "+err.Error()))
		return
	}
	ctx.JSON(http.StatusOK, service.CreateMsgSuccessJsonResponse(gin.H{"_id": id}))
}
