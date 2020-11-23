package entity

import "gopkg.in/mgo.v2/bson"

type AccountEntity struct {
	ID       bson.ObjectId `json:"_id" bson:"_id"`
	Username string        `json:"username"`
	Password string        `json:"password"`
	Tel      string        `json:"tel"`
	Keycode  string        `json:"key"`
}
