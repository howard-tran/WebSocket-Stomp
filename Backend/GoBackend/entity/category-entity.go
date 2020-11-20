package entity

import "gopkg.in/mgo.v2/bson"

type CategoryEntity struct {
	ID         bson.ObjectId              `json:"_id" bson:"_id"`
	Name       string                     `json:"name" bson:"name"`
	Path       string                     `json:"path" bson:"path"`
	Properties []PropertionCategoryEntity `json:"properties" bson:"properties"`
	Lever      string                     `json:"lever" bson:"lever"`
	Avatar     ImageEntity                `json:"avatar" bson:"avatar"`
	Image      []ImageEntity              `json:"image" bson:"image"`
}

type PropertionCategoryEntity struct {
	Key   string   `json:"key" bson:"key"`
	Value []string `json:"value" bson:"value"`
}
