package service

import (
	"GoBackend/entity"
	mongodbservice "GoBackend/service/repository-service"
	"GoBackend/utility"
	"fmt"

	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

const CategoryCollection = "Category"

type CategoryService interface {
	CreateCategory(enti entity.CategoryEntity) (bson.ObjectId, error)
	LoadCategory() ([]entity.CategoryEntity, error)
}

type categoryService struct {
	collection *mgo.Collection
}

func NewCategoryService() (CategoryService, error) {
	sec, err := mongodbservice.NewDBService()
	if err != nil {
		msg := fmt.Sprintf("[ERROR] Database connect faile: %s", err.Error())
		fmt.Println(msg)
		return nil, err
	}
	return &categoryService{
		collection: sec.GetDatabase(utility.GetConfigServerbyKey(utility.Database).(utility.DatabaseStruct).NAME_DATABASE).C(CategoryCollection),
	}, nil
}

func (c *categoryService) CreateCategory(enti entity.CategoryEntity) (bson.ObjectId, error) {
	_id := bson.NewObjectId()
	enti.ID = _id
	err := c.collection.Insert(&enti)

	if enti.Lever == "" {
		enti.Lever = "root"
	}
	if err != nil {
		return bson.NewObjectId(), err
	}
	return _id, nil
}

func (c *categoryService) LoadCategory() ([]entity.CategoryEntity, error) {

	var result []entity.CategoryEntity
	err := c.collection.Find(bson.M{}).All(&result)

	if err != nil {
		return nil, err
	}
	return result, nil
}
