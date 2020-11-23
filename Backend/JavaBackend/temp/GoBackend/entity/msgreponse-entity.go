package entity

type MsgReponseErrorEntity struct {
	Status int    `json:"status"`
	Error  string `json:"error"`
}
type MsgReponseSuccessEntity struct {
	Status int         `json:"status"`
	Data   interface{} `json:"data"`
}
