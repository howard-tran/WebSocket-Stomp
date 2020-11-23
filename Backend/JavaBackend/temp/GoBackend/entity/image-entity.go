package entity

type ImageEntity struct {
	Link string `json:"link" bson:"link"`
	Alt  string `json:"alt" bson:"alt"`
}
