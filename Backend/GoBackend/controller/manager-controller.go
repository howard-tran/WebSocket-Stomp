package controller

import "GoBackend/service"

var categoryservice service.CategoryService

func InitController() error {
	c, err := service.NewCategoryService()
	if err != nil {
		return err
	}
	categoryservice = c

	return nil
}
