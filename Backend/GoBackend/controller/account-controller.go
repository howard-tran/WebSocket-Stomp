package controller

import (
	"GoBackend/entity"
	"GoBackend/service"
	mongodbservice "GoBackend/service/repository-service"
	"GoBackend/utility"
	"fmt"
	"net/http"
	"regexp"
	"time"

	"github.com/gin-gonic/gin"
	"gopkg.in/mgo.v2/bson"
)

type KeyCodeTel struct {
	Time time.Time `json:"time" bson:"time"`
	Tel  string    `json:"tel" bson:"tel"`
	Key  string    `json:"key" bson:"key"`
}

func LoginHandle(ctx *gin.Context) {
	var account entity.AccountEntity
	ctx.BindJSON(&account)
	fmt.Println(account)
	sec, err := mongodbservice.NewDBService()
	if err != nil {
		msg := fmt.Sprintf("[ERROR] Database connect faile: %s", err.Error())
		fmt.Println(msg)
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": msg})
		return
	}

	c := sec.GetDatabase(utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct).NAME_DATABASE).C("Account")

	query := c.Find(bson.M{"username": account.Username, "password": account.Password})

	if n, _ := query.Count(); n == 1 {
		serviceSecure := service.NewJwtService()

		token := serviceSecure.GenerationToken(account.Username, account.Password)
		ctx.JSON(http.StatusOK, service.CreateMsgSuccessJsonResponse(gin.H{"token": token})) //gin.H{"token": token})
	} else {
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusUnauthorized, "Infomation login incorrect"))
	}
}

func SignupHandle(ctx *gin.Context) {
	var account entity.AccountEntity
	ctx.BindJSON(&account)
	sec, err := mongodbservice.NewDBService()
	if err != nil {
		msg := fmt.Sprintf("[ERROR] Database connect faile: %s", err.Error())
		fmt.Println(msg)
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": msg})
		return
	}

	c := sec.GetDatabase(utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct).NAME_DATABASE).C("Account")

	query := c.Find(bson.M{"username": account.Username})

	if n, _ := query.Count(); n >= 1 {
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusConflict, "Username already exists"))
		return
	}
	account.ID = bson.NewObjectId()
	err = c.Insert(&account)

	if err != nil {
		ctx.JSON(http.StatusOK, service.CreateMsgErrorJsonResponse(http.StatusConflict, err.Error()))
	} else {
		ctx.JSON(http.StatusOK, service.CreateMsgSuccessJsonResponse(gin.H{"msg": "Account created"}))
	}
}

func ConfirmTelbySMS(ctx *gin.Context) {
	var account entity.AccountEntity
	ctx.BindJSON(&account)
	sec, err := mongodbservice.NewDBService()
	if err != nil {
		msg := fmt.Sprintf("[ERROR] Database connect faile: %s", err.Error())
		fmt.Println(msg)
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": msg})
		return
	}
	isTel, _ := regexp.Match(`([\d\(][\(\)\s\.\-\d]{9,11}\d)`, []byte(account.Tel))

	if isTel == false {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": "Telephone wrong!!!"})
		return
	}

	c := sec.GetSession().DB(utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct).NAME_DATABASE).C("KeyCodeTel")

	keyCodeTel := KeyCodeTel{Tel: account.Tel, Time: time.Now(), Key: utility.GenerateKeycode()}

	if n, _ := c.Find(bson.M{"tel": account.Tel}).Count(); n > 5 {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": "You have been limited to the number of submissions!!!"})
		return
	}

	err = service.SendKeycode(keyCodeTel.Tel, keyCodeTel.Key)

	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": "Server too load. Please try again!!!"})
		return
	}

	err = c.Insert(&keyCodeTel)
	if err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": err})
	} else {
		ctx.JSON(200, gin.H{"msg": "Send keycode success"})
	}
}

func CheckTelbySMS(ctx *gin.Context) {
	var account entity.AccountEntity
	ctx.BindJSON(&account)
	sec, err := mongodbservice.NewDBService()
	if err != nil {
		msg := fmt.Sprintf("[ERROR] Database connect faile: %s", err.Error())
		fmt.Println(msg)
		ctx.JSON(http.StatusBadRequest, gin.H{"msg": msg})
		return
	}

	c := sec.GetSession().DB(utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct).NAME_DATABASE).C("KeyCodeTel")

	var keyCodeTel KeyCodeTel
	c.Find(bson.M{"tel": account.Tel, "key": account.Keycode}).One(&keyCodeTel)
	fmt.Println(keyCodeTel)
	if keyCodeTel == (KeyCodeTel{}) {
		ctx.JSON(http.StatusUnauthorized, gin.H{"msg": "Keycode wrong or expired!!!"})
		return
	}
	err = c.Remove(keyCodeTel)
	if err != nil {
		fmt.Errorf("%s\n", err)
	}
	ctx.JSON(http.StatusOK, gin.H{"msg": "Confirm tel success"})
}
