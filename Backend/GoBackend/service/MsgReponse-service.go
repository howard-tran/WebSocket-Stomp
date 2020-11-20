package service

import (
	"GoBackend/entity"
)

func CreateMsgErrorJsonResponse(statuscode int, msg string) interface{} {
	result := entity.MsgReponseErrorEntity{
		Status: statuscode,
		Error:  msg,
	}
	//b, _ := json.Marshal(result)
	//return string(b)
	return result
}

func CreateMsgSuccessJsonResponse(datajson interface{}) interface{} {
	result := entity.MsgReponseSuccessEntity{
		Status: 200,
		Data:   datajson,
	}
	// b, _ := json.Marshal(result)
	// return string(b)
	return result
}
