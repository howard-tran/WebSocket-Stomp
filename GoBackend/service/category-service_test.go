package service_test

import (
	"GoBackend/entity"
	"GoBackend/service"
	"testing"
)

func test(t *testing.T) {
	t.Log("vmdfvdfv")
	ca, err := service.NewCategoryService()

	if err != nil {
		t.Errorf("NewCategoryServiceError:%s\n", err.Error())
	}
	pro := []entity.PropertionCategoryEntity{
		entity.PropertionCategoryEntity{
			Key: "size",
			Value: []string{
				"X",
				"XL",
				"M",
			},
		},
		entity.PropertionCategoryEntity{
			Key: "color",
			Value: []string{
				"red",
				"blu",
			},
		},
		entity.PropertionCategoryEntity{
			Key:   "brand",
			Value: []string{},
		},
	}

	imgs := []entity.ImageEntity{
		entity.ImageEntity{
			Link: "dtryuvbijnl",
			Alt:  "hello",
		},
		entity.ImageEntity{
			Link: "324234",
			Alt:  "hello Minh",
		},
		entity.ImageEntity{
			Link: "dtrycsdcsdcsnl",
			Alt:  "hello Ngoc",
		},
	}

	enti := entity.CategoryEntity{
		Name:       "haha",
		Path:       "paht-csdc",
		Properties: pro,
		Avatar:     "etdrytufygiuhoi.png",
		Image:      imgs,
	}
	_, err = ca.CreateCategory(enti)

	if err != nil {
		t.Errorf("CreateCategory:%s\n", err.Error())
	}

	k, err := ca.LoadCategory()

	if err != nil {
		t.Errorf("LoadCategory Error:%s\n", err.Error())
	}
	if len(k) == 0 {
		t.Errorf("LoadCategory Count:%s\n", err.Error())
	}
}
